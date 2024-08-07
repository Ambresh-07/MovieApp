package com.tech.moviesfilx.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.moviesfilx.Dto.MovieDto;
import com.tech.moviesfilx.ExceptionaHandling.EmptyFileException;
import com.tech.moviesfilx.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")

public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("/add-movies")
    public ResponseEntity<MovieDto> addMovie(@RequestPart String MovieDto, @RequestPart MultipartFile file) throws IOException {

        MovieDto moviedto = convertMovieObjectToDto(MovieDto);

        if(file.isEmpty()){
            throw new EmptyFileException("File is Empty.Provide valid fileName");
        }


        return new ResponseEntity(movieService.addMovies(moviedto, file), HttpStatus.CREATED);

    }


    public MovieDto convertMovieObjectToDto(String MovieDtoobj) throws JsonProcessingException {
        ObjectMapper objmapper = new ObjectMapper();
        return objmapper.readValue(MovieDtoobj, MovieDto.class);
    }

    //get movies by id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.getMovies(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/MovieList")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public MovieDto updateMovieById(@RequestPart String MovieDto, @RequestPart MultipartFile file, @PathVariable Long id) throws IOException {

        MovieDto moviedto = convertMovieObjectToDto(MovieDto);
        return movieService.updateMovieById(moviedto, file, id);


    }

    @DeleteMapping("/{id}")
    public String deleteMovieById(@PathVariable Long id) throws IOException {
        return movieService.deleteMovieById(id);
    }


}


