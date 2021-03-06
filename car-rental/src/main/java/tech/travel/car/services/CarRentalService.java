package tech.travel.car.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.travel.model.CarCancellationRequest;
import tech.travel.car.model.CarInfo;
import tech.travel.model.CarRentalRequest;
import tech.travel.model.CarRentalResponse;
import tech.travel.model.CarRentalStatus;
import tech.travel.car.model.Transmission;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarRentalService {

    public static final int CURRENCY_EXPONENT = 2;

    private final List<CarInfo> cars = List.of(

            CarInfo.builder()
                    .id(UUID.fromString("9f805fff-1912-48a6-ae06-5c4b906f4179"))
                    .name("Audi")
                    .fullName("Audi R8 Spyder 4.2 FSI Quattro")
                    .basicPrice(BigDecimal.valueOf(20670, CURRENCY_EXPONENT))
                    .consumption(BigDecimal.valueOf(2130, CURRENCY_EXPONENT))
                    .power(430)
                    .doors(2)
                    .transmission(Transmission.MANUAL)
                    .available(false)
                    .build(),

            CarInfo.builder()
                    .id(UUID.fromString("9c9a85ca-4a78-4b4f-88a8-9f0690caf667"))
                    .name("Fiat")
                    .fullName("Fiat 500")
                    .basicPrice(BigDecimal.valueOf(5500, CURRENCY_EXPONENT))
                    .consumption(BigDecimal.valueOf(530, CURRENCY_EXPONENT))
                    .power(70)
                    .doors(2)
                    .transmission(Transmission.AUTOMATIC)
                    .available(true)
                    .build()
    );

    public List<CarInfo> getAll() {
        return cars;
    }

    public CarRentalResponse rent(CarRentalRequest carRentalRequest) {

        Optional<CarInfo> carInfo = cars.stream()
                .filter(car -> carRentalRequest.getName().equals(car.getName()))
                .findFirst();

        return registerCarRentEvent(carRentalRequest.getTripId(), carInfo);
    }

    private CarRentalResponse registerCarRentEvent(UUID bookingId, Optional<CarInfo> carInfo) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return CarRentalResponse.builder()
                .id(UUID.randomUUID())
                .tripId(bookingId)
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .carId(carInfo.map(CarInfo::getId).orElse(null))
                .status(carInfoToStatus(carInfo.orElse(null)))
                .build();
    }

    public CarInfo getCarInfo(UUID carId) {
        return cars.stream()
                .filter(car -> carId.equals(car.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private CarRentalStatus carInfoToStatus(CarInfo carInfo) {
        if (carInfo == null) {
            return CarRentalStatus.NOT_FOUND;
        } else {
            return carInfo.isAvailable() ? CarRentalStatus.OK : CarRentalStatus.NOT_AVAILABLE;
        }
    }

    public CarRentalResponse cancel(CarCancellationRequest carCancellationRequest) {

        OffsetDateTime eventDateTime = OffsetDateTime.now();
        return CarRentalResponse.builder()
                .id(carCancellationRequest.getReservationId())
                .tripId(carCancellationRequest.getTripId())
                .createdDate(eventDateTime)
                .lastModifiedDate(eventDateTime)
                .carId(UUID.randomUUID())
                .status(CarRentalStatus.CANCELLED)
                .build();
    }
}
