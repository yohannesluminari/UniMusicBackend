package it.luminari.UniMuiscBackend.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank(message = "Title is mandatory and can't be empty")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Content is mandatory and can't be empty")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;

    private Long userId; // ID dell'utente autore del post

    private Double rating;

    public PostRequest(String title, String content, Long userId, Double rating) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.rating = rating;
    }
}
