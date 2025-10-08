package edu.co.icesi.examenmvc.services;


import edu.co.icesi.examenmvc.model.Movie;

import java.util.List;

public interface IMoviesService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie createMovie(Movie movie);
    Movie updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);
}
