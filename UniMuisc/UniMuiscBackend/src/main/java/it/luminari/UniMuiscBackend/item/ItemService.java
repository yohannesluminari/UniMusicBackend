package it.luminari.UniMuiscBackend.item;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // GET ALL
    public List<ItemResponsePrj> findAll() {
        return itemRepository.findAllBy();
    }

    // GET by ID
    public Response findById(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item not found");
        }
        Item entity = itemRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    // POST
    public Response create(@Valid Request request) {
        Item entity = new Item();
        BeanUtils.copyProperties(request, entity);

        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        itemRepository.save(entity);
        return response;
    }

    // PUT
    public Response modify(Long id, Request request) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item not found");
        }

        Item entity = itemRepository.findById(id).get();
        BeanUtils.copyProperties(request, entity);

        itemRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    // DELETE
    public String delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item not found");
        }
        itemRepository.deleteById(id);
        return "Item deleted";
    }
}
