package org.infobip.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private int id;                 // unique identification
    private String firstName;
    private String lastName;
}
