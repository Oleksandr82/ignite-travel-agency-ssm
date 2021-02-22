package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.travel.agent.mapper.TripRequestMapper;
import tech.travel.agent.model.Status;
import tech.travel.model.CarRentalResponse;
import tech.travel.model.CarRentalStatus;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.model.FlightReservationResponse;
import tech.travel.model.FlightReservationStatus;
import tech.travel.model.HotelCancellationRequest;
import tech.travel.model.HotelReservationResponse;
import tech.travel.model.HotelReservationStatus;
import tech.travel.agent.model.TripRequest;
import tech.travel.agent.model.TripStatus;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final CarRentalService carRentalService;
    private final HotelReservationService hotelReservationService;
    private final FlightReservationService flightReservationService;
    private final TripRequestMapper tripRequestMapper;
    private final TripStatusService tripStatusService;

    public TripStatus bookTrip(TripRequest tripRequest) {

        UUID travelId = UUID.randomUUID();

        // A big block of ugly spaghetti code that must be replaced with the state machine

        FlightReservationResponse flightReservationResponse = flightReservationService.bookFlight(
                tripRequestMapper.toFlightReservationRequest(travelId, tripRequest));
        if (FlightReservationStatus.OK != flightReservationResponse.getStatus()) {
            return TripStatus.builder()
                    .status(Status.FAILED)
                    .build();
        }

        HotelReservationResponse hotelReservationResponse = hotelReservationService
                .bookHotel(tripRequestMapper.toHotelReservationRequest(travelId, tripRequest));
        if (HotelReservationStatus.OK != hotelReservationResponse.getStatus()) {
            flightReservationService.cancel(flightReservationResponse.getId(),
                    FlightCancellationRequest.builder()
                            .tripId(flightReservationResponse.getTripId())
                            .reservationId(flightReservationResponse.getId())
                            .build());
            return TripStatus.builder()
                    .status(Status.FAILED)
                    .build();
        }

        CarRentalResponse carReservationResponse = carRentalService
                .bookCar(tripRequestMapper.toCarRentalRequest(travelId, tripRequest));
        if (CarRentalStatus.OK != carReservationResponse.getStatus()) {
            hotelReservationService.cancel(hotelReservationResponse.getId(),
                    HotelCancellationRequest.builder()
                            .tripId(hotelReservationResponse.getTripId())
                            .reservationId(hotelReservationResponse.getId())
                            .build());
            flightReservationService.cancel(flightReservationResponse.getId(),
                    FlightCancellationRequest.builder()
                            .tripId(flightReservationResponse.getTripId())
                            .reservationId(flightReservationResponse.getId())
                            .build());
            return TripStatus.builder()
                    .status(Status.FAILED)
                    .build();
        }

        TripStatus tripStatus = TripStatus.builder()
                .travelId(travelId)
                .hotelReservationResponse(hotelReservationResponse)
                .flightReservationResponse(flightReservationResponse)
                .carRentalResponse(carReservationResponse)
                .status(Status.BOOKED_SUCCESSFULLY)
                .build();

        tripStatusService.save(tripStatus);

        return tripStatus;
    }

    public TripStatus getTripStatus(UUID id) {
        return tripStatusService.findByTravelId(id).orElseThrow();
    }
}
