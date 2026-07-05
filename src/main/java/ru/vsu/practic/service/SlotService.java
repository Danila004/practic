package ru.vsu.practic.service;

import org.springframework.stereotype.Service;
import ru.vsu.practic.dto.SlotResponse;
import ru.vsu.practic.repository.SlotRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {
    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<SlotResponse> getSlots(LocalDate fromDate, LocalDate toDate) {
        LocalDate from = (fromDate != null) ? fromDate : LocalDate.now();
        LocalDate to = (toDate != null) ? toDate : from.plusDays(7);

        return slotRepository.findSlots(from, to);
    }
}
