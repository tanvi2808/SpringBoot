package io.learning.movieinfoservice.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MovieInfoItem {

    private String movieId;
    private String movieName;
}
