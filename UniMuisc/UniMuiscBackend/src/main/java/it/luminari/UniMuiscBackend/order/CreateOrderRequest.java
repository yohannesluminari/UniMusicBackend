package it.luminari.UniMuiscBackend.order;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long itemId;
    private Long buyerId;

  //  // Getters e setters
//
  //  public Long getItemId() {
  //      return itemId;
  //  }
//
  //  public void setItemId(Long itemId) {
  //      this.itemId = itemId;
  //  }
//
  //  public Long getBuyerId() {
  //      return buyerId;
  //  }
//
  //  public void setBuyerId(Long buyerId) {
  //      this.buyerId = buyerId;
  //  }
}
