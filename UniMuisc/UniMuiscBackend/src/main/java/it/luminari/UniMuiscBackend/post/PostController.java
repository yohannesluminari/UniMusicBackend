package it.luminari.UniMuiscBackend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.create(postRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> modify(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.modify(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(postService.delete(id));
    }
}
