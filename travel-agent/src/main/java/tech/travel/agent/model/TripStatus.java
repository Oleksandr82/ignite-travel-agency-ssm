package tech.travel.agent.model;

import lombok.Builder;
import lombok.Data;
import tech.travel.model.CarRentalResponse;
import tech.travel.model.FlightReservationResponse;
import tech.travel.model.HotelReservationResponse;

import java.util.UUID;

@Data
@Builder
public class TripStatus {

    private UUID travelId;

    private HotelReservationResponse hotelReservationResponse;

    private FlightReservationResponse flightReservationResponse;

    private CarRentalResponse carRentalResponse;

    @Builder.Default
    private Status status = Status.IN_PROGRESS;
}
