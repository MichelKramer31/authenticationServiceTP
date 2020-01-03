package authenticationService;

/**
 * Definit le model d'un resultat à une requete
 */
public class Result {


    /**
     * La requete d'origine, toujours en reflexion avec son resultat
     * @author michelkramer
     */
    private Request request;

    /**
     * La chaine de resultat
     */
    private String result;


    /**
     * Constructeur de base, on par du principe qu'une requete est toujours à l'origine d'un resultat
     * @param request La requete dont on construit le resultat
     */
    public Result(Request request) {
        this.request = request;
    }

    /**
     * @param result La chaine du resultat de la requete
     */
    public void setResult(String result){
        this.result = result;
    }


    /**
     * @return La chaine du resultat de la requete
     */
    public String getResult(){
        return this.result;
    }


    /**
     * @return La requete à l'origine du resultat
     */
    public Request getRequest() {
        return request;
    }
}
