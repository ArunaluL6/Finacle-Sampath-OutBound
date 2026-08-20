package com.l6.va_transaction_receiver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private Context context;
    // Spec response payload is an empty object {} on both success and failure.
    private Map<String, Object> payload = new HashMap<>();

    public Context getContext() { return context; }
    public void setContext(Context v) { this.context = v; }
    public Map<String, Object> getPayload() { return payload; }
    public void setPayload(Map<String, Object> v) { this.payload = v; }
}
