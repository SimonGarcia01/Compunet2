package edu.co.icesi.examenmvc.services.impl;


import edu.co.icesi.examenmvc.model.Movie;
import edu.co.icesi.examenmvc.repository.IMoviesRepository;
import edu.co.icesi.examenmvc.services.IMoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoviesServiceImpl implements IMoviesService {

    private final IMoviesRepository moviesRepository;
    
    @Override
    public List<Movie> getAllMovies() {
        return moviesRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return moviesRepository.findById(id).orElse(null);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        if (!moviesRepository.existsById(id)) {
            return null;
        }
        movie.setId(id);
        return moviesRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }

}
