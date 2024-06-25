package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.security.JWTTools;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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



    public Response register(Request request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode the password

        userRepository.save(user);

        // Creazione della risposta senza includere la password
        Response response = new Response();
        BeanUtils.copyProperties(user, response, "password"); // Escludi completamente la password

        return response;
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

    public Response create(Request request){
        User entity = new User();
        BeanUtils.copyProperties(request, entity);

        // Encode the password before saving
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
}
