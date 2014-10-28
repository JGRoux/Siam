package siam;

/**
 * Cette classe représente un pion de type Montagne
 * Elle hérite de Pion
 * 
 */
public class Montagne extends Pion {

	/**
     * Constructeur spécifiant l'addresse de l'image à utiliser
     *
     */
    public Montagne() {
        super("/images/montagne.jpg");
    }
    
    /**
     * Méthode permattant de récupérer le type (Montagne) de l'objet
     * Permet d'éviter la méthode instanceof
     * @return String: "Montagne"
     */
    @Override
    public String getType() {
        return "Montagne";
    }

}
