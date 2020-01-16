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
