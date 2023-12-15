package com.example.creditospreaprobadoskafka.model;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.google.gson.Gson;
import java.util.Map;

public record LibranzaAWSSqs(Map<String, MessageAttributeValue> atributosMensaje) implements LibranzaKafkaDto {
    @Override
    public String getKey() {
        final String prefijoAWSSqs = "SQS-";
        return prefijoAWSSqs + atributosMensaje.get("documento").getStringValue();
    }
    @Override
    public String getCedula() {
        return atributosMensaje.get("documento").getStringValue();
    }

    @Override
    public String getTotalPrestamo() {
        return atributosMensaje.get("prestamo").getStringValue();
    }

    @Override
    public String getTotalConsumido() {
        return atributosMensaje.get("consumido").getStringValue();
    }

    @Override
    public String getFechaVencimiento() {
        return atributosMensaje.get("vencimiento").getStringValue();
    }

    @Override
    public String libranzaToString() {
        Map<String, String> atributosMensajeString = Map.of(
                "totalPrestamo", this.getTotalPrestamo(),
                "totalConsumido", this.getTotalConsumido(),
                "fechaVencimiento", this.getFechaVencimiento(),
                "cedula", this.getCedula(),
                "key", this.getKey()
        );
        return new Gson().toJson(atributosMensajeString);
    }
}
