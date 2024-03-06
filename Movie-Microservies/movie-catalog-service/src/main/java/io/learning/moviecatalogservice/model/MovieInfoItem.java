package io.learning.moviecatalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MovieInfoItem {

    private String movieId;
    private String movieName;
}
