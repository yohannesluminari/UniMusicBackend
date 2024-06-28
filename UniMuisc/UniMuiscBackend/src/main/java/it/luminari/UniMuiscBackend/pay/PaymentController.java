//package it.luminari.UniMuiscBackend.pay;
//
//
//
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping("/create-payment-intent")
//    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
//        try {
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getPaymentMethodId(),
//                    Math.toIntExact(request.getAmount()),
//                    request.getCurrency()
//            );
//            return ResponseEntity.ok(paymentIntent);
//        } catch (StripeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/confirm-order")
//    public ResponseEntity<?> confirmOrder(@RequestParam Long itemId, @RequestParam Long buyerId) {
//        try {
//            orderService.processOrder(itemId, buyerId);
//            return ResponseEntity.ok("Order confirmed successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}
