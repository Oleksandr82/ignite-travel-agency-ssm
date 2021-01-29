package tech.travel.car.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class CarInfo {

    UUID id;
    String name;
    String fullName;
    BigDecimal basicPrice;
    Integer doors;
    Transmission transmission;
    Integer power;
    BigDecimal consumption;
    boolean available;
}
