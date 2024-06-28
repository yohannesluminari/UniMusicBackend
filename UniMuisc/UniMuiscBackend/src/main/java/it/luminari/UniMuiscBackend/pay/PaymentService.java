package it.luminari.UniMuiscBackend.pay;


import it.luminari.UniMuiscBackend.item.Item;
import it.luminari.UniMuiscBackend.item.ItemRepository;
import it.luminari.UniMuiscBackend.user.User;
import it.luminari.UniMuiscBackend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment createPayment(Long itemId, Long buyerId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        User buyer = userRepository.findById(buyerId).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        User seller = item.getUser();

        Payment payment = new Payment();
        payment.setAmount(item.getPrice());
        payment.setBuyer(buyer);
        payment.setSeller(seller);
        payment.setItem(item);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING); // Usa `PaymentStatus.PENDING`

        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        payment.setStatus(status); // Usa l'Enum `PaymentStatus`
        return paymentRepository.save(payment);
    }
}

