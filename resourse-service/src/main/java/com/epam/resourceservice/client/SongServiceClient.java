package com.epam.resourceservice.client;

import com.epam.resourceservice.client.dto.SongMetadataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SongServiceClient {
    private final RestTemplate restTemplate;

    @Value("${client.song-service.url}")
    private String songServiceUrl;

    public SongServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void publishSongMetadata(SongMetadataDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SongMetadataDto> request = new HttpEntity<>(dto, headers);

        restTemplate.postForObject(songServiceUrl, request, String.class);
    }
}
