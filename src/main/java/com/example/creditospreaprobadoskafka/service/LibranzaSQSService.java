package com.example.creditospreaprobadoskafka.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibranzaSQSService {

    private final Logger logger = LoggerFactory.getLogger(LibranzaSQSService.class);

    private final AmazonSQS sqsClient;

    private String getQueueUrl(String queueName){
        return sqsClient.getQueueUrl(queueName).getQueueUrl();
    }

    public List<Message> receiveMessages(String queueName, Integer maxNumberMessages, Integer waitTimeSecond){
        String queueUrl = getQueueUrl(queueName);
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(maxNumberMessages);
        receiveMessageRequest.setWaitTimeSeconds(waitTimeSecond);
        receiveMessageRequest.setMessageAttributeNames(List.of("All"));
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        logger.info("Received {} messages from queue {}", messages.size(), queueName);
        return messages;
    }

}
