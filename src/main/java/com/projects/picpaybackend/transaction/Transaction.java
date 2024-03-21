package com.projects.picpaybackend.transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("TRANSACTIONS")
public record Transaction(
    @Id Long id,
    Long payer,
    Long payee,
    BigDecimal value,
    @CreatedDate LocalDateTime createdAt) {


    public Transaction {
	value = value.setScale(2);
    }

    public Transaction(TransactionRequestDTO dto) {
	this(dto.id(), dto.payer(), dto.payee(), dto.value(), dto.createdAt());
    }
}
