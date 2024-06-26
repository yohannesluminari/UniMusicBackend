package it.luminari.UniMuiscBackend.saveMusic;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.album.AlbumRepository;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.artist.ArtistRepository;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class DeezerDataService {

    private static final String API_ARTIST_URL = "https://api.deezer.com/artist/";
    private static final String API_ALBUM_URL = "https://api.deezer.com/album/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Transactional
    public void fetchAndSaveData(Long artistId) {
        saveArtistById(artistId);
        saveAlbumsById(artistId);
        saveTracksById(artistId);
    }

    private void saveArtistById(Long artistId) {
        String artistUrl = API_ARTIST_URL + artistId;

        try {
            URI uri = new URI(artistUrl);
            String artistResponse = restTemplate.getForObject(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode artistNode = mapper.readTree(artistResponse);
            saveArtist(artistNode);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Handle exception accordingly
        }
    }

    private void saveAlbumsById(Long artistId) {
        String albumsUrl = API_ARTIST_URL + artistId + "/albums";

        try {
            URI uri = new URI(albumsUrl);
            String albumsResponse = restTemplate.getForObject(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode albumsNode = mapper.readTree(albumsResponse);

            for (JsonNode albumNode : albumsNode.path("data")) {
                saveAlbum(albumNode, artistId);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Handle exception accordingly
        }
    }

    private void saveTracksById(Long artistId) {
        String tracksUrl = API_ARTIST_URL + artistId + "/top";

        try {
            URI uri = new URI(tracksUrl);
            String tracksResponse = restTemplate.getForObject(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tracksNode = mapper.readTree(tracksResponse);

            for (JsonNode trackNode : tracksNode.path("data")) {
                saveTrack(trackNode, artistId);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Handle exception accordingly
        }
    }

    private void saveArtist(JsonNode artistNode) {
        Artist artist = new Artist();
        artist.setId(artistNode.path("id").asLong());
        artist.setName(artistNode.path("name").asText());
        artist.setLink(artistNode.path("link").asText());
        artist.setPicture(artistNode.path("picture").asText());
        artist.setPictureBig(artistNode.path("picture_big").asText());
        artist.setTracklist(artistNode.path("tracklist").asText());

        artistRepository.save(artist);
    }

    private void saveAlbum(JsonNode albumNode, Long artistId) {
        Album album = new Album();
        album.setId(albumNode.path("id").asLong());
        album.setTitle(albumNode.path("title").asText());
        album.setLink(albumNode.path("link").asText());
        album.setCover(albumNode.path("cover").asText());
        album.setCoverBig(albumNode.path("cover_big").asText());
        album.setReleaseDate(albumNode.path("release_date").asText());

        // Set artist
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new RuntimeException("Artist not found"));
        album.setArtist(artist);

        albumRepository.save(album);
    }

    private void saveTrack(JsonNode trackNode, Long artistId) {
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

        JsonNode albumNode = trackNode.path("album");
        if (!albumNode.isNull() && !albumNode.isMissingNode()) {
            Long albumId = albumNode.path("id").asLong();

            // Verifica se l'album esiste già nel database, altrimenti crea un nuovo album
            Album album = albumRepository.findById(albumId).orElseGet(() -> {
                Album newAlbum = new Album();
                newAlbum.setId(albumId);
                newAlbum.setTitle(albumNode.path("title").asText());
                newAlbum.setCover(albumNode.path("cover").asText());
                newAlbum.setCoverBig(albumNode.path("cover_big").asText());
                newAlbum.setTracklist(albumNode.path("tracklist").asText());
                newAlbum.setLink(albumNode.path("link").asText());
                newAlbum.setReleaseDate(albumNode.path("release_date").asText());
                return albumRepository.save(newAlbum);
            });

            // Recupera l'artista dal repository
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));

            // Imposta l'album e l'artista per la traccia
            track.setArtist(artist);
            track.setAlbum(album);

            // Salva la traccia nel repository
            trackRepository.save(track);
        } else {
            throw new RuntimeException("Album information not found in track data");
        }
    }


}
