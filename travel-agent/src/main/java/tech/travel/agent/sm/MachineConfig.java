package tech.travel.agent.sm;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.EnumSet;

@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class MachineConfig
        extends EnumStateMachineConfigurerAdapter<ReservationState, ReservationEvent> {

    public static final String TRAVEL_REQUEST_HEADER = "TRAVEL_REQUEST_HEADER";
    public static final String TRAVEL_STATUS_HEADER = "TRAVEL_STATUS_HEADER";
    public static final String CAR_RESERVATION_STATUS_HEADER = "CAR_RESERVATION_STATUS_HEADER";
    public static final String HOTEL_RESERVATION_STATUS_HEADER = "HOTEL_RESERVATION_STATUS_HEADER";
    public static final String FLIGHT_RESERVATION_STATUS_HEADER = "FLIGHT_RESERVATION_STATUS_HEADER";

    private final Action<ReservationState, ReservationEvent> initializeTripAction;
    private final Action<ReservationState, ReservationEvent> reserveFlightAction;
    private final Action<ReservationState, ReservationEvent> reserveHotelAction;
    private final Action<ReservationState, ReservationEvent> rentCarAction;
    private final Action<ReservationState, ReservationEvent> completeFlightReservationAction;
    private final Action<ReservationState, ReservationEvent> completeHotelReservationAction;
    private final Action<ReservationState, ReservationEvent> completeCarReservationAction;
    private final Action<ReservationState, ReservationEvent> completeTripAction;

    private final Guard<ReservationState, ReservationEvent> flightReservationSucceededGuard;
    private final Guard<ReservationState, ReservationEvent> flightReservationFailedGuard;
    private final Guard<ReservationState, ReservationEvent> hotelReservationSucceededGuard;
    private final Guard<ReservationState, ReservationEvent> hotelReservationFailedGuard;
    private final Guard<ReservationState, ReservationEvent> carReservationSucceededGuard;
    private final Guard<ReservationState, ReservationEvent> carReservationFailedGuard;

    private final StateMachineListenerAdapter<ReservationState, ReservationEvent> stateMachineListener;
    private final StateMachineRuntimePersister<ReservationState, ReservationEvent, String> stateMachinePersister;


    @Override
    public void configure(StateMachineConfigurationConfigurer<ReservationState, ReservationEvent> config) throws Exception {
        config.withConfiguration().autoStartup(true);
        config.withConfiguration().listener(stateMachineListener);
        config.withPersistence().runtimePersister(stateMachinePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<ReservationState, ReservationEvent> states) throws Exception {
        states.withStates()
                .initial(ReservationState.NEW)
                .states(EnumSet.allOf(ReservationState.class))
                .end(ReservationState.FAILED)
                .end(ReservationState.COMPLETED)
                .end(ReservationState.MANUAL_INTERVENTION_REQUIRED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ReservationState, ReservationEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(ReservationState.NEW).target(ReservationState.INITIALIZED)
                .event(ReservationEvent.INITIALIZE)
                .action(initializeTripAction)

                .and()
                .withExternal()
                .source(ReservationState.INITIALIZED).target(ReservationState.FLIGHT_IN_PROGRESS)
                .action(reserveFlightAction)

                .and()
                .withExternal()
                .source(ReservationState.FLIGHT_IN_PROGRESS).target(ReservationState.FLIGHT_COMPLETED)
                .event(ReservationEvent.FLIGHT_RESERVATION_COMPLETED)
                .action(completeFlightReservationAction)

                .and()
                .withExternal()
                .source(ReservationState.FLIGHT_COMPLETED).target(ReservationState.HOTEL_IN_PROGRESS)
                .guard(flightReservationSucceededGuard)
                .action(reserveHotelAction)

                .and()
                .withExternal()
                .source(ReservationState.FLIGHT_COMPLETED).target(ReservationState.FAILED)
                .guard(flightReservationFailedGuard)

                .and()
                .withExternal()
                .source(ReservationState.HOTEL_IN_PROGRESS).target(ReservationState.HOTEL_COMPLETED)
                .event(ReservationEvent.HOTEL_RESERVATION_COMPLETED)
                .action(completeHotelReservationAction)

                .and()
                .withExternal()
                .source(ReservationState.HOTEL_COMPLETED).target(ReservationState.FLIGHT_CANCELLATION_IN_PROGRESS)
                .guard(hotelReservationFailedGuard)
//                .action(cancelFlightAction)

                .and()
                .withExternal()
                .source(ReservationState.HOTEL_COMPLETED).target(ReservationState.CAR_IN_PROGRESS)
                .guard(hotelReservationSucceededGuard)
                .action(rentCarAction)

                .and()
                .withExternal()
                .source(ReservationState.CAR_IN_PROGRESS).target(ReservationState.CAR_COMPLETED)
                .event(ReservationEvent.CAR_RESERVATION_COMPLETED)
                .action(completeCarReservationAction)

                .and()
                .withExternal()
                .source(ReservationState.CAR_COMPLETED).target(ReservationState.COMPLETED)
                .guard(carReservationSucceededGuard)
                .action(completeTripAction)

                .and()
                .withExternal()
                .source(ReservationState.CAR_COMPLETED).target(ReservationState.HOTEL_CANCELLATION_IN_PROGRESS)
                .guard(carReservationFailedGuard)
                //.action(cancelHotelAction)
        ;

    }
}
