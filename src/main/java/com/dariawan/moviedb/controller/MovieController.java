package com.dariawan.moviedb.controller;

import com.dariawan.moviedb.domain.Movie;
import com.dariawan.moviedb.service.MovieService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private MovieService movieService;

    @Value("${msg.title}")
    private String title;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }

    @GetMapping(value = "/movies")
    public String getMovies(Model model,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Movie> movies = movieService.findAll(pageNumber, ROW_PER_PAGE);

        long count = movieService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("movies", movies);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "movie-list";
    }

    @GetMapping(value = "/movies/{movieId}")
    public String getMovieById(Model model, @PathVariable long movieId) {
        Movie movie = null;
        String errorMessage = null;
        try {
            movie = movieService.findById(movieId);
        } catch (Exception ex) {
            errorMessage = "Movie not found";
        }

        // List<Movie> movies = new ArrayList<>();
        // movies.add(movie);
        // model.addAttribute("movies", movies);
        model.addAttribute("movie", movie);
        model.addAttribute("allowDelete", false);
        model.addAttribute("errorMessage", errorMessage);
        return "movie-view";
    }

    @GetMapping(value = {"/movies/add"})
    public String showAddMovie(Model model) {
        Movie movie = new Movie();
        model.addAttribute("add", true);
        model.addAttribute("movie", movie);
        model.addAttribute("actionUrl", "/movies/add");

        return "movie-edit";
    }

    @PostMapping(value = "/movies/add")
    public String addMovie(Model model,
            @ModelAttribute("movie") Movie movie) {        
        try {
            Movie newMovie = movieService.save(movie);
            return "redirect:/movies/" + String.valueOf(newMovie.getId());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            //model.addAttribute("movie", movie);
            model.addAttribute("add", true);
            return "movie-edit";
        }        
    }

    @GetMapping(value = {"/movies/{movieId}/edit"})
    public String showEditMovie(Model model, @PathVariable long movieId) {
        Movie movie = null;
        String errorMessage = null;
        try {
            movie = movieService.findById(movieId);
        } catch (Exception ex) {
            errorMessage = "Movie not found";            
        }
        model.addAttribute("add", false);
        model.addAttribute("movie", movie);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("actionUrl", 
                "/movies/" + (movie == null ? 0 : movie.getId()) + "/edit");
        return "movie-edit";
    }

    @PostMapping(value = {"/movies/{movieId}/edit"})
    public String updateMovie(Model model,
            @PathVariable long movieId,
            @ModelAttribute("movie") Movie movie) {        
        try {
            movie.setId(movieId);
            movieService.update(movie);
            return "redirect:/movies/" + String.valueOf(movie.getId());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "movie-edit";
        }
    }

    @GetMapping(value = {"/movies/{movieId}/delete"})
    public String showDeleteMovieById(
            Model model, @PathVariable long movieId) {
        Movie movie = null;
        String errorMessage = null;
        try {
            movie = movieService.findById(movieId);
        } catch (Exception ex) {
            errorMessage = "Movie not found";

        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("movie", movie);
        model.addAttribute("errorMessage", errorMessage);
        return "movie-view";
    }

    @PostMapping(value = {"/movies/{movieId}/delete"})
    public String deleteMovieById(
            Model model, @PathVariable long movieId) {
        try {
            movieService.deleteById(movieId);
            return "redirect:/movies";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "movie-view";
        }
    }
}
