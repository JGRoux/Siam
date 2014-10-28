package siam;

/**
 * Cette classe repr�sente un pion de type Rhino
 * Elle h�rite de Pion
 * 
 */
public class Rhino extends Pion {
	
	/**
     * Constructeur sp�cifiant l'addresse de l'image � utiliser
     *
     */
    public Rhino() {
        super("/images/rhino.jpg");
    }
    
    /**
     * M�thode permattant de r�cup�rer le type (Rhino) de l'objet
     * Permet d'�viter la m�thode instanceof
     * @return String: "Rhino"
     */
    @Override
    public String getType() {
        return "Rhino";
    }

}
