package com.projects.picpaybackend.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
public class TransactionController {


    final TransactionService transactionService;

    @GetMapping
    public List<TransactionResponseDTO> list() {
	return transactionService.list();
    }

    @PostMapping
    public TransactionResponseDTO createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
	return transactionService.create(transactionRequestDTO);
    }
}
