package it.luminari.UniMuiscBackend.user;


import it.luminari.UniMuiscBackend.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Username is mandatory and can't be empty")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Password is mandatory")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private String password; // Lombok generates getter and setter for this field

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Column(length = 255) // Adjust length as needed for storing avatar image path or URL
    private String avatar; // Lombok generates getter and setter for this field


    @OneToMany
    private List<Item> items;
}