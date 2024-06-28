package it.luminari.UniMuiscBackend.track;

import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TrackController {

    @Autowired
    private UserTrackInteractionRepository userTrackInteractionRepository;

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/update-track-listen-count/{userId}/{trackId}/{listenDuration}")
    public ResponseEntity<String> updateTrackListenCount(
            @PathVariable Long userId,
            @PathVariable Long trackId,
            @PathVariable int listenDuration
    ) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new RuntimeException("Track not found with id: " + trackId));

            UserTrackInteraction interaction = userTrackInteractionRepository.findByUserAndTrack(user, track)
                    .orElseGet(() -> {
                        UserTrackInteraction newInteraction = new UserTrackInteraction();
                        newInteraction.setUser(user);
                        newInteraction.setTrack(track);
                        return newInteraction;
                    });

            int listenCount = interaction.getListenCount();
            listenCount++;
            interaction.setListenCount(listenCount);
            userTrackInteractionRepository.save(interaction);

            // Check if user has listened to at least 80% of track duration
            if (listenDuration >= 0.8 * track.getDuration()) {
                // Update total listening time for user
                user.setTotalListeningTimeInMinutes(user.getTotalListeningTimeInMinutes() + listenDuration);
                userRepository.save(user);
            }

            return ResponseEntity.ok("Track listen count updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating track listen count: " + e.getMessage());
        }
    }
}
