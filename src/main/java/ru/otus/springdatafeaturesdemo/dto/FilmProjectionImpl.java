package ru.otus.springdatafeaturesdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.springdatafeaturesdemo.models.Actor;
import ru.otus.springdatafeaturesdemo.models.Film;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmProjectionImpl implements FilmProjection {
    private long id;
    private String title;
    private String description;
    private String director;
    private String genre;
    private String actors;

    public FilmProjectionImpl(Film film) {
        this(film.getId(), film.getTitle(), film.getDescription().substring(0, 300) + "...",
                film.getDirector().getName(), film.getGenre().getName(),
                film.getActors().stream()
                        .map(Actor::getName)
                        .collect(Collectors.joining(", "))
        );
    }
}
