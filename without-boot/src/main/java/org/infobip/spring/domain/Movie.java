package org.infobip.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private int id;                 // unique identifier
    private String title;           // original title
    private int duration;           // duration of movie in minutes
    private Set<Actor> actors;      // main set of actors
}
