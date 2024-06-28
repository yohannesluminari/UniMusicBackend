package it.luminari.UniMuiscBackend.album;


import it.luminari.UniMuiscBackend.artist.Artist;
import jakarta.persistence.*;
import lombok.Data;

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

    private int listeningTimeInMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id") // Nome della colonna che collega l'album all'artista
    private Artist artist;



}
