package it.luminari.UniMuiscBackend.user;

import lombok.Data;

@Data
public class Response {

    private Long id;
    private String username;
    private String email;
    private String avatar; // Avatar URL/path

    // Constructors, if needed, can be added for creating responses from user entities
}
