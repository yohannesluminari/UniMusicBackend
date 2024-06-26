package it.luminari.UniMuiscBackend.saveMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@Order(4)
public class DataInitializer  {

    @Autowired
    private DeezerDataService deezerDataService;

    @GetMapping("/save-artist/{id}")
    public ResponseEntity<String> saveArtistData(@PathVariable Long id) {
        try {
            deezerDataService.fetchAndSaveData(id);
            return ResponseEntity.ok("Dati dell'artista salvati con successo!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante il salvataggio dei dati dell'artista: " + e.getMessage());
        }
    }
}
