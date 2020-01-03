package authenticationService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Classe Singleton qui permet de logger des requêtes vers un serveur de log sur le port 3244 de la machine locale
 *
 *
 * @author torguet
 *
 */
public class JsonLogger {


    Socket sc = null;
    PrintStream socketSortie = null;

    /**
     * L
     */
    private JsonLogger() {

        try {
            // pas le peine de
            this.sc = new Socket("localhost", 3244);
        } catch (IOException e) {
            System.out.println("Impossible d'acceder au serveur, vérifier le numéro de port");
        }

        // Construction d'un PrintStream pour envoyer les logs à travers la connexion socket
        PrintStream sortieSocket = null;
        try {
            this.socketSortie = new PrintStream(sc.getOutputStream());
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir la connection");
        }

        // Le socket est pret à être utilisé
    }

    /**
     * Transforme une requête en Json
     *
     * @param host machine client
     * @param port port sur la machine client
     * @param proto protocole de transport utilisé
     * @param type type de la requête
     * @param login login utilisé
     * @param result résultat de l'opération
     * @return un objet Json correspondant à la requête
     */
    private static JsonObject reqToJson(String host, int port, String proto, String type, String login, String result) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("host", host)
                .add("port", port)
                .add("proto", proto)
                .add("type", type)
                .add("login", login)
                .add("result", result)
                .add("date", new Date().toString());

        return builder.build();
    }

    /**
     *  singleton
     */
    private static JsonLogger logger = null;

    /**
     * récupération du logger qui est créé si nécessaire
     *
     * @return le logger
     */
    private static JsonLogger getLogger() {
        if (logger == null) {
            logger = new JsonLogger();
        }
        return logger;
    }

    /**
     * méthode pour logger
     *
     * @param host machine client
     * @param port port sur la machine client
     * @param proto protocole de transport utilisé
     * @param type type de la requête
     * @param login login utilisé
     * @param result résultat de l'opération
     */
    public static void log(String host, int port, String proto, String type, String login, String result) {

        // on recupere le logger
        JsonLogger logger = getLogger();

        //on transforme nos donnnée en format json
        JsonObject log = JsonLogger.reqToJson(host, port, proto, type, login, result);

        //finalement, on envoi au serveur pour que le tout soit logger
        JsonLogger.getLogger().socketSortie.println(log.toString());

    }

    public static void close(){
        try {
            JsonLogger.getLogger().sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
