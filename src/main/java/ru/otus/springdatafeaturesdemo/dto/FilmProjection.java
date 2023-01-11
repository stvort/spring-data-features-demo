package ru.otus.springdatafeaturesdemo.dto;

import org.springframework.beans.factory.annotation.Value;
import ru.otus.springdatafeaturesdemo.models.Actor;

import java.util.List;
import java.util.stream.Collectors;

public interface FilmProjection {
    long getId();

    String getTitle();

    @Value("#{target.description.substring(0, 300) + \"...\"}")
    String getDescription();

    @Value("#{target.director.name}")
    String getDirector();

    @Value("#{target.genre.name}")
    String getGenre();

    @Value("#{T(ru.otus.springdatafeaturesdemo.dto.FilmProjection).actorsAsString(target.actors)}")
    String getActors();

    static String actorsAsString(List<Actor> actors) {
        return actors.stream().map(Actor::getName).collect(Collectors.joining(", "));
    }
}
