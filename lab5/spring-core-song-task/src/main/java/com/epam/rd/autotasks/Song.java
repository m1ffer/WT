package com.epam.rd.autotasks;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("/application.properties")

@Getter
@Setter
public class Song {
    private String title, artist, year;
    public Song(@Value("${Title}") String title,
                @Value("${Artist}") String artist,
                @Value("${Year}") String year){
        this.title = title;
        this.artist = artist;
        this.year = year;
    }
}
