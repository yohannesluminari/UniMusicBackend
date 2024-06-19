package it.luminari.UniMuiscBackend.item;

import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ItemResponse> findAllByUserId(Long userId) {
        return itemRepository.findAllByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ItemResponse findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item non trovato"));
        return mapToResponse(item);
    }

    public List<ItemResponse> findByAvailability(String available) {
        return itemRepository.findByAvailable(available).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ItemResponse create(@Valid ItemRequest itemRequest) {
        User user = userRepository.findById(itemRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User non trovato"));

        Item item = new Item();
        BeanUtils.copyProperties(itemRequest, item);
        item.setUser(user);
        item.setCreatedAt(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        return mapToResponse(savedItem);
    }

    public ItemResponse modify(Long id, ItemRequest itemRequest) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item non trovato"));

        BeanUtils.copyProperties(itemRequest, item);
        item.setCreatedAt(LocalDateTime.now());

        Item updatedItem = itemRepository.save(item);
        return mapToResponse(updatedItem);
    }

    public String delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item non trovato");
        }

        itemRepository.deleteById(id);
        return "Item eliminato";
    }

    public ItemResponse updateAvailability(Long id, String available) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item non trovato"));
        item.setAvailable(available);
        Item updatedItem = itemRepository.save(item);
        return mapToResponse(updatedItem);
    }

    private @NotNull ItemResponse mapToResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setTitle(item.getTitle());
        response.setDescription(item.getDescription());
        response.setPrice(item.getPrice());
        response.setAvailable(item.getAvailable()); // Aggiungi lo stato di disponibilità nella risposta
        response.setUserId(item.getUser().getId());
        response.setUsername(item.getUser().getUsername());
        response.setCreatedAt(item.getCreatedAt().toString());
        return response;
    }
}
