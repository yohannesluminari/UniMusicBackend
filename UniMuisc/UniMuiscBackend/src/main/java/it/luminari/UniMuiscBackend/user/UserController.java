package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrackRepository trackRepository;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody Request request) {
        try {
            Response response = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // Aggiungi altri metodi se necessario, come logout, cambio password, etc.

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response> handleAuthenticationException(AuthenticationException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id){
        try {
            Response response = userService.findById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponsePrj>> findAll(){
        List<UserResponsePrj> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody Request request){
        try {
            // Creazione utente
            Response response = userService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Cattura l'eccezione e restituisci un messaggio di errore dettagliato
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> modify(@PathVariable Long id, @RequestBody Request request){
        try {
            Response response = userService.modify(id, request);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            String message = userService.delete(id);
            return ResponseEntity.ok(message);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // FAVOURITE SONGS
    @GetMapping("/{userId}/favourite-tracks")
    public ResponseEntity<Set<Track>> getFavouriteTracks(@PathVariable Long userId) {
        Set<Track> favouriteTracks = userService.getFavouriteTracks(userId);
        return ResponseEntity.ok(favouriteTracks);
    }

    @PostMapping("/{userId}/favourite-tracks/{trackId}")
    public ResponseEntity<Response> likeTrack(@PathVariable Long userId, @PathVariable Long trackId) {
        try {
            userService.likeTrack(userId, trackId);
            Response response = new Response();
            response.setMessage("Track liked");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{userId}/favourite-tracks/{trackId}")
    public ResponseEntity<Response> unlikeTrack(@PathVariable Long userId, @PathVariable Long trackId) {
        try {
            userService.unlikeTrack(userId, trackId);
            Response response = new Response();
            response.setMessage("Track unliked");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException(EntityNotFoundException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/{userId}/update-track-listen-count/{trackId}")
    public ResponseEntity<Response> updateTrackListenCount(
            @PathVariable Long userId,
            @PathVariable Long trackId,
            @RequestParam int listenDuration
    ) {
        try {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new EntityNotFoundException("Track not found"));

            // Update user's track interaction and check for album and artist listening time
            userService.updateTrackListenCount(user, track, listenDuration);

            Response response = new Response();
            response.setMessage("Track listen count updated successfully");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Response errorResponse = new Response();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long targetUserId) {
        userService.followUser(userId, targetUserId);
        return ResponseEntity.ok("User followed successfully");
    }

    @PutMapping("/{userId}/unfollow/{targetUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @PathVariable Long targetUserId) {
        userService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok("User unfollowed successfully");
    }

}