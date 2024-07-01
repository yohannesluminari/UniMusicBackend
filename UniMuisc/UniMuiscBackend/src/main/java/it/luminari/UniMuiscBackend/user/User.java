package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.item.Item;
import it.luminari.UniMuiscBackend.post.Post;
import it.luminari.UniMuiscBackend.track.Track;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Username is mandatory and can't be empty")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Password is mandatory")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Column(length = 255)
    private String avatar;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Item> items;

    @ManyToMany
    @JoinTable(
            name = "user_favourite_tracks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Track> favouriteTracks;

    @ManyToMany(mappedBy = "likedByUsers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Artist> likedArtists;

    @ManyToMany(mappedBy = "likedByUsers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Album> likedAlbums;

    @Column(name = "total_listening_time_in_minutes")
    private int totalListeningTimeInMinutes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "most_listened_album_id")
    private Album mostListenedAlbum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "most_listened_artist_id")
    private Artist mostListenedArtist;

    // Nuove relazioni per follower e following
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> following;

    // Override methods for UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Empty list since no roles/authorities are defined
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement actual logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement actual logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement actual logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement actual logic if needed
    }
}
