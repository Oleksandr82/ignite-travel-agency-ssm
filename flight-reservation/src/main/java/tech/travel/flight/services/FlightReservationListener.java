package tech.travel.flight.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.flight.config.JmsConfig;
import tech.travel.model.FlightReservationRequest;
import tech.travel.model.FlightReservationResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlightReservationListener {

    private final FlightReservationService flightReservationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.FLIGHT_RESERVATION_REQUEST)
    public void listen(FlightReservationRequest flightReservationRequest) {
        log.debug("Reserve a Flight (JMS): {}", flightReservationRequest);
        FlightReservationResponse flightReservationResult = flightReservationService.bookFlight(flightReservationRequest);
        jmsTemplate.convertAndSend(JmsConfig.FLIGHT_RESERVATION_RESPONSE, flightReservationResult);
    }
}
