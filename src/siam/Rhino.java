package siam;

/**
 * Cette classe représente un pion de type Rhino
 * Elle hérite de Pion
 * 
 */
public class Rhino extends Pion {
	
	/**
     * Constructeur spécifiant l'addresse de l'image à utiliser
     *
     */
    public Rhino() {
        super("/images/rhino.jpg");
    }
    
    /**
     * Méthode permattant de récupérer le type (Rhino) de l'objet
     * Permet d'éviter la méthode instanceof
     * @return String: "Rhino"
     */
    @Override
    public String getType() {
        return "Rhino";
    }

}
