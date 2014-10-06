package GrumpyCat;

import Jeu.PacDoge;
import iut.Game;
import java.awt.Graphics;
import iut.Objet;

/**
 * Le grumpy par d�faut. Il tue le joueur instantan�ment lors d'une collision.
 */
public class GrumpyTueur extends GrumpyCat {

        public GrumpyTueur(Game g, String nom, int x, int y, PacDoge p, int num) {
            super(g, nom, x, y, p, num);
			etat = 't';
	}

	/**
	 * Action : effet d'une collision entre l'objet et le param�tre
	 */
	public void effect(Objet o) {
		
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
		return true;
	}
}