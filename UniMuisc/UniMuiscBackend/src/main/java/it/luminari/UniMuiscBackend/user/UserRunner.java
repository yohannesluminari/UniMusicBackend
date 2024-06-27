package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.item.ItemRunner;
import it.luminari.UniMuiscBackend.post.PostRunner;
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

    @Autowired
    PostRunner postRunner;
    @Autowired
    ItemRunner itemRunner;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Lista degli utenti da inserire
        List<Request> users = Arrays.asList(
                new Request("user1", "password1", "user1@example.com", "avatar1.jpg"),
                new Request("user2", "password2", "user2@example.com", "avatar2.jpg")
        );

        // Per ogni utente nella lista, verifica se l'email è valida e non esiste nel database
        users.forEach(request -> {
            if (isValidEmail(request.getEmail())) {
                userService.register(request); // Registra solo se l'email è valida
                System.out.println("Registrazione effettuata con successo per l'email: " + request.getEmail());
            } else {
                System.out.println("Email non valida: " + request.getEmail());
            }
        });

        // Stampare un messaggio dopo aver inserito gli utenti
        System.out.println("--- Users inserted ---");
        // Dopo aver registrato gli utenti, esegui i runner successivi
        postRunner.run(args);
        itemRunner.run(args);
    }

    // Metodo semplificato per verificare se un'email è valida (non integra una vera verifica di esistenza delle email)
    private boolean isValidEmail(String email) {
        // Implementazione semplificata per verificare il formato dell'email
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}

