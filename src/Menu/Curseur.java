/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Menu;

import Jeu.Coordonnees;
import Jeu.PacDoge;
import iut.Game;
import iut.Objet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author François
 */
public class Curseur extends Objet implements KeyListener {
	
	private int nOptions;	//Nombre d'options disponibles dans le menu
	private int numOption;	//Numéro de l'option courante
	
	private PacDoge p;		//Référence vers le jeu
	private Menu m;			//Référence vers la carte
	
	private int vOffset;	//Décalage vertical du bloc d'options
	private int vSpacing;	//Espacement vertical des options entre elles
	private int hOffset;	//Décalage horizontal du bloc d'options
	private int hBlockOffset;
	private int vBlockOffset;
	
	public Curseur(Game g, String s, int x, int y, PacDoge p, Menu m)
	{
		super(g,s,x,y);
		this.p = p;
		this.m = m;
		nOptions = m.getNOptions();
		numOption = 1;
		vSpacing = m.getVSpacing();
		hBlockOffset = m.getHBlockOffset();
		hOffset = m.getHOffset() - hBlockOffset;
		vBlockOffset = m.getVBlockOffset();
		vOffset = m.getVOffset() - vBlockOffset;
	}
	
	public void move(long dt)
	{

	}
	
	public void keyPressed(KeyEvent e) {	//Capte les évènements claviers et produit des mouvements en conséquence
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				if (numOption > 1)
				{
					numOption--;
					moveUp();
				}
				break;
			case KeyEvent.VK_DOWN:
				if (numOption < nOptions)
				{
					numOption++;
					moveDown();
				}
				break;
			case KeyEvent.VK_SPACE:
				m.menuAction(numOption);
			case KeyEvent.VK_ENTER:
				m.menuAction(numOption);
		}
	}
	
	public void moveUp()
	{
		moveY(-vSpacing);
	}
	
	public void moveDown()
	{
		moveY(vSpacing);
	}
	
	@Override
	public void keyTyped(KeyEvent ke) {
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}
	
	public boolean collision(Objet o)
	{
		return false;
	}
	
	public void effect(Objet o)
	{
		
	}
	
	public boolean isFriend()
	{
		return false;
	}
	
	public boolean isEnnemy()
	{
		return false;
	}
}
