package ru.vsu.practic.dto;

import java.time.LocalDateTime;

public record BookingResponse(
        Integer bookingId,
        Integer slotId,
        String status,
        String equipmentOption,
        Boolean isLateCancellation,
        LocalDateTime createdAt,
        LocalDateTime cancelledAt,
        SlotInfo slot,
        String cancellationReason
) {
}
