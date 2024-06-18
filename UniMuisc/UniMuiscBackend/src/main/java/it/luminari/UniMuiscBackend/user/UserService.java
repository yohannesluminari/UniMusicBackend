package it.luminari.UniMuiscBackend.user;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // GET ALL
    public List<UserResponsePrj> findAll() {
        List<User> users = userRepository.findAll();
        return userRepository.findAllBy();
    }

    // GET by ID
    public Response findById(Long id) {
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User non trovato");
        }
        User entity = userRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    // POST
    public Response create(@Valid Request request){
        User entity = new User();
        BeanUtils.copyProperties(request, entity);

        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        userRepository.save(entity);
        return response;
    }

    // PUT
    public Response modify(Long id, Request request){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User non trovato");
        }

        User entity = userRepository.findById(id).get();
        BeanUtils.copyProperties(request, entity);

        userRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    //DELETE
    public String delete(Long id){
        // Questo metodo elimina un autore dal database.
        // Prima verifica se l'autore esiste nel database. Se non esiste, viene generata un'eccezione.
        if(!userRepository.existsById(id)){
            throw  new EntityNotFoundException("Autore non trovato");
        }
        // Se l'autore esiste, viene eliminato dal database.
        userRepository.deleteById(id);
        return "Autore eliminato";
    }
}
