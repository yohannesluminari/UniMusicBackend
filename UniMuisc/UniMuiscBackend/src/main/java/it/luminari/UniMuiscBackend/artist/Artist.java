package it.luminari.UniMuiscBackend.artist;

import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "artists")
public class Artist {

    @Id
    private Long id;
    private String name;
    private String link;
    private String picture;
    private String pictureBig;
    private String tracklist;

    private int listeningTimeInMinutes; // Aggregate listening time for the artist

    // New field to track total listening time for the artist for each user
    @ElementCollection
    @CollectionTable(name = "user_artist_listening_time", joinColumns = @JoinColumn(name = "artist_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "listening_time_in_seconds")
    private Map<User, Integer> userListeningTimes = new HashMap<>();
}
