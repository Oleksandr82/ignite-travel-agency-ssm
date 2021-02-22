package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;
import tech.travel.model.FlightReservationResponse;
import tech.travel.model.FlightReservationStatus;
import tech.travel.model.HotelReservationResponse;
import tech.travel.model.HotelReservationStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteFlightCancellationAction implements Action<ReservationState, ReservationEvent> {

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        FlightReservationResponse flightReservationResponse = tripStatus.getFlightReservationResponse();
        flightReservationResponse.setStatus(FlightReservationStatus.CANCELLED);
        tripStatus.setFlightReservationResponse(flightReservationResponse);
        StateMachineUtils.setTripStatus(context, tripStatus);

        log.debug("Flight Reservation Status Registered: {}", tripStatus);
    }
}
