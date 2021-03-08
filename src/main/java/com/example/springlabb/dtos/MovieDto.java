package com.example.springlabb.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MovieDto { //Dto = Data transfer object. Används när vi ska skicka info mellan
    //olika lager i vår kod. När vi komunicerar mellan controller och service så används denna dto.


    private long id;
    private String title;
    private String director;


    public MovieDto(long id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

}
