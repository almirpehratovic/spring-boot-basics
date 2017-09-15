package org.infobip.spring.controllers;

import org.infobip.spring.domain.Movie;
import org.infobip.spring.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("/movies")
    public Set<Movie> movies() {
        return cinemaService.findAllMovies();
    }
}
