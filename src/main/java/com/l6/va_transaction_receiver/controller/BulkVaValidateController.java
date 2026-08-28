package com.l6.va_transaction_receiver.controller;

import com.l6.va_transaction_receiver.dto.Context;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateRequest;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateResponse;
import com.l6.va_transaction_receiver.dto.bulkvalidate.BulkValidateResponsePayload;
import com.l6.va_transaction_receiver.service.BulkVaValidateService;

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

import java.util.ArrayList;

/**
 * Mock of the Virtual Account Management "Bulk VA Validate" API that
 * com.sampath.ci.fioutbound.custom.BulkVAValidate (called from l6_BulkVAValidate.scr)
 * invokes outbound from Finacle. Matches the URL path used in the .scr:
 *   http://<vam_host>:<vam_port>/virtualaccountsbo/v1/virtualaccounts/vavalidatebulk
 */
@RestController
@RequestMapping("/virtualaccountsbo/v1/virtualaccounts/vavalidatebulk")
public class BulkVaValidateController {

    private static final Logger log = LoggerFactory.getLogger(BulkVaValidateController.class);

    private final BulkVaValidateService service;

    // reuses the same shared-secret pair as PostVATransactionController - the .scr script
    // sends Username/API_KEY "finacle" / "changeme" by default, matching application.yaml
    @Value("${finacle.outbound.expected-username}")
    private String expectedUsername;

    @Value("${finacle.outbound.expected-api-key}")
    private String expectedApiKey;

    public BulkVaValidateController(BulkVaValidateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BulkValidateResponse> handle(
            @RequestHeader(value = "USER_NAME", required = false) String userName,
            @RequestHeader(value = "API_KEY", required = false) String apiKey,
            @RequestBody BulkValidateRequest request) {

        log.info("Received Bulk VA Validate request");

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

            BulkValidateResponse authResp = new BulkValidateResponse();
            authResp.setContext(ctx);
            BulkValidateResponsePayload payload = new BulkValidateResponsePayload();
            payload.setVaNumberDetails(new ArrayList<>());
            authResp.setPayload(payload);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResp);
        }

        try {
            BulkValidateResponse response = service.process(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing bulk VA validate request", e);
            Context ctx = new Context();
            ctx.setStatus("FAILURE");
            ctx.setErrorCode("ERR500");
            ctx.setErrorMessage("Internal error : " + e.getMessage());

            BulkValidateResponse errResp = new BulkValidateResponse();
            errResp.setContext(ctx);
            BulkValidateResponsePayload payload = new BulkValidateResponsePayload();
            payload.setVaNumberDetails(new ArrayList<>());
            errResp.setPayload(payload);

            return ResponseEntity.ok(errResp);
        }
    }
}
