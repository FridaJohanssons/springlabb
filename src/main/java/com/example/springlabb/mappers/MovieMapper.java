package com.example.springlabb.mappers;

import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.entities.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    public MovieMapper() {
    }//Mapp metod som kan kopiera över information från Movie till MovieDto.

    //Skapar ett MovieDto object från ett Movie Object.
    public MovieDto mapp(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getDirector());

    }//Mapp metod åt andra hållet. Alltså från MovieDto till Movie objekt.

    //kan ha samma namn "mapp" eftersom inparametrarna är olik så då kan kompilatorn skilja på dem.
    public Movie mapp(MovieDto movieDto) {
        return new Movie(movieDto.getId(), movieDto.getTitle(), movieDto.getDirector());
    }//Ännu en mapp metod för Optional<Movie> till att retunera Optional<MovieDto>.

    public Optional<MovieDto> mapp(Optional<Movie> optionalMovie) {
        if (optionalMovie.isEmpty())
            return Optional.empty();
        return Optional.of(mapp(optionalMovie.get()));

    }

    public List<MovieDto> mapp(List<Movie> all) {

        return all
                .stream()
                // Filter ska egentligen va här, bättre att filtrera innan man mappar.
                .map(this::mapp)
                //.filter(movieDto -> movieDto.getId() < 5) //Man kan lägga till tex filter.
                //.limit(100)   //Även limit finns som funktion.
                .collect(Collectors.toList());


        //Detta sätt går bra att mappa en Lista men är något svårläst så vi gör som ovanför istället med .stream().
//        List<MovieDto> movieDtoList = new ArrayList<>();
//        for (var movie: all){
//            movieDtoList.add(mapp(movie));
//        }
//        return movieDtoList;
    }
}