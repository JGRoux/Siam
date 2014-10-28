package siam;

/**
 * Cette classe repr�sente un pion de type Elephant
 * Elle h�rite de Pion
 * 
 */
public class Elephant extends Pion {

	/**
     * Constructeur sp�cifiant l'addresse de l'image � utiliser
     *
     */
    public Elephant() {
        super("/images/eleph.jpg");
    }
    
    /**
     * M�thode permattant de r�cup�rer le type (Elephant) de l'objet
     * Permet d'�viter la m�thode instanceof
     * @return String: "Elephant"
     */
    @Override
    public String getType() {
        return "Elephant";
    }

}
