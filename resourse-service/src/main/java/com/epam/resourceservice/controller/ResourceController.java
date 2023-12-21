package com.epam.resourceservice.controller;

import com.epam.resourceservice.dto.DeletedResponse;
import com.epam.resourceservice.dto.UploadResponse;
import com.epam.resourceservice.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("resources")
public class ResourceController {

    private final FileUploadService fileUploadService;

    public ResourceController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public ResponseEntity<?> uploadMP3File(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        String fileId = fileUploadService.processAndStoreFile(file);

        if (fileId != null) {
            return new ResponseEntity<>(new UploadResponse(fileId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("File upload failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> getMP3File(@PathVariable String id) {
        byte[] audioBytes = fileUploadService.getAudioBytesById(id);

        if (audioBytes != null) {
            return ResponseEntity.ok(audioBytes);
        } else {
            return new ResponseEntity<>("Audio not found!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMP3Files(@RequestParam("id") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));

        List<String> deletedIds = idList.stream()
                .filter(fileUploadService::deleteResourceById)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DeletedResponse(deletedIds));
    }
}
