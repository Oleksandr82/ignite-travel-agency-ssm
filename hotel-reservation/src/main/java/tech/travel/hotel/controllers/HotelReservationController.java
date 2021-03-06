package tech.travel.hotel.controllers;

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
import tech.travel.model.HotelCancellationRequest;
import tech.travel.hotel.model.HotelInfo;
import tech.travel.model.HotelReservationRequest;
import tech.travel.model.HotelReservationResponse;
import tech.travel.hotel.services.HotelReservationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static tech.travel.hotel.ApiConstants.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_HOTELS)
@Validated
public class HotelReservationController {

    private final HotelReservationService hotelReservationService;

    @GetMapping
    List<HotelInfo> getCars() {
        log.debug("Get all available hotels");
        return hotelReservationService.getAll();
    }

    @GetMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    HotelInfo getHotelInfo(@PathVariable UUID hotelId) {
        return hotelReservationService.getHotelInfo(hotelId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    HotelReservationResponse bookHotel(@RequestBody @Valid HotelReservationRequest hotelReservationRequest) {
        log.debug("Book a Hotel: {}", hotelReservationRequest);
        return hotelReservationService.book(hotelReservationRequest);
    }

    @PutMapping(value = "/{reservationId}/cancelled", produces = MediaType.APPLICATION_JSON_VALUE)
    HotelReservationResponse cancel(@PathVariable @NotNull UUID reservationId,
                                  @RequestBody @Valid HotelCancellationRequest cancellationRequest) {

        if (!reservationId.equals(cancellationRequest.getReservationId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        log.debug("Cancel a Hotel reservation: {}", cancellationRequest);
        return hotelReservationService.cancel(cancellationRequest);
    }
}
