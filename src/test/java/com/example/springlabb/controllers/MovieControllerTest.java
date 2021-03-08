package com.example.springlabb.controllers;

import com.example.springlabb.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
//Unit Tester. Testar en sak taget, isolerat. (Tex Inte från en riktig databas)
//Vi vill här bara testa funktionalliteten i klassen MovieController. (Ej Servicen eller Repositoryt)

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {
    @Test
    void callingOneWithValidIdReturnsOneMovie(){
        MovieController movieController = new MovieController(new TestService());

        var movie = movieController.one(1L);

        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getTitle()).isEqualTo("Test");
        assertThat(movie.getDirector()).isEqualTo("Test");
    }

    //Detta testet lyckas ifall metoden misslyckas. Den säkerställer att ifall vi kör movie med
    //ett id som inte existerar (fel inparameter) så ska det komma ett exception.
    // Om det inte kommer ett exception det så misslyckas testet.
    //Med assertThat så kontrollerar vi även att det är rätt felmeddelande som skickas.
    @Test
    void callingOneWithInvalidIdThrowsExceptionWithResponseStatus404(){
        MovieController movieController = new MovieController(new TestService());

        //Lambda utryck istället nedan.
       // var movie = movieController.one(2L);

        var exception = assertThrows(ResponseStatusException.class, () -> movieController.one(2L));
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}