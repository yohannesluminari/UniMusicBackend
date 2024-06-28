package it.luminari.UniMuiscBackend.pay;


import lombok.Data;

@Data
public class PaymentIntentRequest {
    private String paymentMethodId;
    private Long amount;
    private String currency;

}
