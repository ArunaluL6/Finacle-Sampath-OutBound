package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VaNumberRequest {
    private String vaNumber;
    private String transactionCurrency;
    private String clearingType;
    private String clearingMemberId;

    public String getVaNumber() { return vaNumber; }
    public void setVaNumber(String v) { this.vaNumber = v; }
    public String getTransactionCurrency() { return transactionCurrency; }
    public void setTransactionCurrency(String v) { this.transactionCurrency = v; }
    public String getClearingType() { return clearingType; }
    public void setClearingType(String v) { this.clearingType = v; }
    public String getClearingMemberId() { return clearingMemberId; }
    public void setClearingMemberId(String v) { this.clearingMemberId = v; }
}
