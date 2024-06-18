package it.luminari.UniMuiscBackend.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Order(2) // Secondo rispetto a UserRunner
public class ItemRunner implements ApplicationRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (itemRepository.count() == 0) {
            List<Request> items = Arrays.asList(
                    new Request("Laptop", "High-end gaming laptop", "laptop.jpg", LocalDateTime.now(), true),
                    new Request("Smartphone", "Latest model smartphone", "smartphone.jpg", LocalDateTime.now(), true),
                    new Request("Headphones", "Noise-cancelling headphones", "headphones.jpg", LocalDateTime.now(), false)
            );

            items.forEach(request -> itemService.create(request));
            System.out.println("--- Items inseriti ---");
        } else {
            System.out.println("--- Items già inseriti ---");
        }
    }
}
