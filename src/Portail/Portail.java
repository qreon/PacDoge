package Portail;

import Jeu.PacDoge;
import iut.Game;
import iut.Objet;
import iut.ObjetTouchable;

/**
 * Permet de se t�l�porter(Classe abstraite)
 */
public abstract class Portail extends ObjetTouchable {
	protected PacDoge p;
	protected char couleur;
	
	public Portail(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y);
		this.p = p;
	}

	public void effect(Objet o)
	{
		if(o.isFriend())
		{
			if(!p.playerHasJustTPed())
			{
				p.setPlayerTPState(true);
				System.out.println(couleur+"Portal: "+o);
				tpDoge();
			}
		}
	}
	
	public void tpDoge()
	{
		System.out.println("Teleporting to not "+couleur+" portal...");
		if (couleur == 'o')
		{
			p.tpToBlue();
		}
		else
		{
			p.tpToOrange();
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
		return false;
	}
}