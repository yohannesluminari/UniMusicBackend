package it.luminari.UniMuiscBackend.album;

import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@Table(name = "albums")
public class Album {

    @Id
    private Long id;
    private String title;
    private String cover;
    private String coverBig;
    private String tracklist;

    private String link;
    private String releaseDate;

    private int listeningTimeInMinutes; // Aggregate listening time for the album

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    // New field to track total listening time for the album for each user
    @ElementCollection
    @CollectionTable(name = "user_album_listening_time", joinColumns = @JoinColumn(name = "album_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "listening_time_in_seconds")
    private Map<User, Integer> userListeningTimes = new HashMap<>();


    @ManyToMany
    @JoinTable(
            name = "user_liked_albums",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();
}
