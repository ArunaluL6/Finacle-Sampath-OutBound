package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * Field names here are intentionally exact matches for the keys that
 * com.sampath.ci.fioutbound.custom.BulkVAValidate reads with optString()/optJSONObject()
 * (vaNumber, status, errorCode, errorMessage, vaName, vaIBAN, customerID, customerName,
 * vaExpiryDate, vaStatus, realAccountNumber, realAccountCurrency, debitAllowed, creditAllowed,
 * vaAliasName, parentVaNumber, rootVa, vaType, intermediaryAccountNumber, realAccountName,
 * accountCreditFlag, transactionCurrency, mobileNumber, emailId, rootVaBalance, vaSelfBalance,
 * vaLedgerBalance, vaBalance, vaLimit, guid).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaNumberDetail {
    private String vaNumber;
    private String status;        // SUCCESS / FAILURE for this individual VA
    private String errorCode;
    private String errorMessage;

    private String vaName;
    private String vaIBAN;
    private String customerID;
    private String customerName;
    private String vaExpiryDate;  // dd-MM-yyyy, matches setDate() parsing on the Finacle side
    private String vaStatus;
    private String realAccountNumber;
    private String realAccountCurrency;
    private String debitAllowed;
    private String creditAllowed;
    private String vaAliasName;
    private String parentVaNumber;
    private String rootVa;
    private String vaType;
    private String intermediaryAccountNumber;
    private String realAccountName;
    private String accountCreditFlag;
    private String transactionCurrency;
    private String mobileNumber;
    private String emailId;

    private BigDecimal rootVaBalance;
    private BigDecimal vaSelfBalance;
    private BigDecimal vaLedgerBalance;
    private BigDecimal vaBalance;
    private BigDecimal vaLimit;

    private String guid;

    public String getVaNumber() { return vaNumber; }
    public void setVaNumber(String v) { this.vaNumber = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String v) { this.errorCode = v; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }
    public String getVaName() { return vaName; }
    public void setVaName(String v) { this.vaName = v; }
    public String getVaIBAN() { return vaIBAN; }
    public void setVaIBAN(String v) { this.vaIBAN = v; }
    public String getCustomerID() { return customerID; }
    public void setCustomerID(String v) { this.customerID = v; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { this.customerName = v; }
    public String getVaExpiryDate() { return vaExpiryDate; }
    public void setVaExpiryDate(String v) { this.vaExpiryDate = v; }
    public String getVaStatus() { return vaStatus; }
    public void setVaStatus(String v) { this.vaStatus = v; }
    public String getRealAccountNumber() { return realAccountNumber; }
    public void setRealAccountNumber(String v) { this.realAccountNumber = v; }
    public String getRealAccountCurrency() { return realAccountCurrency; }
    public void setRealAccountCurrency(String v) { this.realAccountCurrency = v; }
    public String getDebitAllowed() { return debitAllowed; }
    public void setDebitAllowed(String v) { this.debitAllowed = v; }
    public String getCreditAllowed() { return creditAllowed; }
    public void setCreditAllowed(String v) { this.creditAllowed = v; }
    public String getVaAliasName() { return vaAliasName; }
    public void setVaAliasName(String v) { this.vaAliasName = v; }
    public String getParentVaNumber() { return parentVaNumber; }
    public void setParentVaNumber(String v) { this.parentVaNumber = v; }
    public String getRootVa() { return rootVa; }
    public void setRootVa(String v) { this.rootVa = v; }
    public String getVaType() { return vaType; }
    public void setVaType(String v) { this.vaType = v; }
    public String getIntermediaryAccountNumber() { return intermediaryAccountNumber; }
    public void setIntermediaryAccountNumber(String v) { this.intermediaryAccountNumber = v; }
    public String getRealAccountName() { return realAccountName; }
    public void setRealAccountName(String v) { this.realAccountName = v; }
    public String getAccountCreditFlag() { return accountCreditFlag; }
    public void setAccountCreditFlag(String v) { this.accountCreditFlag = v; }
    public String getTransactionCurrency() { return transactionCurrency; }
    public void setTransactionCurrency(String v) { this.transactionCurrency = v; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String v) { this.mobileNumber = v; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String v) { this.emailId = v; }
    public BigDecimal getRootVaBalance() { return rootVaBalance; }
    public void setRootVaBalance(BigDecimal v) { this.rootVaBalance = v; }
    public BigDecimal getVaSelfBalance() { return vaSelfBalance; }
    public void setVaSelfBalance(BigDecimal v) { this.vaSelfBalance = v; }
    public BigDecimal getVaLedgerBalance() { return vaLedgerBalance; }
    public void setVaLedgerBalance(BigDecimal v) { this.vaLedgerBalance = v; }
    public BigDecimal getVaBalance() { return vaBalance; }
    public void setVaBalance(BigDecimal v) { this.vaBalance = v; }
    public BigDecimal getVaLimit() { return vaLimit; }
    public void setVaLimit(BigDecimal v) { this.vaLimit = v; }
    public String getGuid() { return guid; }
    public void setGuid(String v) { this.guid = v; }
}
