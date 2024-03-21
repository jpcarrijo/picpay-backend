package com.projects.picpaybackend.authorization;

import com.projects.picpaybackend.transaction.TransactionResponseDTO;
import com.projects.picpaybackend.transaction.UnauthorizedTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizationService {


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

    public void authorize(TransactionResponseDTO transactionResponseDTO) {
	RestClient restClient = RestClient.create("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc");

	LOGGER.info("authorizing transaction {}...", transactionResponseDTO);
	var response = restClient.get().retrieve().toEntity(Authorization.class);
	if (response.getStatusCode().isError() || !response.getBody().isAuthorized())
	    throw new UnauthorizedTransactionException("Unauthorized");
    }

    record Authorization(String message) {


	public boolean isAuthorized() {
	    return message.equals("Autorizado");
	}
    }
}


