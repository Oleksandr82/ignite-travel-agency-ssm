package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.agent.model.FlightReservationRequest;
import tech.travel.agent.model.FlightReservationStatus;

@FeignClient(value = "flight-reservation-service")
public interface FlightReservationService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.RESERVE_FLIGHT)
    FlightReservationStatus bookFlight(@RequestBody FlightReservationRequest flightReservationRequest);
}
