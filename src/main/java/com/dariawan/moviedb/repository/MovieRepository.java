package com.dariawan.moviedb.repository;

import com.dariawan.moviedb.domain.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>, 
        JpaSpecificationExecutor<Movie> {
}
