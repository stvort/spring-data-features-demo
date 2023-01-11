package ru.otus.springdatafeaturesdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.springdatafeaturesdemo.dto.FilmProjection;
import ru.otus.springdatafeaturesdemo.dto.FilmProjectionImpl;
import ru.otus.springdatafeaturesdemo.models.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    List<Film> findAll();

    @Query("select new ru.otus.springdatafeaturesdemo.dto.FilmProjectionImpl(f) from Film f")
    List<FilmProjectionImpl> findAllWithProjection();

    List<FilmProjectionImpl> findAllWithProjectionWithoutQueryBy();

    List<FilmProjection> findAllWithInterfaceProjectionWithoutQueryBy();
}
