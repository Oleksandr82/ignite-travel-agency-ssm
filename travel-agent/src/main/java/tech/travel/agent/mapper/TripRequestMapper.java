package tech.travel.agent.mapper;

import org.springframework.stereotype.Component;
import tech.travel.model.CarRentalRequest;
import tech.travel.model.FlightReservationRequest;
import tech.travel.model.HotelReservationRequest;
import tech.travel.agent.model.TripRequest;

import java.util.UUID;

@Component
public class TripRequestMapper {

    public FlightReservationRequest toFlightReservationRequest(UUID travelId, TripRequest tripRequest) {
        return  FlightReservationRequest.builder()
                .tripId(travelId)
                .code(tripRequest.getFlightCode())
                .build();
    }

    public HotelReservationRequest toHotelReservationRequest(UUID travelId, TripRequest tripRequest) {
        return  HotelReservationRequest.builder()
                .tripId(travelId)
                .name(tripRequest.getHotelName())
                .build();
    }

    public CarRentalRequest toCarRentalRequest(UUID travelId, TripRequest tripRequest) {
        return CarRentalRequest.builder()
                .tripId(travelId)
                .name(tripRequest.getCarName())
                .build();
    }
}
