//package it.luminari.UniMuiscBackend.pay;
//
//
//import it.luminari.UniMuiscBackend.item.Item;
//import it.luminari.UniMuiscBackend.item.ItemRepository;
//import it.luminari.UniMuiscBackend.user.User;
//import it.luminari.UniMuiscBackend.user.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    public void processOrder(Long itemId, Long buyerId) {
//        User buyer = userRepository.findById(buyerId)
//                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
//
//        // Imposta lo stato dell'item come venduto e l'utente che lo ha acquistato
//        item.setSold(true);
//        item.setBuyer(buyer);
//        itemRepository.save(item);
//
//        // Implementa la logica per la conferma e la notifica della consegna all'utente
//        // Esegui azioni come notificare il venditore, gestire lo stato dell'ordine, etc.
//    }
//}
