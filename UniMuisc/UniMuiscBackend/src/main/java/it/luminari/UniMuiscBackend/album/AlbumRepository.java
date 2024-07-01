package it.luminari.UniMuiscBackend.album;


import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.artist.name IN :artistNames")
    List<Album> findByArtistNameIn(List<String> artistNames);

    @Modifying
    @Query("UPDATE Album a SET a.likedByUsers = :user WHERE a.id = :albumId")
    void addLikeToAlbum(@Param("albumId") Long albumId, @Param("user") User user);
}
