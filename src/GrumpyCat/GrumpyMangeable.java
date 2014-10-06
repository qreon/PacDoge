package GrumpyCat;

import Jeu.PacDoge;
import iut.Game;
import java.awt.Graphics;
import iut.Objet;

/**
 * Un Grumpy mangeable peut etre mang� par un super doge.
 * Un grumpy mangeable ne peut exister que lorsque le joueur est en mode "super-doge"
 */
public class GrumpyMangeable extends GrumpyCat {

	public GrumpyMangeable(Game g, String nom, int x, int y, PacDoge p, int num) {
		super(g, nom, x, y, p, num);
		etat = 'm';
	}

	/**
	 * Action : effet d'une collision entre l'objet et le param�tre
	 */
	public void effect(Objet o) {
		if(o.isFriend())
		{
			p.upScore(3500 + ((int)(Math.random()*3500))); //Permet d'obtenir des scores ridicules
			p.backGrumpy(num);
		}
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