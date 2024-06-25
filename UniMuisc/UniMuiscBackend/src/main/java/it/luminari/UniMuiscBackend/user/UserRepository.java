package it.luminari.UniMuiscBackend.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<UserResponsePrj> findAllBy();

    public List<User> findAllById(Long id);

    Optional<User> findByUsername(String username);


    // Controlla se esiste un utente con un determinato username
    boolean existsByUsername(String username);

    // Controlla se esiste un utente con una determinata email
    boolean existsByEmail(String email);


}