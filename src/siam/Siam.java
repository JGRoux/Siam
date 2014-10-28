package siam;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * La classe Siam est une interface graphique basique qui permet aux joueurs 
 * d'écrire leur nom et commencer une nouvelle partie. Cette classe joue aussi le role de main.
 *
 */
public class Siam extends JFrame implements ActionListener {

    private JTextField joueur_E;
    private JTextField joueur_R;
    private JButton play;

    public Siam() {
        super("Jeu de Siam");

        this.joueur_R = new JTextField("Joueur1");
        this.joueur_E = new JTextField("Joueur2");
        this.play = new JButton("Play");
        this.play.addActionListener(this);
        this.play.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /*this.play.setOpaque(false);
         this.play.setContentAreaFilled(false);
         this.play.setBorderPainted(false);
         this.play.setFont(new java.awt.Font("Serif",1,25));
         this.play.setForeground(Color.WHITE);*/

        JLabel imagedefond = new JLabel(new ImageIcon(this.getClass().getResource("/images/OpeningWindow.jpg")));

        this.setContentPane(imagedefond);
        this.setLayout(null);
        this.joueur_E.setBounds(575, 410, 182, 35);
        this.joueur_R.setBounds(156, 410, 182, 35);
        this.play.setBounds(357, 280, 208, 71);
        this.add(this.joueur_E);
        this.add(this.joueur_R);
        this.add(this.play);

        //this.setMinimumSize(new Dimension(650,300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(913, 576);
        this.setLocationRelativeTo(null); // Center on the screen
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.play)) {
            this.dispose();
            new Plateau(this.joueur_E.getText(), this.joueur_R.getText());
        }
    }

    public static void main(String args[]) {
        new Siam();
    }
}
