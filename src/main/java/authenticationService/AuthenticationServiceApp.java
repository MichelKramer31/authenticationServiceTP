package authenticationService;

import requestHandler.*;

import java.io.IOException;


/**
 * Programme principal utilisé pour démmmarer les services
 * @author michelkramer
 */
public class AuthenticationServiceApp {

    public static void main(String[] args) {

        //On definit les constantes de l'application
        // Le port sur lequel le serveur UDP va écouter
        final Integer UDPPort = 28414;
        // Le port sur lequel le serveur TCP va écouter
        final Integer TCPCPort = 28414;
        //Le port sur lequel le serveur TCP qui gère les requetes du manager va écouter
        final Integer TCPMPort = 28415;

        // On initialise le service d'identification et notre interpréteur de requetes
        ListeAuth la = new ListeAuth();

       AuthenticationRequestHandler rmC = new AuthenticationCheckHandler(la);

       // on peut joliment imbriqué nos handler pour faire une chaine de handler qui va être suivit pour resoudre notre requete
       AuthenticationRequestHandler rmM = new AuthenticationCheckHandler(la);
       rmM.appendNext(new AuthenticationAddHandler());
       rmM.appendNext(new AuthenticationModifyHandler());
       rmM.appendNext(new AuthenticationDeleteHandler());

        // On démarre le serveur UDP
        ServeurUDP udp = new ServeurUDP(UDPPort, rmC);
        udp.start();

        try {
            //On essai de lancer le server TCP pour traiter les requetes checker
            ServeurTCP tcpACC = new ServeurTCP(TCPCPort, rmC);
            tcpACC.start();

            //On essai de lancer le server TCP pour les requetes manager
            ServeurTCP tcpACM = new ServeurTCP(TCPMPort, rmM);
            tcpACM.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
