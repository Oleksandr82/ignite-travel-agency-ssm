package tech.travel.hotel.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class HotelInfo {

    UUID id;
    String hotelName;
    String location;
    BigDecimal basicPrice;
    Integer rooms;
    boolean available;
}
