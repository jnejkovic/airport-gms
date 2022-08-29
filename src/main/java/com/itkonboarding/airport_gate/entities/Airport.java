package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.AUTO;

/**
 * Entity representing airport
 *
 * @author jnejkovic
 */
@Entity
@Getter
@Setter
public class Airport {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "airport_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String airportName;

    @JsonIgnore
    @OneToMany(mappedBy = "airport", cascade = REFRESH)
    private List<Gate> gates = new ArrayList<>();

    @Version
    private Integer version;
}
