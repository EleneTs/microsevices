package com.epam.songservice.service;

import com.epam.songservice.data.model.SongMetadata;
import com.epam.songservice.data.repository.SongMetadataRepository;
import com.epam.songservice.dto.SongMetadataDto;
import org.springframework.stereotype.Service;

@Service
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;

    public SongMetadataService(SongMetadataRepository songMetadataRepository) {
        this.songMetadataRepository = songMetadataRepository;
    }

    public String uploadSongMetadata(SongMetadataDto songMetadataDto) {
        SongMetadata metadata = new SongMetadata(songMetadataDto.getName(),
                songMetadataDto.getArtist(),
                songMetadataDto.getAlbum(),
                songMetadataDto.getLength(),
                songMetadataDto.getResourceId(),
                songMetadataDto.getYear());
        return songMetadataRepository.save(metadata).getId();
    }

    public SongMetadataDto getSongMetadataById(String id) {
        SongMetadata metadata = songMetadataRepository.findById(id).orElse(null);
        if (metadata != null) {
            return new SongMetadataDto(metadata.getName(),
                    metadata.getArtist(),
                    metadata.getAlbum(),
                    metadata.getLength(),
                    metadata.getResourceId(),
                    metadata.getYear());
        } else {
            return null;
        }
    }

    public boolean deleteSongMetadataById(String id) {
        if (songMetadataRepository.existsById(id)) {
            songMetadataRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
