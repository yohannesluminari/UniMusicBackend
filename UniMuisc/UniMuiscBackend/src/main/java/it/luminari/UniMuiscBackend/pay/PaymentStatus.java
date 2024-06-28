package it.luminari.UniMuiscBackend.pay;

public enum PaymentStatus {
    PENDING,     // Transazione di pagamento avviata ma non ancora completata
    COMPLETED,   // Pagamento completato con successo
    FAILED,      // Transazione di pagamento non riuscita
    REFUNDED     // Pagamento rimborsato
}
