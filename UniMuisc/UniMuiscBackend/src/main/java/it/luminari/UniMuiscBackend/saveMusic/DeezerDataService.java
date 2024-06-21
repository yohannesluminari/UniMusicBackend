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
                "Tedua", "Sfera Ebbasta", "Ernia", "Drake","Dua Lipa","Nayt"
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
                // Parsing the Track
                Track track = new Track();
                track.setId(trackNode.path("id").asLong());
                track.setReadable(trackNode.path("readable").asBoolean());
                track.setTitle(trackNode.path("title").asText());
                track.setTitleShort(trackNode.path("title_short").asText());
                track.setTitleVersion(trackNode.path("title_version").asText(""));
                track.setLink(trackNode.path("link").asText());
                track.setDuration(trackNode.path("duration").asInt());
                track.setRank(trackNode.path("rank").asLong());
                track.setExplicitLyrics(trackNode.path("explicit_lyrics").asBoolean());
                track.setExplicitContentLyrics(trackNode.path("explicit_content_lyrics").asInt());
                track.setExplicitContentCover(trackNode.path("explicit_content_cover").asInt());
                track.setPreview(trackNode.path("preview").asText());
                track.setMd5Image(trackNode.path("md5_image").asText());

                // Parsing the Artist
                JsonNode artistNode = trackNode.path("artist");
                Artist artist = new Artist();
                artist.setId(artistNode.path("id").asLong());
                artist.setName(artistNode.path("name").asText());
                artist.setLink(artistNode.path("link").asText());
                artist.setPicture(artistNode.path("picture").asText());
                artist.setPictureSmall(artistNode.path("picture_small").asText());
                artist.setPictureMedium(artistNode.path("picture_medium").asText());
                artist.setPictureBig(artistNode.path("picture_big").asText());
                artist.setPictureXl(artistNode.path("picture_xl").asText());
                artist.setTracklist(artistNode.path("tracklist").asText());

                // Save artist to the repository if not already present
                Artist savedArtist = artistRepository.findById(artist.getId()).orElse(null);
                if (savedArtist == null) {
                    artistRepository.save(artist);
                } else {
                    artist = savedArtist;
                }

                // Parsing the Album
                JsonNode albumNode = trackNode.path("album");
                Album album = new Album();
                album.setId(albumNode.path("id").asLong());
                album.setTitle(albumNode.path("title").asText());
                album.setCover(albumNode.path("cover").asText());
                album.setCoverSmall(albumNode.path("cover_small").asText());
                album.setCoverMedium(albumNode.path("cover_medium").asText());
                album.setCoverBig(albumNode.path("cover_big").asText());
                album.setCoverXl(albumNode.path("cover_xl").asText());
                album.setMd5Image(albumNode.path("md5_image").asText());
                album.setTracklist(albumNode.path("tracklist").asText());

                // Save album to the repository if not already present
                Album savedAlbum = albumRepository.findById(album.getId()).orElse(null);
                if (savedAlbum == null) {
                    albumRepository.save(album);
                } else {
                    album = savedAlbum;
                }

                // Associate the track with artist and album
                track.setArtist(artist);
                track.setAlbum(album);

                // Save track to the repository if not already present
                Track savedTrack = trackRepository.findById(track.getId()).orElse(null);
                if (savedTrack == null) {
                    trackRepository.save(track);
                }
            }
        } catch (HttpServerErrorException.InternalServerError | IOException e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            e.printStackTrace();
        }
    }
}
