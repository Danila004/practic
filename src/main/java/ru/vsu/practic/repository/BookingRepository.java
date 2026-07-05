package ru.vsu.practic.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vsu.practic.dto.BookingDetailsResponse;
import ru.vsu.practic.dto.BookingResponse;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookingRepository {

    private final JdbcTemplate jdbc;
    private final BookingRowMapper rowMapper;
    private final BookingDetailsRowMapper bookingDetailsRowMapper;

    public BookingRepository(JdbcTemplate jdbc, BookingRowMapper rowMapper, BookingDetailsRowMapper bookingDetailsRowMapper) {
        this.jdbc = jdbc;
        this.rowMapper = rowMapper;
        this.bookingDetailsRowMapper = bookingDetailsRowMapper;
    }

    public List<BookingResponse> findAllByClientId(Integer clientId) {
        String sql = """
            SELECT
                b.booking_id,
                b.slot_id,
                b.status                AS booking_status,
                b.equipment_option,
                b.is_late_cancellation,
                b.created_at,
                b.cancelled_at          AS booking_cancelled_at,
                ts.slot_id              AS slot_id,
                ts.start_datetime,
                ts.duration_minutes,
                ts.status               AS slot_status,
                ts.cancellation_reason,
                z.name                  AS zone_name,
                z.level                 AS zone_level,
                i.instructor_id,
                i.full_name             AS instructor_name
            FROM Booking b
            JOIN TrainingSlot ts ON ts.slot_id = b.slot_id
            JOIN Zone          z  ON z.zone_id  = ts.zone_id
            JOIN Instructor    i  ON i.instructor_id = ts.instructor_id
            WHERE b.client_id = ?
            ORDER BY ts.start_datetime ASC
            """;
        return jdbc.query(sql, rowMapper, clientId);
    }

    public BookingDetailsResponse findBookingDetailsById(Long bookingId) {
        String sql = """
                SELECT b.booking_id, b.status, b.equipment_option, 
                       ts.start_datetime, z.name AS zone_name, 
                       i.full_name AS instructor_name, ts.cancellation_reason
                FROM Booking b
                JOIN TrainingSlot ts ON b.slot_id = ts.slot_id
                JOIN Zone z ON ts.zone_id = z.zone_id
                JOIN Instructor i ON ts.instructor_id = i.instructor_id
                WHERE b.booking_id = ?
                """;

        return jdbc.queryForObject(sql, bookingDetailsRowMapper, bookingId);
    }

    public void updateBookingCancellation(Long bookingId, String newStatus, boolean isLateCancellation, LocalDateTime cancelledAt) {
        String sql = """
            UPDATE Booking 
            SET status = ?, is_late_cancellation = ?, cancelled_at = ? 
            WHERE booking_id = ?
            """;
        jdbc.update(sql, newStatus, isLateCancellation, cancelledAt, bookingId);
    }
}
