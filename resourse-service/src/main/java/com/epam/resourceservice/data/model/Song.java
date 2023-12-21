package com.epam.resourceservice.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
@Data
public class Song {

    @Id
    private String id;

    private String base64EncodedFile;

    public Song(String base64EncodedFile) {
        this.base64EncodedFile = base64EncodedFile;
    }
}
