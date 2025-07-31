package com.acmeair.flightbooking.controller;

import com.acmeair.flightbooking.model.BookingDto;
import com.acmeair.flightbooking.model.FlightDto;
import com.acmeair.flightbooking.model.PassengerDto;
import com.acmeair.flightbooking.service.FlightBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class FlightBookingController {

    private final FlightBookingService flightService;

    @Autowired
    public FlightBookingController(FlightBookingService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flight/search")
    public ResponseEntity<Page<FlightDto>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime departureTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "departureTime") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<FlightDto> results = flightService.searchFlights(
                origin, destination, departureTime, page, size, sortBy, sortDir);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/flights/{id}")
    public FlightDto getFlight(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @PostMapping("/bookings")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BookingDto> bookFlight(@RequestParam Long flightId, @Valid @RequestBody PassengerDto passenger) {
        return new ResponseEntity<>(flightService.createBooking(flightId, passenger), HttpStatus.CREATED);
    }

    @PutMapping("/bookings/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BookingDto> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerDto passenger) {
        return ResponseEntity.ok(flightService.updatePassengerDetails(id, passenger));
    }

    @DeleteMapping("/bookings/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        flightService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

}
