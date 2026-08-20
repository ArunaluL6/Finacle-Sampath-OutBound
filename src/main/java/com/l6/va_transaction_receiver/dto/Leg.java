package com.l6.va_transaction_receiver.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Leg {
    private String debitCreditFlg;

    // Spec field table = "accountNumber"; spec JSON sample = "accountNumer".
    // Accept BOTH on the way in so this mock works regardless of which the sender uses.
    @JsonProperty("accountNumber")
    @JsonAlias({"accountNumer"})
    private String accountNumber;

    private String accountName;
    private String accountCCY;
    private String amountAcntCCY;
    private String tranParticular1;
    private String tranParticular2;
    private String tranRemarks;

    public String getDebitCreditFlg() { return debitCreditFlg; }
    public void setDebitCreditFlg(String v) { this.debitCreditFlg = v; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String v) { this.accountNumber = v; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String v) { this.accountName = v; }
    public String getAccountCCY() { return accountCCY; }
    public void setAccountCCY(String v) { this.accountCCY = v; }
    public String getAmountAcntCCY() { return amountAcntCCY; }
    public void setAmountAcntCCY(String v) { this.amountAcntCCY = v; }
    public String getTranParticular1() { return tranParticular1; }
    public void setTranParticular1(String v) { this.tranParticular1 = v; }
    public String getTranParticular2() { return tranParticular2; }
    public void setTranParticular2(String v) { this.tranParticular2 = v; }
    public String getTranRemarks() { return tranRemarks; }
    public void setTranRemarks(String v) { this.tranRemarks = v; }
}
