package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitializeTripAction implements Action<ReservationState, ReservationEvent> {

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        MessageHeaders headers = context.getMessage().getHeaders();
        TripRequest tripRequest = (TripRequest) headers.get(MachineConfig.TRIP_REQUEST_HEADER);
        TripStatus tripStatus = (TripStatus) headers.get(MachineConfig.TRIP_STATUS_HEADER);

        StateMachineUtils.setTravelRequest(context, tripRequest);
        StateMachineUtils.setTripStatus(context, tripStatus);

        log.debug("Trip request is initialized");
    }
}
