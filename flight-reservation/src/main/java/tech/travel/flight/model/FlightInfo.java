package tech.travel.flight.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class FlightInfo {

    UUID id;
    String code;
    String departureAirport;
    String destinationAirport;
    BigDecimal price;
    FlightClass flightClass;
    boolean available;
}
