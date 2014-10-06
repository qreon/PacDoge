package Boulette;

import Jeu.PacDoge;
import iut.Game;
import iut.Objet;

/**
 * Boulette qui donne un pouvoir et des points.
 */
public class SuperBoulette extends Boulette {
	/**
	 * Temps d'invincibilit�
	 */
	private long tps;

	public SuperBoulette(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y, p);
	}    

	public void move(long dt) {
	}

	/**
	 * Action : effet d'une collision entre l'objet et le param�tre
	 */
	public void effect(Objet o) {
		if(o.isFriend())
		{
			if(!effected)
			{
				effected = true;
				System.out.println("superBoulette : "+ o);
				p.upScore(1500 + ((int)(Math.random()*1500))); //Permet d'obtenir des scores ridicules
				p.upgradePlayer();
				p.downgradeGrumpy();
				p.remove(this);
				p.rmBoulette();
			}
		}
	}
}