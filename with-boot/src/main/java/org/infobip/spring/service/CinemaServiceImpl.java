package org.infobip.spring.service;

import org.infobip.spring.domain.Actor;
import org.infobip.spring.domain.Movie;
import org.infobip.spring.repository.ActorRepository;
import org.infobip.spring.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CinemaServiceImpl implements CinemaService{
    private MovieRepository movieRepository;
    private ActorRepository actorRepository;

    @Autowired
    public CinemaServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public Movie findMovieById(int id) {
        return movieRepository.findById(id);
    }

    @Override
    public Set<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie saveMovie(Movie movie) {
        Movie retreived = movieRepository.findById(movie.getId());
        if (retreived != null) {
            movieRepository.delete(movie);
        }

        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(int id) {
        Movie movie = movieRepository.findById(id);

        if (movie != null) {
            movieRepository.delete(movie);
        }
    }

    @Override
    public Actor findActorById(int id) {
        return actorRepository.findById(id);
    }

    @Override
    public Set<Actor> findAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor saveActor(Actor actor) {
        Actor retreived = actorRepository.findById(actor.getId());
        if (retreived != null) {
            actorRepository.delete(actor);
        }

        return actorRepository.save(actor);
    }

    @Override
    public void deleteActor(int id) {
        Actor actor = actorRepository.findById(id);
        if (actor != null) {
            actorRepository.delete(actor);
        }
    }
}
