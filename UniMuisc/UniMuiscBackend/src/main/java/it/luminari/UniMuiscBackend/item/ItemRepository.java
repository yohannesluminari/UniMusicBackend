package it.luminari.UniMuiscBackend.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByUserId(Long userId);

    List<Item> findByAvailable(String available);

}
