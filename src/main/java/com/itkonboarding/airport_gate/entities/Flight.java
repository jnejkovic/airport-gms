package com.itkonboarding.airport_gate.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Flight {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "flight_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String flightIndex;

    @ManyToOne(cascade = REFRESH, fetch = LAZY)
    @JoinColumn(name = "gate")
    private Gate gate;

    @Version
    private Integer version;
}
