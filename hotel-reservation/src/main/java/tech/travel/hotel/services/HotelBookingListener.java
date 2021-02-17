package tech.travel.hotel.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tech.travel.hotel.config.JmsConfig;
import tech.travel.model.HotelReservationRequest;
import tech.travel.model.HotelReservationResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class HotelBookingListener {

    private final HotelReservationService hotelReservationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BOOK_HOTEL_REQUEST)
    public void listen(HotelReservationRequest hotelReservationRequest) {
        log.debug("Book a Hotel (JMS): {}", hotelReservationRequest);
        HotelReservationResponse carRentalResult = hotelReservationService.book(hotelReservationRequest);
        jmsTemplate.convertAndSend(JmsConfig.BOOK_HOTEL_RESPONSE, carRentalResult);
    }
}
