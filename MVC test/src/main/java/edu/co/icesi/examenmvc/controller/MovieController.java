package edu.co.icesi.examenmvc.controller;

import edu.co.icesi.examenmvc.model.Movie;
import edu.co.icesi.examenmvc.services.IMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMoviesService moviesService;

    @GetMapping("/")
    public String getMovies(Model model){
        List<Movie> movies = moviesService.getAllMovies();

        model.addAttribute("movies", movies);

        return "/movies/home";

    }

    @GetMapping("/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model){
        Movie movie = moviesService.getMovieById(id);
        model.addAttribute("movie", movie);

        return "/movies/details";
    }

    @GetMapping("/create")
    public String createMovie(Model model){
        model.addAttribute("movie", new Movie());
        return "/movies/movie-creation";
    }

    @PostMapping("/create")
    public String saveMovie(@ModelAttribute Movie movie){
        moviesService.createMovie(movie);
        return "redirect:/movies/";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id){
        moviesService.deleteMovie(id);
        return "redirect:/movies/";
    }

    @GetMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") Long id, Model model){
        Movie movie = moviesService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "/movies/update";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") Long id, @ModelAttribute Movie movie){
        moviesService.updateMovie(id, movie);
        return "redirect:/movies/";
    }
}
