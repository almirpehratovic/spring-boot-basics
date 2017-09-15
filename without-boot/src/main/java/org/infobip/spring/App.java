package org.infobip.spring;

import org.infobip.spring.config.Config;
import org.infobip.spring.service.CinemaService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        CinemaService cinemaService = ctx.getBean(CinemaService.class);
        printDatabase(cinemaService);

    }

    private static void printDatabase(CinemaService cinemaService) {
        cinemaService.findAllMovies().forEach(System.out::println);
    }
}
