package it.luminari.UniMuiscBackend.item;

import it.luminari.UniMuiscBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<ItemResponsePrj> findAllBy();

    public List<Item> findByAUser(User user);


    List<Item> findAllByAvailable(boolean available);

    List<Item> findAllByName(String name);

}
