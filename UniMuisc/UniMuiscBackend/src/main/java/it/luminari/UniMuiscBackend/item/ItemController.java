package it.luminari.UniMuiscBackend.item;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemResponse> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<ItemResponse> getItemsByUserId(@PathVariable Long userId) {
        return itemService.findAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @GetMapping("/available/{status}")
    public List<ItemResponse> getItemsByAvailability(@PathVariable String status) {
        return itemService.findByAvailability(status);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.create(itemRequest));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody @Valid ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.modify(id, itemRequest));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<ItemResponse> updateItemAvailability(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(itemService.updateAvailability(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.delete(id));
    }
}
