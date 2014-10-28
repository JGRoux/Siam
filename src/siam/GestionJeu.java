package siam;

/**
 * Cette classe gère toute la gestion interne du jeu, c'est-à-dire, tous les
 * calculs et les fonctions qui ne concernent pas l'affichage graphique
 *
 */
public class GestionJeu {

	private int numTour = 0;
	private Case pioche_E[], pioche_R[], plateau[][];
	private String player_E, player_R;
	public String typeGagnant = new String();

	/**
	 * Le constructeur de la classe GestionJeu
	 *
	 * @param plateau : le tableau de cases qui forme le jeu
	 * @param pioche_E : la pioche des elephants
	 * @param pioche_R: la pioche des rhinos
	 * @param player_E : le joueurs des elephants
	 * @param player_R : le joueur des rhinos
	 */
	public GestionJeu(Case plateau[][], Case pioche_E[], Case pioche_R[], String player_E, String player_R) {
		this.pioche_E = pioche_E;
		this.pioche_R = pioche_R;
		this.player_E = player_E;
		this.player_R = player_R;
		this.plateau = plateau;
	}

	/**
	 * La fonction générale du programme, elle permet de savoir si une pièce
	 * peut se déplacer/pousser une autre case et dans le cas échéant, se
	 * déplacer ou pousser
	 *
	 * @param xA : les coordonnées en x de la pièce sélectionnée
	 * @param yA : les coordonnées en y de la pièce selectionnée
	 * @param xP : les coordonnées en x de la case ou on veut se
	 * deplacer/pousser
	 * @param yP : les coordonnées en y de la case ou on veut se
	 * deplacer/pousser
	 * @param peutPousser : booléen pour savoir si on a déjà tourné cette pièce
	 * durant ce tour, si oui, on ne peut pas pousser durant le meme tour
	 * @return
	 */
	public boolean calcul_pousse(int xA, int yA, int xP, int yP, boolean peutPousser) {
		float force = 0;
		System.out.println(yA + " " + xA + " " + yP + " " + xP);
		// si on envoie une pièce depuis l'une des pioches
		if ((xA == -1) || (xA == 7)) {
			//si on envoie la pièce sur la colonne de gauche
			if (xP == 1) {
				//s'il n'y a pas de pièce, on déplace simplement 
				if (plateau[yP][xP].get_pion() == null) {
					deplacerPion(xA, yA, xP, yP);
				} else {
					//sinon on deplace la pièce sur la colonne "invisible" et on calcule la poussee
					deplacerPion(xA, yA, xP - 1, yP);
					if (!calcul_pousse(xP - 1, yP, xP, yP, true)) {

						deplacerPion(xP - 1, yP, xA, yA);
						return false;
					}

				}
				return true;
				//de meme pour la colonne de droite
			} else if (xP == 5) {
				if (plateau[yP][xP].get_pion() == null) {
					deplacerPion(xA, yA, xP, yP);

				} else {

					deplacerPion(xA, yA, xP + 1, yP);
					if (!calcul_pousse(xP + 1, yP, xP, yP, true)) {
						deplacerPion(xP + 1, yP, xA, yA);
						return false;
					}

				}
				return true;
				//de meme pour la ligne du bas
			} else if (yP == 5) {
				if (plateau[yP][xP].get_pion() == null) {
					deplacerPion(xA, yA, xP, yP);
				} else {
					deplacerPion(xA, yA, xP, yP + 1);
					if (!calcul_pousse(xP, yP + 1, xP, yP, true)) {
						deplacerPion(xP, yP + 1, xA, yA);
						return false;
					}

				}
				return true;
				//de même pour la ligne du haut
			} else if (yP == 1) {
				if (plateau[yP][xP].get_pion() == null) {
					deplacerPion(xA, yA, xP, yP);
					System.out.println("zU1");

				} else {
					deplacerPion(xA, yA, xP, yP - 1);
					if (!calcul_pousse(xP, yP - 1, xP, yP, true)) {
						deplacerPion(xP, yP - 1, xA, yA);
						return false;
					}

				}
				return true;
			}
			//si on veut bouger une pièce qui est déjà sur la plateau 
		} else {
			//si la 2eme case cliquée est accessible au pion situé sur la 1ere case
			if (((xP == xA + 1 || xP == xA - 1) && (yP == yA)) || ((yP == yA + 1 || yP == yA - 1) && (xP == xA))) {
				//si la 2eme case cliquée est vide, on deplace simplement le pion sur cette case
				if (this.plateau[yP][xP].get_pion() == null) {
					deplacerPion(xA, yA, xP, yP);
					return true;
				} //sinon on va calculer pour savoir si la poussee est possible
				else if (peutPousser == true) {
					//déplacement vers la droite
					if (xP > xA) {
						if (this.plateau[yA][xA].get_pion().get_orientation() == 4) {
							force = 1;
							int i, dernierPionIndice = xA;
							for (i = xP; i < 7; i++) {
								if (plateau[yP][i].get_pion() != null) {
									dernierPionIndice++;
									//calcul de la force, pour savoir si on peut pousser
									if (plateau[yP][i].get_pion().getType().equals("Montagne")) {
										force -= 0.9;
										//si la pièce est dans le meme sens
									} else if (plateau[yP][i].get_pion().get_orientation() == 4) {
										force += 1.0;
										//si la pièce est dans le sens opposé
									} else if (plateau[yP][i].get_pion().get_orientation() == 2) {
										force -= 1.0;
									}
									if (force <= 0) {
										return false;
									}
								} else {

									break;
								}

							}
							System.out.println("force = " + force);
							System.out.println("dernierPionIndice = " + dernierPionIndice);
							if (force > 0) {
								// Si un pion est au bord => go to pioche
								if (dernierPionIndice == 5) {
									if (plateau[yP][5].get_pion().getType().equals("Rhino")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_R[k].get_pion() == null) {
												pioche_R[k].set_pion(plateau[yP][5].get_pion());
												pioche_R[k].get_pion().set_orientation(1);
												break;
											}
										}
									} else if (plateau[yP][5].get_pion().getType().equals("Elephant")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_E[k].get_pion() == null) {
												pioche_E[k].set_pion(plateau[yP][5].get_pion());
												pioche_E[k].get_pion().set_orientation(1);
												break;
											}
										}
									} //si montagne 
									else if (plateau[yP][5].get_pion().getType().equals("Montagne")) {
										for (int j = 4; j >= xA; j--) {
											if (plateau[yP][j].get_pion().get_orientation() == 4) {
												typeGagnant = plateau[yP][j].get_pion().getType();
												break;
											}

										}

									}

									dernierPionIndice--;
								}
								// On décale tous les pions
								for (int j = dernierPionIndice; j >= xA; j--) {
									plateau[yP][j + 1].set_pion(plateau[yP][j].get_pion());
								}
								plateau[yA][xA].set_pion(null);
								return true;
							}
						}
					} // déplacement vers la gauche
					else if (xP < xA) {
						if (this.plateau[yA][xA].get_pion().get_orientation() == 2) {
							force = 1;
							int i, dernierPionIndice = xA;
							for (i = xP; i >= 0; i--) {
								if (plateau[yP][i].get_pion() != null) {
									dernierPionIndice--;
									if (plateau[yP][i].get_pion().getType().equals("Montagne")) {
										force -= 0.9;
									} else if (plateau[yP][i].get_pion().get_orientation() == 2) {
										force += 1.0;
									} else if (plateau[yP][i].get_pion().get_orientation() == 4) {
										force -= 1.0;
									}
									if (force <= 0) {
										return false;
									}
								} else {

									break;
								}

							}
							System.out.println("force = " + force);
							System.out.println("dernierPionIndice = " + dernierPionIndice);
							if (force > 0) {
								// Si un pion est au bord => go to pioche
								if (dernierPionIndice == 1) {
									if (plateau[yP][1].get_pion().getType().equals("Rhino")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_R[k].get_pion() == null) {
												pioche_R[k].set_pion(plateau[yP][1].get_pion());
												pioche_R[k].get_pion().set_orientation(1);
												break;
											}
										}
									} else if (plateau[yP][1].get_pion().getType().equals("Elephant")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_E[k].get_pion() == null) {
												pioche_E[k].set_pion(plateau[yP][1].get_pion());
												pioche_E[k].get_pion().set_orientation(1);
												break;
											}
										}
									} //si montagne 
									else if (plateau[yP][1].get_pion().getType().equals("Montagne")) {
										for (int j = 2; j <= xA; j++) {
											if (plateau[yP][j].get_pion().get_orientation() == 2) {
												typeGagnant = plateau[yP][j].get_pion().getType();
												break;
											}
										}
									}
									dernierPionIndice++;
								}
								// On décale tous les pions
								for (int j = dernierPionIndice; j <= xA; j++) {
									plateau[yP][j - 1].set_pion(plateau[yP][j].get_pion());
								}
								plateau[yA][xA].set_pion(null);
								return true;
							}
						}
					} // déplacement vers le bas
					else if (yP > yA) {
						if (this.plateau[yA][xA].get_pion().get_orientation() == 1) {
							force = 1;
							int i, dernierPionIndice = yA;
							for (i = yP; i < 7; i++) {
								if (plateau[i][xP].get_pion() != null) {
									dernierPionIndice++;
									if (plateau[i][xP].get_pion().getType().equals("Montagne")) {
										force -= 0.9;
									} else if (plateau[i][xP].get_pion().get_orientation() == 1) {
										force += 1.0;
									} else if (plateau[i][xP].get_pion().get_orientation() == 3) {
										force -= 1.0;
									}
									if (force <= 0) {
										return false;
									}
								} else {
									break;
								}

							}
							System.out.println("force = " + force);
							System.out.println("dernierPionIndice = " + dernierPionIndice);
							if (force > 0) {
								// Si un pion est au bord => go to pioche
								if (dernierPionIndice == 5) {
									if (plateau[5][xP].get_pion().getType().equals("Rhino")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_R[k].get_pion() == null) {
												pioche_R[k].set_pion(plateau[5][xP].get_pion());
												pioche_R[k].get_pion().set_orientation(1);
												break;
											}
										}
									} else if (plateau[5][xP].get_pion().getType().equals("Elephant")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_E[k].get_pion() == null) {
												pioche_E[k].set_pion(plateau[5][xP].get_pion());
												pioche_E[k].get_pion().set_orientation(1);
												break;
											}
										}
									} //si montagne 
									else if (plateau[5][xP].get_pion().getType().equals("Montagne")) {
										for (int j = 4; j >= yA; j--) {
											if (plateau[j][xP].get_pion().get_orientation() == 1) {
												typeGagnant = plateau[j][xP].get_pion().getType();
												break;
											}
										}
									}
									dernierPionIndice--;
								}
								// On décale tous les pions
								for (int j = dernierPionIndice; j >= yA; j--) {
									plateau[j + 1][xP].set_pion(plateau[j][xP].get_pion());
								}
								plateau[yA][xA].set_pion(null);
								return true;
							}
						}
					} // déplacement vers le haut
					else if (yP < yA) {
						if (this.plateau[yA][xA].get_pion().get_orientation() == 3) {
							force = 1;
							int i, dernierPionIndice = yA;
							for (i = yP; i >= 0; i--) {
								if (plateau[i][xP].get_pion() != null) {
									dernierPionIndice--;
									if (plateau[i][xP].get_pion().getType().equals("Montagne")) {
										force -= 0.9;
									} else if (plateau[i][xP].get_pion().get_orientation() == 3) {
										force += 1.0;
									} else if (plateau[i][xP].get_pion().get_orientation() == 1) {
										force -= 1.0;
									}
									if (force <= 0) {
										return false;
									}
								} else {

									break;
								}

							}
							System.out.println("force = " + force);
							System.out.println("dernierPionIndice = " + dernierPionIndice);
							if (force > 0) {
								// Si un pion est au bord => go to pioche
								if (dernierPionIndice == 1) {
									if (plateau[1][xP].get_pion().getType().equals("Rhino")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_R[k].get_pion() == null) {
												pioche_R[k].set_pion(plateau[1][xP].get_pion());
												pioche_R[k].get_pion().set_orientation(1);
												break;
											}
										}
									} else if (plateau[1][xP].get_pion().getType().equals("Elephant")) {
										for (int k = 0; k < 5; k++) {
											if (this.pioche_E[k].get_pion() == null) {
												pioche_E[k].set_pion(plateau[1][xP].get_pion());
												pioche_E[k].get_pion().set_orientation(1);
												break;
											}
										}
									} //si montagne 
									else if (plateau[1][xP].get_pion().getType().equals("Montagne")) {
										for (int j = 2; j <= yA; j++) {
											if (plateau[j][xP].get_pion().get_orientation() == 3) {
												typeGagnant = plateau[j][xP].get_pion().getType();
												break;
											}
										}
									}
									dernierPionIndice++;
								}
								// On décale tous les pions
								for (int j = dernierPionIndice; j <= yA; j++) {
									plateau[j - 1][xP].set_pion(plateau[j][xP].get_pion());
								}
								plateau[yA][xA].set_pion(null);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Cette fonction permet de déplacer un pion, de la pioche vers le plateau,
	 * d'une case du plateau vers une autre, ou du plateau vers l'une des
	 * pioches
	 *
	 * @param xA : coordonnée en x de la pièce selectionnée
	 * @param yA : coordonnée en y de la pièce selectionnée
	 * @param xP : coordonnée en x de la case suivante
	 * @param yP : coordonnée en y de la case suivante
	 */
	private void deplacerPion(int xA, int yA, int xP, int yP) {
		if (xA == -1) {
			this.plateau[yP][xP].set_pion(this.pioche_R[yA].get_pion());

			this.pioche_R[yA].set_pion(null);
		} else if (xP == -1) {
			this.pioche_R[yP].set_pion(this.plateau[yA][xA].get_pion());
			this.plateau[yA][xA].set_pion(null);
		} else if (xA == 7) {

			this.plateau[yP][xP].set_pion(this.pioche_E[yA].get_pion());

			this.pioche_E[yA].set_pion(null);

		} else if (xP == 7) {
			this.pioche_E[yP].set_pion(this.plateau[yA][xA].get_pion());
			this.plateau[yA][xA].set_pion(null);
		} else {
			this.plateau[yP][xP].set_pion(this.plateau[yA][xA].get_pion());
			this.plateau[yA][xA].set_pion(null);
		}
	}

	/**
	 * Pour savoir qui doit jouer le prochain tour
	 *
	 * @return : true si le joueur avec les elephants doit jouer, false sinon
	 */
	public boolean tourJoueurDebut() {
		if (this.numTour % 2 == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Nouveau tour
	 */
	public void newTour() {
		this.numTour++;
	}

	/**
	 * Connaitre le tour 
	 * @return le num du tour
	 */
	public int getNumTour() {
		return this.numTour;
	}

	/**
	 * Afficher quel joueur doit jouer
	 * @return
	 */
	public String aQuiLeTour() {
		if (this.tourJoueurDebut()) {
			return this.player_E + " avec les éléphants";
		} else {
			return this.player_R + " avec les rhinocéros";
		}
	}

}
