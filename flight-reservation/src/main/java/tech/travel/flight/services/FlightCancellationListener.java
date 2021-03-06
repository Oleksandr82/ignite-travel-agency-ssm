package tech.travel.flight.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.flight.config.JmsConfig;
import tech.travel.model.FlightCancellationRequest;
import tech.travel.model.FlightReservationResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlightCancellationListener {

    private final FlightReservationService flightReservationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.FLIGHT_CANCELLATION_REQUEST)
    public void listen(FlightCancellationRequest flightCancellationRequest) {
        log.debug("Cancel a Flight reservation (JMS): {}", flightCancellationRequest);
        FlightReservationResponse flightReservationResult = flightReservationService.cancel(flightCancellationRequest);
        jmsTemplate.convertAndSend(JmsConfig.FLIGHT_CANCELLATION_RESPONSE, flightReservationResult);
    }
}
