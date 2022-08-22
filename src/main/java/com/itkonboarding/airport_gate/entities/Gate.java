package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Gate {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "gate_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String gateName;

    @ManyToOne(cascade = REFRESH, fetch = LAZY)
    @JoinColumn(name = "airport")
    private Airport airport;

    @JsonIgnore
    @OneToMany(mappedBy = "gate", cascade = REFRESH)
    private List<Flight> flights = new ArrayList<>();

    @Column (nullable = false)
    private Boolean status;

    @Version
    private Integer version;
}
