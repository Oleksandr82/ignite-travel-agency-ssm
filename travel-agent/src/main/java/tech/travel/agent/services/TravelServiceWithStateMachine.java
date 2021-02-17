package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.sm.MachineConfig;
import tech.travel.agent.sm.ReservationEvent;
import tech.travel.agent.sm.ReservationState;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelServiceWithStateMachine {

    private final StateMachineService<ReservationState, ReservationEvent> stateMachineService;

    public TripStatus bookTravel(TripRequest tripRequest) {

        UUID travelId = UUID.randomUUID();

        TripStatus tripStatus = TripStatus.builder()
                .travelId(travelId)
                .build();

        stateMachineService.acquireStateMachine(travelId.toString()).sendEvent(MessageBuilder
                .withPayload(ReservationEvent.INITIALIZE)
                .setHeader(MachineConfig.TRAVEL_REQUEST_HEADER, tripRequest)
                .setHeader(MachineConfig.TRAVEL_STATUS_HEADER, tripStatus)
                .build());

        return tripStatus;
    }

    public ReservationState getStateMachineState(String travelId) {
        return stateMachineService.acquireStateMachine(travelId).getState().getId();
    }
}
