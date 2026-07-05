package ru.vsu.practic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.practic.dto.BookingResponse;
import ru.vsu.practic.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final BookingService bookingService;

    public ClientController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(path = "/{clientId}/bookings")
    public List<BookingResponse> getMyBookings(@PathVariable Integer clientId) {
        return bookingService.getBookingsForClient(clientId);
    }
}
