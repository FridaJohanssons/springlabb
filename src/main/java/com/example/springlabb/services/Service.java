package com.example.springlabb.services;

import com.example.springlabb.dtos.MovieDirector;
import com.example.springlabb.dtos.MovieDto;

import java.util.List;
import java.util.Optional;

public interface Service {
    //I denna måste vi mappa från OptionalMovie till OptionalMovieDto
    List<MovieDto> getAllMovies();

    Optional<MovieDto> getOne(Long id);

    MovieDto createMovie(MovieDto movie);

    void delete(Long id);

    MovieDto replace(Long id, MovieDto movieDto);

    //Ändrad från MovieDto till MovieDirector för att endast kunna uppdatera director och inte Title.
    MovieDto update(Long id, MovieDirector movieDirector);

    List<MovieDto> searchAllByTitle (String title);
}
