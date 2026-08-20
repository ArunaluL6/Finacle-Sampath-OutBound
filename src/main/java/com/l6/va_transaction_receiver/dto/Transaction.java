package com.l6.va_transaction_receiver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Transaction {
    private String bankEntityID;
    private String txnCode;
    private String transactionID;
    private String transactionDate;
    private String valueDate;
    private String fxRate;
    private String customerTranRefNumber;
    private String bankTranRefNumber;
    private String instrumentType;
    private String instrumentNumber;
    private String mandateRefNumber;
    private String destinationBicCode;
    private String txnBicCode;
    // Spelling kept EXACTLY as the VA Transaction API spec (typo is consistent in the spec).
    private String txnRountingNumber;
    private String txnBank;
    private String txnBranch;
    private String tellerID;

    // Spec field is capitalised "Sequence".
    @JsonProperty("Sequence")
    private String sequence;

    private String hostDate;
    private String branchcode;
    private String tranProdCode;
    private String vendorCode;
    private String pickupPointCode;

    private List<Leg> legs;

    public String getBankEntityID() { return bankEntityID; }
    public void setBankEntityID(String v) { this.bankEntityID = v; }
    public String getTxnCode() { return txnCode; }
    public void setTxnCode(String v) { this.txnCode = v; }
    public String getTransactionID() { return transactionID; }
    public void setTransactionID(String v) { this.transactionID = v; }
    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String v) { this.transactionDate = v; }
    public String getValueDate() { return valueDate; }
    public void setValueDate(String v) { this.valueDate = v; }
    public String getFxRate() { return fxRate; }
    public void setFxRate(String v) { this.fxRate = v; }
    public String getCustomerTranRefNumber() { return customerTranRefNumber; }
    public void setCustomerTranRefNumber(String v) { this.customerTranRefNumber = v; }
    public String getBankTranRefNumber() { return bankTranRefNumber; }
    public void setBankTranRefNumber(String v) { this.bankTranRefNumber = v; }
    public String getInstrumentType() { return instrumentType; }
    public void setInstrumentType(String v) { this.instrumentType = v; }
    public String getInstrumentNumber() { return instrumentNumber; }
    public void setInstrumentNumber(String v) { this.instrumentNumber = v; }
    public String getMandateRefNumber() { return mandateRefNumber; }
    public void setMandateRefNumber(String v) { this.mandateRefNumber = v; }
    public String getDestinationBicCode() { return destinationBicCode; }
    public void setDestinationBicCode(String v) { this.destinationBicCode = v; }
    public String getTxnBicCode() { return txnBicCode; }
    public void setTxnBicCode(String v) { this.txnBicCode = v; }
    public String getTxnRountingNumber() { return txnRountingNumber; }
    public void setTxnRountingNumber(String v) { this.txnRountingNumber = v; }
    public String getTxnBank() { return txnBank; }
    public void setTxnBank(String v) { this.txnBank = v; }
    public String getTxnBranch() { return txnBranch; }
    public void setTxnBranch(String v) { this.txnBranch = v; }
    public String getTellerID() { return tellerID; }
    public void setTellerID(String v) { this.tellerID = v; }
    public String getSequence() { return sequence; }
    public void setSequence(String v) { this.sequence = v; }
    public String getHostDate() { return hostDate; }
    public void setHostDate(String v) { this.hostDate = v; }
    public String getBranchcode() { return branchcode; }
    public void setBranchcode(String v) { this.branchcode = v; }
    public String getTranProdCode() { return tranProdCode; }
    public void setTranProdCode(String v) { this.tranProdCode = v; }
    public String getVendorCode() { return vendorCode; }
    public void setVendorCode(String v) { this.vendorCode = v; }
    public String getPickupPointCode() { return pickupPointCode; }
    public void setPickupPointCode(String v) { this.pickupPointCode = v; }
    public List<Leg> getLegs() { return legs; }
    public void setLegs(List<Leg> v) { this.legs = v; }
}
