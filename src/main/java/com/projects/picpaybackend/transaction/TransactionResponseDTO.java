package com.projects.picpaybackend.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record TransactionResponseDTO(
    Long payer,
    Long payee,
    BigDecimal value) {


    public TransactionResponseDTO(Transaction entity) {
	this(entity.payer(), entity.payee(), entity.value());
    }

    public static List<TransactionResponseDTO> toList(List<Transaction> transactionList) {
	return transactionList.stream()
			      .map(e -> new TransactionResponseDTO(e.payer(), e.payee(), e.value()))
			      .collect(Collectors.toList());
    }
}
