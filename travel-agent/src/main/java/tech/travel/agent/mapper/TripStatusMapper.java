package tech.travel.agent.mapper;

import org.springframework.stereotype.Component;
import tech.travel.agent.model.TripStatus;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.model.HotelCancellationRequest;

import java.util.UUID;

@Component
public class TripStatusMapper {

    public FlightCancellationRequest toFlightCancellationRequest(UUID tripId, TripStatus tripStatus) {
        return FlightCancellationRequest.builder()
                .tripId(tripId)
                .reservationId(tripStatus.getFlightReservationResponse().getFlightId())
                .build();
    }

    public HotelCancellationRequest toHotelCancellationRequest(UUID tripId, TripStatus tripStatus) {
        return HotelCancellationRequest.builder()
                .tripId(tripId)
                .reservationId(tripStatus.getHotelReservationResponse().getHotelId())
                .build();
    }
}
