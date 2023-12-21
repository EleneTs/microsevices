package com.epam.songservice.controller;

import com.epam.songservice.dto.DeletedResponse;
import com.epam.songservice.dto.SongMetadataDto;
import com.epam.songservice.dto.UploadResponse;
import com.epam.songservice.service.SongMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("songs")
public class SongMetadataController {

    private final SongMetadataService songMetadataService;

    public SongMetadataController(SongMetadataService songMetadataService) {
        this.songMetadataService = songMetadataService;
    }

    @PostMapping
    public ResponseEntity<?> uploadSongMetadata(@RequestBody SongMetadataDto dto) {
        String songId = songMetadataService.uploadSongMetadata(dto);

        if (songId != null) {
            return new ResponseEntity<>(new UploadResponse(songId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Song Metadata upload failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> getSongMetadata(@PathVariable String id) {
        SongMetadataDto dto = songMetadataService.getSongMetadataById(id);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return new ResponseEntity<>("Song Metadata not found!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSongsMetadata(@RequestParam("id") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));

        List<String> deletedIds = idList.stream()
                .filter(songMetadataService::deleteSongMetadataById)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DeletedResponse(deletedIds));
    }
}
