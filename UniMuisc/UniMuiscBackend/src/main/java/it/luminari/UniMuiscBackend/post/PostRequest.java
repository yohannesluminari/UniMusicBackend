package it.luminari.UniMuiscBackend.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 255, message = "Image URL/path must be less than 255 characters")
    private String image;


    // Constructor, getters, and setters
    public PostRequest(Long userId, String description, String image) {
        this.userId = userId;
        this.description = description;
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
