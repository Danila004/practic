package ru.vsu.practic.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.vsu.practic.dto.BookingDetailsResponse;
import ru.vsu.practic.dto.BookingResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class BookingDetailsRowMapper implements RowMapper<BookingDetailsResponse> {
    @Override
    public BookingDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookingDetailsResponse dto = new BookingDetailsResponse();
        dto.setBookingId(rs.getLong("booking_id"));
        dto.setStatus(rs.getString("status"));
        dto.setEquipmentOption(rs.getString("equipment_option"));
        dto.setStartDatetime(rs.getObject("start_datetime", LocalDateTime.class));
        dto.setZoneName(rs.getString("zone_name"));
        dto.setInstructorName(rs.getString("instructor_name"));
        dto.setCancellationReason(rs.getString("cancellation_reason"));
        return dto;
    }
}
