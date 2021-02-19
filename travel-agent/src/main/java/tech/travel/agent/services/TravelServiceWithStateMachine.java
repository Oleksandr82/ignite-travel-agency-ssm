package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import tech.travel.agent.model.Status;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelServiceWithStateMachine {

    private final TripStatusService tripStatusService;

    private final StateMachineService<ReservationState, ReservationEvent> stateMachineService;

    public TripStatus bookTrip(TripRequest tripRequest) {

        UUID travelId = UUID.randomUUID();

        TripStatus tripStatus = TripStatus.builder()
                .travelId(travelId)
                .build();

        stateMachineService.acquireStateMachine(travelId.toString()).sendEvent(MessageBuilder
                .withPayload(ReservationEvent.INITIALIZE)
                .setHeader(MachineConfig.TRIP_REQUEST_HEADER, tripRequest)
                .setHeader(MachineConfig.TRIP_STATUS_HEADER, tripStatus)
                .build());

        return tripStatus;
    }

    public Status stateMachineStateToTripStatus(StateMachine<ReservationState, ReservationEvent> machine) {

        switch (machine.getState().getId()) {
            case NEW:
                return Status.NEW;
            case MANUAL_INTERVENTION_REQUIRED:
                return Status.FAILED;
            case NOT_AVAILABLE:
                return Status.NOT_AVAILABLE;
            case COMPLETED:
                return Status.BOOKED_SUCCESSFULLY;
            default:
                return Status.IN_PROGRESS;
        }
    }

    public TripStatus getTripInfo(UUID travelId) {
        return tripStatusService.findByTravelId(travelId).orElseThrow();
    }
}
