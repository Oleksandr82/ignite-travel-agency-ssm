package tech.travel.agent.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.travel.model.CarRentalResponse;
import tech.travel.model.FlightReservationResponse;
import tech.travel.model.HotelReservationResponse;

import javax.persistence.Id;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class TripStatus {

    @Id
    private UUID travelId;

    private HotelReservationResponse hotelReservationResponse;

    private FlightReservationResponse flightReservationResponse;

    private CarRentalResponse carRentalResponse;

    @Builder.Default
    private Status status = Status.NEW;
}
