package com.itkonboarding.airport_gate.services;

import com.itkonboarding.airport_gate.entities.Airport;
import com.itkonboarding.airport_gate.exceptions.ResourceNotFoundException;
import com.itkonboarding.airport_gate.repositories.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static com.itkonboarding.airport_gate.exceptions.ErrorCode.AIRPORT_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceImpTest {

    @Mock
    private AirportRepository airportRepository;
    @InjectMocks
    private AirportServiceImp airportServiceImp;

    @Test
    void findById() {
        var id = new Random().nextInt();

        airportServiceImp.findById(id);

        verify(airportRepository).findById(id);
    }

    @Test
    void create() {
        var airport = mock(Airport.class);

        airportServiceImp.create(airport);

        verify(airportRepository).save(airport);
    }

    @Test
    void update_notFoundException() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class)
                        .isThrownBy(() -> airportServiceImp.update(id, airport))
                        .withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoInteractions(airport),
                () -> verifyNoMoreInteractions(airportRepository)
        );
    }

    @Test
    void update() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);
        var updatedAirport = mock(Airport.class);
        var airportName = make();

        when(airportRepository.findById(id)).thenReturn(of(updatedAirport));
        when(airport.getAirportName()).thenReturn(airportName);

        airportServiceImp.update(id, airport);

        assertAll(
                () -> verify(updatedAirport).setAirportName(airportName),
                () -> verify(airportRepository).save(updatedAirport)
        );
    }

    @Test
    void delete_notFoundException() {
        var id = new Random().nextInt();

        when(airportRepository.findById(id)).thenReturn(empty());

        assertAll(
                () -> assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->
                        airportServiceImp.delete(id)).withMessage(AIRPORT_NOT_FOUND),
                () -> verifyNoMoreInteractions(airportRepository)
        );
    }

    @Test
    void delete() {
        var id = new Random().nextInt();
        var airport = mock(Airport.class);

        when(airportRepository.findById(id)).thenReturn(of(airport));

        airportServiceImp.delete(id);

        verify(airportRepository).delete(airport);
    }

    @Test
    void getAllTest() {
        airportServiceImp.getAll();

        verify(airportRepository).findAll();
    }
}