package it.luminari.UniMuiscBackend.item;

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
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ItemResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(itemService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.create(itemRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> modify(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.modify(id, itemRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.delete(id));
    }
}
