package tech.travel.agent.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import tech.travel.agent.config.JmsConfig;
import tech.travel.model.FlightReservationResponse;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightReservationListener {

    private final StateMachineService<ReservationState, ReservationEvent> stateMachineService;

    @JmsListener(destination = JmsConfig.FLIGHT_RESERVATION_RESPONSE)
    public void completeFlightReservation(FlightReservationResponse result) {

        log.debug("Flight Reservation Result: {}", result);

        StateMachine<ReservationState, ReservationEvent> stateMachine = stateMachineService
                .acquireStateMachine(result.getTripId().toString());

        stateMachine.sendEvent(MessageBuilder
                .withPayload(ReservationEvent.FLIGHT_RESERVATION_COMPLETED)
                .setHeader(MachineConfig.FLIGHT_RESERVATION_STATUS_HEADER, result)
                .build());
    }
}
