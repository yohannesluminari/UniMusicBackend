package it.luminari.UniMuiscBackend.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String description;
    private String image;
    private LocalDateTime publicationDate;
    // Costruttore, getter e setter
    public PostResponse() {
    }

    public PostResponse(Long id, String description, String image, LocalDateTime publicationDate) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.publicationDate = publicationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getId() {
        return getId();
    }
}
