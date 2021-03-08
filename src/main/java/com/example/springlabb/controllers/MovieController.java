package com.example.springlabb.controllers;

import com.example.springlabb.dtos.MovieDirector;
import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.services.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MovieController {

    private Service service;

    public MovieController(Service service) { this.service = service; }

    @GetMapping("/movies")
    public List<MovieDto> all() {
        return service.getAllMovies();
    }


    @GetMapping("/movies/{id}")
    public MovieDto one(@PathVariable Long id){
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id "
                        + id + " not found"));
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movie){
        return service.createMovie(movie);
    }

    @DeleteMapping("/movies/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    //PUT byter ut(uppdaterar) hela objectet. Förväntar sig alla parametrar i objectet.
    @PutMapping("/movies/{id}")
    public MovieDto replace(@RequestBody MovieDto movieDto, @PathVariable Long id){
        return service.replace(id,movieDto);
    }

//    //PATCH uppdaterar hela objectet
//    @PatchMapping("movies/{id}")
//    public MovieDto update(@RequestBody MovieDto movieDto, @PathVariable Long id){
//        return movieService.update(id,movieDto);
//    }

    //PATCH fast man kan bara uppdatera director.
    @PatchMapping("/movies/{id}")
    public MovieDto replace(@RequestBody MovieDirector movieDto, @PathVariable Long id){
        return service.update(id,movieDto);
    }


    @GetMapping("/movies/search")
    public List<MovieDto> search(@RequestParam(value = "title", required = false) String title){
            return service.searchAllByTitle(title);

    }
}

