package tech.travel.car.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.car.config.JmsConfig;
import tech.travel.model.CarRentalRequest;
import tech.travel.model.CarRentalResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class CarRentalListener {

    private final CarRentalService carRentalService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.RENT_CAR_REQUEST)
    public void listen(CarRentalRequest carReservationRequest) {
        log.debug("Rent a Car (JMS): {}", carReservationRequest);
        CarRentalResponse carRentalResponse = carRentalService.rent(carReservationRequest);
        jmsTemplate.convertAndSend(JmsConfig.RENT_CAR_RESPONSE, carRentalResponse);
    }
}
