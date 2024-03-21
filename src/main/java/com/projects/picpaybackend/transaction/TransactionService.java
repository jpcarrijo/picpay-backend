package com.projects.picpaybackend.transaction;

import com.projects.picpaybackend.authorization.AuthorizationService;
import com.projects.picpaybackend.notification.NotificationService;
import com.projects.picpaybackend.wallet.WalletRepository;
import com.projects.picpaybackend.wallet.WalletType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    final TransactionRepository transactionRepository;
    final WalletRepository walletRepository;
    final AuthorizationService authorizationService;
    final NotificationService notificationService;


    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
	validate(transactionRequestDTO);

	var date = LocalDateTime.now();
	var transaction = new Transaction(
	    transactionRequestDTO.id(),
	    transactionRequestDTO.payer(),
	    transactionRequestDTO.payee(),
	    transactionRequestDTO.value(),
	    date);
	var newTransaction = transactionRepository.save(transaction);

	var walletPayer = walletRepository.findById(transactionRequestDTO.payer());
	var walletPayee = walletRepository.findById(transactionRequestDTO.payee());
	walletPayer.map(e -> e.debit(transactionRequestDTO.value())).ifPresent(walletRepository::save);
	walletPayee.map(e -> e.credit(transactionRequestDTO.value())).ifPresent(walletRepository::save);

	var transactionResponseDTO = new TransactionResponseDTO(newTransaction);
	authorizationService.authorize(transactionResponseDTO);

	notificationService.notify(transactionResponseDTO);

	return transactionResponseDTO;
    }

    public List<TransactionResponseDTO> list() {
	return TransactionResponseDTO.toList(transactionRepository.findAll());
    }

    private void validate(TransactionRequestDTO transactionRequestDTO) {

	walletRepository.findById(transactionRequestDTO.payee())
			.map(payee -> walletRepository.findById(transactionRequestDTO.payer())
						      .map(
							  payer -> payer.type() == WalletType.COMMON.getValue() &&
								       payer.balance().compareTo(transactionRequestDTO.value()) >= 0 &&
								       !payer.id().equals(transactionRequestDTO.payee()) ? true : null)
						      .orElseThrow(() -> new InvalidTransactionException(
							  "Invalid transaction - " + transactionRequestDTO)))
			.orElseThrow(() -> new InvalidTransactionException(
			    "Invalid transaction - " + transactionRequestDTO));
    }
}
