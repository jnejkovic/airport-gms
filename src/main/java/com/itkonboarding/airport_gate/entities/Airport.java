package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "airport_id")
    private Integer id;
    @Column(nullable = false)
    private String airportName;
    @JsonIgnore
    @OneToMany(mappedBy = "airport", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Gate> gates = new ArrayList<>();
    @Version
    private Integer version;

    public Airport() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }
}
