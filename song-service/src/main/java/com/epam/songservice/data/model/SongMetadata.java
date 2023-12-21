package com.epam.songservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songsMetadata")
@Data
public class SongMetadata {

    @Id
    private String id;

    private String name;

    private String artist;

    private String album;

    private String length;

    private String resourceId;

    private Integer year;

    public SongMetadata(String name, String artist, String album, String length, String resourceId, Integer year) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.resourceId = resourceId;
        this.year = year;
    }
}
