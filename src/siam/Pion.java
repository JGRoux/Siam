package siam;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Cette classe abstraite repr�sente un pion
 * 
 */
public abstract class Pion {

    private Image image;
    private int orientation;
    
    /**
     * Ce constructeur sp�cifie l'image du pion
     * @param URl: addresse de l'image
     */
    public Pion(String URL) {
        this.image = new ImageIcon(this.getClass().getResource(URL)).getImage();
        this.orientation = 1;
    }
    
    /**
     * Cette m�thode r�cup�re l'image du pion
     * @return Image: image du pion
     */
    public Image get_image() {
        return this.image;
    }
    
    /**
     * Cette m�thode r�cup�re l'orientation du pion
     * @return int: orientation du pion
     */
    public int get_orientation() {
        return this.orientation;
    }
    
    /**
     * Cette m�thode indique l'orientation du pion
     * @param orientation: 0 bas, 1 gauche, 2 haut, 3 droite
     */
    public void set_orientation(int orientation) {
        this.orientation = orientation;
    }
    
    /**
     * Cette m�thode abstraite retourne le type du Pion
     * 
     */
    public abstract String getType();

}
