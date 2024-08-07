package com.tech.moviesfilx.Dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class MovieDto {

    private Long movieId;

    @NotBlank(message = "please provide Movie's title !")
    private String title;

    @NotBlank(message = "please provide Movie's director !")
    private String director;

    @NotBlank(message = "please provide Movie's studio !")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @NotBlank(message = "please provide Movie's releaseYear !")

    private String releaseYear;
    @NotBlank(message = "please provide Movie's poster !")
    private String poster;
    @NotBlank(message = "please provide poster's Url !")
    private String postUrl;

}
