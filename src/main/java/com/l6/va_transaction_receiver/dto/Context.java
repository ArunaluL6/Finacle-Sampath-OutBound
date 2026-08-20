package com.l6.va_transaction_receiver.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Context {
    // --- request context ---
    private String interfaceId;
    private String channelId;
    private String channelRefNumber;
    private String bankEntityId;
    private String branchCode;
    private String reqSequenceNumber;
    private String requestDate;
    private String userId;

    // --- response context ---
    private String status;        // SUCCESS / FAILURE
    private String errorCode;

    // The spec field table calls this "errorMessage"; the spec JSON sample uses "errormsg".
    // We serialize as "errormsg" (matching the sample) and accept either name on the way in.
    @JsonProperty("errormsg")
    @JsonAlias({"errorMessage"})
    private String errorMessage;

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String v) { this.interfaceId = v; }
    public String getChannelId() { return channelId; }
    public void setChannelId(String v) { this.channelId = v; }
    public String getChannelRefNumber() { return channelRefNumber; }
    public void setChannelRefNumber(String v) { this.channelRefNumber = v; }
    public String getBankEntityId() { return bankEntityId; }
    public void setBankEntityId(String v) { this.bankEntityId = v; }
    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String v) { this.branchCode = v; }
    public String getReqSequenceNumber() { return reqSequenceNumber; }
    public void setReqSequenceNumber(String v) { this.reqSequenceNumber = v; }
    public String getRequestDate() { return requestDate; }
    public void setRequestDate(String v) { this.requestDate = v; }
    public String getUserId() { return userId; }
    public void setUserId(String v) { this.userId = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String v) { this.errorCode = v; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { this.errorMessage = v; }
}
