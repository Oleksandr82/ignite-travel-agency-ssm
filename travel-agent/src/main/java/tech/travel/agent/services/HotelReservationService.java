package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.agent.model.HotelReservationRequest;
import tech.travel.agent.model.HotelReservationStatus;

@FeignClient(value = "hotel-reservation-service")
public interface HotelReservationService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.BOOK_HOTEL)
    HotelReservationStatus bookHotel(@RequestBody HotelReservationRequest reservationRequest);
}
