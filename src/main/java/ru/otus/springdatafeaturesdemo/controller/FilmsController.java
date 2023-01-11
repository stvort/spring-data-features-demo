package ru.otus.springdatafeaturesdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.springdatafeaturesdemo.services.FilmService;

@Controller
@RequiredArgsConstructor
public class FilmsController {

    private final FilmService filmService;

    @GetMapping("/")
    public String filmsListPage(Model model) {
        var films = filmService.findAll();
        //var films = filmService.findAllWithDirectProjection();
        //var films = filmService.findAllWithDirectProjectionWithoutQuery();
        //var films = filmService.findAllWithInterfaceProjectionWithoutQuery();
        model.addAttribute("films", films);
        return "filmsList";
    }

    @GetMapping("/search")
    public String filmsSearchResultPage(Model model,
                                        String directorNamePart,
                                        String genreNamePart,
                                        String actorNamePart) {
        //var films = filmService.findAllByExample(directorNamePart, genreNamePart, actorNamePart);
        var films = filmService.findAllBySpecification(directorNamePart,
                genreNamePart, actorNamePart);

        model.addAttribute("films", films);
        model.addAttribute("directorNamePart", directorNamePart);
        model.addAttribute("genreNamePart", genreNamePart);
        model.addAttribute("actorNamePart", actorNamePart);
        return "filmsList";
    }
}
