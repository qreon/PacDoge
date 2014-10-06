package Portail;

import Jeu.PacDoge;
import iut.Game;

/**
 * Le portail li� au portail orange
 */
public class PortailBleu extends Portail {
    
        public PortailBleu(Game g, String nom, int x, int y, PacDoge p) {
                super(g, nom, x, y, p);
				couleur = 'b';
	}

	/**
	 * D�place l'objet
	 * @param dt le temps �coul� en millisecondes depuis le pr�c�dent d�placement
	 */
	public void move(long dt) {
	}
}