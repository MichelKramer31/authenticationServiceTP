package authenticationService;

/**
 * Classe hérité de Request donnant les spécificité d'une requete d'identification
 * @author michelkramer
 */
public class AuthenticationRequest extends Request{

    /**
     * Le type de requete (ADD, MOD, DEL ...)
     */
    private String type;

    /**
     * Le pseudo utilisé dan le requete
     */
    private String pseudo;

    /**
     * Le mot de passse utilisé dans la requete
     */
    private String password;


    /**
     * @param request Une requete simple à partir de laquelle on va créer notre requete d'identification
     * @throws BadRequestException Si la requete est mal forméé, c'est à dire elle ne contient pas au moins trois mots
     */
    public AuthenticationRequest(Request request) throws BadRequestException {
        super(request.getHeader(), request.getRawRequest());
        //on decomposer la chaine
        String[] spString = request.getRawRequest().split(" ");

        //si le nombre de mot est inférieur à trois
        if(spString.length < 3){
            throw new BadRequestException("Le format de la requête n'est pas valide",this);
        }

        this.type = spString[0];
        this.pseudo =  spString[1];
        this.password =  spString[2];

        this.type = type ;
        this.pseudo = pseudo;
        this.password = password;
    }

    /**
     * @return Le type de la requete
     */
    public String getType() {
        return type;
    }

    /**
     * @return Le pseudo de la requete
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return Le mot de passe de la requete
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return Le resultat de la requete
     */
    public Result getResult(){
        return super.getResult();
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
