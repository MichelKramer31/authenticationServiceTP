package requestHandler;

import authenticationService.*;

/**
 *  Défini les spécificités d'un Handler spécifique pour les requêtes d'identification (CHK, ADD, MOD ...)
 *  @author michelkramer
 */
public abstract class AuthenticationRequestHandler extends RequestHandler{


    /**
     *  Le gestionaire d'identification tel que telechargé sur Chamilo.
     */
    private ListeAuth la;

    /**
     * Ce constructeur est géneralement utilisé lorsque le handler est seul dans la chaine
     * @param la le gestionnaire d'identification avec lequel construire le handler
     */
    public AuthenticationRequestHandler(ListeAuth la){
        super();
        this.la = la;
    }

    /**
     * Ce constructeur est géneralement utitiliser pour chainer à la construction
     * @param la la le gestionnaire d'identification avec lequel construire le handler
     * @param rq Le handler à chainé
     */
    public AuthenticationRequestHandler(ListeAuth la, RequestHandler rq){
        super();
        this.la = la;
        this.setNext(rq);
    }

    /**
     * Constructeur pour le chaine des handlers
     * @param rq Le handler à chainer
     */
    public AuthenticationRequestHandler(AuthenticationRequestHandler rq) {
        super();
        this.setNext(rq);
    }

    /**
     * Ce constructeur est géneralement utilisé lorsque le handler est le dernier dans la chaine
     */
    public AuthenticationRequestHandler(){
        super();
    };


    /**
     * Si le handler que l'on veut chainer hértie également de AuthenticationRequestHandler et qu'il n'a pas de ListAuth, on va lui donner celui du handler actuel et repasser la main à la classe parente
     * @param next le handler héritant de cette classe ci à chainer
     */
    public void setNext(AuthenticationRequestHandler next) {

        if(!next.hasLa() && this.hasLa()){
            next.setLa(this.la);
        }
        super.setNext(next);
    }

    /**
     * Tout comme la méthode setNext(), on va tenter de faire transferer le gestionnaire d'identification au element suivant dans la chaine
     * @param next le handler héritant de cette classe ci à chainer
     */
    public void appendNext(AuthenticationRequestHandler next) {
        //Si le handler suivant n'a pas de gestionnaire et que le actuel en a un, alors on lui donne le la du handler actuel
        if(!next.hasLa() && this.hasLa()){
            next.setLa(this.la);
        }
        super.appendNext(next);
    }

    /**
     * Methode abstraite utiliser pour executer une AuthenticationRequest valide par des handler hériant de cette classe ci.
     * La méthode est abstraite car même si on capable de préparer une requete classique en requete d'indentification, seul le handler final impletemente une logique
     *  Il n'y pas de logique par default pour traiter une requete d'identification
     * @param request La requête à traiter
     * @return Le resultat de la requete
     */
    public abstract Result process(AuthenticationRequest request);


    /**
     * Methode adaptateur utilisé pour l'execution d'une requete dans le cas ou les handlers de cette famille sont sollicités avec une requete
     * Ici on va adapteur notre object requete en un object requete d'identification
     * Si la requete de base est correctement formé et qu'on arrive à la transformer, on peut la faire executer
     * Sinon on tente de la faire executer par le handler suivant0
     * @param rawRequest Une requete classique tel que transmise par le serveur
     * @return Le resultat de la requete
     */
    public Result execute(Request rawRequest){

        //creation de l'object
        AuthenticationRequest request = null;
        // On essai de initialiser
        try {
            request = new AuthenticationRequest(rawRequest);
        } catch (BadRequestException e) {

            // Si la requete est mal formulé, on ne pourra demander son execution, on la redirige vers le handler suivant
            System.out.println("Il semblerait que la requete soit mal formulé pour ce handler. Si un handler est chainé, elle sera transféré");
            return super.processNext(e.getBadAuthenticationRequest());

        }
        //si on arrive à créer notre object requete valide, elle peut aller se faire traiter par les handler de la famulle AuthenticationHandler
        return this.process(request);

    }


    /**
     * @return L'object ListLa utilisé par le handler
     */
    public ListeAuth getLa() {
        return la;
    }


    /**
     * Test l'existance d'un gestionnaire d'identification dans le handler
     * @return Vrai si la handler a un object ListAuth, faux si non
     */
    protected boolean hasLa(){
        if(this.la != null){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * @param la L'object listAuth a associer au handler
     */
    protected void setLa(ListeAuth la){
        this.la = la;
    }


    /**
     * Methode de log à partir d'une requete d'identification
     * Repose sur la classe JsonLogger
     * @param request Une requête d'identification qui doit être logger
     */
    public void log(AuthenticationRequest request){

        JsonLogger.log(request.getHeader().getHost(), request.getHeader().getPort(), request.getHeader().getProtocol(), request.getType(), request.getPseudo(), request.getResult().getResult());

    }
}
