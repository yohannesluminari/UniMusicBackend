package it.luminari.UniMuiscBackend.order;

import it.luminari.UniMuiscBackend.item.Item;
import it.luminari.UniMuiscBackend.item.ItemRepository;
import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogisticsService logisticsService;

    public Order createOrder(Long itemId, Long buyerId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        User seller = item.getUser();

        String deliveryCode = logisticsService.fetchDeliveryCode(); // Ottieni il codice di consegna dal servizio di logistica

        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setItem(item);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryCode(deliveryCode);

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

