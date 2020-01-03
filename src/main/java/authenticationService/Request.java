package authenticationService;

/**
 * Class model d'une requete de base, tel que n'importe quel handler doit pouvoir la traiter
 * @author michelkramer
 */
public class Request {


    /**
     * l'object representant la reponse formuler par le serveur
     */
    private Result result;

    /**
     *  La requete brut, telle que transmise par le client
     */
    private String rawRequest;

    /**
     * Par default, toutes les requetes donne lieux à une réponse de la part du serveur
     */
    private boolean response = true;

    /**
     * Les informations sur la requetes
     */
    Header header;


    /**
     * Constructeur par default, simplement à partir de la requete brut
     * @param header L'entete de la requete
     * @param rawRequest La requete brut
     */
    public Request(Header header, String rawRequest){
        this.rawRequest = rawRequest;
        this.result = new Result(this);
        this.header = header;
    }

    /**
     * @return La requête brut
     */
    public String getRawRequest() {
        return rawRequest;
    }


    /**
     * @return Le result de la requête, une fois traité par les handlers associé au serveur
     */
    public Result getResult(){
        return this.result;
    }


    /**
     * @return Vrai si le client qui envoi la requete souhaite une reponse, false si non.
     */
    public boolean requiereResponse(){
        return this.response;
    }

    /**
     * Indique que la requete n'a pas besoin de reponse
     * Generalement manipuler par un handler pour empecher le serveur de repondre
     */
    public void preventFromResponse(){
        this.response = false;
    }


    /**
     * @return Le header de la requete
     */
    public Header getHeader() {
        return header;
    }
}
