package it.luminari.UniMuiscBackend.track;


import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTrackInteractionRepository extends JpaRepository<UserTrackInteraction, Long> {

    // Method to find all interactions by a specific user
    List<UserTrackInteraction> findByUser(User user);

    // Optional method if you want to find interaction by both user and track
    Optional<UserTrackInteraction> findByUserAndTrack(User user, Track track);
}
