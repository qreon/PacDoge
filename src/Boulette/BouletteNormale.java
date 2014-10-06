package Boulette;

import Jeu.PacDoge;
import iut.Game;
import iut.Objet;

/**
 * Donne des points
 */
public class BouletteNormale extends Boulette {

    
	public BouletteNormale(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y, p);
	}    

	public void move(long dt) {
	}

	/**
	 * Action : effet d'une collision entre l'objet et le paramètre
	 */
	public void effect(Objet o) {
		if(o.isFriend())
		{
			if (!effected)			//Lors de la collision avec le doge, la collision est étrangement déclenché deux fois...
			{						//On limite les dégâts avec ça
				effected = true;
				System.out.println("bouletteNormale : "+ o);
				p.upScore(350 + ((int)(Math.random()*350))); //Permet d'obtenir des scores ridicules
				p.remove(this);
				p.rmBoulette();
			}
		}
	}
}