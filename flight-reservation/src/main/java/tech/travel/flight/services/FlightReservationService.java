package tech.travel.flight.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.flight.model.FlightInfo;
import tech.travel.flight.model.FlightReservationRequest;
import tech.travel.flight.model.FlightReservationStatus;
import tech.travel.flight.model.FlightClass;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightReservationService {

    public static final int CURRENCY_EXPONENT = 2;

    private final List<FlightInfo> flights = List.of(

            FlightInfo.builder()
                    .id(UUID.fromString("4e18afe0-3f1f-426b-a7c7-82e8be93036a"))
                    .code("KL1009")
                    .departureAirport("AMS")
                    .destinationAirport("LHR")
                    .flightClass(FlightClass.PREMIUM)
                    .available(true)
                    .build(),

            FlightInfo.builder()
                    .id(UUID.fromString("3bde95ae-179e-4a8a-9351-fabd73611260"))
                    .code("KL1385")
                    .departureAirport("AMS")
                    .destinationAirport("KBP")
                    .flightClass(FlightClass.ECONOMIC)
                    .available(true)
                    .build(),

            FlightInfo.builder()
                    .id(UUID.fromString("782a4090-ffc8-43f6-a93e-f042e056e80e"))
                    .code("KL1234")
                    .departureAirport("AMS")
                    .destinationAirport("LHR")
                    .flightClass(FlightClass.BUSINESS)
                    .available(false)
                    .build()
    );

    public List<FlightInfo> getAll() {
        return flights;
    }

    public FlightReservationStatus bookFlight(FlightReservationRequest flightReservationRequest) {

        Optional<FlightInfo> flightInfo = flights.stream()
                .filter(flight -> flightReservationRequest.getCode().equals(flight.getCode()))
                .findFirst();

        return registerFlightReservationEvent(flightReservationRequest.getBookingId(), flightInfo);
    }

    private FlightReservationStatus registerFlightReservationEvent(UUID bookingId, Optional<FlightInfo> flightInfo) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return FlightReservationStatus.builder()
                .id(UUID.randomUUID())
                .bookingId(bookingId)
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .flightId(flightInfo.map(FlightInfo::getId).orElse(null))
                .available(flightInfo.map(FlightInfo::isAvailable).orElse(false))
                .found(flightInfo.isPresent())
                .build();
    }

    public FlightInfo getFlightInfo(UUID flightId) {
        return flights.stream()
                .filter(flight -> flightId.equals(flight.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
