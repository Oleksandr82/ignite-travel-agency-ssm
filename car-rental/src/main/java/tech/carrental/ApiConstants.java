package tech.carrental;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstants {

    public static final String GET_ALL_CARS = "api/v1/booking/cars";
    public static final String BOOK_CAR = "api/v1/booking/{bookingId}/car/{carId}";
}
