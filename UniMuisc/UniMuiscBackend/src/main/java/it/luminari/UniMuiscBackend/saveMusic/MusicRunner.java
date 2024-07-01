package it.luminari.UniMuiscBackend.saveMusic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(4)
public class MusicRunner implements ApplicationRunner {

    @Autowired
    private DeezerDataService deezerDataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {


            List<Long> artistIds = Arrays.asList(

                    // ARTISTI ITALIANI
                    1310298L, 48975581L,  63273652L, 2727681L, 48975581L, 79671L, 86918012L, 13700149L, 2936L, 1288678L, 7165296L, 10190528L,

                    8647392L, 8586004L, 5502481L, 5893334L, 10688385L, 11060196L, 12726119L, 13440671L, 57573792L, 7371074L, 9375398L,  10666535L, 10666539L, 63923922L,

                   3501141L,  4768672L, 4072357L, 60156142L, 10666537L, 50285952L, 4328078L,4844243L, 75618L, 54513262L, 7979L, 88901492L, 11621109L, 4901462L, 176639L,

                       /* Shiva, Fabri Fibra, Marracash, Lazza, Nayt, 3D, Luche, Tony Boy, Mecna, Night Skinny, Gemello, Jake La Furia, Sick Luke, Guè, Vegas Jones, Sfera Ebbasta,
                           Ketama126, Franco126, Capo Plaza, Frah Quintale, Rkomi, Bresh, Tedua, Rose Villain, Ernia, Tony Effe, Massimo Pericolo, Venerus, Geolier, Side Baby,
                           Leon Faun, Madame, Diss Gacha, Mara Sattei, Artie 5ive, Mace, ANNA, Il Supremo, Gemitaiz, Nitro, */

                   4446887L, 492372L, 5061189L, 9373892L, 10686109L, 11503771L, 2577251L, 1580268L, 145693L, 245432L, 309916L, 60822372L, 13612387L, 8531L, 5938702L,
                   124420L, 1655339L, 7010575L, 9977802L,64932L, 2276L, 5648L, 1319138L, 9781586L,187153877L,186233L,75680L,14756291L,5608864L, 534258L, 1146332L,
                   12174992L, 9392314L, 144717012L, 4677848L, 12196866L, 139795852L, 90401082L,

                       /*Mahmood, MadMan, Coez, Irama,Takagi & Ketra ,Gazzelle,Ghali, Francesca Michielin,
                           Elodie, Annalisa , Gaia, Rosa Chemical,Maneskin, Lucio Dalla, Willie Peyote, Random, Calcutta, Dardust
                           Ele A, Ligabue, Vasco Rossi, Ultimo,Carl Brave, solomarco, Club Dogo,Dargen D'Amico, Disme,Mezzosangue, ,Jamil
                           Nashley, Joan Thiele, chiello, Izi,Marco Castello, Kid Yugi, Digital Astro*/



                   // ARTISTI INTERNAZIONALI

                   246791L, 525046L, 4495513L, 339209L, 15166511L, 382937L, 230L,  7101343L, 165930L, 1590752L,  13594287L,  4968870L, 9093938L, 398521L, 1194083L,  1518490L,

                   10002824L, 6853403L, 74309L,

                       /*Artisti Hip-Hop/Rap:
                           Drake, Kendrick Lamar, Travis Scott, J. Cole, Lil Nas X, Nicki Minaj, Kanye West, Lil Uzi Vert, Future, Young Thug, Roddy Ricch, Metro Boomin,
                           Jack Harlow, Lil Baby,Tyler the Creator, A$AP Rocky, Playboi Carti, 21 Savage, Lil Wayne, (Machine Gun Kelly), (Cardi B)*/

                   12246L, 9635624L, 1562681L, 384236L, 145L, 8706544L, 4050205L, 288166L, 5313805L, 11152580L, 5578942L, 1362735L, 292185L, 362326L, 7543848L,

                   564L, 12436L, 144227L, 75491L,  429675L, 1147L,  313L, 5531258L,  193875L, 1097709L,

                       /*Cantanti Pop/R&B:
                           Taylor Swift, Billie Eilish, Ariana Grande, Ed Sheeran, Beyoncé, Dua Lipa, The Weeknd, Justin Bieber, Harry Styles, Olivia Rodrigo,Doja Cat,Charlie Puth,S
                           elena Gomez, Khalid, Post Malone, Rihanna, Miley Cyrus, Katy Perry, Lady Gaga, Bruno Mars, Justin Timberlake, John Legend, SZA, Demi Lovato,Sam Smith,*/


                   6982223L,  416239L,  892L,  1188L, 4104927L, 647650L, 74398L,

                       /*Band e Gruppi:
                           BTS, Imagine Dragons, Coldplay, Maroon 5,The Chainsmokers, Twenty One Pilots, OneRepublic*/


                   12178L, 293585L, 4999707L,
                       /*Artisti Elettronica/Dance:
                           Calvin Harris, Marshmello, David Guetta, DJ Snake, Kygo, Zedd, Martin Garrix, Diplo, Avicii, Tiësto, Alan Walker, Steve Aoki, Armin van Buuren*/

                   10583405L,  4860761L, 554792L, 1424602L, 7961888L,  4937383L,160L
                       /*Artisti Latin/Reggaeton:
                            Bad Bunny, J Balvin, Rosalía, Maluma, Anuel AA, Ozuna, Daddy Yankee, Shakira*/
            );

            for (Long artistId : artistIds) {
                deezerDataService.fetchAndSaveData(artistId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
