package it.luminari.UniMuiscBackend.track;


import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_track_interaction")
public class UserTrackInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Track track;

    private int listenCount;

}
