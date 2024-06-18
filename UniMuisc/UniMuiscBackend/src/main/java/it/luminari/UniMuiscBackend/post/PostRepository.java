package it.luminari.UniMuiscBackend.post;

import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Metodo per trovare tutti i post di un utente specifico
    List<Post> findAllByUser(User user);

    // Metodo per trovare tutti i post di un utente specifico per ID dell'utente
    List<Post> findAllByUserId(Long userId);

}