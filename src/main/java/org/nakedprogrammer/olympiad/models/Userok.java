package org.nakedprogrammer.olympiad.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userok{

    @Id @GeneratedValue
    private Long id;

    private String password;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Quest> quests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Translation> translations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Solution> solutions;
}
