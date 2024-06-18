package it.luminari.UniMuiscBackend.post;

import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // GET ALL
    @Transactional
    public List<Post> findAll() { //i metodi sono verdi perche hai totlo post da  findAll e findById
        return postRepository.findAll();
    }

    // GET by ID
    @Transactional
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    // POST
    @Transactional
    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUsername(currentPrincipalName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentPrincipalName));

        Post post = new Post();
        post.setDescription(request.getDescription());
        post.setImage(request.getImage());
        post.setPublicationDate(LocalDateTime.now());
        post.setUser(user); // Set the user for the post

        Post savedPost = postRepository.save(post);

        return createPostResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        post.setDescription(request.getDescription());
        post.setImage(request.getImage());
        // Optionally update publication date
        // post.setPublicationDate(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);

        return createPostResponse(updatedPost);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    private PostResponse createPostResponse(Post post) {
        return new PostResponse(post.getId(), post.getDescription(), post.getImage(), post.getPublicationDate());
    }
}
