package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.config.JmsConfig;
import tech.travel.agent.mapper.TripStatusMapper;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;
import tech.travel.model.CarCancellationRequest;


@Slf4j
@Component
@RequiredArgsConstructor
public class CancelCarReservationAction implements Action<ReservationState, ReservationEvent> {

    private final JmsTemplate jmsTemplate;
    private final TripStatusMapper tripStatusMapper;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        CarCancellationRequest carCancellationRequest = tripStatusMapper
                .toCarCancellationRequest(tripStatus.getTravelId(), tripStatus);

        jmsTemplate.convertAndSend(JmsConfig.CANCEL_CAR_REQUEST, carCancellationRequest);

        log.debug("Travel: {} - Cancel Car Reservation request: {}", tripStatus, carCancellationRequest);
    }
}
