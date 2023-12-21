package com.epam.songservice.data.repository;

import com.epam.songservice.data.model.SongMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongMetadataRepository extends MongoRepository<SongMetadata, String> {
}
