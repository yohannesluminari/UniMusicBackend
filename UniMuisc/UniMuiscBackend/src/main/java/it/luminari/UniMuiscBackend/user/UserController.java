package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.track.Track;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponsePrj>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody Request request){
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> modify(@PathVariable Long id, @RequestBody Request request){
        return ResponseEntity.ok(userService.modify(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }


    // FAVOURITE SONGS
    @GetMapping("/{userId}/favourite-tracks")
    public Set<Track> getFavouriteTracks(@PathVariable Long userId) {
        return userService.getFavouriteTracks(userId);
    }

    @PostMapping("/{userId}/favourite-tracks/{trackId}")
    public void likeTrack(@PathVariable Long userId, @PathVariable Long trackId) {
        userService.likeTrack(userId, trackId);
    }

    @DeleteMapping("/{userId}/favourite-tracks/{trackId}")
    public void unlikeTrack(@PathVariable Long userId, @PathVariable Long trackId) {
        userService.unlikeTrack(userId, trackId);
    }
}
