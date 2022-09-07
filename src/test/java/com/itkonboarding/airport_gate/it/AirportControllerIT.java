package com.itkonboarding.airport_gate.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itkonboarding.airport_gate.dto.request.AirportRequestDto;
import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class AirportControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AirportRepository airportRepository;

    @BeforeEach
    void setup(){
        airportRepository.deleteAll();
    }

    @Test
    public void create() throws Exception {
        var airportRequest = new AirportRequestDto()
                .setAirportName(make());

        var response = mockMvc.perform(post("/airport")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(airportRequest)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.airportName",
                        is(airportRequest.getAirportName())))
                .andExpect(jsonPath("$.id",notNullValue()));
    }

    @Test
    public void update() throws Exception {
        var airport =  new Airport().setAirportName(make());
        airportRepository.save(airport);

        var updatedAirport = new AirportRequestDto().setAirportName(make());

        ResultActions response = mockMvc.perform(put("/airport/{id}", airport.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAirport)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.airportName", is(updatedAirport.getAirportName())));
    }

    @Test
    public void update_notFound() throws Exception {
       var airportId = new Random().nextInt();

        var updatedAirport = new AirportRequestDto().setAirportName(make());

        var response = mockMvc.perform(put("/airport/{id}", airportId)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAirport)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void delete_OK() throws Exception{
        var airport =  new Airport().setAirportName(make());
        airportRepository.save(airport);

        var response = mockMvc.perform(delete("/airport/{id}", airport.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void delete_notFound() throws Exception{
     var airportId = new Random().nextInt();

        var response = mockMvc.perform(delete("/airport/{id}", airportId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void get_OK() throws Exception{
        var airport =  new Airport().setAirportName(make());
        airportRepository.save(airport);

        var response = mockMvc.perform(get("/airport/{id}",airport.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.airportName", is(airport.getAirportName())))
                .andExpect(jsonPath("$.id", is(airport.getId())));
    }

    @Test
    public void get_notFound() throws Exception{
        var airportId =  new Random().nextInt();

        var response = mockMvc.perform(get("/airport/{id}",airportId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void getAll() throws Exception{
        List<Airport> airports =new ArrayList<>();
        airports.add(new Airport().setAirportName(make()));
        airports.add(new Airport().setAirportName(make()));
        airportRepository.saveAll(airports);

        var response = mockMvc.perform(get("/airport"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(airports.size())));
    }
}
