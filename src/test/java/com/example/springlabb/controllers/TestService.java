package com.example.springlabb.controllers;

import com.example.springlabb.dtos.MovieDirector;
import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.services.Service;

import java.util.List;
import java.util.Optional;

public class TestService implements Service {
    @Override
    public List<MovieDto> getAllMovies() {
        return List.of(new MovieDto(1,"Test1","Test1"),new MovieDto(2,"Test2","Test2"));
    }

    //Här kan man säga att vi simulerar en databas för att testa. Vi vill inte koppla
    //upp oss mot en riktig databas när vi ska göra tester.
    @Override
    public Optional<MovieDto> getOne(Long id) {
        if(id == 1)
            return Optional.of(new MovieDto(1, "Test", "Test"));
        return Optional.empty();
    }

    @Override
    public MovieDto createMovie(MovieDto movie) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public MovieDto replace(Long id, MovieDto movieDto) {
        return null;
    }

    @Override
    public MovieDto update(Long id, MovieDirector movieDirector) {
        return null;
    }

    @Override
    public List<MovieDto> searchAllByTitle(String title) {
        return null;
    }

}
