package it.luminari.UniMuiscBackend.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;


import java.math.BigDecimal;

@Data

public class ItemRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull
    @NotBlank
    private String available; // Changed from BigDecimal to String

    private Long userId;

    @Size(max = 255, message = "Image URL/path must be less than 255 characters")
    private String image; // New field for image URL/path

 // Constructor with all fields
  public ItemRequest(String title, String description, BigDecimal price, Long userId, String available, String image) {
      this.title = title;
      this.description = description;
      this.price = price;
      this.userId = userId;
      this.available = available;
      this.image = image;
  }
}
