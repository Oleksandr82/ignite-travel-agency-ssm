package tech.travel.agent.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import tech.travel.agent.model.Status;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.services.TripStatusService;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;
import tech.travel.model.CarRentalStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteTripAction implements Action<ReservationState, ReservationEvent> {

    private final TripStatusService tripStatusService;

    @Override
    public void execute(StateContext<ReservationState, ReservationEvent> context) {

        TripStatus tripStatus = StateMachineUtils.getTripStatus(context);
        if (tripStatus.getCarRentalResponse().getStatus() == CarRentalStatus.OK) {
            tripStatus.setStatus(Status.BOOKED_SUCCESSFULLY);
        } else {
            tripStatus.setStatus(Status.NOT_AVAILABLE);
        }
        StateMachineUtils.setTripStatus(context, tripStatus);

        tripStatusService.save(tripStatus);

        log.debug("Trip request is completed successfully");
    }
}
