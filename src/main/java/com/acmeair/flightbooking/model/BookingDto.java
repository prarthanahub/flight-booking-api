package com.acmeair.flightbooking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("booking")
public class BookingDto {

    @JsonProperty("bookingId")
    private Long id;

    private FlightDto flight;

    private PassengerDto passenger;

    public BookingDto(Long id, FlightDto flight, PassengerDto passenger) {
        this.id = id;
        this.flight = flight;
        this.passenger = passenger;
    }

    public BookingDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightDto getFlight() {
        return flight;
    }

    public void setFlight(FlightDto flight) {
        this.flight = flight;
    }

    public PassengerDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDto passenger) {
        this.passenger = passenger;
    }
}
