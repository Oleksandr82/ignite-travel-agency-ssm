package tech.travel.agent.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TravelStatus {

    private UUID travelId;

    private HotelReservationStatus hotelReservationStatus;

    private FlightReservationStatus flightReservationStatus;

    private CarRentalStatus carRentalStatus;

    @Builder.Default
    private Status status = Status.IN_PROGRESS;
}
