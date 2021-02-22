package tech.travel.hotel.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.hotel.config.JmsConfig;
import tech.travel.model.HotelCancellationRequest;
import tech.travel.model.HotelReservationResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class HotelCancellationListener {

    private final HotelReservationService hotelReservationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.CANCEL_HOTEL_REQUEST)
    public void listen(HotelCancellationRequest cancellationRequest) {
        log.debug("Cancel a Hotel reservation (JMS): {}", cancellationRequest);
        HotelReservationResponse cancellationResponse = hotelReservationService.cancel(cancellationRequest);
        jmsTemplate.convertAndSend(JmsConfig.CANCEL_HOTEL_RESPONSE, cancellationResponse);
    }
}
