package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.track.Track;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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
            if (userService.usernameOrEmailExists(request.getUsername(), request.getEmail())) {
                throw new IllegalArgumentException("Username or email already exists");
            }
            Response response = userService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
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
}