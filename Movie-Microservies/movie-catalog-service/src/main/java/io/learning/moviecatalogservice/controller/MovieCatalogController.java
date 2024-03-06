package io.learning.moviecatalogservice.controller;


import io.learning.moviecatalogservice.model.CatalogItem;
import io.learning.moviecatalogservice.model.MovieInfoItem;
import io.learning.moviecatalogservice.model.Rating;
import io.learning.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogItems(@PathVariable("userId")    String userId){

        UserRating userRating= restTemplate.getForObject("http://ratings-data-service/ratings/user/"+userId, UserRating.class);
        assert userRating != null;
        List<Rating> ratings = userRating.getRatings();

        List<CatalogItem> catalogItems;

         catalogItems = ratings.stream().map(rating -> {
            MovieInfoItem movieInfoItem = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), MovieInfoItem.class);
             assert movieInfoItem != null;
             return new CatalogItem(movieInfoItem.getMovieName(),"test",rating.getRating());
        }).collect(Collectors.toList());

       return catalogItems;

    }


}
