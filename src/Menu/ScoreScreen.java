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
import java.util.ArrayList;

/**
 *
 * @author François
 */
public class ScoreScreen extends Objet {
	
	private PacDoge p;		//Référence vers le jeu
	private CurseurScore c;

	private int nOptions;	//Nombre d'options disponibles dans le menu
	private int vOffset;	//Décalage vertical du bloc d'options
	private int vSpacing;	//Espacement vertical des options entre elles
	private int hOffset;	//Décalage horizontal du bloc d'options
	private int hBlockOffset;//Décalage horizontal des chaînes du bloc
	private int vBlockOffset;//Décalage vertical des chaînes du bloc

	private int scoreHOffset;//Mêmes histoire de décalages avec le bloc de scores
	private int scoreVOffset;
	private int scoreVSpacing;
	
	public ScoreScreen(Game g, String s, int x, int y, PacDoge p)
	{
		super(g,s,x,y);
		this.p = p;
		nOptions = 2;
		vOffset = 590;
		vSpacing = 50;
		hOffset = 915;
		hBlockOffset = 70;
		vBlockOffset = 40;
		c = new CurseurScore(g, "doge", hOffset-hBlockOffset, vOffset-vBlockOffset+vSpacing, p, this);
		
		scoreHOffset = 270;
		scoreVOffset = 350;
		scoreVSpacing = 40;
		p.add(c);
		p.publicAddKeyInteractiveObject(c);
	}
	
	public void draw(Graphics g) throws Exception
	{
		ArrayList<Long> scores = p.getScoreRecorder().getScores();
		
		//Titre
		String title = "PAC - DOGE";
		g.setFont(new Font("Comic Sans MS",Font.BOLD,144));
		g.setColor(Color.CYAN);
		g.drawString(title, 200, 200);

		//Sous titre
		String subtitle = "Meilleurs scores";
		g.setFont(new Font("Comic Sans MS",Font.ITALIC,48));
		g.setColor(Color.ORANGE);
		g.drawString(subtitle, 230, 280);

		//Option 1
		String opt1 = "Réinitialiser";
		g.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		g.setColor(Color.CYAN);
		g.drawString(opt1, hOffset, vOffset+vSpacing*0);

		//Option 2
		String opt2 = "Retour";
		g.drawString(opt2, hOffset, vOffset+vSpacing*1);
		
		//Scores
		g.setColor(Color.MAGENTA);
		for(int i = 0; i < scores.size(); i++)
		{
			g.drawString(""+(i+1)+" : "+scores.get(i), scoreHOffset, scoreVOffset+scoreVSpacing*i);
		}
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
	
	public void scoreAction(int n)
	{
		System.out.println("Action with "+n);
		switch(n)
		{
			case 1:	p.getScoreRecorder().clearScores();
				break;
			case 2: removeObjects();
				p.invokeMenuFromScores();
				break;
		}
	}
	
	public void clearScores()
	{
		
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
