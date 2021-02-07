package tech.travel.agent.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.travel.agent.ApiConstants;
import tech.travel.agent.model.HotelCancellationRequest;
import tech.travel.agent.model.HotelReservationRequest;
import tech.travel.agent.model.HotelReservationResponse;

import java.util.UUID;

@FeignClient(value = "hotel-reservation-service")
public interface HotelReservationService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = ApiConstants.BOOK_HOTEL)
    HotelReservationResponse bookHotel(@RequestBody HotelReservationRequest reservationRequest);

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = ApiConstants.BOOK_CAR + "/{reservationId}/canceled")
    HotelReservationResponse cancel(@PathVariable UUID reservationId,
                                     @RequestBody HotelCancellationRequest cancellationRequest);

}
