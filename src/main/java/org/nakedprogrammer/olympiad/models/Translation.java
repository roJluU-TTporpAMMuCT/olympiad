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

    @Id
    private Long id;

    private String lang;

    private String visibleTestCode;

    private String hiddenTestCode;

    @OneToMany
    private List<Solution> solutions;

    @ManyToOne
    private Quest quest;

    @ManyToOne
    private User user;
}
