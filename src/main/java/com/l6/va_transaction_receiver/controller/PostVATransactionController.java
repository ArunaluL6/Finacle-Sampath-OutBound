package com.l6.va_transaction_receiver.controller;

import com.l6.va_transaction_receiver.dto.ApiRequest;
import com.l6.va_transaction_receiver.dto.ApiResponse;
import com.l6.va_transaction_receiver.dto.Context;
import com.l6.va_transaction_receiver.service.PostVATransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/olive/publisher/vaValidate/ledger")
public class PostVATransactionController {

    private static final Logger log = LoggerFactory.getLogger(PostVATransactionController.class);

    private final PostVATransactionService service;

    @Value("${finacle.outbound.expected-username}")
    private String expectedUsername;

    @Value("${finacle.outbound.expected-api-key}")
    private String expectedApiKey;

    public PostVATransactionController(PostVATransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> handle(
            @RequestHeader(value = "USER_NAME", required = false) String userName,
            @RequestHeader(value = "API_KEY", required = false) String apiKey,
            @RequestBody ApiRequest request) {

        log.info("Received VA transaction request");

        // shared-secret auth
        if (!expectedUsername.equals(userName) || !expectedApiKey.equals(apiKey)) {
            log.warn("Auth failed. USER_NAME={}", userName);
            Context ctx = new Context();
            if (request != null && request.getContext() != null) {
                ctx.setInterfaceId(request.getContext().getInterfaceId());
                ctx.setChannelId(request.getContext().getChannelId());
                ctx.setChannelRefNumber(request.getContext().getChannelRefNumber());
            }
            ctx.setStatus("FAILURE");
            ctx.setErrorCode("AUTH001");
            ctx.setErrorMessage("Authentication failed");
            ApiResponse authResp = new ApiResponse();
            authResp.setContext(ctx);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResp);
        }

        try {
            ApiResponse response = service.process(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing request", e);
            Context ctx = new Context();
            ctx.setStatus("FAILURE");
            ctx.setErrorCode("ERR500");
            ctx.setErrorMessage("Internal error : " + e.getMessage());
            ApiResponse errResp = new ApiResponse();
            errResp.setContext(ctx);
            return ResponseEntity.ok(errResp);
        }
    }
}
