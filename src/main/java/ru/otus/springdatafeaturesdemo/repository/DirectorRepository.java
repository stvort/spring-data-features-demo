package ru.otus.springdatafeaturesdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springdatafeaturesdemo.models.Director;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findAll();
}
