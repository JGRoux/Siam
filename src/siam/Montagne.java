package siam;

/**
 * Cette classe repr�sente un pion de type Montagne
 * Elle h�rite de Pion
 * 
 */
public class Montagne extends Pion {

	/**
     * Constructeur sp�cifiant l'addresse de l'image � utiliser
     *
     */
    public Montagne() {
        super("/images/montagne.jpg");
    }
    
    /**
     * M�thode permattant de r�cup�rer le type (Montagne) de l'objet
     * Permet d'�viter la m�thode instanceof
     * @return String: "Montagne"
     */
    @Override
    public String getType() {
        return "Montagne";
    }

}
