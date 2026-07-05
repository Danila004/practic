package ru.vsu.practic.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.vsu.practic.dto.SlotResponse;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SlotRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SlotRowMapper slotRowMapper;

    public SlotRepository(JdbcTemplate jdbcTemplate, SlotRowMapper slotRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.slotRowMapper = slotRowMapper;
    }

    public List<SlotResponse> findSlots(LocalDate fromDate, LocalDate toDate) {
        String sql = """
            SELECT 
                ts.slot_id,
                ts.start_datetime,
                z.name AS zone_name,
                i.full_name AS instructor_name,
                (z.capacity - COALESCE(SUM(CASE WHEN b.status = 'active' THEN 1 ELSE 0 END), 0)) AS available_spots
            FROM TrainingSlot ts
            JOIN Zone z ON ts.zone_id = z.zone_id
            JOIN Instructor i ON ts.instructor_id = i.instructor_id
            LEFT JOIN Booking b ON ts.slot_id = b.slot_id
            WHERE ts.status = 'active'
              AND ts.start_datetime >= ?
              AND ts.start_datetime <= ?
            GROUP BY ts.slot_id, ts.start_datetime, z.name, i.full_name, z.capacity
            ORDER BY ts.start_datetime ASC
            """;

        // Преобразуем LocalDate в Timestamp для корректной работы с типом datetime в БД
        Timestamp fromTs = Timestamp.valueOf(fromDate.atStartOfDay());
        Timestamp toTs = Timestamp.valueOf(toDate.atTime(23, 59, 59));

        return jdbcTemplate.query(sql, slotRowMapper, fromTs, toDate);
    }
}
