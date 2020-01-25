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
package com.dariawan.moviedb.service;

import com.dariawan.moviedb.domain.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.dariawan.moviedb.repository.MovieRepository;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;
    
    private boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }
    
    public Movie findById(Long id) throws Exception {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie==null) {
            throw new Exception("Cannot find movie with id: " + id);
        }
        else return movie;
    }
    
    public List<Movie> findAll(int pageNumber, int rowPerPage) {
        List<Movie> movies = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, 
                Sort.by("id").ascending());
        movieRepository.findAll(sortedByIdAsc).forEach(movies::add);
        return movies;
    }
    
    public Movie save(Movie movie) throws Exception {
        if (!StringUtils.isEmpty(movie.getTitle())) {
            if (movie.getId() != null && existsById(movie.getId())) { 
                throw new Exception("Movie with id: " + movie.getId() +
                        " already exists");
            }
            return movieRepository.save(movie);
        }
        else {
            throw new Exception("Title cannot be null or empty");
        }
    }
    
    public void update(Movie movie) throws Exception {
        if (!StringUtils.isEmpty(movie.getTitle())) {
            if (!existsById(movie.getId())) {
                throw new Exception("Cannot find movie with id: " + movie.getId());
            }
            movieRepository.save(movie);
        }
        else {
            throw new Exception("Title cannot be null or empty");
        }
    }
    
    public void deleteById(Long id) throws Exception {
        if (!existsById(id)) { 
            throw new Exception("Cannot find movie with id: " + id);
        }
        else {
            movieRepository.deleteById(id);
        }
    }
    
    public Long count() {
        return movieRepository.count();
    }
}
