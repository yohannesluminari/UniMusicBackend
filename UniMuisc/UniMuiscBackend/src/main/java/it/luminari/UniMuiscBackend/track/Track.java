package it.luminari.UniMuiscBackend.track;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "tracks")
public class Track {

    @Id
    private Long id;

    private String title;


    private String link;

    private int duration;

    private long rank;

    @Column(name = "explicit_lyrics")
    private boolean explicitLyrics;

    @Column(name = "explicit_content_lyrics")
    private int explicitContentLyrics;

    @Column(name = "explicit_content_cover")
    private int explicitContentCover;

    private String preview;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Album album;

    @ManyToMany(mappedBy = "favouriteTracks")
    private Set<User> likedByUsers;

    @OneToMany(mappedBy = "track")
    private Set<UserTrackInteraction> userInteractions;
}
