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
import tech.travel.agent.model.TravelRequest;
import tech.travel.agent.model.TravelStatus;
import tech.travel.agent.services.TravelService;

import javax.validation.Valid;
import java.util.UUID;

import static tech.travel.agent.ApiConstants.BOOK_TRAVEL;
import static tech.travel.agent.ApiConstants.GET_TRAVEL_INFO;


@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class TravelController {

    private final TravelService travelService;

    @GetMapping(GET_TRAVEL_INFO)
    TravelStatus getTravelInfo(@PathVariable UUID id) {

        return travelService.getTravelStatus(id);
    }

    @PostMapping (value = BOOK_TRAVEL, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    TravelStatus bookTravel(@RequestBody @Valid TravelRequest travelRequest) {
        log.debug("Book a Travel");
        return travelService.bookTravel(travelRequest);
    }
}
