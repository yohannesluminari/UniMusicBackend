package it.luminari.UniMuiscBackend.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<UserResponsePrj> findAllBy();

    public List<User> findAllById(Long id);

    Optional<User> findByUsername(String username);

}