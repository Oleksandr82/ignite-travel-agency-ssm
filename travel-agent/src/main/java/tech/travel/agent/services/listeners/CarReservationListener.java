package tech.travel.agent.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import tech.travel.agent.config.JmsConfig;
import tech.travel.model.CarRentalResponse;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarReservationListener {

    private final StateMachineService<ReservationState, ReservationEvent> stateMachineService;

    @JmsListener(destination = JmsConfig.RENT_CAR_RESPONSE)
    public void completeCarReservation(CarRentalResponse result) {

        log.debug("Car Reservation Result: {}", result);

        StateMachine<ReservationState, ReservationEvent> stateMachine = stateMachineService
                .acquireStateMachine(result.getTripId().toString());

        stateMachine.sendEvent(MessageBuilder
                .withPayload(ReservationEvent.CAR_RESERVATION_COMPLETED)
                .setHeader(MachineConfig.CAR_RESERVATION_STATUS_HEADER, result)
                .build());
    }
}
