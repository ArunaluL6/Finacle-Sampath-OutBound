package com.l6.va_transaction_receiver.repository;

import com.l6.va_transaction_receiver.dto.TransactionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRecordRepository extends MongoRepository<TransactionRecord, String> {
}
