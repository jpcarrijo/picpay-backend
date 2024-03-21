package com.projects.picpaybackend.notification;

import com.projects.picpaybackend.transaction.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {


    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    final NotificationProducer notificationProducer;


    public void notify(TransactionResponseDTO transactionResponseDTO) {
	LOGGER.info("notifying transaction {}...", transactionResponseDTO);

	notificationProducer.sendNotification(transactionResponseDTO);
    }
}
