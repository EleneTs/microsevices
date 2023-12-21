package com.epam.songservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongMetadataDto {

    private String name;

    private String artist;

    private String album;

    private String length;

    private String resourceId;

    private Integer year;
}
