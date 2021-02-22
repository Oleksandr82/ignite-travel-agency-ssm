package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.model.FlightReservationRequest;
import tech.travel.model.FlightReservationResponse;

import java.util.UUID;

@FeignClient(value = "flight-reservation-service")
public interface FlightReservationService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.FLIGHT_RESERVATION)
    FlightReservationResponse bookFlight(@RequestBody FlightReservationRequest flightReservationRequest);

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = ApiConstants.FLIGHT_RESERVATION + "/{reservationId}/cancelled")
    FlightReservationResponse cancel(@PathVariable UUID reservationId,
                                     @RequestBody FlightCancellationRequest cancellationRequest);

}
