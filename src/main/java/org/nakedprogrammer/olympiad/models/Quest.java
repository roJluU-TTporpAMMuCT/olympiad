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

    @Id
    private Long id;

    private String name;

    private String description;

    @OneToMany
    private List<Translation> translations;

    @ManyToOne
    private User user;
}
