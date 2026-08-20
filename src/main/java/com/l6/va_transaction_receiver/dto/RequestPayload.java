package com.l6.va_transaction_receiver.dto;

import java.util.List;

public class RequestPayload {
    private String batchID;
    private List<Transaction> transactions;

    public String getBatchID() { return batchID; }
    public void setBatchID(String v) { this.batchID = v; }
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> v) { this.transactions = v; }
}
