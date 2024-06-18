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
            List<Request> users = Arrays.asList(
                    new Request("user1", "password1", "user1@example.com", ""),
                    new Request("user2", "password2", "user2@example.com", ""),
                    new Request("user3", "password3", "user3@example.com", "")
                    // Aggiungi altri utenti di esempio se necessario
            );

            users.forEach(userService::create);
            System.out.println("--- Utenti inseriti ---");
        } else {
            System.out.println("--- Utenti già presenti nel database ---");
        }
    }
}
