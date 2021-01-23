package tech.carrental.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.carrental.model.CarInfo;
import tech.carrental.model.CarRentalConfirmation;
import tech.carrental.services.CarRentalService;

import java.util.List;
import java.util.UUID;

import static tech.carrental.ApiConstants.BOOK_CAR;
import static tech.carrental.ApiConstants.GET_ALL_CARS;


@Slf4j
@RequiredArgsConstructor
@RestController
public class CarRentalController {

    private final CarRentalService carRentalService;

    @GetMapping(GET_ALL_CARS)
    List<CarInfo> getCars() {
        log.debug("Get all available cars");
        return carRentalService.getAll();
    }

    @PostMapping (BOOK_CAR)
    CarRentalConfirmation rentCar(@PathVariable UUID bookingId, @PathVariable UUID carId) {
        log.debug("Rent a Car");
        return carRentalService.rent(bookingId, carId);
    }
}
