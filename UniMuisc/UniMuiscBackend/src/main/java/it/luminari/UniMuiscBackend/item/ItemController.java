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

    // GET all items
    @GetMapping
    public List<ItemResponsePrj> getAllItems() {
        return itemService.findAll();
    }

    // GET item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    // POST create new item
    @PostMapping
    public ResponseEntity<Response> createItem(@RequestBody Request request) {
        return ResponseEntity.ok(itemService.create(request));
    }

    // PUT modify existing item
    @PutMapping("/{id}")
    public ResponseEntity<Response> modifyItem(@PathVariable Long id, @RequestBody Request request) {
        return ResponseEntity.ok(itemService.modify(id, request));
    }

    // DELETE item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.delete(id));
    }
}
