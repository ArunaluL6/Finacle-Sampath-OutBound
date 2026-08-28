package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkValidatePayload {
    private List<VaNumberRequest> vaNumbers;

    public List<VaNumberRequest> getVaNumbers() { return vaNumbers; }
    public void setVaNumbers(List<VaNumberRequest> v) { this.vaNumbers = v; }
}
