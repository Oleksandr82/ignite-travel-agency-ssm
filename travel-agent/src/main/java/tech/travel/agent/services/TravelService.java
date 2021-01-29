package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.travel.agent.model.CarRentalRequest;
import tech.travel.agent.model.CarRentalStatus;
import tech.travel.agent.model.FlightReservationRequest;
import tech.travel.agent.model.FlightReservationStatus;
import tech.travel.agent.model.HotelReservationRequest;
import tech.travel.agent.model.HotelReservationStatus;
import tech.travel.agent.model.TravelRequest;
import tech.travel.agent.model.TravelStatus;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final CarRentalService carRentalService;
    private final HotelReservationService hotelReservationService;
    private final FlightReservationService flightReservationService;

    public TravelStatus bookTravel(TravelRequest travelRequest) {

        UUID uuid = UUID.randomUUID();

        CarRentalStatus carStatus = carRentalService.bookCar(CarRentalRequest.builder()
                .bookingId(uuid)
                .name(travelRequest.getCarName())
                .build());
        HotelReservationStatus hotelStatus = hotelReservationService.bookHotel(HotelReservationRequest.builder()
                .bookingId(uuid)
                .name(travelRequest.getHotelName())
                .build());
        FlightReservationStatus flightStatus = flightReservationService.bookFlight(FlightReservationRequest.builder()
                .bookingId(uuid)
                .code(travelRequest.getFlightCode())
                .build());

        return TravelStatus.builder()
                .hotelReservationStatus(hotelStatus)
                .flightReservationStatus(flightStatus)
                .carRentalStatus(carStatus)
                .build();
    }

    public TravelStatus getTravelStatus(UUID id) {

        return TravelStatus.builder().build();
    }
}
