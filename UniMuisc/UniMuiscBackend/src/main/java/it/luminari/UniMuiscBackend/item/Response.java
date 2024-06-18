package it.luminari.UniMuiscBackend.item;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Response {

    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime publicationDate;
    private boolean available;

    // dati utente che ha messo in vendita
    private Long userId;
    private String username;

}
