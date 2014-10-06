package Doge;

import GrumpyCat.GrumpyMangeable;
import GrumpyCat.GrumpyTueur;
import Jeu.PacDoge;
import iut.Game;
import iut.Objet;

/**
 * Le Doge par d�faut.
 */
public class DogeNormal extends Doge {
	private boolean effected;
    
	public DogeNormal(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y, p);
		effected = false;
	}
        
	public void effect(Objet o) {
		if(o.isEnnemy())
		{
			if (!effected)
			{
				effected = true;
				System.out.println("Mort: "+o);
				p.perdu();
			}
		}
	}
}	