package it.luminari.UniMuiscBackend.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Request {

    @NotBlank(message = "Item name is mandatory and can't be empty")
    @Size(max = 100, message = "Item name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory and can't be empty")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 255, message = "Image URL/path must be less than 255 characters")
    private String image;

    private LocalDateTime publicationDate;

    private boolean available;

    private Long idUser; // ID dell'utente che ha messo in vendita l'articolo

    public Request(String name, String description, String image, LocalDateTime publicationDate, boolean available) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.publicationDate = publicationDate;
        this.available = available;
    }
}
