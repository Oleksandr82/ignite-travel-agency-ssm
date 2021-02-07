package tech.travel.car.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.car.config.JmsConfig;
import tech.travel.car.model.CarCancellationRequest;
import tech.travel.car.model.CarRentalRequest;
import tech.travel.car.model.CarRentalResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class CarCancellationListener {

    private final CarRentalService carRentalService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.CANCEL_CAR_REQUEST)
    public void listen(CarCancellationRequest carCancellationRequest) {

        CarRentalResponse carRentalResponse = carRentalService.cancel(carCancellationRequest);
        jmsTemplate.convertAndSend(JmsConfig.CANCEL_CAR_RESPONSE, carRentalResponse);
    }
}
