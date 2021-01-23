package tech.carrental.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class CarInfo {

    UUID id;
    String name;
    BigDecimal basicPrice;
    Integer doors;
    Transmission transmission;
    Integer power;
    BigDecimal consumption;
    //BodyType bodyType;
}
