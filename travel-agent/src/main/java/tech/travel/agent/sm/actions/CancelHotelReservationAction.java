package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.mapper.TripStatusMapper;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.model.HotelCancellationRequest;

import static tech.travel.agent.config.JmsConfig.CANCEL_HOTEL_REQUEST;
import static tech.travel.agent.config.JmsConfig.FLIGHT_CANCELLATION_REQUEST;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelHotelReservationAction implements Action<ReservationState, ReservationEvent> {

    private final JmsTemplate jmsTemplate;
    private final TripStatusMapper tripStatusMapper;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        TripRequest tripRequest = StateMachineUtils.getTravelRequest(context);
        HotelCancellationRequest flightCancellationRequest = tripStatusMapper
                .toHotelCancellationRequest(tripStatus.getTravelId(), tripStatus);

        jmsTemplate.convertAndSend(CANCEL_HOTEL_REQUEST, flightCancellationRequest);

        log.debug("Travel: {} - Send Hotel Cancellation request: {}", tripRequest, flightCancellationRequest);
    }
}
