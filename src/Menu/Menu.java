/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Menu;

import Jeu.PacDoge;
import iut.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author François
 */
public class Menu extends Objet {
	
	private PacDoge p;		//Référence vers le jeu
	private Curseur c;

	private int nOptions;	//Nombre d'options disponibles dans le menu
	private int vOffset;	//Décalage vertical du bloc d'options
	private int vSpacing;	//Espacement vertical des options entre elles
	private int hOffset;	//Décalage horizontal du bloc d'options
	private int hBlockOffset;//Décalage horizontal des chaînes du bloc
	private int vBlockOffset;//Décalage vertical des chaînes du bloc
	
	public Menu(Game g, String s, int x, int y, PacDoge p)
	{
		super(g,s,x,y);
		this.p = p;
		nOptions = 3;
		vOffset = 492;
		vSpacing = 50;
		hOffset = 915;
		hBlockOffset = 70;
		vBlockOffset = 40;
		c = new Curseur(g, "doge", hOffset-hBlockOffset, vOffset-vBlockOffset, p, this);
		p.add(c);
		p.publicAddKeyInteractiveObject(c);
	}
	
	public void draw(Graphics g) throws Exception
	{
		String title = "PAC - DOGE";
		g.setFont(new Font("Comic Sans MS",Font.BOLD,144));
		g.setColor(Color.CYAN);
		g.drawString(title, 200, 200);

		//Option 1
		String opt1 = "Jouer";
		g.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		g.setColor(Color.CYAN);
		g.drawString(opt1, hOffset, vOffset+vSpacing*0);

		//Option 2
		String opt2 = "Meilleurs scores";
		g.drawString(opt2, hOffset, vOffset+vSpacing*1);

		//Option 3
		String opt4 = "Quitter";
		g.drawString(opt4, hOffset, vOffset+vSpacing*2);

	}
	
	public int getNOptions()
	{
		return nOptions;
	}
	
	public int getVOffset()
	{
		return vOffset;
	}
	
	public int getVSpacing()
	{
		return vSpacing;
	}
	
	public int getHOffset()
	{
		return hOffset;
	}
	
	public int getHBlockOffset()
	{
		return hBlockOffset;
	}
	
	public int getVBlockOffset()
	{
		return vBlockOffset;
	}
	
	public void menuAction(int n)
	{
		System.out.println("Action with "+n);
		switch(n)
		{
			case 1:	p.play();
				break;
			case 2: p.scores();
				break;
			case 3: p.quit();
				break;
		}
	}
	
	public void removeObjects()
	{
		p.remove(c);
		p.removeKeyListener(c);
	}
	
	public void move(long dt)
	{
		
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
		return true;
	}
}
