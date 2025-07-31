package com.acmeair.flightbooking.repository;

import com.acmeair.flightbooking.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Page<Flight> findByOriginAndDestination(String origin, String destination, Pageable pageable);
    Page<Flight> findByOriginAndDestinationAndDepartureTime(String origin, String destination, LocalDateTime departureTime, Pageable pageable);

}
