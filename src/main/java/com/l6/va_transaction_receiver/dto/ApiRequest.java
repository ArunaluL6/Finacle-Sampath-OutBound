package com.l6.va_transaction_receiver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequest {
    private Context context;
    private RequestPayload payload;

    public Context getContext() { return context; }
    public void setContext(Context v) { this.context = v; }
    public RequestPayload getPayload() { return payload; }
    public void setPayload(RequestPayload v) { this.payload = v; }
}
