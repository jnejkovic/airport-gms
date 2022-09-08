package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

/**
 * Entity representing gate
 *
 * @author jnejkovic
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Gate {

    public enum Status {
        AVAILABLE, UNAVAILABLE
    }

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "gate_id")
    private Integer id;

    @Column(nullable = false)
    @Size(min = 2, max = 3, message = "Flight index should be between {min} and {max} characters long.")
    private String gateName;

    @ManyToOne(cascade = REFRESH, fetch = LAZY)
    @JoinColumn(name = "airport")
    private Airport airport;

    @JsonIgnore
    @OneToMany(mappedBy = "gate", cascade = REFRESH)
    private List<Flight> flights = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Version
    private Integer version;
}
