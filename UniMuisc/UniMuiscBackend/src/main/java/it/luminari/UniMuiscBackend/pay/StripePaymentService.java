//package it.luminari.UniMuiscBackend.pay;
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class StripePaymentService {
//
//    @Value("${stripe.api.key}")
//    private String stripeApiKey;
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = stripeApiKey;
//    }
//
//    public String createPaymentIntent(int amount, String currency, String description) throws StripeException {
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", amount);
//        params.put("currency", currency);
//        params.put("description", description);
//
//        PaymentIntent intent = PaymentIntent.create(params);
//        return intent.getClientSecret();
//    }
//
//    public void confirmPayment(String paymentIntentId) throws StripeException {
//        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
//        Map<String, Object> params = new HashMap<>();
//        intent.confirm(params);
//    }
//}
