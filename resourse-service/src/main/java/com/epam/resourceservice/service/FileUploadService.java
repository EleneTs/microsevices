package com.epam.resourceservice.service;

import com.epam.resourceservice.client.SongServiceClient;
import com.epam.resourceservice.client.dto.SongMetadataDto;
import com.epam.resourceservice.data.model.Song;
import com.epam.resourceservice.data.repository.SongRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
public class FileUploadService {
    private final SongRepository songRepository;

    private final SongServiceClient songServiceClient;

    public FileUploadService(SongRepository songRepository, SongServiceClient songServiceClient) {
        this.songRepository = songRepository;
        this.songServiceClient = songServiceClient;
    }

    public String processAndStoreFile(MultipartFile file) {
        try {
            String fileId = storeFileInMongoDB(file.getBytes());

            Metadata metadata = extractMetadata(file.getInputStream());
            SongMetadataDto dto = buildSongMetadataDto(metadata, fileId);
            songServiceClient.publishSongMetadata(dto);
            return fileId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (TikaException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getAudioBytesById(String id) {
        return songRepository.findById(id)
                .map(song -> Base64.getDecoder().decode(song.getBase64EncodedFile()))
                .orElse(null);
    }

    public boolean deleteResourceById(String id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String storeFileInMongoDB(byte[] fileBytes) {
        String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);

        Song songDocument = new Song(base64Encoded);
        Song savedDocument = songRepository.save(songDocument);

        return savedDocument.getId();
    }

    private Metadata extractMetadata(InputStream inputStream) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

        //Mp3 parser
        Mp3Parser mp3Parser = new Mp3Parser();
        mp3Parser.parse(inputStream, handler, metadata, pcontext);
        return metadata;
    }

    private SongMetadataDto buildSongMetadataDto(Metadata metadata, String resourceId) {
        return new SongMetadataDto(metadata.get("title"),
                metadata.get("xmpDM:artist"),
                metadata.get("xmpDM:album"),
                metadata.get("xmpDM:duration"),
                resourceId,
                Integer.parseInt(metadata.get("xmpDM:releaseDate")));
    }
}
