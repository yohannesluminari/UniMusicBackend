package it.luminari.UniMuiscBackend.pay;

import java.util.UUID;

public class DeliveryCodeGenerator {
    public static String generateDeliveryCode() {
        return UUID.randomUUID().toString();
    }
}
