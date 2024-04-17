package org.nakedprogrammer.olympiad.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Translation {

    @Id @GeneratedValue
    private Long id;

    private String lang;

    @Transient
    private String sample_solution;

    private String className;

    private String visibleTestCode;

    private String hiddenTestCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "translation")
    private List<Solution> solutions;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @ManyToOne
    @JoinColumn(name = "userok_id")
    private Userok userok;
}
