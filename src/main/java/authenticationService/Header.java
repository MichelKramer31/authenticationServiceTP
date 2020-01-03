package authenticationService;

/**
 * Représente les informations associés à une requête
 * @author michelkramer
 */
public class Header {

    /**
     * L'hote à l'origine d'une requete
     */
    String host;

    /**
     * Le port sur lequel la requete à été envoyé
     */
    Integer port;

    /**
     * Le protocol sur lequel la requete a été envoyé
     */
    String protocol;


    /**
     * @param host L'hote à l'origine d'une requete
     * @param port Le port sur lequel la requete à été envoyé
     * @param protocol Le protocol sur lequel la requete a été envoyé
     */
    public Header(String host, Integer port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

    /**
     * @return L'hote qui a envoyé la requete
     */
    public String getHost() {
        return host;
    }


    /**
     * @return Le port de destination de la requete
     */
    public Integer getPort() {
        return port;
    }


    /**
     * @return Le protocol utilisé par la requête
     */
    public String getProtocol() {
        return protocol;
    }
}
