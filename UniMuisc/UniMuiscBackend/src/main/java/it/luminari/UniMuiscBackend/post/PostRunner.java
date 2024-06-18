package it.luminari.UniMuiscBackend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class PostRunner implements ApplicationRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (postService.findAllPosts().isEmpty()) {
            List<PostRequest> postRequests = Arrays.asList(
                    new PostRequest(1L, "First post", "image1.jpg"),
                    new PostRequest(2L, "Second post", "image2.jpg"),
                    new PostRequest(3L, "Third post", "image3.jpg")
            );

            for (PostRequest request : postRequests) {
                PostResponse createdPost = postService.createPost(request);
                System.out.println("Created post with ID: " + createdPost.getId());
            }

            System.out.println("--- Posts inseriti ---");
        } else {
            System.out.println("--- Posts già inseriti ---");
        }
    }
}
