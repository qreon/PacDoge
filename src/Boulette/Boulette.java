package Boulette;

import Doge.Doge;
import Jeu.Carte;
import Jeu.PacDoge;
import Jeu.Score;
import iut.Game;
import iut.Objet;
import iut.ObjetTouchable;

/**
 * Les objets à ramasser.
 */
public abstract class Boulette extends ObjetTouchable {
	protected char type;
	protected PacDoge p;
	protected boolean effected;

	public Boulette(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y);
		this.p = p;
		effected = false;
	}

	/**
	 * @return true si l'objet est un "ami" du joueur
	 */
	public boolean isFriend() {
		return false;
	}

	/**
	 * @return false si l'objet est un "ennemi" du joueur
	 */
	public boolean isEnnemy() {
		return false;
	}
}