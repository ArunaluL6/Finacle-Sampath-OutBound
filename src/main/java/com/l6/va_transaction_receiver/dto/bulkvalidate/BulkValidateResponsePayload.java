package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkValidateResponsePayload {
    private List<VaNumberDetail> vaNumberDetails;

    public List<VaNumberDetail> getVaNumberDetails() { return vaNumberDetails; }
    public void setVaNumberDetails(List<VaNumberDetail> v) { this.vaNumberDetails = v; }
}
