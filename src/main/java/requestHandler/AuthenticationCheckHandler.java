package requestHandler;

import authenticationService.*;

/**
 *  Handler pour l'action de vérification du couple pseudo/motdepass d'un utilisateur
 *  @author michelkramer
 */
public class AuthenticationCheckHandler extends AuthenticationRequestHandler {

    /**
     * Ce constructeur est géneralement utilisé lorsque le handler est seul dans la chaine
     * @param la le gestionnaire d'identification avec lequel construire le handler
     */
    public AuthenticationCheckHandler(ListeAuth la) {
        super(la);
    }

    /**
     * Ce constructeur est géneralement utitiliser pour chainer à la construction
     * @param la la le gestionnaire d'identification avec lequel construire le handler
     * @param rq Le handler à chainé
     */
    public AuthenticationCheckHandler(ListeAuth la, RequestHandler rq) {
        super(la, rq);
    }

    /**
     * Constructeur pour le chaine des handlers
     * @param rq Le handler à chainer
     */
    public AuthenticationCheckHandler(AuthenticationRequestHandler rq) {
        super(rq);
    }

    /**
     * Ce constructeur est géneralement utilisé lorsque le handler est le dernier dans la chaine
     */
    public AuthenticationCheckHandler() {
    }

    /**
     * La méthode process implemente la logique du handler
     * Elle travail sur une requete adaptée ou non par la methode execute
     * @param authenticationRequest La requete d'identification à tenté de traiter
     * @return Un resultat traité par le handler, ou un resultat traité par le handler suivant
     */
    public Result process(AuthenticationRequest authenticationRequest){

        if(authenticationRequest.getType().equals("CHK")){

            if(super.getLa().tester(authenticationRequest.getPseudo(), authenticationRequest.getPassword())){
                authenticationRequest.getResult().setResult("GOOD");

            } else {
                authenticationRequest.getResult().setResult("BAD");
            }
            this.log(authenticationRequest);
            return authenticationRequest.getResult();
        }
        else{
            //si ce n'est pas une requete de type Check
            return this.processNext(authenticationRequest);
        }
    }
}
