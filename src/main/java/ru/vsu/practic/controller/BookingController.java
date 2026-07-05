package ru.vsu.practic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.practic.dto.BookingDetailsResponse;
import ru.vsu.practic.dto.BookingResponse;
import ru.vsu.practic.dto.CancelBookingRequest;
import ru.vsu.practic.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{bookingId}")
    public BookingDetailsResponse getBookingDetails(@PathVariable Long bookingId) {
        return bookingService.getBookingDetails(bookingId);
    }

    // SCR-05, п. 3
    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId, @RequestBody CancelBookingRequest request) {
        bookingService.cancelBooking(bookingId, request.isConfirmLateCancellation());
        return ResponseEntity.ok().build();
    }
}