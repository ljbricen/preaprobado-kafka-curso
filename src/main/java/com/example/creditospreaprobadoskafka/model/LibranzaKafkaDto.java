package com.example.creditospreaprobadoskafka.model;

public interface LibranzaKafkaDto {

    String getKey();
    String getCedula();
    String getTotalPrestamo();
    String getTotalConsumido();
    String getFechaVencimiento();

    String libranzaToString();

}
