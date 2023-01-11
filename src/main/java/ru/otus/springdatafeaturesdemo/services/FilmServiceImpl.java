package ru.otus.springdatafeaturesdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdatafeaturesdemo.dto.FilmProjection;
import ru.otus.springdatafeaturesdemo.dto.FilmProjectionImpl;
import ru.otus.springdatafeaturesdemo.models.Actor;
import ru.otus.springdatafeaturesdemo.models.Director;
import ru.otus.springdatafeaturesdemo.models.Film;
import ru.otus.springdatafeaturesdemo.models.Genre;
import ru.otus.springdatafeaturesdemo.repository.DirectorRepository;
import ru.otus.springdatafeaturesdemo.repository.FilmRepository;
import ru.otus.springdatafeaturesdemo.repository.GenreRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.otus.springdatafeaturesdemo.repository.spec.FilmSpecification.actorName;
import static ru.otus.springdatafeaturesdemo.repository.spec.FilmSpecification.nameLike;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<FilmProjectionImpl> findAll() {
        return filmRepository.findAll().stream()
                .map(FilmProjectionImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmProjectionImpl> findAllWithDirectProjection() {
        return filmRepository.findAllWithProjection();
    }

    @Override
    public List<FilmProjectionImpl> findAllWithDirectProjectionWithoutQuery() {
        return filmRepository.findAllWithProjectionWithoutQueryBy();
    }

    @Transactional(readOnly = true)
    @Override
    public List<FilmProjection> findAllWithInterfaceProjectionWithoutQuery() {
        directorRepository.findAll();
        genreRepository.findAll();
        return filmRepository.findAllWithInterfaceProjectionWithoutQueryBy();
    }

    @Override
    public List<FilmProjection> findAllByExample(String directorNamePart, String genreNamePart,
                                                 String actorNamePart) {
        return filmRepository.findAll(makeExample(directorNamePart, genreNamePart, actorNamePart))
                .stream()
                .map(FilmProjectionImpl::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmProjection> findAllBySpecification(String directorNamePart, String genreNamePart,
                                                       String actorNamePart) {
        var spec = makeSpec(directorNamePart, genreNamePart, actorNamePart);
        return spec.map(filmRepository::findAll)
                .orElseGet(filmRepository::findAll)
                .stream()
                .map(FilmProjectionImpl::new)
                .collect(Collectors.toList());
    }

    private Example<Film> makeExample(String directorNamePart, String genreNamePart, String actorNamePart) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("genre.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("director.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("actors.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("id", "title", "description", "director.id", "genre.id", "actor.id");

        var film = new Film(0, null, null, new Director(0, directorNamePart),
                new Genre(0, genreNamePart), Set.of(new Actor(0, actorNamePart)));
        return Example.of(film, exampleMatcher);
    }


    private Optional<Specification<Film>> makeSpec(String directorNamePart, String genreNamePart,
                                                   String actorNamePart) {
        var specList = Stream.of(nameLike("director", "name", directorNamePart),
                        nameLike("genre", "name", genreNamePart), actorName(actorNamePart))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (specList.isEmpty()) {
            return Optional.empty();
        }

        var spec = where(specList.get(0));
        for (int i = 1; i < specList.size(); i++) {
            spec = spec.and(specList.get(i));
        }

        return Optional.of(spec);
    }
}
