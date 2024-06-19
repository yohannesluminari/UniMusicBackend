package it.luminari.UniMuiscBackend.post;

import lombok.Data;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String username;
    private Double rating;
    private String createdAt;
}
