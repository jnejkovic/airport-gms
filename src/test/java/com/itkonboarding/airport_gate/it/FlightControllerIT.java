package com.itkonboarding.airport_gate.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itkonboarding.airport_gate.dto.request.FlightRequestDto;
import com.itkonboarding.airport_gate.dto.request.FlightUpdateRequestDto;
import com.itkonboarding.airport_gate.entities.Flight;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.repositories.FlightRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.*;
import static net.bytebuddy.utility.RandomString.make;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class FlightControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private GateRepository gateRepository;

    @Test
    public void get_OK() throws Exception {
        var flight = new Flight().setFlightIndex(make());
        flightRepository.save(flight);

        var response = mockMvc.perform(get("/flight/{id}", flight.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.flightIndex", is(flight.getFlightIndex())))
                .andExpect(jsonPath("$.id", is(flight.getId())));
    }

    @Test
    public void get_notFound() throws Exception {
        var flightId = new Random().nextInt();

        var response = mockMvc.perform(get("/flight/{id}", flightId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(FLIGHT_NOT_FOUND)));
    }

    @Test
    public void create() throws Exception {
        var flightRequest = new FlightRequestDto().setFlightIndex(make(4));

        var response = mockMvc.perform(post("/flight")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightIndex", is(flightRequest.getFlightIndex())))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void create_notValid() throws Exception {
        var flightRequest = new FlightRequestDto().setFlightIndex(null);

        var response = mockMvc.perform(post("/flight")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("NotBlank")));
    }

    @Test
    public void create_flightIndexLessThanValid() throws Exception {
        var flightRequest = new FlightRequestDto().setFlightIndex(make(1));

        var response = mockMvc.perform(post("/flight")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void create_flightIndexGreaterThanValid() throws Exception {
        var flightRequest = new FlightRequestDto().setFlightIndex(make(6));

        var response = mockMvc.perform(post("/flight")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void update() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var updatedFlight = new FlightUpdateRequestDto()
                .setFlightIndex(make(4)).setGateId(gate.getId());

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.flightIndex", is(updatedFlight.getFlightIndex())));
    }

    @Test
    public void update_gateAndFlightNull() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);

        var updatedFlight = new FlightUpdateRequestDto();

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.flightIndex", is(flight.getFlightIndex())));
    }

    @Test
    public void update_flightNotFound() throws Exception {
        var flightId = new Random().nextInt();

        var updatedFlight = new FlightUpdateRequestDto().setFlightIndex(make(4));
        var response = mockMvc.perform(put("/flight/{id}", flightId)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(FLIGHT_NOT_FOUND)));
    }

    @Test
    public void update_gateNotFound() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);

        var updatedFlight = new FlightUpdateRequestDto()
                .setFlightIndex(make(4))
                .setGateId(Math.abs(new Random().nextInt()));

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_FOUND)));

    }

    @Test
    public void update_gateNotAvailable() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);
        var gate = new Gate().setGateName(make(3)).setStatus(UNAVAILABLE);
        gateRepository.save(gate);

        var updatedFlight = new FlightUpdateRequestDto()
                .setFlightIndex(make(4))
                .setGateId(gate.getId());

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_AVAILABLE)));
    }

    @Test
    public void update_flightIndexLessThanValid() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);

        var updatedFlight = new FlightUpdateRequestDto()
                .setFlightIndex(make(1));

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void update_flightIndexGreaterThanValid() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);

        var updatedFlight = new FlightUpdateRequestDto()
                .setFlightIndex(make(6));

        var response = mockMvc.perform(put("/flight/{id}", flight.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFlight)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void delete_OK() throws Exception {
        var flight = new Flight().setFlightIndex(make(4));
        flightRepository.save(flight);

        var response = mockMvc.perform(delete("/flight/{id}", flight.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void delete_notFound() throws Exception {
        var flightId = new Random().nextInt();

        var response = mockMvc.perform(delete("/flight/{id}", flightId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(FLIGHT_NOT_FOUND)));
    }

    @Test
    public void getAll() throws Exception {
        flightRepository.deleteAll();
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight().setFlightIndex(make(4)));
        flights.add(new Flight().setFlightIndex(make(4)));
        flightRepository.saveAll(flights);

        var response = mockMvc.perform(get("/flight"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(flights.size())));
    }
}
