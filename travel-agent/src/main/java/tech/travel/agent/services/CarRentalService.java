package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.agent.model.CarRentalRequest;
import tech.travel.agent.model.CarRentalStatus;

@FeignClient(value = "car-rental-service")
public interface CarRentalService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.BOOK_CAR)
    CarRentalStatus bookCar(@RequestBody CarRentalRequest carRentalRequest);
}
