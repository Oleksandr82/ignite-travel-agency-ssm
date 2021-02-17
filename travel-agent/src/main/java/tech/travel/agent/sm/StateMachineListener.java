package tech.travel.agent.sm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StateMachineListener extends StateMachineListenerAdapter<ReservationState, ReservationEvent> {

    @Override
    public void stateChanged(State<ReservationState, ReservationEvent> from,
                             State<ReservationState, ReservationEvent> to) {

        log.info(String.format("stateChanged (from: %s, to: %s)", from, to));
    }
}
