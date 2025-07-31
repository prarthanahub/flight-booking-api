package com.acmeair.flightbooking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonRootName("flight")
public class FlightDto {

    public FlightDto() {
    }

    public FlightDto(Long id, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @JsonProperty("flightId")
    private Long id;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getOrigin() {
        return origin;
    }

    public void setOrigin(@NotBlank String origin) {
        this.origin = origin;
    }

    public @NotBlank String getDestination() {
        return destination;
    }

    public void setDestination(@NotBlank String destination) {
        this.destination = destination;
    }

    public @NotNull LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@NotNull LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public @NotNull LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(@NotNull LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
