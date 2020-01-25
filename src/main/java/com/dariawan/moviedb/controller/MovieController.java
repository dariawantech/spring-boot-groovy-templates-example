/**
 * Spring Boot + Groovy Templates Example  (https://www.dariawan.com)
 * Copyright (C) 2020 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
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
