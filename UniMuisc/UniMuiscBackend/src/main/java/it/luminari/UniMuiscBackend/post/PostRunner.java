package it.luminari.UniMuiscBackend.post;

import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
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
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (postRepository.count() == 0) {
            List<PostRequest> posts = Arrays.asList(
                    new PostRequest("First Post", "Content of the first post", 1L, 4.5, "image1.jpg"),
                    new PostRequest("Second Post", "Content of the second post", 2L, 3.0, "image2.jpg"),
                    new PostRequest("Third Post", "Content of the third post", 3L, 5.0, "image3.jpg")
            );

            posts.forEach(postRequest -> postService.create(postRequest));
            System.out.println("--- Posts inseriti ---");
        } else {
            System.out.println("--- Posts già inseriti ---");
        }
    }
}
