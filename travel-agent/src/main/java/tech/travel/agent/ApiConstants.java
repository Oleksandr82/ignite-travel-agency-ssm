package tech.travel.agent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstants {

    public static final String CAR_RESERVATION = "api/v1/cars";
    public static final String HOTEL_RESERVATION = "api/v1/hotels";
    public static final String FLIGHT_RESERVATION = "api/v1/flights";

    public static final String GET_TRAVEL_INFO = "api/v1/booking/trip/{id}";
    public static final String BOOK_TRIP = "api/v1/booking/trip";

    public static final String GET_TRIP_INFO_V2 = "api/v2/booking/trip/{id}";
    public static final String BOOK_TRAVEL_V2 = "api/v2/booking/trip";
}
