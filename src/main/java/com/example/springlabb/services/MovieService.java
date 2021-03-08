package com.example.springlabb.services;

import com.example.springlabb.dtos.MovieDirector;
import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.entities.Movie;
import com.example.springlabb.mappers.MovieMapper;
import com.example.springlabb.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements com.example.springlabb.services.Service {
    //Denna klass finns för att finnas "mellan" Movie klassen och Repositoryt för att
    //man ska kunna ha en validering.

    private final MovieMapper movieMapper;
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }




    //I denna måste vi mappa från OptionalMovie till OptionalMovieDto
    @Override
    public List<MovieDto> getAllMovies(){
        return movieMapper.mapp(movieRepository.findAll());
    }

    @Override
    public Optional<MovieDto> getOne(Long id){
        return movieMapper.mapp(movieRepository.findById(id));
    }

    @Override
    public MovieDto createMovie(MovieDto movie){
        //validering:
        if (movie.getTitle().isEmpty())
            throw new RuntimeException();

        //Mappas eftersomMovieRepository förväntar sig en Movie och inte en MovieDto.
        //Mapp från MovieDto till Movie
        return movieMapper.mapp(movieRepository.save(movieMapper.mapp(movie)));
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDto replace(Long id, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()){
            Movie updatedMovie = movie.get();
            updatedMovie.setTitle(movieDto.getTitle());
            updatedMovie.setDirector(movieDto.getDirector());
            return movieMapper.mapp(movieRepository.save(updatedMovie));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id "
                    + id + " not found");
    }

    //Ändrad från MovieDto till MovieDirector för att endast kunna uppdatera director och inte Title.
    @Override
    public MovieDto update(Long id, MovieDirector movieDirector) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()){
            Movie updatedMovie = movie.get();
//            if (movieDto.getTitle() != null)
//            updatedMovie.setTitle(movieDto.getTitle());
            if(movieDirector.director != null)
            updatedMovie.setDirector(movieDirector.director);
            return movieMapper.mapp(movieRepository.save(updatedMovie));
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id "
                    + id + " not found");
    }

    //Ville göra en if(title != null) return else throw responsStatusException Not.Found men det blev 200 ok varje gång ändå.
    @Override
    public List<MovieDto> searchAllByTitle(String title){
        return movieRepository.getAllByTitle(title);}
}
