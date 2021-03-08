package com.example.springlabb.controllers;

import com.example.springlabb.dtos.MovieDto;
import com.example.springlabb.services.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(MovieController.class)
//@Import(TestService.class) //våran egen testservice klass
public class MvcTest {

    //Nedan: Istället för @Import våran testservice klass! Ramverket mockito tittar istället på
    //interfacet service och så kommer den när vi kör våra tester att dynamiskt
    // skapa en klass som implementerar service Interfacet. Detta gör den vid uppstart
    //@Autowired
    @MockBean
    Service service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Test
    void callingWithUrlMoviesShouldReturnALlMoviesAsJson() throws Exception {
        //Tala om för mockito vad den ska returnera när vi kallar på metoder i service
        Mockito.when(service.getAllMovies()).thenReturn(List.of(new MovieDto(1,"","")));

        //den vill veta vilken url den ska testa
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }


    @Test
    void callingPOSTWithNewMoviesShouldSaveMovieAndReturnNewMovieWithId() throws Exception {
        //Tala om för mockito vad den ska returnera när vi kallar på metoder i service
        Mockito.when(service.createMovie(any(MovieDto.class))).thenReturn(new MovieDto(1,"TestMvc","TestMvc"));

        var movieDto = new MovieDto(1,",","");
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsBytes(movieDto))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);

        //Testar bara controllerklassen och inte..
    }

}
