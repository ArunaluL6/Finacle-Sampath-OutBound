package com.l6.va_transaction_receiver.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * What gets persisted to MongoDB (collection "va_transactions") every time
 * PostVATransactionController receives a request. One document per request,
 * success or failure, so the React dashboard has a full real-time feed.
 */
@Document(collection = "va_transactions")
public class TransactionRecord {

    @Id
    private String id;

    private Instant receivedAt;

    private String batchID;
    private String transactionID;
    private String transactionDate;
    private String bankEntityID;
    private String txnCode;

    private String status;        // SUCCESS / FAILURE
    private String errorCode;
    private String errorMessage;

    private BigDecimal debitTotal;
    private BigDecimal creditTotal;

    private List<Leg> legs;

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public Instant getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Instant v) { this.receivedAt = v; }
    public String getBatchID() { return batchID; }
    public void setBatchID(String v) { this.batchID = v; }
    public String getTransactionID() { return transactionID; }
    public void setTransactionID(String v) { this.transactionID = v; }
    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String v) { this.transactionDate = v; }
    public String getBankEntityID() { return bankEntityID; }
    public void setBankEntityID(String v) { this.bankEntityID = v; }
    public String getTxnCode() { return txnCode; }
    public void setTxnCode(String v) { this.txnCode = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String v) { this.errorCode = v; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }
    public BigDecimal getDebitTotal() { return debitTotal; }
    public void setDebitTotal(BigDecimal v) { this.debitTotal = v; }
    public BigDecimal getCreditTotal() { return creditTotal; }
    public void setCreditTotal(BigDecimal v) { this.creditTotal = v; }
    public List<Leg> getLegs() { return legs; }
    public void setLegs(List<Leg> v) { this.legs = v; }
}
