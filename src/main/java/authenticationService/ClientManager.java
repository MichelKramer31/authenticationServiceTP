package authenticationService;
import requestHandler.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *  Classe utiliser pour traiter la demande d'un client en TCP
 *  Celle ci accorde et gère chaque demande dans un nouveau thrad
 *  @author michelkramer
 */
public class ClientManager extends Thread{


    /**
     * La chaine de handler à utiliser pour le thread
     */
    RequestHandler rh;

    /**
     * Le socket de la connexion
     */
    Socket sss;

    ClientManager(RequestHandler rh, Socket sss) {
        this.rh = rh;
        this.sss = sss;
    }

    /**
     *  Execution du client dans un thread, tel que spécifié dans le classe parente
     */
    public void run() {

        try {
            // Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
            BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));
            // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
            PrintStream sortieSocket = new PrintStream(sss.getOutputStream());

            String chaine = "";

            while (chaine != null) {

                // lecture d'une chaine envoyée à travers la connexion socket
                chaine = entreeSocket.readLine();

                //on construit un objet requete et une entete à partir de notre requete et des infos de notre environement
                Request request = new Request(new Header(sss.getLocalAddress().toString(),sss.getPort(), "TCP"), chaine);

                // on execute notre requete
                Result response = rh.execute(request);

                //si la requete necessitait une reponse
                if(response.getRequest().requiereResponse()) {

                //on répond au client
                sortieSocket.println(response.getResult());

                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
