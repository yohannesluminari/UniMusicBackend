package it.luminari.UniMuiscBackend.item;

import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory and can't be empty")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory and can't be empty")
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false) // Oppure @DecimalMin(value = "0.01") per un valore minimo positivo non zero
    private BigDecimal price;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
