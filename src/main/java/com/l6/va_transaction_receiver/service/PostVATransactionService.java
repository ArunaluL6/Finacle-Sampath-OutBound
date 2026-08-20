package com.l6.va_transaction_receiver.service;

import com.l6.va_transaction_receiver.dto.ApiRequest;
import com.l6.va_transaction_receiver.dto.ApiResponse;
import com.l6.va_transaction_receiver.dto.Context;
import com.l6.va_transaction_receiver.dto.Leg;
import com.l6.va_transaction_receiver.dto.RequestPayload;
import com.l6.va_transaction_receiver.dto.Transaction;
import com.l6.va_transaction_receiver.dto.TransactionRecord;
import com.l6.va_transaction_receiver.repository.TransactionRecordRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class PostVATransactionService {

    private static final Logger log = LoggerFactory.getLogger(PostVATransactionService.class);

    private final TransactionRecordRepository repository;
    private final TransactionBroadcastService broadcastService;

    public PostVATransactionService(TransactionRecordRepository repository,
                                    TransactionBroadcastService broadcastService) {
        this.repository = repository;
        this.broadcastService = broadcastService;
    }

    public ApiResponse process(ApiRequest request) {
        Context reqCtx = request.getContext();

        // echo back the identifying context fields
        Context resCtx = new Context();
        if (reqCtx != null) {
            resCtx.setInterfaceId(reqCtx.getInterfaceId());
            resCtx.setChannelId(reqCtx.getChannelId());
            resCtx.setChannelRefNumber(reqCtx.getChannelRefNumber());
        }

        ApiResponse response = new ApiResponse();
        response.setContext(resCtx);

        RequestPayload payload = request.getPayload();
        if (payload == null || payload.getTransactions() == null || payload.getTransactions().isEmpty()) {
            return fail(resCtx, response, null, null, "ERRPMSG00010", "No transaction found in request");
        }

        Transaction txn = payload.getTransactions().get(0);
        List<Leg> legs = txn.getLegs();
        if (legs == null || legs.isEmpty()) {
            return fail(resCtx, response, payload, txn, "ERRPMSG00011", "No legs found in transaction");
        }

        BigDecimal debitTotal = BigDecimal.ZERO;
        BigDecimal creditTotal = BigDecimal.ZERO;

        for (Leg leg : legs) {
            if (leg.getAccountNumber() == null || leg.getAccountNumber().trim().isEmpty()) {
                return fail(resCtx, response, payload, txn, "ERRPMSG0007", "AccountNumber is not valid");
            }
            BigDecimal amt;
            try {
                amt = new BigDecimal(leg.getAmountAcntCCY() == null ? "0" : leg.getAmountAcntCCY().trim());
            } catch (NumberFormatException e) {
                return fail(resCtx, response, payload, txn, "ERRPMSG00012", "Invalid amount : " + leg.getAmountAcntCCY());
            }
            if ("D".equalsIgnoreCase(leg.getDebitCreditFlg())) {
                debitTotal = debitTotal.add(amt);
            } else if ("C".equalsIgnoreCase(leg.getDebitCreditFlg())) {
                creditTotal = creditTotal.add(amt);
            } else {
                return fail(resCtx, response, payload, txn, "ERRPMSG00013", "Invalid debitCreditFlg : " + leg.getDebitCreditFlg());
            }
        }

        if (debitTotal.compareTo(creditTotal) != 0) {
            log.warn("Debit total {} != Credit total {}", debitTotal, creditTotal);
            return fail(resCtx, response, payload, txn, "ERRPMSG00014", "Both Debit And Credit Amount Should Match");
        }

        log.info("Accepted VA transaction {} (batch {}), DR=CR={}",
                txn.getTransactionID(), payload.getBatchID(), debitTotal);

        resCtx.setStatus("SUCCESS");
        resCtx.setErrorCode("000");
        resCtx.setErrorMessage("Success");

        saveAndBroadcast(payload, txn, legs, debitTotal, creditTotal, "SUCCESS", "000", "Success");

        return response;
    }

    private ApiResponse fail(Context resCtx, ApiResponse response, RequestPayload payload, Transaction txn,
                             String code, String msg) {
        log.warn("Rejecting VA transaction : {} - {}", code, msg);
        resCtx.setStatus("FAILURE");
        resCtx.setErrorCode(code);
        resCtx.setErrorMessage(msg);
        response.setContext(resCtx);

        List<Leg> legs = txn != null ? txn.getLegs() : null;
        saveAndBroadcast(payload, txn, legs, null, null, "FAILURE", code, msg);

        return response;
    }

    /**
     * Persists this attempt (success or failure) to MongoDB and immediately
     * pushes it out over SSE so the React dashboard updates in real time.
     */
    private void saveAndBroadcast(RequestPayload payload, Transaction txn, List<Leg> legs,
                                  BigDecimal debitTotal, BigDecimal creditTotal,
                                  String status, String errorCode, String errorMessage) {
        TransactionRecord record = new TransactionRecord();
        record.setReceivedAt(Instant.now());
        record.setBatchID(payload != null ? payload.getBatchID() : null);
        if (txn != null) {
            record.setTransactionID(txn.getTransactionID());
            record.setTransactionDate(txn.getTransactionDate());
            record.setBankEntityID(txn.getBankEntityID());
            record.setTxnCode(txn.getTxnCode());
        }
        record.setLegs(legs);
        record.setDebitTotal(debitTotal);
        record.setCreditTotal(creditTotal);
        record.setStatus(status);
        record.setErrorCode(errorCode);
        record.setErrorMessage(errorMessage);

        try {
            TransactionRecord saved = repository.save(record);
            broadcastService.broadcast(saved);
        } catch (Exception e) {
            // Never let a Mongo/SSE problem break the Finacle response contract -
            // the outbound API must still get its ApiResponse either way.
            log.error("Failed to save/broadcast transaction record", e);
        }
    }
}
