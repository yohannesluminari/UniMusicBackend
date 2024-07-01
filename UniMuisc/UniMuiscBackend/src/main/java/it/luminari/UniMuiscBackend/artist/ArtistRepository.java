package it.luminari.UniMuiscBackend.artist;


import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Modifying
    @Query("UPDATE Artist ar SET ar.likedByUsers = :user WHERE ar.id = :artistId")
    void addLikeToArtist(@Param("artistId") Long artistId, @Param("user") User user);

}
