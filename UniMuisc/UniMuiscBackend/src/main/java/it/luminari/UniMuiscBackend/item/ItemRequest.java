package it.luminari.UniMuiscBackend.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequest {

    @NotBlank(message = "Title is mandatory and can't be empty")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory and can't be empty")
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotBlank(message = "Price is mandatory")
    private BigDecimal price;

    private Long userId; // ID dell'utente che pubblica l'articolo

    public ItemRequest(String title, String description, BigDecimal price, Long userId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }
}
