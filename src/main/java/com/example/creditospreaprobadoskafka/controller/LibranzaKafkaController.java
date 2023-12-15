package com.example.creditospreaprobadoskafka.controller;

import com.amazonaws.services.sqs.model.Message;
import com.example.creditospreaprobadoskafka.model.LibranzaDto;
import com.example.creditospreaprobadoskafka.service.LibranzaKafkaService;
import com.example.creditospreaprobadoskafka.service.LibranzaSQSService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libranza-promociones")
@RequiredArgsConstructor
public class LibranzaKafkaController {

    private final Logger logger = LoggerFactory.getLogger(LibranzaKafkaController.class);

    private final LibranzaSQSService libranzaSQSService;

    private final LibranzaKafkaService libranzaKafkaService;

    private final String TOPIC_PROMOCIONES = "libranza-promociones";
    private final String TOPIC_ONDEMAND = "libranza-ondemand";

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
        logger.info("Received {} messages from SQS", awsSqsMessages.size());
        return libranzaKafkaService.publishAwsSqsMessageToKafka(awsSqsMessages, TOPIC_PROMOCIONES);

    }

    @PostMapping("/on-demand")
    public String saveOnDemandLibranza(@RequestBody LibranzaDto libranzaDto){
        libranzaKafkaService.send(TOPIC_ONDEMAND, libranzaDto);
        return "LibranzaDto sent to Kafka";
    }

}
