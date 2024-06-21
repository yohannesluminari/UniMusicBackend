package it.luminari.UniMuiscBackend.saveMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DeezerDataService deezerDataService;

    @Override
    public void run(String... args) throws Exception {
        deezerDataService.fetchAndSaveTracks();
    }
}
