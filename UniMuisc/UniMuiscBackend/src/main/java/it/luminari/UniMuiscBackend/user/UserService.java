package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.security.JWTTools;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;


    public Response register(Request request) {
        // Verifica se il nome utente esiste già
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Verifica se l'email esiste già
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Procedi con la creazione dell'utente se username ed email sono disponibili
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Codifica la password

        userRepository.save(user);

        // Invia email di conferma registrazione all'utente
        sendRegistrationConfirmationEmail(user);

        // Invia email di notifica all'indirizzo y.luminari@gmail.com
        sendAdminNotificationEmail(user);

        // Creazione della risposta senza includere la password
        Response response = new Response();
        BeanUtils.copyProperties(user, response);
        return response;
    }


    private void sendRegistrationConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Benvenuto su UniMusic!");
        message.setText("Grazie per esserti registrato su UniMusic. Goditi la tua permanenza!");

        javaMailSender.send(message); // Utilizza javaMailSender invece di mailSender
    }

    private void sendAdminNotificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("y.luminari@gmail.com");
        message.setSubject("Nuova registrazione su UniMusic");
        message.setText("L'utente " + user.getUsername() + " si è registrato con successo su UniMusic.");

        javaMailSender.send(message); // Utilizza javaMailSender invece di mailSender
    }









    public String login(Request request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return jwtTools.createToken(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }








    public List<UserResponsePrj> findAll() {
        return userRepository.findAllBy();
    }

    public Response findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Response create(Request request) {
        // Verifica se il nome utente esiste già
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Verifica se l'email esiste già
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Se l'username e l'email sono disponibili, procedi con la creazione dell'utente
        User entity = new User();
        BeanUtils.copyProperties(request, entity);

        // Codifica la password prima di salvarla
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        userRepository.save(entity);
        return response;
    }


    public Response modify(Long id, Request request){
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setEmail(request.getEmail());
        entity.setAvatar(request.getAvatar());

        userRepository.save(entity);

        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public String delete(Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return "User deleted";
    }

    public void likeTrack(Long userId, Long trackId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new EntityNotFoundException("Track not found"));

        user.getFavouriteTracks().add(track);
        userRepository.save(user);
    }

    public void unlikeTrack(Long userId, Long trackId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new EntityNotFoundException("Track not found"));

        user.getFavouriteTracks().remove(track);
        userRepository.save(user);
    }

    public Set<Track> getFavouriteTracks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getFavouriteTracks();
    }


    public boolean usernameOrEmailExists(String username, String email) {
        // Verifica se esiste un utente con lo stesso username
        boolean usernameExists = userRepository.existsByUsername(username);
        // Verifica se esiste un utente con la stessa email
        boolean emailExists = userRepository.existsByEmail(email);

        // Restituisce true se esiste un utente con lo stesso username o email
        return usernameExists || emailExists;
    }

}
