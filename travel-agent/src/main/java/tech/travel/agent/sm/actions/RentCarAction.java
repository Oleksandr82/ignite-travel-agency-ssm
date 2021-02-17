package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.config.JmsConfig;
import tech.travel.agent.mapper.TripRequestMapper;
import tech.travel.model.CarRentalRequest;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class RentCarAction implements Action<ReservationState, ReservationEvent> {

    private final JmsTemplate jmsTemplate;
    private final TripRequestMapper tripRequestMapper;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        TripRequest tripRequest = StateMachineUtils.getTravelRequest(context);
        CarRentalRequest carRentalRequest = tripRequestMapper
                .toCarRentalRequest(tripStatus.getTravelId(), tripRequest);

        jmsTemplate.convertAndSend(JmsConfig.RENT_CAR_REQUEST, carRentalRequest);

        log.debug("Travel: {} - Send Rent a Car request: {}", tripStatus, carRentalRequest);
    }
}
