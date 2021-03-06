package tech.travel.hotel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BOOK_HOTEL_REQUEST = "hotel-reservation-request";
    public static final String BOOK_HOTEL_RESPONSE = "hotel-reservation-response";
    public static final String CANCEL_HOTEL_REQUEST = "hotel-cancellation-request";
    public static final String CANCEL_HOTEL_RESPONSE = "hotel-cancellation-response";
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
