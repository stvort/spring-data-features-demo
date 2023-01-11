package ru.otus.springdatafeaturesdemo.services;

import ru.otus.springdatafeaturesdemo.dto.FilmProjection;
import ru.otus.springdatafeaturesdemo.dto.FilmProjectionImpl;

import java.util.List;

public interface FilmService {
    List<FilmProjectionImpl> findAll();

    List<FilmProjectionImpl> findAllWithDirectProjection();

    List<FilmProjectionImpl> findAllWithDirectProjectionWithoutQuery();

    List<FilmProjection> findAllWithInterfaceProjectionWithoutQuery();

    List<FilmProjection> findAllByExample(String directorNamePart, String genreNamePart, String actorNamePart);

    List<FilmProjection> findAllBySpecification(String directorNamePart, String genreNamePart, String actorNamePart);
}
