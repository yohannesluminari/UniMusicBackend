package it.luminari.UniMuiscBackend.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Metodo per trovare tutti gli utenti
    public List<Response> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Metodo per trovare un utente per ID
    public Response findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return convertToResponse(user);
    }

    // Metodo per creare un nuovo utente
    @Transactional
    public Response create(Request request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    // Metodo per modificare un utente esistente
    @Transactional
    public Response modify(Long id, Request request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        BeanUtils.copyProperties(request, user);
        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }

    // Metodo per eliminare un utente
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Metodo privato per convertire User in Response
    private Response convertToResponse(User user) {
        Response response = new Response();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
