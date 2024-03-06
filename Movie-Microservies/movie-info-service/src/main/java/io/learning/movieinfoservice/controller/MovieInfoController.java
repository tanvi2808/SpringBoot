package io.learning.movieinfoservice.controller;

import io.learning.movieinfoservice.model.MovieInfoItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    @GetMapping("/{movieId}")
    public MovieInfoItem getMovie(@PathVariable("movieId") String movieId){


        return new MovieInfoItem(movieId, "Titanic");

    }
}
