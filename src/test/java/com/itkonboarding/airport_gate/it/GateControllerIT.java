package com.itkonboarding.airport_gate.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itkonboarding.airport_gate.dto.request.GateRequestDto;
import com.itkonboarding.airport_gate.dto.request.GateUpdateRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.entities.Gate;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import com.itkonboarding.airport_gate.repositories.GateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.itkonboarding.airport_gate.entities.Gate.Status.AVAILABLE;
import static com.itkonboarding.airport_gate.entities.Gate.Status.UNAVAILABLE;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static com.itkonboarding.airport_gate.exceptions.ErrorCode.GATE_NOT_FOUND;
import static java.time.LocalTime.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class GateControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void get_OK() throws Exception {
        var gate = new Gate().setGateName(make(3)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var response = mockMvc.perform(get("/gate/{id}", gate.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.gateName", is(gate.getGateName())))
                .andExpect(jsonPath("$.id", is(gate.getId())));
    }

    @Test
    public void get_notFound() throws Exception {
        var gateId = new Random().nextInt();

        var response = mockMvc.perform(get("/gate/{id}", gateId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_FOUND)));
    }

    @Test
    public void create() throws Exception {
        var airport = new Airport().setAirportName(make());
        airportRepository.save(airport);
        var gateRequest = new GateRequestDto().setGateName(make(2)).setAirportId(airport.getId());

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gateName", is(gateRequest.getGateName())))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.airportName", is(airport.getAirportName())));
    }

    @Test
    public void create_airportNull() throws Exception {
        var gateRequest = new GateRequestDto().setGateName(make(2));

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gateName", is(gateRequest.getGateName())))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void create_airportNotFound() throws Exception {
        var gateRequest = new GateRequestDto().setGateName(make(2))
                .setAirportId(Math.abs(new Random().nextInt()));

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(AIRPORT_NOT_FOUND)));
    }

    @Test
    public void create_gateNameNotValid() throws Exception {
        var gateRequest = new GateRequestDto().setGateName(null);

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("NotBlank")));
    }

    @Test
    public void create_gateNameLessThanValid() throws Exception {
        var gateRequest = new GateRequestDto().setGateName(make(1));

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void create_gateNameGreaterThanValid() throws Exception {
        var gateRequest = new GateRequestDto().setGateName(make(4));

        var response = mockMvc.perform(post("/gate")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gateRequest)));

        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void update() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);
        var airport = new Airport().setAirportName(make());
        airportRepository.save(airport);

        var updatedGate = new GateUpdateRequestDto().setGateName(make(3))
                .setAirportId(airport.getId()).setAvailableFrom(of(8, 0))
                .setAvailableTo(of(15, 0));

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.gateName", is(updatedGate.getGateName())))
                .andExpect(jsonPath("$.availableFrom", is(updatedGate.getAvailableFrom().format(DateTimeFormatter.ISO_TIME))))
                .andExpect(jsonPath("$.availableTo", is(updatedGate.getAvailableTo().format(DateTimeFormatter.ISO_TIME))));
    }

    @Test
    public void update_gateAndAirportNull() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var updatedGate = new GateUpdateRequestDto();

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.gateName", is(gate.getGateName())));
    }

    @Test
    public void update_gateNotFound() throws Exception {
        var gateId = new Random().nextInt();

        var updatedGate = new GateUpdateRequestDto().setGateName(make(3));

        var response = mockMvc.perform(put("/gate/{id}", gateId)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_FOUND)));
    }

    @Test
    public void update_airportNotFound() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);
        var airportId = Math.abs(new Random().nextInt());

        var updatedGate = new GateUpdateRequestDto().setGateName(make(3))
                .setAirportId(airportId);

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(AIRPORT_NOT_FOUND)));
    }

    @Test
    public void update_gateLessThanValid() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var updatedGate = new GateUpdateRequestDto().setGateName(make(1));

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void update_gateGreaterThanValid() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var updatedGate = new GateUpdateRequestDto().setGateName(make(4));

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Size")));
    }

    @Test
    public void update_airportIdNotValid() throws Exception {
        var airportId = new Random().nextInt(2) - 1;
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var updatedGate = new GateUpdateRequestDto().setGateName(make(2))
                .setAirportId(airportId);

        var response = mockMvc.perform(put("/gate/{id}", gate.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGate)));

        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.validationMessage[0].code", is("Min")));
    }

    @Test
    public void makeAvailable() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(UNAVAILABLE);
        gateRepository.save(gate);

        var response = mockMvc.perform(put("/gate/{id}/available", gate.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status", is(AVAILABLE.toString())));
    }

    @Test
    public void makeAvailable_gateNotFound() throws Exception {
        var gateId = new Random().nextInt();

        var response = mockMvc.perform(put("/gate/{id}/available", gateId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_FOUND)));
    }

    @Test
    public void delete_OK() throws Exception {
        var gate = new Gate().setGateName(make(2)).setStatus(AVAILABLE);
        gateRepository.save(gate);

        var response = mockMvc.perform(delete("/gate/{id}", gate.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void delete_notFound() throws Exception {
        var gateId = new Random().nextInt();

        var response = mockMvc.perform(delete("/gate/{id}", gateId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(GATE_NOT_FOUND)));
    }

    @Test
    public void getAllGatesForAirport_OK() throws Exception {
        var airport = new Airport().setAirportName(make());
        airportRepository.save(airport);
        List<Gate> gates = new ArrayList<>();
        gates.add(new Gate().setGateName(make(2)).setStatus(AVAILABLE).setAirport(airport));
        gates.add(new Gate().setGateName(make(2)).setStatus(AVAILABLE).setAirport(airport));
        gateRepository.saveAll(gates);

        var response = mockMvc.perform(get("/gate/{id}/gates", airport.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(gates.size())));
    }

    @Test
    public void getAllGatesForAirport_notFound() throws Exception {
        var airportId = new Random().nextInt();

        var response = mockMvc.perform(get("/gate/{id}/gates", airportId));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(AIRPORT_NOT_FOUND)));
    }
}
