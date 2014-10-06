package Portail;

import Jeu.PacDoge;
import iut.Game;

/**
 * Le portail li� au portail bleu
 */
public class PortailOrange extends Portail {

        public PortailOrange(Game g, String nom, int x, int y, PacDoge p) {
                super(g, nom, x, y, p);
				couleur = 'o';
	}

	/**
	 * D�place l'objet
	 * @param dt le temps �coul� en millisecondes depuis le pr�c�dent d�placement
	 */
	public void move(long dt) {
	}
}