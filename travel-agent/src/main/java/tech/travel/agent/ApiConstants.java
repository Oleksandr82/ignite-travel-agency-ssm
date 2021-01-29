package tech.travel.agent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstants {

    public static final String BOOK_CAR = "api/v1/cars";
    public static final String BOOK_HOTEL = "api/v1/hotels";
    public static final String RESERVE_FLIGHT = "api/v1/flights";

    public static final String GET_TRAVEL_INFO = "api/v1/booking/travel/{id}";
    public static final String BOOK_TRAVEL = "api/v1/booking/travel";
}
