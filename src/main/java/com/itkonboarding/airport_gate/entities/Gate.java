package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gate_id")
    private Integer id;
    @Column(nullable = false, unique = true)
    private String gateName;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="airport")
    private Airport airport;
    @JsonIgnore
    @OneToMany(mappedBy = "gate", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Flight> flights = new ArrayList<>();
    @Version
    private Integer version;

    public Gate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
