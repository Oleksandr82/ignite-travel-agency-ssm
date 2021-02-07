package tech.travel.agent.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TravelStatus {

    private UUID travelId;

    private HotelReservationResponse hotelReservationResponse;

    private FlightReservationResponse flightReservationResponse;

    private CarRentalResponse carRentalResponse;

    @Builder.Default
    private Status status = Status.IN_PROGRESS;
}
