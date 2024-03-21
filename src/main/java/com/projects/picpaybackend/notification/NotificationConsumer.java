package com.projects.picpaybackend.notification;

import com.projects.picpaybackend.transaction.TransactionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class NotificationConsumer {


    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-backend")
    public void receiveNotification(TransactionResponseDTO transactionResponseDTO) {
	RestClient restClient = RestClient.create("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6");
	LOGGER.info("notifying transaction {}...", transactionResponseDTO);

	var response = restClient.get().retrieve().toEntity(Notification.class);

	if (response.getStatusCode().isError() || !Objects.requireNonNull(response.getBody()).message())
	    throw new NotificationException("Error notifying transaction " + transactionResponseDTO);

	LOGGER.info("notification has been sent {}...", response.getBody());
    }
}
