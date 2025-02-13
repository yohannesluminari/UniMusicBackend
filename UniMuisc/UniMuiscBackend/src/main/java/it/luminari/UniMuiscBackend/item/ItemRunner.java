package it.luminari.UniMuiscBackend.item;

import it.luminari.UniMuiscBackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@Order(3)
public class ItemRunner implements ApplicationRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Controlla se ci sono item già inseriti
        if (itemRepository.count() == 0) {
            // Lista di item da inserire
            List<ItemRequest> items = Arrays.asList(
                    new ItemRequest("Guitar", "A high-quality acoustic guitar.", new BigDecimal("299.99"), 1L, "available", "guitar.jpg"),
                    new ItemRequest("Drum Set", "A complete drum set for beginners.", new BigDecimal("499.99"), 2L, "available", "drum_set.jpg"),
                    new ItemRequest("Keyboard", "A portable keyboard with 61 keys.", new BigDecimal("199.99"), 1L, "available", "keyboard.jpg")
            );

            // Per ogni item nella lista, crea l'item utilizzando l'ItemService
            items.forEach(itemRequest -> {
                // Utilizza l'ItemService per creare l'item
                itemService.create(itemRequest);
            });

            System.out.println("--- Items inseriti ---");
        } else {
            System.out.println("--- Items già inseriti ---");
        }
    }
}
