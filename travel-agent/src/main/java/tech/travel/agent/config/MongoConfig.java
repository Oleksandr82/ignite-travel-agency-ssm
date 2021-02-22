package tech.travel.agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new OffsetDateTimeReadConverter(),
                new OffsetDateTimeWriteConverter()
        ));
    }

    static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

        @Override
        public Date convert(OffsetDateTime source) {
            return source == null ? null : Date.from(source.toInstant().atZone(ZoneOffset.UTC).toInstant());
        }
    }

    static class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {

        @Override
        public OffsetDateTime convert(Date source) {
            return source == null ? null : source.toInstant().atOffset(ZoneOffset.UTC);
        }
    }
}