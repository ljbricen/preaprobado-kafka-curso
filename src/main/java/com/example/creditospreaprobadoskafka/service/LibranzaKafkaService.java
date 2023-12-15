package com.example.creditospreaprobadoskafka.service;

import com.amazonaws.services.sqs.model.Message;
import com.example.creditospreaprobadoskafka.model.LibranzaAWSSqs;
import com.example.creditospreaprobadoskafka.model.LibranzaKafkaDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibranzaKafkaService {

    private final Logger logger = LoggerFactory.getLogger(LibranzaKafkaService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topicName, LibranzaKafkaDto libranzaKafkaDto){
        var future = kafkaTemplate.send(topicName, libranzaKafkaDto.getKey(), libranzaKafkaDto.libranzaToString());

        future.whenComplete((resultadoEnvio, excepcion)->{
            if(excepcion != null){
                logger.error(excepcion.getMessage());
                future.completeExceptionally(excepcion);
            } else {
                future.complete(resultadoEnvio);
                logger.info("LibranzaKafkaDto enviado al topicName -> {} en Kafka {}", topicName, libranzaKafkaDto.libranzaToString());
            }
        });
    }

    public String publishAwsSqsMessageToKafka(List<Message> messages, String topicName) {
        List<LibranzaKafkaDto> libranzaKafkaDtos = messages.stream()
                .map(message -> (LibranzaKafkaDto) new LibranzaAWSSqs(message.getMessageAttributes()))
                .toList();
        if (libranzaKafkaDtos.isEmpty())
            return "No messages to publish";

        libranzaKafkaDtos.forEach(libranzaKafkaDto -> {
            logger.info("SQS.Messsages: {}",libranzaKafkaDto.libranzaToString());
            send(topicName, libranzaKafkaDto);
        });
        return "Message published";
    }
}
