package it.luminari.UniMuiscBackend.item;


import java.time.LocalDateTime;

public interface ItemResponsePrj {
    String getName();
    String getDescription();
    String getImage();
    LocalDateTime getPublicationDate();
    boolean isAvailable();

}
