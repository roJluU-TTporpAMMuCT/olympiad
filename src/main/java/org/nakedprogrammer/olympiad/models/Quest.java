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
public class Quest {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quest")
    private List<Translation> translations;

    @ManyToOne
    @JoinColumn(name = "userok_id")
    private Userok userok;
}
