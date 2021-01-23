package tech.carrental.services;

import org.springframework.stereotype.Service;
import tech.carrental.model.CarInfo;
import tech.carrental.model.CarRentalConfirmation;
import tech.carrental.model.Transmission;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CarRentalService {

    public static final int CURRENCY_EXPONENT = 2;

    private final List<CarInfo> cars = List.of(

            CarInfo.builder()
                    .id(UUID.fromString("9f805fff-1912-48a6-ae06-5c4b906f4179"))
                    .name("Audi R8 Spyder 4.2 FSI Quattro")
                    .basicPrice(BigDecimal.valueOf(20670, CURRENCY_EXPONENT))
                    .consumption(BigDecimal.valueOf(2130, CURRENCY_EXPONENT))
                    .power(430)
                    .doors(2)
                    .transmission(Transmission.MANUAL)
                    .build(),

            CarInfo.builder()
                    .id(UUID.fromString("9c9a85ca-4a78-4b4f-88a8-9f0690caf667"))
                    .name("Fiat 500")
                    .basicPrice(BigDecimal.valueOf(5500, CURRENCY_EXPONENT))
                    .consumption(BigDecimal.valueOf(530, CURRENCY_EXPONENT))
                    .power(70)
                    .doors(2)
                    .transmission(Transmission.AUTOMATIC)
                    .build()
    );

    public CarRentalConfirmation rent(UUID bookingId, UUID carId) {

        return CarRentalConfirmation.builder()
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .bookingId(bookingId)
                .carId(carId)
                .build();
    }

    public List<CarInfo> getAll() {
        return cars;
    }
}
