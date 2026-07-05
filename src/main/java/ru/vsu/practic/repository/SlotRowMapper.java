package ru.vsu.practic.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.vsu.practic.dto.BookingDetailsResponse;
import ru.vsu.practic.dto.SlotResponse;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SlotRowMapper implements RowMapper<SlotResponse> {
    @Override
    public SlotResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SlotResponse(
                rs.getLong("slot_id"),
                rs.getTimestamp("start_datetime").toLocalDateTime(),
                rs.getString("zone_name"),
                rs.getString("instructor_name"),
                rs.getInt("available_spots"));
    }
}
