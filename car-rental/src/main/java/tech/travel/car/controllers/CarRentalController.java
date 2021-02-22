package tech.travel.car.controllers;

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
import tech.travel.model.CarCancellationRequest;
import tech.travel.car.model.CarInfo;
import tech.travel.model.CarRentalRequest;
import tech.travel.model.CarRentalResponse;
import tech.travel.car.services.CarRentalService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static tech.travel.car.ApiConstants.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_CARS)
@Validated
public class CarRentalController {

    private final CarRentalService carRentalService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CarInfo> getCars() {
        log.debug("Get all available cars");
        return carRentalService.getAll();
    }

    @GetMapping(value = "/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CarInfo getCarInfo(@PathVariable UUID carId) {
        log.debug("Get information for a car by Id");
        return carRentalService.getCarInfo(carId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CarRentalResponse rentCar(@RequestBody @Valid CarRentalRequest carReservationRequest) {
        log.debug("Rent a Car: {}", carReservationRequest);
        return carRentalService.rent(carReservationRequest);
    }

    @PutMapping(value = "/{reservationId}/cancelled", produces = MediaType.APPLICATION_JSON_VALUE)
    CarRentalResponse cancelCar(@PathVariable @NotNull UUID reservationId,
                                @RequestBody @Valid CarCancellationRequest carCancellationRequest) {

        if (!reservationId.equals(carCancellationRequest.getReservationId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        log.debug("Cancel a Car reservation: {}", carCancellationRequest);
        return carRentalService.cancel(carCancellationRequest);
    }
}
