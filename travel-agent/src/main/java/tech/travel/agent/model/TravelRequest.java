package tech.travel.agent.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TravelRequest {

    @NotBlank
    String travelName;

    @NotBlank
    String flightCode;

    @NotBlank
    String hotelName;

    @NotBlank
    String carName;
}
