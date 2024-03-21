package com.projects.picpaybackend.notification;

import com.projects.picpaybackend.transaction.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {


    final KafkaTemplate<String, TransactionResponseDTO> kafkaTemplate;


    public void sendNotification(TransactionResponseDTO transactionResponseDTO) {
	kafkaTemplate.send("transaction-notification", transactionResponseDTO);
    }
}
