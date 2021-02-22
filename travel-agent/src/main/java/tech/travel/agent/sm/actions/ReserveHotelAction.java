package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.config.JmsConfig;
import tech.travel.agent.mapper.TripRequestMapper;
import tech.travel.model.HotelReservationRequest;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveHotelAction implements Action<ReservationState, ReservationEvent> {

    private final JmsTemplate jmsTemplate;
    private final TripRequestMapper tripRequestMapper;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        TripRequest tripRequest = StateMachineUtils.getTravelRequest(context);

        HotelReservationRequest hotelReservationRequest = tripRequestMapper
                .toHotelReservationRequest(tripStatus.getTravelId(), tripRequest);

        jmsTemplate.convertAndSend(JmsConfig.BOOK_HOTEL_REQUEST, hotelReservationRequest);

        log.debug("Travel: {} - Send Hotel Reservation request: {}", tripRequest, hotelReservationRequest);
    }
}
