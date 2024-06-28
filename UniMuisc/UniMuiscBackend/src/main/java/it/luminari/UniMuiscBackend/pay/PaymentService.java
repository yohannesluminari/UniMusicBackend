package it.luminari.UniMuiscBackend.pay;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public PaymentIntent createPaymentIntent(String paymentMethodId, int amount, String currency) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("payment_method_types", Arrays.asList("card"));
        params.put("payment_method", paymentMethodId);
        params.put("confirm", true);

        return PaymentIntent.create(params);
    }

    // Aggiungi altri metodi come il salvataggio delle informazioni del pagamento, gestione errori, etc.
}
