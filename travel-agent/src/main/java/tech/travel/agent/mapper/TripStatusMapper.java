package tech.travel.agent.mapper;

import org.springframework.stereotype.Component;
import tech.travel.agent.model.TripStatus;
import tech.travel.model.CarCancellationRequest;

import java.util.UUID;

@Component
public class TripStatusMapper {

    public CarCancellationRequest toCarCancellationRequest(UUID tripId, TripStatus tripStatus) {
        return CarCancellationRequest.builder()
                .tripId(tripId)
                .tripId(tripStatus.getCarRentalResponse().getTripId())
                .build();
    }
}
