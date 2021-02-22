package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.mapper.TripRequestMapper;
import tech.travel.model.FlightReservationRequest;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;

import static tech.travel.agent.config.JmsConfig.FLIGHT_RESERVATION_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveFlightAction implements Action<ReservationState, ReservationEvent> {

    private final JmsTemplate jmsTemplate;
    private final TripRequestMapper tripRequestMapper;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        TripRequest tripRequest = StateMachineUtils.getTravelRequest(context);
        FlightReservationRequest hotelReservationRequest = tripRequestMapper
                .toFlightReservationRequest(tripStatus.getTravelId(), tripRequest);

        jmsTemplate.convertAndSend(FLIGHT_RESERVATION_REQUEST, hotelReservationRequest);

        log.debug("Travel: {} - Send Flight Reservation request: {}", tripRequest, hotelReservationRequest);
    }
}
