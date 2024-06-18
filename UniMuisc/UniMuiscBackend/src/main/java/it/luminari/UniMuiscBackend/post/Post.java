package it.luminari.UniMuiscBackend.post;


import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts")
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is mandatory and can't be empty")
    @Column(nullable = false, length = 255)
    private String description;

    @NotNull(message = "Publication date is mandatory")
    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(length = 255)
    private String image; // Path or URL of the image for the post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setUser(User user) {
        this.user = user;
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

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public String getImage() {
        return image;
    }

    public User getUser() {
        return user;
    }
}
