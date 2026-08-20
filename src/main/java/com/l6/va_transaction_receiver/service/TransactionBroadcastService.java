package com.l6.va_transaction_receiver.service;

import com.l6.va_transaction_receiver.dto.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Keeps track of every open SSE connection from the React app and pushes
 * each new TransactionRecord to all of them the moment it is saved.
 * This is what makes the dashboard update in real time without polling.
 */
@Service
public class TransactionBroadcastService {

    private static final Logger log = LoggerFactory.getLogger(TransactionBroadcastService.class);
    private static final long EMITTER_TIMEOUT_MS = 0L; // no timeout - stay open until client disconnects

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(EMITTER_TIMEOUT_MS);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        try {
            // send a hello event so the client knows the stream is live
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }
        return emitter;
    }

    public void broadcast(TransactionRecord record) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("transaction").data(record));
            } catch (IOException e) {
                log.debug("Removing dead SSE emitter: {}", e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
}
