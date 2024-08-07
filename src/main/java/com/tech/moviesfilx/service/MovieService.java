package com.tech.moviesfilx.service;

import com.tech.moviesfilx.Dto.MovieDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
//add movies
    public MovieDto addMovies(MovieDto movieDto, MultipartFile file) throws IOException;


    public MovieDto getMovies(Long id);

    List<MovieDto> getAllMovies();

    public MovieDto updateMovieById( MovieDto movidto, MultipartFile file, Long id) throws IOException;

    public String deleteMovieById(@PathVariable Long id) throws IOException;
}
