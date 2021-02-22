package tech.travel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelReservationResponse {

    private UUID id;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private UUID hotelId;
    private UUID tripId;
    private HotelReservationStatus status;
}
