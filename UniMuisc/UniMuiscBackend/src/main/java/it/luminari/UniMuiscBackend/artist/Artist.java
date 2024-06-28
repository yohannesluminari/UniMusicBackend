package it.luminari.UniMuiscBackend.artist;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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

    private int listeningTimeInMinutes;

}
