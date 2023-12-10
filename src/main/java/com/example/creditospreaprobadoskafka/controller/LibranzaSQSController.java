package com.example.creditospreaprobadoskafka.controller;

import com.amazonaws.services.sqs.model.Message;
import com.example.creditospreaprobadoskafka.service.LibranzaSQSService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libranza-promociones")
@RequiredArgsConstructor
public class LibranzaSQSController {

    private final Logger logger = LoggerFactory.getLogger(LibranzaSQSController.class);

    private final LibranzaSQSService libranzaSQSService;

    private final static String QUEUE_NAME = "libranza-promociones";

    @PostMapping("/sqs-promociones")

    public String receiveMessagesAndSendKafka(
            @RequestParam(value = "messages", defaultValue = "10") Integer maxNumberMessages,
            @RequestParam(value = "timeout", defaultValue = "10") Integer waitTimeSeconds
    ){
        List<Message> awsSqsMessages = libranzaSQSService.receiveMessages(
                QUEUE_NAME,
                maxNumberMessages,
                waitTimeSeconds
        );
        return "Received " + awsSqsMessages.size() + " messages from SQS";
    }

}
