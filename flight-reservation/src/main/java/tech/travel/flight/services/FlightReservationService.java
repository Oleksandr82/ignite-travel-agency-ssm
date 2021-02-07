package tech.travel.flight.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.flight.model.FlightCancellationRequest;
import tech.travel.flight.model.FlightInfo;
import tech.travel.flight.model.FlightReservationRequest;
import tech.travel.flight.model.FlightReservationResponse;
import tech.travel.flight.model.FlightClass;
import tech.travel.flight.model.FlightReservationStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightReservationService {

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

    public FlightReservationResponse bookFlight(FlightReservationRequest flightReservationRequest) {

        Optional<FlightInfo> flightInfo = flights.stream()
                .filter(flight -> flightReservationRequest.getCode().equals(flight.getCode()))
                .findFirst();

        return registerFlightReservationEvent(flightReservationRequest.getBookingId(), flightInfo);
    }

    private FlightReservationResponse registerFlightReservationEvent(UUID bookingId, Optional<FlightInfo> flightInfo) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return FlightReservationResponse.builder()
                .id(UUID.randomUUID())
                .bookingId(bookingId)
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .flightId(flightInfo.map(FlightInfo::getId).orElse(null))
                .status(flightInfoToStatus(flightInfo.orElse(null)))
                .build();
    }

    public FlightInfo getFlightInfo(UUID flightId) {
        return flights.stream()
                .filter(flight -> flightId.equals(flight.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private FlightReservationStatus flightInfoToStatus(FlightInfo flightInfo) {
        if (flightInfo == null) {
            return FlightReservationStatus.NOT_FOUND;
        } else {
            return flightInfo.isAvailable() ? FlightReservationStatus.OK : FlightReservationStatus.NOT_AVAILABLE;
        }
    }

    public FlightReservationResponse cancel(FlightCancellationRequest flightCancellationRequest) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return FlightReservationResponse.builder()
                .id(flightCancellationRequest.getReservationId())
                .bookingId(flightCancellationRequest.getBookingId())
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .flightId(UUID.randomUUID())
                .status(FlightReservationStatus.CANCELLED)
                .build();
    }
}
