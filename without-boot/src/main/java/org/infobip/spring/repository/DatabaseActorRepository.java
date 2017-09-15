package org.infobip.spring.repository;

import org.infobip.spring.domain.Actor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseActorRepository implements ActorRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Actor> rowMapper;

    public DatabaseActorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Actor findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM ACTORS WHERE ID = ?", new Object[] {id}, getRowMapper());
    }

    @Override
    public Set<Actor> findAll() {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM ACTORS", new Object[] {}, getRowMapper()));
    }

    @Override
    public Set<Actor> findByFirstNameAndLastName(String firstName, String lastName) {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM ACTORS WHERE FIRST_NAME = ? AND LAST_NAME = ?", new Object[] {firstName, lastName}, getRowMapper()));
    }

    @Override
    public Actor save(Actor actor) {
        if (actor.getId() > 0) {
            jdbcTemplate.update("UPDATE ACTORS SET FIRST_NAME = ?, LAST_NAME = ?", new Object[]{actor.getFirstName(), actor.getLastName()});
        } else {
            jdbcTemplate.update("INSERT INTO ACTORS (ID, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?)", new Object[]{actor.getId(), actor.getFirstName(), actor.getLastName()});
        }
        return actor;
    }

    @Override
    public void delete(Actor actor) {
        jdbcTemplate.update("DELETE FROM ACTORS WHERE ID = ?", new Object[]{actor.getId()});
    }

    private RowMapper<Actor> getRowMapper() {
        if (rowMapper != null) {
            return rowMapper;
        }

        this.rowMapper = new BeanPropertyRowMapper<>(Actor.class);
        return rowMapper;
    }

}
