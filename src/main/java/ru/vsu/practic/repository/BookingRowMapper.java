package ru.vsu.practic.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.vsu.practic.dto.BookingResponse;
import ru.vsu.practic.dto.SlotInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class BookingRowMapper implements RowMapper<BookingResponse> {

    @Override
    public BookingResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer bookingId   = rs.getInt("booking_id");
        Integer slotId      = rs.getInt("slot_id");
        String  status      = rs.getString("booking_status");
        String  equip       = rs.getString("equipment_option");
        boolean lateCancel  = rs.getBoolean("is_late_cancellation");
        if (rs.wasNull()) lateCancel = false; // для примитивов

        LocalDateTime createdAt   = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime cancelledAt = rs.getTimestamp("booking_cancelled_at") == null ?
                null :
                rs.getTimestamp("booking_cancelled_at").toLocalDateTime();
        String cancellationReason = rs.getString("cancellation_reason");

        SlotInfo slot = new SlotInfo(
                slotId,
                rs.getString("zone_name"),
                rs.getString("zone_level"),
                rs.getString("instructor_name"),
                rs.getInt("instructor_id"),
                rs.getTimestamp("start_datetime").toLocalDateTime(),
                rs.getInt("duration_minutes"),
                rs.getString("slot_status")
        );

        return new BookingResponse(
                bookingId, slotId, status, equip, lateCancel,
                createdAt, cancelledAt, slot, cancellationReason
        );
    }
}
