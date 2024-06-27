package it.luminari.UniMuiscBackend.post;

import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PostResponse> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post non trovato"));
        return mapToResponse(post);
    }

    public PostResponse create(@Valid PostRequest postRequest) {
        Long userId = postRequest.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    private PostResponse mapToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setUserId(post.getUser().getId());
        response.setUsername(post.getUser().getUsername());
        response.setRating(post.getRating());
        response.setCreatedAt(post.getCreatedAt().toString());
        response.setImage(post.getImage()); // Set image in the response
        return response;
    }

    public PostResponse modify(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post non trovato"));

        BeanUtils.copyProperties(postRequest, post);
        post.setCreatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return mapToResponse(updatedPost);
    }

    public String delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post non trovato");
        }

        postRepository.deleteById(id);
        return "Post eliminato";
    }


}
