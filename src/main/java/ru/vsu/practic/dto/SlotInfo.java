package ru.vsu.practic.dto;

import java.time.LocalDateTime;

public record SlotInfo(
        Integer slotId,
        String zoneName,
        String zoneLevel,
        String instructorName,
        Integer instructorId,
        LocalDateTime startDatetime,
        Integer durationMinutes,
        String slotStatus
) {
}
