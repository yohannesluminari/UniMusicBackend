package it.luminari.UniMuiscBackend.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Item name is mandatory and can't be empty")
    @Size(max = 100, message = "Item name must be less than 100 characters")
    private String name;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Description is mandatory and can't be empty")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Column(length = 255)
    private String image; // Path or URL to the image

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Cascade PERSIST crea la sotto-entita' quando non presente, MERGE modifica la sotto-entity *vedi metodo bookAndAuthorModify nel serviceLibro.
    @ToString.Exclude // aggiungere sempre il ToString e il JsonProperties alla creazione di una entity relazionabile ad altra entity
    @JsonIgnoreProperties({"libri","id"}) // possibile sia att. con {"x"} per piu elementi, che singolo "x"
    // @JsonIgnore // elimina dalla response l'intera entity non molto utile in caso di relazioni bidirezionali preferibile approccio con DtoS
    private User user;
}
