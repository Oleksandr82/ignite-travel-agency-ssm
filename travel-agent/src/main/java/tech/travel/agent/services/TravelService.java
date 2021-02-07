package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.agent.model.CarRentalRequest;
import tech.travel.agent.model.CarRentalResponse;
import tech.travel.agent.model.CarRentalStatus;
import tech.travel.agent.model.FlightCancellationRequest;
import tech.travel.agent.model.FlightReservationRequest;
import tech.travel.agent.model.FlightReservationResponse;
import tech.travel.agent.model.FlightReservationStatus;
import tech.travel.agent.model.HotelCancellationRequest;
import tech.travel.agent.model.HotelReservationRequest;
import tech.travel.agent.model.HotelReservationResponse;
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

        // A big block of ugly spaghetti code that must be replaced with the state machine

        FlightReservationResponse flightReservationResponse = flightReservationService.bookFlight(
                FlightReservationRequest.builder()
                        .bookingId(uuid)
                        .code(travelRequest.getFlightCode())
                        .build());
        if (FlightReservationStatus.OK != flightReservationResponse.getStatus()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        HotelReservationResponse hotelReservationResponse = hotelReservationService
                .bookHotel(HotelReservationRequest.builder()
                        .bookingId(uuid)
                        .name(travelRequest.getHotelName())
                        .build());
        if (HotelReservationStatus.OK != hotelReservationResponse.getStatus()) {
            flightReservationService.cancel(
                    flightReservationResponse.getId(),
                    FlightCancellationRequest.builder()
                            .bookingId(flightReservationResponse.getBookingId())
                            .reservationId(flightReservationResponse.getId())
                            .build());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        CarRentalResponse carReservationResponse = carRentalService
                .bookCar(CarRentalRequest.builder()
                        .bookingId(uuid)
                        .name(travelRequest.getCarName())
                        .build());
        if (CarRentalStatus.OK != carReservationResponse.getStatus()) {
            flightReservationService.cancel(
                    carReservationResponse.getId(),
                    FlightCancellationRequest.builder()
                            .bookingId(flightReservationResponse.getBookingId())
                            .reservationId(flightReservationResponse.getId())
                            .build());
            hotelReservationService.cancel(
                    hotelReservationResponse.getId(),
                    HotelCancellationRequest.builder()
                            .bookingId(hotelReservationResponse.getBookingId())
                            .reservationId(hotelReservationResponse.getId())
                            .build());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return TravelStatus.builder()
                .hotelReservationResponse(hotelReservationResponse)
                .flightReservationResponse(flightReservationResponse)
                .carRentalResponse(carReservationResponse)
                .build();
    }

    public TravelStatus getTravelStatus(UUID id) {
        return TravelStatus.builder().build();
    }
}
