package it.luminari.UniMuiscBackend.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}
