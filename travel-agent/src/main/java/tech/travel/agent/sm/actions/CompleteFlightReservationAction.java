package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.model.FlightReservationResponse;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteFlightReservationAction implements Action<ReservationState, ReservationEvent> {

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        MessageHeaders headers = context.getMessage().getHeaders();
        FlightReservationResponse reservationResponse = (FlightReservationResponse) headers.get(MachineConfig.FLIGHT_RESERVATION_STATUS_HEADER);

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        tripStatus.setFlightReservationResponse(reservationResponse);
        StateMachineUtils.setTripStatus(context, tripStatus);

        log.debug("Flight Reservation Status Registered: {}", tripStatus);
    }
}
