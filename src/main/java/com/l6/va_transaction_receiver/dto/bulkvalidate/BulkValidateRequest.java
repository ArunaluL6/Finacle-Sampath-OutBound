package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.l6.va_transaction_receiver.dto.Context;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkValidateRequest {
    private Context context;
    private BulkValidatePayload payload;

    public Context getContext() { return context; }
    public void setContext(Context v) { this.context = v; }
    public BulkValidatePayload getPayload() { return payload; }
    public void setPayload(BulkValidatePayload v) { this.payload = v; }
}
