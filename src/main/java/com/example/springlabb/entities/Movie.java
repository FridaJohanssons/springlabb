package com.example.springlabb.entities;


import javax.persistence.*;

@Entity
@Table(name = "moviesLabb")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    private String title;
    private String director;


    public Movie(long id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

    public Movie(){}

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

