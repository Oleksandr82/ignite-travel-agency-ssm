package tech.travel.hotel.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.travel.hotel.model.HotelInfo;
import tech.travel.hotel.model.HotelReservationRequest;
import tech.travel.hotel.model.HotelReservationStatus;
import tech.travel.hotel.services.HotelReservationService;

import javax.validation.Valid;
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
    HotelReservationStatus bookHotel(@RequestBody @Valid HotelReservationRequest hotelReservationRequest) {
        log.debug("Book a Hotel");
        return hotelReservationService.book(hotelReservationRequest);
    }
}
