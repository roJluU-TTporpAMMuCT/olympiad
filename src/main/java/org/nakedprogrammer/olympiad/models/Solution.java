package org.nakedprogrammer.olympiad.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

    @Id @GeneratedValue
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "translation_id")
    private Translation translation;

    @ManyToOne
    @JoinColumn(name = "userok_id")
    private Userok userok;
}
