package tech.travel.agent.sm;

public enum ReservationEvent {
    INITIALIZE,

    FLIGHT_RESERVATION_COMPLETED,
    FLIGHT_CANCELLATION_COMPLETED,

    HOTEL_RESERVATION_COMPLETED,
    HOTEL_CANCELLATION_COMPLETED,

    CAR_RESERVATION_COMPLETED,
    CAR_CANCELLATION_COMPLETED
}
