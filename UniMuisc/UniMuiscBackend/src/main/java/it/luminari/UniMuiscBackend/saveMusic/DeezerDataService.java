package it.luminari.UniMuiscBackend.saveMusic;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.album.AlbumRepository;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.artist.ArtistRepository;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DeezerDataService {

    private static final String API_URL = "https://striveschool-api.herokuapp.com/api/deezer/search?q=";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Transactional
    public void fetchAndSaveData() {
        List<String> artistsToSave = Arrays.asList(
                "Nayt", "Tedua", "Sfera Ebbasta", "Ernia", "Marracash", "Rkomi", "Lazza","Guè", "Shiva","Sick Luke"
        );

        artistsToSave.forEach(this::saveArtistData);
    }

    private void saveArtistData(String artistName) {
        String url = API_URL + artistName.replace(" ", "%20");

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.path("data");

            for (JsonNode trackNode : dataNode) {
                saveTrack(trackNode);
            }
        } catch (HttpServerErrorException e) {
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            System.err.println("HTTP Error: " + statusCode + ", Response: " + responseBody);
            // Potresti anche loggare l'errore con un sistema di logging
            // logger.error("HTTP Error: " + statusCode + ", Response: " + responseBody, e);
        } catch (IOException e) {
            // Handle IOException (e.g., network issues, JSON parsing errors)
            e.printStackTrace();
            // Potresti anche loggare l'errore con un sistema di logging
            // logger.error("IOException occurred", e);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
            // Potresti anche loggare l'errore con un sistema di logging
            // logger.error("Unexpected exception occurred", e);
        }
    }


    private void saveTrack(JsonNode trackNode) {
        Track track = new Track();
        track.setId(trackNode.path("id").asLong());
        track.setTitle(trackNode.path("title").asText());
        track.setLink(trackNode.path("link").asText());
        track.setDuration(trackNode.path("duration").asInt());
        track.setRank(trackNode.path("rank").asLong());
        track.setExplicitLyrics(trackNode.path("explicit_lyrics").asBoolean());
        track.setExplicitContentLyrics(trackNode.path("explicit_content_lyrics").asInt());
        track.setExplicitContentCover(trackNode.path("explicit_content_cover").asInt());
        track.setPreview(trackNode.path("preview").asText());

        // Parsing the Artist
        JsonNode artistNode = trackNode.path("artist");
        Artist artist = saveArtist(artistNode);

        // Parsing the Album
        JsonNode albumNode = trackNode.path("album");
        Album album = saveAlbum(albumNode, artist);

        // Associate the track with artist and album
        track.setArtist(artist);
        track.setAlbum(album);

        // Save track to the repository if not already present
        Track savedTrack = trackRepository.findById(track.getId()).orElse(null);
        if (savedTrack == null) {
            trackRepository.save(track);
        }
    }

    private Artist saveArtist(JsonNode artistNode) {
        Artist artist = new Artist();
        artist.setId(artistNode.path("id").asLong());
        artist.setName(artistNode.path("name").asText());
        artist.setLink(artistNode.path("link").asText());
        artist.setPicture(artistNode.path("picture").asText());
        artist.setPictureBig(artistNode.path("picture_big").asText());
        artist.setTracklist(artistNode.path("tracklist").asText());

        // Save artist to the repository if not already present
        return artistRepository.findById(artist.getId()).orElseGet(() -> artistRepository.save(artist));
    }

    private Album saveAlbum(JsonNode albumNode, Artist artist) {
        Album album = new Album();
        album.setId(albumNode.path("id").asLong());
        album.setTitle(albumNode.path("title").asText());
        album.setCover(albumNode.path("cover").asText());
        album.setCoverBig(albumNode.path("cover_big").asText());
        album.setTracklist(albumNode.path("tracklist").asText());

        // Set the artist to the album
        album.setArtist(artist);

        // Save album to the repository if not already present
        return albumRepository.findById(album.getId()).orElseGet(() -> albumRepository.save(album));
    }
}
