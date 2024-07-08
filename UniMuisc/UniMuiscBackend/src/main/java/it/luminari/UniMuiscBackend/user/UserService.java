package it.luminari.UniMuiscBackend.user;

import it.luminari.UniMuiscBackend.album.Album;
import it.luminari.UniMuiscBackend.album.AlbumRepository;
import it.luminari.UniMuiscBackend.artist.Artist;
import it.luminari.UniMuiscBackend.artist.ArtistRepository;
import it.luminari.UniMuiscBackend.security.JWTTools;
import it.luminari.UniMuiscBackend.track.Track;
import it.luminari.UniMuiscBackend.track.TrackRepository;
import it.luminari.UniMuiscBackend.track.UserTrackInteraction;
import it.luminari.UniMuiscBackend.track.UserTrackInteractionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;


    @Autowired
    private UserTrackInteractionRepository userTrackInteractionRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    /**
     * Metodo per controllare la validità della password secondo i criteri specificati:
     * - Almeno 8 caratteri
     * - Almeno un numero
     * - Almeno una lettera maiuscola
     *
     * @param password La password da controllare
     * @throws IllegalArgumentException Se la password non rispetta i criteri
     */
    public void validatePassword(String password) {
        if (password == null || password.isEmpty() || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        boolean hasDigit = false;
        boolean hasUpperCase = false;

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            }

            // Se entrambe le condizioni sono soddisfatte, possiamo uscire dal ciclo
            if (hasDigit && hasUpperCase) {
                return;
            }
        }

        // Se una delle condizioni non è soddisfatta, lancia un'eccezione
        if (!hasDigit) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }

        if (!hasUpperCase) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
    }



    public Response register(Request request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Validate password
        validatePassword(request.getPassword());

        // Create new user entity
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save user
        userRepository.save(user);

        // Send registration confirmation email
        sendRegistrationConfirmationEmail(user);

        // Create response object (excluding password)
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


    public Response modify(Long id, Request request) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        entity.setUsername(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setAvatar(request.getAvatar());

        // Valida la password solo se viene fornita una nuova password
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            validatePassword(request.getPassword());
            entity.setPassword(passwordEncoder.encode(request.getPassword())); // Codifica la nuova password
        }

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


    public void likeArtist(Long userId, Long artistId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        user.getLikedArtists().add(artist);
        userRepository.save(user);
    }

    public void unlikeArtist(Long userId, Long artistId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        user.getLikedArtists().remove(artist);
        userRepository.save(user);
    }

    public void likeAlbum(Long userId, Long albumId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        user.getLikedAlbums().add(album);
        userRepository.save(user);
    }

    public void unlikeAlbum(Long userId, Long albumId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        user.getLikedAlbums().remove(album);
        userRepository.save(user);
    }






    public boolean usernameOrEmailExists(String username, String email) {
        // Verifica se esiste un utente con lo stesso username
        boolean usernameExists = userRepository.existsByUsername(username);
        // Verifica se esiste un utente con la stessa email
        boolean emailExists = userRepository.existsByEmail(email);

        // Restituisce true se esiste un utente con lo stesso username o email
        return usernameExists || emailExists;
    }




    // GESTIONE N ASCOLTI X AFFINITA
    @Transactional
    public void updateTrackListenCount(User user, Track track, int listenDuration) {
        // Calculate listen duration in seconds
        int listenDurationInSeconds = (listenDuration / 1000);

        // Update or create UserTrackInteraction
        UserTrackInteraction interaction = userTrackInteractionRepository
                .findByUserAndTrack(user, track)
                .orElse(new UserTrackInteraction());
        interaction.setUser(user);
        interaction.setTrack(track);
        interaction.setListenCount(interaction.getListenCount() + 1);
        interaction.setListeningTimeInSeconds(interaction.getListeningTimeInSeconds() + listenDurationInSeconds);

        userTrackInteractionRepository.save(interaction);

        // Update total listening time for the user
        user.setTotalListeningTimeInMinutes(user.getTotalListeningTimeInMinutes() + listenDurationInSeconds / 60);

        // Update album listening time
        Album album = track.getAlbum();
        album.getUserListeningTimes().put(user,
                album.getUserListeningTimes().getOrDefault(user, 0) + listenDurationInSeconds);
        album.setListeningTimeInMinutes(album.getListeningTimeInMinutes() + listenDurationInSeconds / 60);

        // Update artist listening time
        Artist artist = track.getArtist();
        artist.getUserListeningTimes().put(user,
                artist.getUserListeningTimes().getOrDefault(user, 0) + listenDurationInSeconds);
        artist.setListeningTimeInMinutes(artist.getListeningTimeInMinutes() + listenDurationInSeconds / 60);

        // Save updated album and artist
        userRepository.save(user);
    }

    // Method to get the top tracks by listening time for a user
    public List<Track> getUserTopTracks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userTrackInteractionRepository.findByUser(user).stream()
                .sorted(Comparator.comparingInt(UserTrackInteraction::getListeningTimeInSeconds).reversed())
                .map(UserTrackInteraction::getTrack)
                .collect(Collectors.toList());
    }

    // Method to get the top albums by listening time for a user
    public List<Album> getUserTopAlbums(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Aggregate listening times for each album
        Map<Album, Integer> albumListeningTime = new HashMap<>();
        for (UserTrackInteraction interaction : userTrackInteractionRepository.findByUser(user)) {
            Track track = interaction.getTrack();
            Album album = track.getAlbum();
            int duration = interaction.getListeningTimeInSeconds();

            albumListeningTime.put(album, albumListeningTime.getOrDefault(album, 0) + duration);
        }

        return albumListeningTime.entrySet().stream()
                .sorted(Map.Entry.<Album, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Method to get the top artists by listening time for a user
    public List<Artist> getUserTopArtists(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Aggregate listening times for each artist
        Map<Artist, Integer> artistListeningTime = new HashMap<>();
        for (UserTrackInteraction interaction : userTrackInteractionRepository.findByUser(user)) {
            Track track = interaction.getTrack();
            Artist artist = track.getArtist();
            int duration = interaction.getListeningTimeInSeconds();

            artistListeningTime.put(artist, artistListeningTime.getOrDefault(artist, 0) + duration);
        }

        return artistListeningTime.entrySet().stream()
                .sorted(Map.Entry.<Artist, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    // Logica per seguire un altro utente
    public void followUser(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Target User not found"));
        user.getFollowing().add(targetUser);
        targetUser.getFollowers().add(user);
        userRepository.save(user);
        userRepository.save(targetUser);
    }

    // Logica per smettere di seguire un altro utente
    public void unfollowUser(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Target User not found"));
        user.getFollowing().remove(targetUser);
        targetUser.getFollowers().remove(user);
        userRepository.save(user);
        userRepository.save(targetUser);
    }
}