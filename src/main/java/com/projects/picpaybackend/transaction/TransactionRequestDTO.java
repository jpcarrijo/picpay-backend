package com.projects.picpaybackend.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
    Long id,
    Long payer,
    Long payee,
    BigDecimal value,
    LocalDateTime createdAt) {
}
