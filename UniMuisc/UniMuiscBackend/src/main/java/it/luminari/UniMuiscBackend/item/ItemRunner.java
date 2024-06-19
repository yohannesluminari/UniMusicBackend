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
        if (itemRepository.count() == 0) {
            List<ItemRequest> items = Arrays.asList(
                    new ItemRequest("Guitar", "A high-quality acoustic guitar.", new BigDecimal("299.99"), 1L, "available"),
                    new ItemRequest("Drum Set", "A complete drum set for beginners.", new BigDecimal("499.99"), 2L, "available"),
                    new ItemRequest("Keyboard", "A portable keyboard with 61 keys.", new BigDecimal("199.99"), 3L, "available")
            );

            items.forEach(itemRequest -> itemService.create(itemRequest));
            System.out.println("--- Items inseriti ---");
        } else {
            System.out.println("--- Items già inseriti ---");
        }
    }
}
