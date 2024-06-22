package it.luminari.UniMuiscBackend.album;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.artist.name IN :artistNames")
    List<Album> findByArtistNameIn(List<String> artistNames);
}
