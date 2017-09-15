package org.infobip.spring.repository;
import org.infobip.spring.domain.Movie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class DatabaseMovieRepository implements MovieRepository{

    private JdbcTemplate jdbcTemplate;
    private DatabaseActorRepository actorRepository;
    private RowMapper<Movie> rowMapper;

    public DatabaseMovieRepository(JdbcTemplate jdbcTemplate, DatabaseActorRepository actorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.actorRepository = actorRepository;
    }

    @Override
    public Movie findById(int id)
    {
        Movie movie = jdbcTemplate.queryForObject("SELECT * FROM MOVIES WHERE ID = ?", new Object[] {id}, getRowMapper());
        populateActors(movie);
        return movie;
    }

    @Override
    public Set<Movie> findAll() {
        Set<Movie> movies = new HashSet<>(jdbcTemplate.query("SELECT * FROM MOVIES", new Object[] {}, getRowMapper()));
        movies.forEach(movie -> populateActors(movie));
        return movies;
    }

    @Override
    public Set<Movie> findByTitle(String title) {
        Set<Movie> movies =  new HashSet<>(jdbcTemplate.query("SELECT * FROM MOVIES WHERE TITLE = ?", new Object[] {title}, getRowMapper()));
        movies.forEach(movie -> populateActors(movie));
        return movies;
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() > 0) {
            jdbcTemplate.update("UPDATE MOVIES SET TITLE = ?, DURATION = ?", new Object[]{movie.getTitle(), movie.getDuration()});
        } else {
            jdbcTemplate.update("INSERT INTO MOVIES(ID, TITLE, DURATION) VALUES (?, ?, ?)", new Object[]{movie.getId(), movie.getTitle(), movie.getDuration()});
        }
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        jdbcTemplate.update("DELETE FROM MOVIES WHERE ID = ?", new Object[] {movie.getId()});
    }

    private RowMapper<Movie> getRowMapper() {
        if (rowMapper != null) {
            return rowMapper;
        }

        this.rowMapper = new BeanPropertyRowMapper<>(Movie.class);
        return rowMapper;
    }

    private void populateActors(Movie movie) {
        movie.setActors(new HashSet<>());
        List<Integer> actors = jdbcTemplate.queryForList("SELECT ACTOR_ID FROM ACTORS_MOVIES WHERE MOVIE_ID = ?", Integer.class, movie.getId());
        actors.forEach(actorId -> movie.getActors().add(actorRepository.findById(actorId)));
    }
}
