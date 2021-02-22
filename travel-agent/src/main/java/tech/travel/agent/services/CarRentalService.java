package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.model.CarCancellationRequest;
import tech.travel.model.CarRentalRequest;
import tech.travel.model.CarRentalResponse;

import java.util.UUID;

@FeignClient(value = "car-rental-service")
public interface CarRentalService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.CAR_RESERVATION)
    CarRentalResponse bookCar(@RequestBody CarRentalRequest rentalRequest);

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = ApiConstants.CAR_RESERVATION + "/{reservationId}/cancelled")
    CarRentalResponse cancel(@PathVariable UUID reservationId,
                             @RequestBody CarCancellationRequest cancellationRequest);
}
