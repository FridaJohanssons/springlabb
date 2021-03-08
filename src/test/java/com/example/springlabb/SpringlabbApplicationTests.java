package com.example.springlabb;

import com.example.springlabb.dtos.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import static org.assertj.core.api.Assertions.assertThat;

//End to end test.(höhsta nivå av tester) Testar hela applikationen
// från http request och vi kör med en riktig databas i botten. Alltså från ände till ände.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringlabbApplicationTests {
    @LocalServerPort
    int port;

    //Vi behöver något att köra testerna med. För att ställe frågor till applikationen.
    //Som en klientprogrammvara som kan skicka fullständiga http requests mot springservern.
    //Detta är en automatiserad client.
    @Autowired
    TestRestTemplate testClient;

    public static final String hostname = "Http://localhost:";
    //HttpClient Kan också användas

    //@Autowired
    //RestTemplate testClient;  Detta används om man vill anroppa wn vebbservice på en annan dator.

    @Test
    void testGetAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/xml");

        MovieDto movieDto = new MovieDto(1,"testTest","testTest");
        var postMovie = testClient.postForEntity(hostname + port +"/movies/", movieDto ,MovieDto.class);

        //testClient.exchange("localhost:8080/movie", HttpMethod.GET, new HttpEntity<>(headers),MovieDto[].class);
        var result = testClient.getForEntity("Http://localhost:" + port +"/movies/", MovieDto[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().length).isGreaterThan(0);
    }

    @Test
    void postSomething() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/xml");

        MovieDto movieDto = new MovieDto(1,"testTest","testTest");
        var result = testClient.postForEntity(hostname + port +"/movies/", movieDto ,MovieDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(movieDto.getId()).isEqualTo(1);
        System.out.println(result.getStatusCode());

        //eventuelt verifiera med en get request för movie med id
        //en fråga mot databasen

        result = testClient.getForEntity(hostname + port +"/movies/1", MovieDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(result.getStatusCode());

        //sist dumpa databasen. Lämpligt att köra mot en destdatabas sätt ddl auto till create-drop
    }

    @Test
    void putsomething(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/xml");
        MovieDto movieDto = new MovieDto(1,"testTest","testTest");

        var result = testClient.postForEntity(hostname + port +"/movies/", movieDto ,MovieDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(movieDto.getId()).isEqualTo(1);

        testClient.put(hostname + port + "/movies/1", movieDto);
    }

    @Test
    void testDeleteSomething(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/xml");

        //Lägg till en movie
        MovieDto movieDto = new MovieDto(1,"testTest","testTest");
        var result = testClient.postForEntity(hostname + port +"/movies/", movieDto ,MovieDto.class);
        System.out.println(movieDto.getId() + " "+  movieDto.getTitle());

        //testa ta bort movie med id1
        testClient.delete(hostname + port + "/movies/1");

        //Försök hitta id 1 men få NOT_FOUND
        result = testClient.getForEntity(hostname + port +"/movies/1", MovieDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        System.out.println(result.getStatusCode());
    }


    @Test
    void patchSomething(){

        MovieDto movieDto = new MovieDto(1,"testTest","testTest");
        var result = testClient.postForEntity(hostname + port +"/movies/", movieDto ,MovieDto.class);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);

        testClient.getRestTemplate().setRequestFactory(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/xml");
        //headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieDto> request = new HttpEntity<>(movieDto);
//        MovieDto patchMovieDto = new MovieDto(1,"testTest", "patchTest");
//        movieDto = patchMovieDto;
        ResponseEntity<MovieDto> response = testClient.exchange(hostname + port +
                "/movies/1",HttpMethod.PATCH,request, MovieDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
