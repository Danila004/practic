package ru.vsu.practic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.practic.dto.BookingDetailsResponse;
import ru.vsu.practic.dto.BookingResponse;
import ru.vsu.practic.repository.BookingRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsForClient(Integer clientId) {
        return bookingRepository.findAllByClientId(clientId);
    }

    public BookingDetailsResponse getBookingDetails(Long bookingId) {
        return bookingRepository.findBookingDetailsById(bookingId);
    }

    @Transactional
    public void cancelBooking(Long bookingId, boolean confirmLateCancellation) {
        BookingDetailsResponse booking = getBookingDetails(bookingId);

        if (!"active".equals(booking.getStatus())) {
            throw new IllegalStateException("Запись уже отменена или завершена");
        }

        LocalDateTime startDatetime = booking.getStartDatetime();
        long minutesUntilStart = ChronoUnit.MINUTES.between(LocalDateTime.now(), startDatetime);

        boolean isLate = minutesUntilStart < 10;
        bookingRepository.updateBookingCancellation(
                bookingId,
                "cancelled_by_client",
                isLate,
                LocalDateTime.now()
        );
    }
}
