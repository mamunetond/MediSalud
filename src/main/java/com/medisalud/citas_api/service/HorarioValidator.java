package com.medisalud.citas_api.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class HorarioValidator {

    public void validarHorario(LocalDateTime fechaHora) {

        DayOfWeek dia = fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();

        if (dia == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(
                    "No se pueden agendar citas los domingos");
        }

        if (dia == DayOfWeek.SATURDAY) {

            if (hora.isBefore(LocalTime.of(8, 0))
                    || !hora.isBefore(LocalTime.of(13, 0))) {

                throw new IllegalArgumentException(
                        "Horario fuera de atención del sábado");
            }

            return;
        }

        if (hora.isBefore(LocalTime.of(8, 0))
                || !hora.isBefore(LocalTime.of(18, 0))) {

            throw new IllegalArgumentException(
                    "Horario fuera de atención");
        }
    }

    public void validarFranja(LocalDateTime fechaHora){

    int minutos = fechaHora.getMinute();

    if(minutos != 0 && minutos != 30){
        throw new IllegalArgumentException(
            "Las citas solo pueden programarse en franjas de 30 minutos"
        );
    }
}
}