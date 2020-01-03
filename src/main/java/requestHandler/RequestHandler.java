package requestHandler;

import authenticationService.Request;
import authenticationService.Result;

/**
 * Request handler de base
 * Definit le fonctionnement d'un handler et ce qu'il doit implémenter
 * @author michelkramer
 */
public abstract class RequestHandler {

    RequestHandler next;

    RequestHandler(RequestHandler next){
        this.next = next;
    }

    public RequestHandler() {}

    public abstract Result execute(Request rawRequest);

    /**
     * Tansmet la requete au prochain handler dans la chaine
     * @param request La requete à faire passer au handler suivant
     * @return Le resultat
     */
    public Result processNext(Request request){

        if(this.hasNext()){
            //  Si il est encore possible de faire traiter la requete par un handler, on lui transmet.
            return next.execute(request);
        }

        //si il n'y a plus de handler et que rien n'a été retourné, on renvoie une reponse d'erreur
        else{
            // Si la requête n'a été intercepter par aucun handler, on retourne un resultat et on retourne une erreur
            request.getResult().setResult("ERROR");
            return request.getResult();
        }
    }

    /**
     * @param next Le handler a chainer
     */
    public void setNext(RequestHandler next) {
        this.next = next;
    }


    /**
     * @param next Le handler à ajouter à la fin de la fin de la chaine
     */
    public void appendNext(RequestHandler next){
        if(this.getNext()==null){
            this.setNext(next);
        }
        else{
            this.getNext().appendNext(next);
        }
    }


    /**
     * @return Le handler suivant
     */
    public RequestHandler getNext(){
        return this.next;
    }


    /**
     * Evalue la présence d'un handler à la suite
     * @return Vrai si le handler a un handler à sa suite, faux sinon
     */
    public boolean hasNext(){
        if(this.next != null){
            return true;
        }
        else{
            return false;
        }
    }

}
