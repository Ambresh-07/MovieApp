package com.tech.moviesfilx.ServiceImpl;

import com.tech.moviesfilx.Dto.MovieDto;
import com.tech.moviesfilx.ExceptionaHandling.EmptyFileException;
import com.tech.moviesfilx.ExceptionaHandling.FileExistException;
import com.tech.moviesfilx.ExceptionaHandling.MovieNotFoundException;
import com.tech.moviesfilx.entitys.Movie;
import com.tech.moviesfilx.repository.MovieRepository;
import com.tech.moviesfilx.service.Filmservice;
import com.tech.moviesfilx.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesServices implements MovieService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    Filmservice filmservice;
    @Value("${project.poster}")
    public String path;

    @Value("${base.url}")
    public String baseurl;

    @Override
    public MovieDto addMovies(MovieDto movieDto, MultipartFile file) throws IOException {
        //        upload the file

        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistException("file is already preset chgane file name !");
        }
        String uploadedFileName = filmservice.uploadFile(path, file);

//        save the file name to the poster
        movieDto.setPoster(uploadedFileName);
//        map to dto to movie object

        Movie movie = Movie.builder()
                .movieId(null)
                .title(movieDto.getTitle())
                .director(movieDto.getDirector())
                .studio(movieDto.getStudio())
                .movieCast(movieDto.getMovieCast())
                .releaseYear(movieDto.getReleaseYear())
                .poster(movieDto.getPoster())
                .build();


//        save and return the movie object

        Movie savedMovieObject = movieRepository.save(movie);


        //generate the poster url

        String generateUrl = baseurl + "/fileService/" + uploadedFileName;


        //map movie object to dto object

        MovieDto responseDto = MovieDto.builder()
                .movieId(savedMovieObject.getMovieId())
                .title(savedMovieObject.getTitle())
                .director(savedMovieObject.getDirector())
                .studio(savedMovieObject.getStudio())
                .movieCast(savedMovieObject.getMovieCast())
                .releaseYear(savedMovieObject.getReleaseYear())
                .poster(savedMovieObject.getPoster())
                .postUrl(generateUrl)

                .build();

        return responseDto;
    }

    @Override
    public MovieDto getMovies(Long id) {
//        check is this id movie present or not


        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("This Id's Movie Not Found..."));
//generate poster
        String generateUrl = baseurl + "/fileService/" + movie.getPoster();
//convertMovie to movie Dto
        MovieDto responseDto = MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .studio(movie.getStudio())
                .movieCast(movie.getMovieCast())
                .releaseYear(movie.getReleaseYear())
                .poster(movie.getPoster())
                .postUrl(generateUrl)

                .build();
        return responseDto;
    }

    @Override
    public List<MovieDto> getAllMovies() {

        //fetch all recods

        List<Movie> moviedb = movieRepository.findAll();
        //list of movieDto

        List<MovieDto> movieDtoa = new ArrayList<>();
        for (Movie movies : moviedb) {
            String generateurl = baseurl + "/fileService/" + movies.getPoster();
            MovieDto responseDto = MovieDto.builder()
                    .movieId(movies.getMovieId())
                    .title(movies.getTitle())
                    .director(movies.getDirector())
                    .studio(movies.getStudio())
                    .movieCast(movies.getMovieCast())
                    .releaseYear(movies.getReleaseYear())
                    .poster(movies.getPoster())
                    .postUrl(generateurl)

                    .build();
            movieDtoa.add(responseDto);

        }


        return movieDtoa;
    }

    @Override
    public MovieDto updateMovieById(MovieDto movidto, MultipartFile file, Long id) throws EmptyFileException, MovieNotFoundException, IOException {


//      1. check if moviedto exixst from the given movie Id
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("This Id's Movie Not Found..."));

        //2.if file is null do nothing
        //3.if file is not null.then delete existing file associated with the record  and upload new file
        String filename = movie.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + filename));
            filename = filmservice.uploadFile(path, file);

        }
        movidto.setPoster(filename);

        //4 map it to movie object
//        Movie mv = movie.builder()
//                .movieId(movidto.getMovieId())
//                .title(movidto.getTitle())
//                .director(movidto.getDirector())
//                .studio(movidto.getStudio())
//                .movieCast(movidto.getMovieCast())
//                .releaseYear(movidto.getReleaseYear())
//                .poster(movidto.getPoster())
//
//                .build();


        Movie movied=new Movie(
                movie.getMovieId(),
                movidto.getTitle(),
                movidto.getDirector(),
                movidto.getStudio(),
                movidto.getMovieCast(),
                movidto.getReleaseYear(),
                movidto.getPoster()
                 );

        //5 save the movie object -> return saved movie object
        Movie updatedMovie = movieRepository.save(movied);
        //6 generate poster url for that
        String newPosterUrl = baseurl + "/fileService/" + updatedMovie.getPoster();


        //7 map to movieDto and return it
        MovieDto movDto = MovieDto.builder()
                .movieId(updatedMovie.getMovieId())
                .title(updatedMovie.getTitle())
                .director(updatedMovie.getDirector())
                .studio(updatedMovie.getStudio())
                .movieCast(updatedMovie.getMovieCast())
                .releaseYear(updatedMovie.getReleaseYear())
                .poster(updatedMovie.getPoster())
                .postUrl(newPosterUrl)

                .build();

        return movDto;
    }

    @Override
    public String deleteMovieById(Long id) throws IOException, MovieNotFoundException {
        Movie mv = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("id not found for this is " + id));
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));
        movieRepository.delete(mv);
        Long ids = mv.getMovieId();

        return "delete Movie Successflly Done with Id : " + ids;
    }
}
