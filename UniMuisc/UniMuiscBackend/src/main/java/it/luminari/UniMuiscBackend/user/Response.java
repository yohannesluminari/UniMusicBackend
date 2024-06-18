package it.luminari.UniMuiscBackend.user;

import lombok.Data;

@Data
public class Response {

    private Long id;
    private String username;
    private String email;
    private String avatar; // Optional field, to return the avatar path or URL

    // You might also want to include information about playlists, posts, etc., in a simplified form
    // For example, counts of related entities:


    // Constructors, if needed, can be added for creating responses from user entities
}
