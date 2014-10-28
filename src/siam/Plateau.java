package siam;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
/**
 * 
 * Cette GUI permet au joueur de jouer au jeu de Siam.
 * Elle permet la liaison entre utilisateurs et gestion des donnÈes.
 *
 */
public class Plateau extends JFrame implements ActionListener, KeyListener {

    private Case pioche_E[], pioche_R[], plateau[][];
    private GestionJeu jeu;
    private Case tmpcase;
    private JPanel plateaucentral, JRhino, JElephant;
    private JLabel tourDeJeu;
    private int xA, yA;
    private String player_E, player_R;
    private boolean peutPousser;
    /**
     * 
     * @param player_E String qui reprÈsente le nom du joueur possÈdant les ÈlÈphants
     * @param player_R String qui reprÈsente le nom du joueur possÈdant les rhinocÈros
     */
    public Plateau(String player_E, String player_R) {

        super("Jeu de Siam");
        this.player_E = player_E;
        this.player_R = player_R;
        this.ini_plateau(player_E, player_R);
        this.jeu = new GestionJeu(this.plateau, this.pioche_E, this.pioche_R, player_E, player_R);
        this.setTextTour();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Center on the screen
        this.setVisible(true);
    }
    /**
     * Cette fonction initialise les diffÈrents composants swing
     * @param player_E String qui reprÈsente le nom du joueur possÈdant les ÈlÈphants
     * @param player_R String qui reprÈsente le nom du joueur possÈdant les rhinocÈros
     * 
     */
    private void ini_plateau(String player_E, String player_R) {
        //Cr√©ation de l'interface graphique
        this.plateaucentral = new JPanel();
        this.JRhino = new JPanel();
        this.JElephant = new JPanel();
        this.tourDeJeu = new JLabel();
        GridBagConstraints gbc = new GridBagConstraints();//permet la mise en forme des Panel

        //Cr√©ation des Panels gauche et droite:
        //Si le texte est trop grand sup√©rieur √† 10 caract√©res
        //On remplace la fin par ...
        JLabel nomJElephant, nomJRhino;
        if (player_R.length() > 17) {
            nomJRhino = new JLabel(player_R.substring(0, 14) + "...");
        } else {
            nomJRhino = new JLabel(player_R);
        }

        if (player_E.length() > 10) {
            nomJElephant = new JLabel(player_E.substring(0, 7) + "...");
        } else {
            nomJElephant = new JLabel(player_E);
        }

        // Cr√©ation Panel rhino et elephant
        this.pioche_E = new Case[5];
        this.pioche_R = new Case[5];
        for (int i = 0; i < 5; i++) {
            this.pioche_E[i] = new Case(new Elephant());
            this.pioche_E[i].addActionListener(this);
            this.pioche_E[i].addKeyListener(this);
            this.pioche_R[i] = new Case(new Rhino());
            this.pioche_R[i].addActionListener(this);
            this.pioche_R[i].addKeyListener(this);
        }

        JPanel pionRhino = new JPanel();
        pionRhino.setLayout(new GridLayout(5, 1));
        JPanel pionEleph = new JPanel();
        pionEleph.setLayout(new GridLayout(5, 1));

        for (int i = 0; i < 5; i++) {
            pionRhino.add(this.pioche_R[i]);
            pionEleph.add(this.pioche_E[i]);
        }

        this.JRhino.setLayout(new GridBagLayout());
        this.JElephant.setLayout(new GridBagLayout());

        //Label Rhino et Elephant
        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 10, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;//C'est le dernier composant 
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        this.JRhino.add(nomJRhino, gbc);
        gbc.insets = new Insets(10, 0, 10, 0);
        this.JElephant.add(nomJElephant, gbc);
        //Les pions
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;//Aucun redimensionnemnt possible
        gbc.insets = new Insets(0, 0, 0, 10);

        this.JElephant.add(pionEleph, gbc);
        //On ne modifie qu'un seul param pour les rhino
        gbc.insets = new Insets(0, 10, 0, 0);
        this.JRhino.add(pionRhino, gbc);

        //Cr√©ation du plateau central
        this.plateaucentral.setLayout(new GridBagLayout());

        //Je place le composant sur la case 0
        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(15, 10, 10, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;//avant dernier composant
        gbc.gridheight = 1;

        this.plateaucentral.add(this.tourDeJeu, gbc);

        this.plateau = new Case[7][];
        for (int i = 0; i < 7; i++) {
            this.plateau[i] = new Case[7];
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 0;
            gbc.weighty = 0;

            gbc.insets = new Insets(0, 0, 0, 0);
            for (int j = 0; j < 7; j++) {
                gbc.gridx = j;
                this.plateau[i][j] = new Case(null);
                if (i > 0 && i < 6 && j>0 && j<6) {
                    this.plateau[i][j].addActionListener(this);
                    this.plateau[i][j].addKeyListener(this);
                    this.plateaucentral.add(this.plateau[i][j], gbc);
                }

            }
        }
        this.addKeyListener(this);

        for (int i = 2; i < 5; i++) {
            this.plateau[3][i].set_pion(new Montagne());
        }

        //Je PLace le plateau de jeu
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;//dernier composant
        gbc.gridheight = GridBagConstraints.REMAINDER;//dernier composant de la colonne
        //Prend tous l'espace
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;//Aucun redimensionnemnt possible
        gbc.insets = new Insets(0, 10, 0, 10);

        //Ajout des √©lements sur l'interface graphique
        this.getContentPane().add(JRhino, BorderLayout.WEST);
        this.getContentPane().add(this.plateaucentral, BorderLayout.CENTER);
        this.getContentPane().add(JElephant, BorderLayout.EAST);
    }


    /**
     * Cette mÈthode met ‡ jour le tour et affiche la phrase:"Tour numÈro ?, c'est au tour de X"
     */
    public void setTextTour() {
        this.jeu.newTour();
        this.tourDeJeu.setText("Tour numÈro "
                + this.jeu.getNumTour() + ", c'est au tour de " + this.jeu.aQuiLeTour());
    }
    /**
     * DÈtecte quelle case dans la pioche ou le plateau a ÈtÈ sÈlectionÈ.
     * Le relais est ensuite passÈ ‡ la classe GestionJeu.
     */
    public void actionPerformed(ActionEvent e) {

        // D√©placement du pion ou finir tour apres orientation 
        if (this.tmpcase != null) {
            //finir tour apres orientation
            if (((Case) e.getSource()) == this.tmpcase) {
                this.tmpcase.setSelected(false);
                this.tmpcase = null;
                this.setTextTour();
            } else {

                for (int i = 0; i < 7; i++) {
                    // Envoi sur plateau 
                    for (int j = 0; j < 7; j++) {
                        if (this.plateau[i][j] == ((Case) e.getSource())) {
                            if (this.jeu.calcul_pousse(xA, yA, j, i, peutPousser)) {
                                this.tmpcase.setSelected(false);
                                this.repaint();
                                this.tmpcase = null;
                                if (this.jeu.typeGagnant.equals("Elephant")) {
                                    JOptionPane.showMessageDialog(this, player_E + " a gagn√©",
                                            "Partie termin√©e", JOptionPane.INFORMATION_MESSAGE);
                                    this.dispose();
                                    new Siam();
                                }
                                if (this.jeu.typeGagnant.equals("Rhino")) {
                                    JOptionPane.showMessageDialog(this, player_R + " a gagn√©",
                                            "Partie termin√©e", JOptionPane.INFORMATION_MESSAGE);
                                    this.dispose();
                                    new Siam();
                                }
                                this.setTextTour();
                            }
                        }

                        // Envoi dans les pioches
                        for (int k = 0; k < 5; k++) {
                            if (this.pioche_E[k] == ((Case) e.getSource()) && (this.pioche_E[k].get_pion() == null) && (this.jeu.tourJoueurDebut())) {
                                if ((this.tmpcase == plateau[i][j]) && (i == 1 || i == 5 || j == 1 || j == 5)) {
                                    this.pioche_E[k].set_pion(this.tmpcase.get_pion());
                                    this.pioche_E[k].get_pion().set_orientation(1);
                                    this.tmpcase.set_pion(null);
                                    this.tmpcase.setSelected(false);
                                    this.tmpcase = null;
                                    this.setTextTour();
                                    this.repaint();
                                }

                            } else if (this.pioche_R[k] == ((Case) e.getSource()) && (this.pioche_R[k].get_pion() == null) && (!this.jeu.tourJoueurDebut())) {
                                if ((this.tmpcase == plateau[i][j]) && (i == 1 || i == 5 || j == 1 || j == 5)) {
                                    this.pioche_R[k].set_pion(this.tmpcase.get_pion());
                                    this.pioche_R[k].get_pion().set_orientation(1);
                                    this.tmpcase.set_pion(null);
                                    this.tmpcase.setSelected(false);
                                    this.tmpcase = null;
                                    this.setTextTour();
                                    this.repaint();
                                }

                            }
                        }
                    }

                }
            }
        } // S√©lection du pion
        else {
            if (((Case) e.getSource()).get_pion() != null) {
                System.out.println("" + ((Case) e.getSource()).get_pion().getType());
                if (((Case) e.getSource()).get_pion().getClass() != Montagne.class) {

                    // Si le player Rhino joue un Rhino ou le player Eleph joue un eleph
                    if ((!this.jeu.tourJoueurDebut() && ((Case) e.getSource()).get_pion().getClass() == Rhino.class)
                            || (this.jeu.tourJoueurDebut() && ((Case) e.getSource()).get_pion().getClass() == Elephant.class)) {

                        this.tmpcase = (Case) e.getSource();
                        for (int i = 0; i < 5; i++) {
                            if (this.pioche_R[i] == this.tmpcase) {
                                this.xA = -1;
                                this.yA = i;
                            } else if (this.pioche_E[i] == this.tmpcase) {
                                this.xA = 7;
                                this.yA = i;
                            }
                            for (int j = 1; j < 6; j++) {
                                if (this.plateau[i+1][j] == this.tmpcase) {
                                    this.xA = j;
                                    this.yA = i+1;
                                }
                            }
                        }
                        this.tmpcase.setSelected(true);
                        this.peutPousser = true;
                    }
                }
            }
        }

    }

    @Override
    /**
     * DÈtecte si une touche directionnelle est utilisÈe. Si oui et si un pion a ÈtÈ sÈlectionnÈ
     * alors on rÈoriente ce dernier en fonction de la touche directionelle choisie.
     */
    public void keyPressed(KeyEvent e) {
        if (this.tmpcase != null) {
            //this.tmpcase.get_pion().set_orientation(e.getKeyCode() % 4 + 1);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                this.tmpcase.get_pion().set_orientation(4);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                this.tmpcase.get_pion().set_orientation(2);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                this.tmpcase.get_pion().set_orientation(3);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                this.tmpcase.get_pion().set_orientation(1);
            }
            this.peutPousser = false;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
