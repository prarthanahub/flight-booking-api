package com.acmeair.flightbooking.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonRootName("passenger")
public class PassengerDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    public PassengerDto() {
    }

    public PassengerDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }
}
