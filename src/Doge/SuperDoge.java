package Doge;

import Jeu.PacDoge;
import iut.Game;
import iut.Objet;

/**
 * Invincible. Transforme les grumpyTueur en GrumpyMangeable pour une certaine dur�e. Un super doge ne peut exister qu'apr�s avoir mang� une super boulette.
 */
public class SuperDoge extends Doge {
	
	private long timer;
	private long duration;

	public SuperDoge(Game g, String nom, int x, int y, PacDoge p) {
		super(g, nom, x, y, p);
		timer = 0;
		duration = p.getDuration();
	}
	
	public void resetTimer()
	{
		timer = 0;
	}
	
	@Override
	public void move(long dt)	
	/* Avec cette méthode, on ne va pas bouger le doge,
	 * mais elle nous permet d'avoir une notion du temps écoulé grâce au paramètre.
	 * On va donc s'en servir pour redevenir un doge normal après quelques instants
	 */
	{
		timer += dt;
		if(timer > duration)
		{
			p.downgradePlayer();
			p.restoreGrumpy();
		}
	}
	
	public void effect(Objet o) {
                
	}
}