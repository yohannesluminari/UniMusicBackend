package it.luminari.UniMuiscBackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class UserRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() == 0) {
            List<Request> user = Arrays.asList(
                    new Request("user1", "password1", "user1@example.com", "avatar1.jpg"),
                    new Request("user2", "password2", "user2@example.com", "avatar2.jpg"),
                    new Request("user3", "password3", "user3@example.com", "avatar3.jpg"),

                    new Request("user4", "password4", "user2@example.com", "avatar4.jpg"),
                    new Request("user5", "password5", "user3@example.com", "avatar5.jpg"),
                    new Request("user6", "password6", "user4@example.com", "avatar6.jpg")

            );

            user.forEach(request -> userService.create(request));
            System.out.println("--- User inseriti ---");
        } else {
            System.out.println("--- User già inseriti ---");
        }
    }
}