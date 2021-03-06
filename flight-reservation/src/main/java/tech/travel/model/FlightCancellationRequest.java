package tech.travel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightCancellationRequest {

    @NotNull
    private UUID tripId;

    @NotNull
    private UUID reservationId;
}
