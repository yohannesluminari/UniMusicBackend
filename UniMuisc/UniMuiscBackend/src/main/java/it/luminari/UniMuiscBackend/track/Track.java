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

    private boolean readable;

    private String title;

    @Column(name = "title_short")
    private String titleShort;

    @Column(name = "title_version")
    private String titleVersion;

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
}
