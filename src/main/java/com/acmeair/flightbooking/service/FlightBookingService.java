package com.acmeair.flightbooking.service;

import com.acmeair.flightbooking.entity.Booking;
import com.acmeair.flightbooking.entity.Flight;
import com.acmeair.flightbooking.entity.Passenger;
import com.acmeair.flightbooking.exception.ResourceNotFoundException;
import com.acmeair.flightbooking.model.BookingDto;
import com.acmeair.flightbooking.model.FlightDto;
import com.acmeair.flightbooking.model.PassengerDto;
import com.acmeair.flightbooking.repository.BookingRepository;
import com.acmeair.flightbooking.repository.FlightRepository;
import com.acmeair.flightbooking.repository.PassengerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightBookingService {


    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public FlightBookingService(FlightRepository flightRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    public Page<FlightDto> searchFlights(String origin, String destination, LocalDateTime departureTime,
                                      int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        if (departureTime != null) {
            List<Flight> listOfFlight = flightRepository.findByOriginAndDestinationAndDepartureTime(origin, destination, departureTime, pageable).getContent();
            return new PageImpl<>(listOfFlight.stream().map(flight -> this.modelMapper.map(flight, FlightDto.class)).collect(Collectors.toList()));
        } else {
            List<Flight> listOfFlight = flightRepository.findByOriginAndDestination(origin, destination, pageable).getContent();
            return new PageImpl<>(listOfFlight.stream().map(flight -> this.modelMapper.map(flight, FlightDto.class)).collect(Collectors.toList()));
        }
    }

    public FlightDto getFlightById(Long id) {
        FlightDto flightDto = this.modelMapper.map(flightRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Flight not found")), FlightDto.class);
        return flightDto;
    }

    public BookingDto createBooking(Long flightId, PassengerDto passengerDto) {
        Passenger passenger = this.modelMapper.map(passengerDto, Passenger.class);
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));
        passengerRepository.save(passenger);
        FlightDto flightDto = this.modelMapper.map(flight, FlightDto.class);
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        BookingDto bookingDto = this.modelMapper.map(bookingRepository.save(booking), BookingDto.class);
        bookingDto.setFlight(flightDto);
        return bookingDto;
    }

    public BookingDto updatePassengerDetails(Long bookingId, PassengerDto updatedPassengerDto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        Passenger passenger = booking.getPassenger();
        passenger.setName(updatedPassengerDto.getName());
        passenger.setEmail(updatedPassengerDto.getEmail());
        passengerRepository.save(passenger);
        BookingDto bookingDto = this.modelMapper.map(booking, BookingDto.class);
        return bookingDto;
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
