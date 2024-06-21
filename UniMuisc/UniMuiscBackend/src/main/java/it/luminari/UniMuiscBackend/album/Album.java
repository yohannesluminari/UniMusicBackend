package it.luminari.UniMuiscBackend.album;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "albums")
public class Album {

    @Id
    private Long id;
    private String title;
    private String cover;
    private String coverSmall;
    private String coverMedium;
    private String coverBig;
    private String coverXl;
    @Column(name = "md5_image")
    private String md5Image;
    private String tracklist;

}
