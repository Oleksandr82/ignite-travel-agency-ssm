package tech.travel.agent.model;

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
public class HotelCancellationRequest {

    @NotNull
    private UUID bookingId;

    @NotNull
    private UUID reservationId;
}