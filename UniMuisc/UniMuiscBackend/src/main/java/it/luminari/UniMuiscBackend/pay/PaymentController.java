package it.luminari.UniMuiscBackend.pay;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')") // Permette solo agli utenti autenticati di creare pagamenti
    public ResponseEntity<Payment> createPayment(@RequestParam Long itemId, @RequestParam Long buyerId) {
        return ResponseEntity.ok(paymentService.createPayment(itemId, buyerId));
    }

    @PatchMapping("/{paymentId}/status")
    @PreAuthorize("hasRole('ADMIN')") // Solo gli admin possono aggiornare lo stato dei pagamenti
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }
}
