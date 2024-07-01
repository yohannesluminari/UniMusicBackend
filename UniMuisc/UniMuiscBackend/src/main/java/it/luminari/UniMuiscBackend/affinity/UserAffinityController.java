package it.luminari.UniMuiscBackend.affinity;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.album.AlbumRepository;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserAffinityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/user-affinity/{userId1}/{userId2}")
    public ResponseEntity<List<UserAffinityDTO>> getUserAffinities(
            @PathVariable Long userId1,
            @PathVariable Long userId2
    ) {
        try {
            User user1 = userRepository.findById(userId1)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId1));

            User user2 = userRepository.findById(userId2)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId2));

            // Calculate affinity based on tracks, albums, likes, etc.
            double trackAffinity = calculateTrackAffinity(user1, user2);
            double albumAffinity = calculateAlbumAffinity(user1, user2);
            double artistAffinity = calculateArtistAffinity(user1, user2);

            // Create a list to store affinities
            List<UserAffinityDTO> userAffinities = new ArrayList<>();

            // Add track affinity to list
            userAffinities.add(new UserAffinityDTO(user1.getId(), user2.getId(), trackAffinity));
            // Add album affinity to list
            userAffinities.add(new UserAffinityDTO(user1.getId(), user2.getId(), albumAffinity));
            // Add artist affinity to list
            userAffinities.add(new UserAffinityDTO(user1.getId(), user2.getId(), artistAffinity));

            // Sort affinities based on score (descending order)
            userAffinities.sort(Comparator.comparingDouble(UserAffinityDTO::getAffinityScore).reversed());

            // Set rank based on sorted order
            for (int i = 0; i < userAffinities.size(); i++) {
                userAffinities.get(i).setRank(i + 1);
            }

            // Return sorted list of affinities
            return ResponseEntity.ok(userAffinities);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }



    // + 1 se hanno canzoni in comune tra i like
    private double calculateTrackAffinity(User user1, User user2) {
        Set<Track> user1LikedTracks = user1.getFavouriteTracks();
        Set<Track> user2LikedTracks = user2.getFavouriteTracks();

        // Calculate common liked tracks count
        int commonLikedTracksCount = 0;
        for (Track track1 : user1LikedTracks) {
            for (Track track2 : user2LikedTracks) {
                if (track1.getId().equals(track2.getId())) {
                    commonLikedTracksCount++;
                    break;
                }
            }
        }

        double affinityScore = (double) commonLikedTracksCount;

        return affinityScore;
    }

    private double calculateAlbumAffinity(User user1, User user2) {
        Set<Long> user1LikedAlbumIds = user1.getLikedAlbums().stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        Set<Long> user2LikedAlbumIds = user2.getLikedAlbums().stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        // Calculate common liked albums count
        Set<Long> commonLikedAlbumIds = new HashSet<>(user1LikedAlbumIds);
        commonLikedAlbumIds.retainAll(user2LikedAlbumIds);
        int commonLikedAlbumsCount = commonLikedAlbumIds.size();

        double affinityScore = (double) commonLikedAlbumsCount;

        return affinityScore;
    }



    private double calculateArtistAffinity(User user1, User user2) {
        Set<Long> user1LikedArtistIds = user1.getLikedArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

        Set<Long> user2LikedArtistIds = user2.getLikedArtists().stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

        // Calculate common liked artists count
        Set<Long> commonLikedArtistIds = new HashSet<>(user1LikedArtistIds);
        commonLikedArtistIds.retainAll(user2LikedArtistIds);
        int commonLikedArtistsCount = commonLikedArtistIds.size();

        double affinityScore = (double) commonLikedArtistsCount;

        return affinityScore;
    }

}

