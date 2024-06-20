package it.luminari.UniMuiscBackend.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {

    @NotBlank(message = "Title is mandatory and can't be empty")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Content is mandatory and can't be empty")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;

    private Long userId; // ID dell'utente autore del post

    private Double rating;

    @Size(max = 255, message = "Image URL/path must be less than 255 characters")
    private String image;

}
