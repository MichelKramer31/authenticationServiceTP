/**
 * @author michelkramer
 **/
package authenticationService;

/**
 * Exeption levée lorsque la transformation q'une requete classique en une requete specifique pour une famille de handler echoue
 * @author michelkramer
 */
public class BadRequestException extends Exception {


    /**
     * La requete invalide
     */
    Request badAuthenticationRequest;

    /**
     * @param message Un message d'erreur
     * @param badAuthenticationRequest la requete invalide
     */
    BadRequestException(String message, Request badAuthenticationRequest){
        super(message);
        this.badAuthenticationRequest = badAuthenticationRequest;
    }


    /**
     * @return Permet de recupérer la requete invalide pour en faire autre chose
     */
    public Request getBadAuthenticationRequest() {
        return badAuthenticationRequest;
    }
}
