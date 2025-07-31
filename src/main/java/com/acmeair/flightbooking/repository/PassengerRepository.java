package com.acmeair.flightbooking.repository;


import com.acmeair.flightbooking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}

