package it.luminari.UniMuiscBackend.item;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String available;
    private Long userId;
    private String username;
    private String createdAt;
    private String image;

}
