package it.luminari.UniMuiscBackend.order;

public enum OrderStatus {
    PENDING,     // Ordine creato ma non ancora elaborato
    SHIPPED,     // Ordine spedito
    DELIVERED,   // Ordine consegnato al compratore
    CANCELLED    // Ordine cancellato
}
