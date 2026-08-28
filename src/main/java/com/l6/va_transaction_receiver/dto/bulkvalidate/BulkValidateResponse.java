package com.l6.va_transaction_receiver.dto.bulkvalidate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.l6.va_transaction_receiver.dto.Context;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkValidateResponse {
    private Context context;
    private BulkValidateResponsePayload payload;

    public Context getContext() { return context; }
    public void setContext(Context v) { this.context = v; }
    public BulkValidateResponsePayload getPayload() { return payload; }
    public void setPayload(BulkValidateResponsePayload v) { this.payload = v; }
}
