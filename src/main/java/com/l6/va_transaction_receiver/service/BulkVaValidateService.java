package com.l6.va_transaction_receiver.service;

import com.l6.va_transaction_receiver.dto.Context;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateRequest;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateResponse;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateResponsePayload;
import com.l6.va_transaction_receiver.dto.bulkvalidate.VaNumberDetail;
import com.l6.va_transaction_receiver.dto.bulkvalidate.VaNumberRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BulkVaValidateService {

    private static final Logger log = LoggerFactory.getLogger(BulkVaValidateService.class);
    private static final DateTimeFormatter EXPIRY_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BulkValidateResponse process(BulkValidateRequest request) {
        Context reqCtx = request.getContext();

        Context resCtx = new Context();
        if (reqCtx != null) {
            resCtx.setInterfaceId(reqCtx.getInterfaceId());
            resCtx.setChannelId(reqCtx.getChannelId());
            resCtx.setChannelRefNumber(reqCtx.getChannelRefNumber());
            resCtx.setBankEntityId(reqCtx.getBankEntityId());
            resCtx.setUserId(reqCtx.getUserId());
        }

        BulkValidateResponse response = new BulkValidateResponse();
        response.setContext(resCtx);

        List<VaNumberRequest> requested = (request.getPayload() == null) ? null : request.getPayload().getVaNumbers();
        if (requested == null || requested.isEmpty()) {
            resCtx.setStatus("FAILURE");
            resCtx.setErrorCode("ERRVA0001");
            resCtx.setErrorMessage("No VA numbers found in request");
            response.setPayload(emptyPayload());
            log.warn("Bulk VA validate {} rejected : no vaNumbers in payload", resCtx.getChannelRefNumber());
            return response;
        }

        List<VaNumberDetail> details = new ArrayList<>();
        int successCount = 0;

        for (VaNumberRequest vr : requested) {
            String vaNumber = vr.getVaNumber();
            VaNumberDetail d = new VaNumberDetail();
            d.setVaNumber(vaNumber);

            if (vaNumber == null || vaNumber.trim().isEmpty()) {
                d.setStatus("FAILURE");
                d.setErrorCode("ERRVA0002");
                d.setErrorMessage("vaNumber is not valid");
                details.add(d);
                continue;
            }

            populateMockDetail(d, vaNumber.trim(), vr.getTransactionCurrency());
            successCount++;
            details.add(d);
        }

        if (successCount == 0) {
            resCtx.setStatus("FAILURE");
            resCtx.setErrorCode("ERRVA0003");
            resCtx.setErrorMessage("None of the VA numbers could be validated");
        } else if (successCount < details.size()) {
            resCtx.setStatus("PARTIAL");
            resCtx.setErrorCode("000");
            resCtx.setErrorMessage("Some VA numbers could not be validated");
        } else {
            resCtx.setStatus("SUCCESS");
            resCtx.setErrorCode("000");
            resCtx.setErrorMessage("Success");
        }

        log.info("Bulk VA validate {} : requested {} succeeded {}",
                resCtx.getChannelRefNumber(), details.size(), successCount);

        BulkValidateResponsePayload payload = new BulkValidateResponsePayload();
        payload.setVaNumberDetails(details);
        response.setPayload(payload);
        return response;
    }

    /**
     * There's no real Virtual Account Management system behind this mock, so it fabricates
     * believable-but-deterministic VA master data for every vaNumber it's asked to validate.
     * Same vaNumber in -> same detail out every time, so retried/re-run Finacle chunks see a
     * stable "account" instead of new random data each call.
     */
    private void populateMockDetail(VaNumberDetail d, String vaNumber, String requestedCcy) {
        int seed = Math.abs(vaNumber.hashCode());

        d.setStatus("SUCCESS");
        d.setErrorCode("000");
        d.setErrorMessage("Success");
        d.setVaName("VA " + vaNumber);
        d.setVaIBAN("LK" + String.format("%02d", seed % 100) + "SAMP" + padLeft(vaNumber, 16));
        d.setCustomerID("CUST" + padLeft(String.valueOf(seed % 1_000_000), 6));
        d.setCustomerName("Customer " + (seed % 100000));
        d.setVaExpiryDate(LocalDate.now().plusYears(1).format(EXPIRY_FMT));
        d.setVaStatus("A");
        d.setRealAccountNumber(padLeft(String.valueOf(seed), 12));
        d.setRealAccountCurrency(firstNonBlank(requestedCcy, "LKR"));
        d.setDebitAllowed("Y");
        d.setCreditAllowed("Y");
        d.setVaAliasName("VA " + vaNumber);
        d.setParentVaNumber("");
        d.setRootVa(vaNumber);
        d.setVaType("01");
        d.setIntermediaryAccountNumber("");
        d.setRealAccountName("Customer " + (seed % 100000));
        d.setAccountCreditFlag("Y");
        d.setTransactionCurrency(firstNonBlank(requestedCcy, "LKR"));
        d.setMobileNumber("07" + String.format("%08d", seed % 100_000_000));
        d.setEmailId("customer" + (seed % 100000) + "@example.com");
        d.setRootVaBalance(BigDecimal.valueOf((seed % 500000) + 1000));
        d.setVaSelfBalance(BigDecimal.valueOf((seed % 200000) + 500));
        d.setVaLedgerBalance(BigDecimal.valueOf((seed % 200000) + 500));
        d.setVaBalance(BigDecimal.valueOf((seed % 200000) + 500));
        d.setVaLimit(BigDecimal.valueOf(1_000_000));
        d.setGuid(UUID.nameUUIDFromBytes(vaNumber.getBytes()).toString());
    }

    private static BulkValidateResponsePayload emptyPayload() {
        BulkValidateResponsePayload p = new BulkValidateResponsePayload();
        p.setVaNumberDetails(new ArrayList<>());
        return p;
    }

    private static String padLeft(String v, int len) {
        StringBuilder sb = new StringBuilder(v == null ? "" : v);
        while (sb.length() < len) sb.insert(0, '0');
        return sb.length() > len ? sb.substring(sb.length() - len) : sb.toString();
    }

    private static String firstNonBlank(String a, String b) {
        return (a == null || a.trim().isEmpty()) ? b : a;
    }
}
