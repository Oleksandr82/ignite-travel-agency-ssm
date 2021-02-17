package tech.travel.agent.sm.guards;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;
import tech.travel.model.HotelReservationStatus;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;
import tech.travel.agent.sm.StateMachineUtils;

@Component
public class HotelReservationSucceededGuard implements Guard<ReservationState, ReservationEvent> {
    @Override
    public boolean evaluate(StateContext<ReservationState, ReservationEvent> context) {
        return StateMachineUtils.getTripStatus(context).getHotelReservationResponse().getStatus()
                == HotelReservationStatus.OK;
    }
}
