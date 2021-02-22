package tech.travel.agent.sm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.statemachine.StateContext;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateMachineUtils {

    public static void setTripStatus(StateContext<ReservationState, ReservationEvent> context,
                                     TripStatus status) {
        context.getExtendedState().getVariables().put(MachineVariables.TRIP_STATUS, status);
    }

    public static TripStatus getTripStatus(StateContext<ReservationState, ReservationEvent> context) {
        return (TripStatus) context.getExtendedState().getVariables().getOrDefault(MachineVariables.TRIP_STATUS, null);
    }

    public static void setTravelRequest(StateContext<ReservationState, ReservationEvent> context,
                                        TripRequest request) {
        context.getExtendedState().getVariables().put(MachineVariables.TRIP_REQUEST, request);
    }

    public static TripRequest getTravelRequest(StateContext<ReservationState, ReservationEvent> context) {
        return (TripRequest) context.getExtendedState().getVariables().getOrDefault(MachineVariables.TRIP_REQUEST, null);
    }
}
