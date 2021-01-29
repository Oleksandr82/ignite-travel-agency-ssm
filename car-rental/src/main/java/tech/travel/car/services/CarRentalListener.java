package tech.travel.car.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.car.config.JmsConfig;
import tech.travel.car.model.CarRentalRequest;
import tech.travel.car.model.CarRentalStatus;

@Slf4j
@RequiredArgsConstructor
@Component
public class CarRentalListener {

    private final CarRentalService carRentalService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.RENT_CAR_REQUEST)
    public void listen(CarRentalRequest carRentalRequest) {

        CarRentalStatus carRentalStatus = carRentalService.rent(carRentalRequest);
        jmsTemplate.convertAndSend(JmsConfig.RENT_CAR_RESPONSE, carRentalStatus);
    }
}
