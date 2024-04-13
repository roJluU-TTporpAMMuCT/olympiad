package org.nakedprogrammer.olympiad.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

    @Id
    private Long id;

    private String code;

    @ManyToOne
    private Translation translation;

    @ManyToOne
    private User user;
}
