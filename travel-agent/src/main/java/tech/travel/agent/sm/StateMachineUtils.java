package tech.travel.agent.sm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.statemachine.StateContext;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateMachineUtils {

    public static final void setTripStatus(StateContext<ReservationState, ReservationEvent> context,
                                           TripStatus status) {
        context.getExtendedState().getVariables().put(MachineVariables.TRAVEL_STATUS, status);
    }

    public static final TripStatus getTripStatus(StateContext<ReservationState, ReservationEvent> context) {
        return (TripStatus) context.getExtendedState().getVariables().getOrDefault(MachineVariables.TRAVEL_STATUS, null);
    }

    public static final void setTravelRequest(StateContext<ReservationState, ReservationEvent> context,
                                             TripRequest request) {
        context.getExtendedState().getVariables().put(MachineVariables.TRAVEL_REQUEST, request);
    }

    public static final TripRequest getTravelRequest(StateContext<ReservationState, ReservationEvent> context) {
        return (TripRequest) context.getExtendedState().getVariables().getOrDefault(MachineVariables.TRAVEL_REQUEST, null);
    }
}
