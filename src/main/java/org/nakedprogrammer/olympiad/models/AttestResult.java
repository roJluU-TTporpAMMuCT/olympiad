package org.nakedprogrammer.olympiad.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttestResult {

    private String text;
    private Boolean pass;
}
