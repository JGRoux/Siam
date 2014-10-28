package siam;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;



import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Cette classe permet de gérer nos cases du jeu.
 * Elle est héritée de JButton.
 * 
 */
@SuppressWarnings("serial")
public class Case extends JButton implements ChangeListener {

	private Pion pion;
	private boolean selected;
	private Border border, yellow, green;

	/**
	 * Le constructeur de la classe Case
	 *
	 * @param pion: instance de la classe Pion
	 */
	public Case(Pion pion) {
		set_pion(pion);
		this.setPreferredSize(new Dimension(98, 98));
		this.setMinimumSize(new Dimension(98, 98));
		this.setBackground(new Color(234, 200, 120));
		this.getModel().addChangeListener(this);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setMultiClickThreshhold(500); // Avoid double click

		Border grey=new LineBorder(Color.GRAY, 1);
		Border tr = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(234, 200, 120));
		this.border=new CompoundBorder(grey,tr);
		this.yellow = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.yellow);
		this.green = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.green);

	}

	/**
	 * Cette méthode permet de récupérer l'instance de pion
	 * @ return pion: pion
	 * 
	 */
	public Pion get_pion() {
		return pion;
	}

	/**
	 * Cette méthode permet d'insérer une instance de pion
	 * @param pion: l'instance de Pion à insérer
	 * 
	 */
	public void set_pion(Pion pion) {
		this.pion = pion;
	}

	/**
	 * Cette méthode d'indiquer si cette case est sélectionné
	 *@param selected: true ou false
	 * 
	 */
	public void setSelected(boolean selected){
		this.selected=selected;
		if(this.selected)
			this.setBorder(new CompoundBorder(border, green));
		else
			this.setBorder(new LineBorder(Color.GRAY, 1));
	}

	/**
	 * Cette méthode permet de modifier la manière dont est peint le bouton
	 *	@param g: Graphics
	 * 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.pion != null) {
			((Graphics2D) g).rotate((this.pion.get_orientation() - 1) * Math.PI / 2, getWidth() / 2, getHeight() / 2);
			g.drawImage(this.pion.get_image(), 0, 0, getWidth(), getHeight(), this);
		}
	}

	/**
	 * Cette méthode permet de modifier la manière de changement d'état du bouton
	 * @param e: ChangeEvent
	 * 
	 */
	@Override
	public void stateChanged(ChangeEvent e) {	
		ButtonModel model = (ButtonModel) e.getSource();
		if (model.isRollover()) {
			if(selected)
				this.setBorder(new CompoundBorder(this.getBorder(), yellow));
			else
				this.setBorder(new CompoundBorder(border, yellow));
		}
		else {
			if(selected)
				this.setBorder(new CompoundBorder(border, green));
			else
				this.setBorder(border);
		}
	}	
}
