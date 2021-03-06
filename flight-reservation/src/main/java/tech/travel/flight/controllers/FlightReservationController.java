package tech.travel.flight.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.flight.model.FlightInfo;
import tech.travel.model.FlightReservationRequest;
import tech.travel.model.FlightReservationResponse;
import tech.travel.flight.services.FlightReservationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static tech.travel.flight.ApiConstants.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(API_FLIGHTS)
public class FlightReservationController {

    private final FlightReservationService flightReservationService;

    @GetMapping
    List<FlightInfo> getFlights() {
        log.debug("Get all available flight options");
        return flightReservationService.getAll();
    }

    @GetMapping(value = "/{flightId}", produces = MediaType.APPLICATION_JSON_VALUE)
    FlightInfo getFlightInfo(@PathVariable UUID flightId) {
        return flightReservationService.getFlightInfo(flightId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    FlightReservationResponse reserveFlight(@RequestBody @Valid FlightReservationRequest flightReservationRequest) {
        log.debug("Reserve a Flight: {}", flightReservationRequest);
        return flightReservationService.bookFlight(flightReservationRequest);
    }

    @PutMapping(value = "/{reservationId}/cancelled", produces = MediaType.APPLICATION_JSON_VALUE)
    FlightReservationResponse cancel(@PathVariable @NotNull UUID reservationId,
                                     @RequestBody @Valid FlightCancellationRequest cancellationRequest) {

        if (!reservationId.equals(cancellationRequest.getReservationId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        log.debug("Cancel a Flight reservation: {}", cancellationRequest);
        return flightReservationService.cancel(cancellationRequest);
    }
}
