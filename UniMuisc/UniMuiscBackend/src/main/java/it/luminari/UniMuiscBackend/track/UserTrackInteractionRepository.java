package it.luminari.UniMuiscBackend.track;


import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTrackInteractionRepository extends JpaRepository<UserTrackInteraction, Long> {

    Optional<UserTrackInteraction> findByUserAndTrack(User user, Track track);
}
