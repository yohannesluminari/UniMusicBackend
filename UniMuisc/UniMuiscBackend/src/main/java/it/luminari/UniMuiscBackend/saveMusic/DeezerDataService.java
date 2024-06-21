package it.luminari.UniMuiscBackend.saveMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.artist.ArtistRepository;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;

import java.io.IOException;
import java.util.Iterator;

@Service
public class DeezerDataService {

    private static final String API_URL = "https://striveschool-api.herokuapp.com/api/deezer/search?q=";

    private static final int MAX_RESULTS = 500;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Transactional
    public void fetchAndSaveTracks() {
        for (char c = 'a'; c <= 'z'; c++) {
            String url = API_URL + c;
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode rootNode = mapper.readTree(jsonResponse);
                JsonNode dataNode = rootNode.path("data");

                Iterator<JsonNode> iterator = dataNode.iterator();
                int count = 0;
                while (iterator.hasNext() && count < MAX_RESULTS) {
                    JsonNode trackNode = iterator.next();
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
                    Artist artist = artistRepository.findById(artistNode.path("id").asLong())
                            .orElse(new Artist());

                    artist.setId(artistNode.path("id").asLong());
                    artist.setName(artistNode.path("name").asText());
                    artist.setLink(artistNode.path("link").asText());
                    artist.setPicture(artistNode.path("picture").asText());
                    artist.setPictureSmall(artistNode.path("picture_small").asText());
                    artist.setPictureMedium(artistNode.path("picture_medium").asText());
                    artist.setPictureBig(artistNode.path("picture_big").asText());
                    artist.setPictureXl(artistNode.path("picture_xl").asText());
                    artist.setTracklist(artistNode.path("tracklist").asText());

                    // Save or update artist to the repository
                    artistRepository.save(artist);

                    // Associate the track with artist
                    track.setArtist(artist);

                    // Save track to the repository
                    trackRepository.save(track);

                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
