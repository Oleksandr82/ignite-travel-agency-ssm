package tech.travel.hotel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.hotel.model.HotelCancellationRequest;
import tech.travel.hotel.model.HotelInfo;
import tech.travel.hotel.model.HotelReservationRequest;
import tech.travel.hotel.model.HotelReservationResponse;
import tech.travel.hotel.model.HotelReservationStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelReservationService {

    public static final int CURRENCY_EXPONENT = 2;

    private final List<HotelInfo> hotel = List.of(

            HotelInfo.builder()
                    .id(UUID.fromString("193f7706-9093-449f-95e0-7f720b98e7f1"))
                    .hotelName("TULIP INN")
                    .location("NL,Leiden")
                    .rooms(10)
                    .basicPrice(BigDecimal.valueOf(10000, CURRENCY_EXPONENT))
                    .available(true)
                    .build(),

            HotelInfo.builder()
                    .id(UUID.fromString("e856051a-0f3e-4b88-b5e4-6c65c6276d31"))
                    .hotelName("Hilton Garde Inn")
                    .location("NL,Utrecht")
                    .rooms(30)
                    .basicPrice(BigDecimal.valueOf(80000, CURRENCY_EXPONENT))
                    .available(true)
                    .build()
    );

    public List<HotelInfo> getAll() {
        return hotel;
    }

    public HotelReservationResponse book(HotelReservationRequest hotelReservationRequest) {

        Optional<HotelInfo> hotelInfo = hotel.stream()
                .filter(hotel -> hotelReservationRequest.getName().equals(hotel.getHotelName()))
                .findFirst();

        return registerBookingEvent(hotelReservationRequest.getBookingId(), hotelInfo);
    }

    private HotelReservationStatus hotelInfoToStatus(HotelInfo hotelInfo) {
        if (hotelInfo == null) {
            return HotelReservationStatus.NOT_FOUND;
        } else {
            return hotelInfo.isAvailable() ? HotelReservationStatus.OK : HotelReservationStatus.NOT_AVAILABLE;
        }
    }

    private HotelReservationResponse registerBookingEvent(UUID bookingId, Optional<HotelInfo> hotelInfo) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return HotelReservationResponse.builder()
                .id(UUID.randomUUID())
                .bookingId(bookingId)
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .hotelId(hotelInfo.map(HotelInfo::getId).orElse(null))
                .status(hotelInfoToStatus(hotelInfo.orElse(null)))
                .build();
    }

    public HotelInfo getHotelInfo(UUID hotelId) {
        return hotel.stream()
                .filter(hotel -> hotelId.equals(hotel.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public HotelReservationResponse cancel(HotelCancellationRequest cancellationRequest) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return HotelReservationResponse.builder()
                .id(cancellationRequest.getReservationId())
                .bookingId(cancellationRequest.getBookingId())
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .hotelId(UUID.randomUUID())
                .status(HotelReservationStatus.CANCELLED.CANCELLED)
                .build();
    }
}
