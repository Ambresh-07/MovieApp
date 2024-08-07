package com.tech.moviesfilx.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;

    @Column(nullable = false)
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

}
