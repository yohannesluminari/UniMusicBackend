package it.luminari.UniMuiscBackend.order;

import it.luminari.UniMuiscBackend.item.Item;
import it.luminari.UniMuiscBackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime orderDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Usa l'Enum `OrderStatus`

    @NotNull
    private String deliveryCode; // Codice di consegna
}
