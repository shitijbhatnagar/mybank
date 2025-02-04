package com.sb.mybank.remote.service;

import com.sb.mybank.model.objects.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RemoteTransactionAppServiceImpl implements RemoteTransactionAppService {

    private String remoteUrl;
    private RestTemplate restTemplate;

    @Override
    public List<TransactionDTO> retrieveTransactions() throws Exception {
        log.debug("RemoteTransactionAppServiceImpl: Starting retrieval of transactions...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<TransactionDTO> transactionDataList = List.of();
        ResponseEntity<List<TransactionDTO>> response;

        HttpEntity<List<TransactionDTO>> httpEntity = new HttpEntity<>(transactionDataList, headers);
        response = restTemplate.exchange(remoteUrl, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<TransactionDTO>>() {
                });

        log.debug("RemoteTransactionAppServiceImpl: Retrieval complete");

        return response.getBody();
    }

    @Override
    public TransactionDTO retrieveTransaction(String id) throws Exception {
        log.debug("RemoteTransactionAppServiceImpl: Starting retrieval of transaction identified by " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransactionDTO transactionData = new TransactionDTO();
        ResponseEntity<TransactionDTO> response;

        HttpEntity<TransactionDTO> httpEntity = new HttpEntity<>(transactionData, headers);
        response = restTemplate.exchange(remoteUrl + "/" + id, HttpMethod.GET, httpEntity, TransactionDTO.class);

        log.debug("RemoteTransactionAppServiceImpl: Retrieval complete");

        return response.getBody();
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionData) throws Exception {

        log.debug("RemoteTransactionAppServiceImpl: Starting creation of new transaction ... " + transactionData.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<TransactionDTO> response;

        HttpEntity<TransactionDTO> httpEntity = new HttpEntity<>(transactionData, headers);
        response = restTemplate.exchange(remoteUrl, HttpMethod.POST, httpEntity, TransactionDTO.class);

        log.debug("RemoteTransactionAppServiceImpl: New transaction created ... " + response.getBody().toString());

        return response.getBody();
    }
}