package ru.otus.springdatafeaturesdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springdatafeaturesdemo.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
}
