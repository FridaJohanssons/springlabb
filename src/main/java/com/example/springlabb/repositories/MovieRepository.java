package com.example.springlabb.repositories;

import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<MovieDto> getAllByTitle(String title);



}
