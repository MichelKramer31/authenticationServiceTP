package requestHandler;

import authenticationService.Request;
import authenticationService.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Handler simple qui log le contenu de toutes les requetes que l'on lui adresse
 * @author michelkramer
 */
public class LogRequestHandler extends RequestHandler {


    /**
     * Le chemin vers le fichier dans lequel il faudra écrire
     */
    Path path;


    /**
     * @param path Le chemin vers le fichier dans lequel il faudra écrire
     */
    public LogRequestHandler(String path){
        this.path = Paths.get(path);
    }

    /**
     * Methode adaptateur pour amener la requete texte vers l'execution dans un handler approprié
     * Ici, même si il n'y qu'un seul handler on comprends bien que l'interet de séparer les deux méthodes
     * Celle-ci servant à spécifier le comportement d'une requete de log, l'autre peux se focaliser sur la logique d'écriture
     * On peut ainsi facilement écrire de nouvelles strategie de log en reduisant le risque de bug     *
     * @param rawRequest La requete brut
     * @return Un resultat, qui ne doit pas être renvoyé
     */
    public Result execute(Request rawRequest){

        Result result = new Result(rawRequest);
        //on interdit au serveur de répondre à cette requete
        result.getRequest().preventFromResponse();

        return this.process(result.getRequest());
    }

    /**
     *
     * @param request La requete contenant le contenu à logger
     * @return DONE si le resultat à été logger, ECHEC si il y'a eu une erreur
     */
    public Result process(Request request){

        // on va tenter d'écrire dans le fichier de log
        try {

            //on ouvre le fichier et on ecrit une ligne à la suite
            Files.write(this.getPath(), request.getRawRequest().concat(System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);

            //on formule une reponse et on retourne
            request.getResult().setResult("DONE");
            return request.getResult();

        } catch (IOException e) {
            // si on arrive pas à ecrir dans le fichier, on renvoit echec
            request.getResult().setResult("ECHEC");
            e.printStackTrace();
            return request.getResult();
        }
    }

    /**
     * @return Le chemin où se trouve le fichier dans lequel on doit logger
     */
    public Path getPath() {
        return this.path;
    }

}
