package ru.otus.springdatafeaturesdemo.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.otus.springdatafeaturesdemo.models.Film;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import static org.springframework.util.StringUtils.hasText;

public class FilmSpecification {
    public static Specification<Film> nameLike(String parentProperty, String propertyName, String name) {
        if (!hasText(name)) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.join(parentProperty).get(propertyName), "%" + name + "%");
    }

    public static Specification<Film> actorName(String actorName) {
        if (!hasText(actorName)) {
            return null;
        }

        return (root, query, cb) -> {
            query.distinct(true);
            Join<Object, Object> actors = root.join("actors", JoinType.INNER);
            return cb.like(actors.get("name"), "%" + actorName + "%");
        };
    }
}
