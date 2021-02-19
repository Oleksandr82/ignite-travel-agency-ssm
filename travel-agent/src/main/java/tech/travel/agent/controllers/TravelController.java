package tech.travel.agent.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.services.TravelService;

import javax.validation.Valid;
import java.util.UUID;

import static tech.travel.agent.ApiConstants.BOOK_TRIP;
import static tech.travel.agent.ApiConstants.GET_TRAVEL_INFO;


@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class TravelController {

    private final TravelService travelService;

    @GetMapping(value = GET_TRAVEL_INFO,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    TripStatus getTravelInfo(@PathVariable UUID id) {
        log.debug("Get Travel Info");
        return travelService.getTripStatus(id);
    }

    @PostMapping (value = BOOK_TRIP,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE,
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    TripStatus bookTravel(@RequestBody @Valid TripRequest tripRequest) {
        log.debug("Book a Travel");
        return travelService.bookTrip(tripRequest);
    }
}
