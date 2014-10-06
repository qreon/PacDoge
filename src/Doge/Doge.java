package Doge;

import Jeu.Carte;
import Jeu.Coordonnees;
import Jeu.PacDoge;
import iut.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Doge classique
 */
public abstract class Doge extends ObjetTouchable implements KeyListener{
    
	protected PacDoge p;
	protected Carte c;
	protected boolean hasJustTPed;	//Indique si le joueur sort tout juste d'un portail
	
	private final static char LEFT = 'l';
	private final static char RIGHT = 'r';
	private final static char UP = 'u';
	private final static char DOWN = 'd';

	public Doge(Game g, String nom, int x, int y, PacDoge p) {		//Le constructeur requiert un pointeur sur le jeu en lui même afin de pouvoir appeler ses méthodes par la suite
			super(g, nom, x, y);
			this.p = p;
			c = p.getMap();		//On se reservira de la carte plus tard
			hasJustTPed = false;
	}

	protected void bouger(int sensX, int sensY)				//Méthode qui contrôle le mouvement du joueur
	{
		moveX(sensX*c.dimX());
		moveY(sensY*c.dimY());
		System.out.println(getRelPos());
	}

	public void move(long dt) {	
	}
	public void keyPressed(KeyEvent e) {	//Capte les évènements claviers et produit des mouvements en conséquence
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				hasJustTPed = false;
				if(noWall(LEFT))			//Avant de se déplacer on regarde déjà si la case voulue n'est pas un mur
					bouger(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				hasJustTPed = false;
				if(noWall(RIGHT))
					bouger(+1, 0);
				break;
			case KeyEvent.VK_UP:
				hasJustTPed = false;
				if(noWall(UP))
					bouger(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				hasJustTPed = false;
				if(noWall(DOWN))
					bouger(0, +1);
				break;
		}
	}

	public boolean noWall(char dir)			//Vérifie que la case adjacente dans la direction indiquée
	{
		Coordonnees pos = getRelPos();		//On récupère la position dans le système de coordonnées de la carte
		switch (dir)
		{
			case LEFT:						//Selon la direction, on ajuste la position
				pos.setX(pos.x()-1);
				break;
			case RIGHT:
				pos.setX(pos.x()+1);
				break;
			case UP:
				pos.setY(pos.y()-1);
				break;
			case DOWN:
				pos.setY(pos.y()+1);
				break;
			default:
		}
		return !(c.isWall(pos) || c.isGrumpySpawn(pos));				//Et on regarde si c'est un mur
	}
	
	public Coordonnees getRelPos()			//Récupère la position dans le système de coordonnées de la carte (coordonnées compréhensibles correspondant à une grille)
	{
		int l = getLeft();					//On récupère les coordonnées à l'écran du sprite
		int t = getTop();
		l -= (c.off()+c.posX());			//auxquelles on retire le décalage et la position de la carte
		t -= (c.off()+c.posY());
		l = (int)(l/c.dimX())+1;			//Enfin, on récupère une coordonnée potable en divisant par la dimension d'une case
		t = (int)(t/c.dimY())+1;
		return new Coordonnees(l,t);
	}
	
	public Coordonnees getScreenCoord()		//Renvoie les coordonnées actuelles du joueur
	{
		return new Coordonnees(getLeft(), getTop());
	}

	public void publicMoveX(int dx)			//Les méthodes moveX et moveY sont protégées, on veut pouvoir y accéder depuis l'extérieur
	{
		moveX(dx);
	}
	
	public void publicMoveY(int dy)
	{
		moveY(dy);
	}
	
	public boolean isFriend() {
		return true;
	}

	public boolean isEnnemy() {
		return false;
	}
	
	public boolean hasJustTPed()
	{
		return hasJustTPed;
	}
	
	public void tpState(boolean state)
	{
		hasJustTPed = state;
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}
}