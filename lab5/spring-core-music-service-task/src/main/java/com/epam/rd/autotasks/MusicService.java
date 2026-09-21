package com.epam.rd.autotasks;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class MusicService {
    private final List<Song> songs = new ArrayList<>();

    @PostConstruct
    public void postConstruct(){
        songs.add(new Song());
        songs.add(new Song());
        songs.add(new Song());
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("Shutting down");
    }
}
