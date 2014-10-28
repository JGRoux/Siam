package siam;

/**
 * Cette classe représente un pion de type Elephant
 * Elle hérite de Pion
 * 
 */
public class Elephant extends Pion {

	/**
     * Constructeur spécifiant l'addresse de l'image à utiliser
     *
     */
    public Elephant() {
        super("/images/eleph.jpg");
    }
    
    /**
     * Méthode permattant de récupérer le type (Elephant) de l'objet
     * Permet d'éviter la méthode instanceof
     * @return String: "Elephant"
     */
    @Override
    public String getType() {
        return "Elephant";
    }

}
