package it.luminari.UniMuiscBackend.pay;


import it.luminari.UniMuiscBackend.item.Item;
import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    private LocalDateTime paymentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Usa l'Enum `PaymentStatus`
}
