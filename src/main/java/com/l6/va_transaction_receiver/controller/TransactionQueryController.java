package com.l6.va_transaction_receiver.controller;

import com.l6.va_transaction_receiver.dto.TransactionRecord;
import com.l6.va_transaction_receiver.repository.TransactionRecordRepository;
import com.l6.va_transaction_receiver.service.TransactionBroadcastService;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * What the React page talks to:
 *  - GET /api/transactions        -> everything saved so far (initial page load)
 *  - GET /api/transactions/stream -> Server-Sent Events, one "transaction" event
 *                                     per new document saved from that point on
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionQueryController {

    private final TransactionRecordRepository repository;
    private final TransactionBroadcastService broadcastService;

    public TransactionQueryController(TransactionRecordRepository repository,
                                      TransactionBroadcastService broadcastService) {
        this.repository = repository;
        this.broadcastService = broadcastService;
    }

    @GetMapping
    public List<TransactionRecord> all() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "receivedAt"));
    }

    @GetMapping("/stream")
    public SseEmitter stream() {
        return broadcastService.subscribe();
    }
}
