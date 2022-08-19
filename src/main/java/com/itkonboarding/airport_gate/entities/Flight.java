package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flight_id")
    private Integer id;
    @Column(nullable = false, unique = true)

    private String flightIndex;
    //    @Column(nullable = false)
//    private Airport airport;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "gate")
    private Gate gate;
    @Version
    private Integer version;

    public Flight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightIndex() {
        return flightIndex;
    }

    public void setFlightIndex(String flightIndex) {
        this.flightIndex = flightIndex;
    }

//    public Airport getAirport() {
//        return airport;
//    }
//
//    public void setAirport(Airport airport) {
//        this.airport = airport;
//    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }
}
