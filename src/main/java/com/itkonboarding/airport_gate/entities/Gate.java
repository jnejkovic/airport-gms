package com.itkonboarding.airport_gate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.time.LocalTime;
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
    @Size(min = 2, max = 3, message = "Gate name should be between {min} and {max} characters long.")
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

    private LocalTime availableFrom;

    private LocalTime availableTo;

    @Version
    private Integer version;
}
