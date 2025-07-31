package com.acmeair.flightbooking;

import com.acmeair.flightbooking.controller.FlightBookingController;
import com.acmeair.flightbooking.exception.ResourceNotFoundException;
import com.acmeair.flightbooking.model.BookingDto;
import com.acmeair.flightbooking.model.FlightDto;
import com.acmeair.flightbooking.model.PassengerDto;
import com.acmeair.flightbooking.service.FlightBookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = FlightBookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FlightComponentTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightBookingService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFlightById_success() throws Exception {
        FlightDto flight = new FlightDto(1L, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        when(flightService.getFlightById(1L)).thenReturn(flight);

        mockMvc.perform(get("/api/flights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destination").value("LAX"));
    }

    @Test
    public void getFlightById_failure() throws Exception {
        when(flightService.getFlightById(99L))
                .thenThrow(new ResourceNotFoundException("Flight not found"));

        mockMvc.perform(get("/api/flights/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Flight not found"));
    }

    @Test
    public void createBooking_success() throws Exception {
        PassengerDto passenger = new PassengerDto("John Doe", "john@example.com");
        FlightDto flight = new FlightDto(1L, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        BookingDto booking = new BookingDto(1L, flight, passenger);

        when(flightService.createBooking(eq(1L), any(PassengerDto.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .param("flightId", "1")
                        .header("Authorization", "Bearer " + "dummy-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.passenger.name").value("John Doe"));
    }

    @Test
    public void createBooking_failure_badrequest() throws Exception {
        PassengerDto passenger = new PassengerDto( "John Doe", "johnexample.com");

        mockMvc.perform(post("/api/bookings")
                        .param("flightId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBooking_success() throws Exception {
        PassengerDto updatedPassenger = new PassengerDto( "Jane Smith", "jane@example.com");
        FlightDto flight = new FlightDto(1L, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now().plusHours(5));
        BookingDto updatedBooking = new BookingDto(1L, flight, updatedPassenger);

        when(flightService.updatePassengerDetails(eq(1L), any(PassengerDto.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/api/bookings/1")
                        .header("Authorization", "Bearer " + "dummy-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPassenger)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passenger.name").value("Jane Smith"));
    }

    @Test
    public void updateBooking_failure_notFound() throws Exception {
        PassengerDto passenger = new PassengerDto("Someone", "some@example.com");

        when(flightService.updatePassengerDetails(eq(99L), any(PassengerDto.class)))
                .thenThrow(new ResourceNotFoundException("Booking not found"));

        mockMvc.perform(put("/api/bookings/99")
                        .header("Authorization", "Bearer " + "dummy-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Booking not found"));
    }

    @Test
    public void cancelBooking_success() throws Exception {
        mockMvc.perform(delete("/api/bookings/1")
                        .header("Authorization", "Bearer " + "dummy-jwt"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void cancelBooking_failure_notFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Booking not found")).when(flightService).cancelBooking(99L);

        mockMvc.perform(delete("/api/bookings/99")
                        .header("Authorization", "Bearer " + "dummy-jwt"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Booking not found"));
    }

}
