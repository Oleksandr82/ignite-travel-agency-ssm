package tech.travel.agent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String RENT_CAR_REQUEST = "rent-car-request";
    public static final String RENT_CAR_RESPONSE = "rent-car-response";
    public static final String BOOK_HOTEL_REQUEST = "book-hotel-request";
    public static final String BOOK_HOTEL_RESPONSE = "book-hotel-response";
    public static final String FLIGHT_RESERVATION_REQUEST = "flight-reservation-request";
    public static final String FLIGHT_RESERVATION_RESPONSE = "flight-reservation-response";

    public static final String TYPE_PROP_NAME = "_type";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPE_PROP_NAME);
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
