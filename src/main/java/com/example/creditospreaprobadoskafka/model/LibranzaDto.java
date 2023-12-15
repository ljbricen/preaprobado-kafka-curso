package com.example.creditospreaprobadoskafka.model;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Schema(description = "Modelo de LibranzaDto que solo muestra los campos document, loanValue y expirationDate")
public record LibranzaDto(@Schema(description = "Documento", example = "123456789")
                          String document,
                          @Schema(description = "Valor del préstamo", example = "10000")
                          BigDecimal loanValue,
                          @Schema(description = "Fecha de vencimiento", example = "2023-12-31")
                          LocalDate expirationDate) implements LibranzaKafkaDto {
    @Override
    @Schema(hidden = true)
    public String getKey() {
        final String prefijoRest = "Rest-";
        return prefijoRest + document;
    }

    @Override
    @Schema(hidden = true)
    public String getCedula() {
        return document;
    }

    @Override
    @Schema(hidden = true)
    public String getTotalPrestamo() {
        return loanValue.toString();
    }

    @Override
    @Schema(hidden = true)
    public String getTotalConsumido() {
        return "0";
    }

    @Override
    @Schema(hidden = true)
    public String getFechaVencimiento() {
        return expirationDate.toString();
    }

    @Override
    @Schema(hidden = true)
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
