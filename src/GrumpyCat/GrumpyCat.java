package GrumpyCat;

import Jeu.Carte;
import Jeu.Coordonnees;
import Jeu.PacDoge;
import iut.Game;
import iut.ObjetTouchable;
import java.util.ArrayList;

/**
 * Ennemi principale du jeu (abstrait)
 */
public abstract class GrumpyCat extends ObjetTouchable {
	protected PacDoge p;	//Référence sur le jeu
	protected Carte c;		//Référence sur la carte
	protected char etat;	//Etat : 't' = tueur ; 'm' = mangeable
	protected long debuffTimer;		//Timer du debuff des super boulettes
	protected long debuffDuration;	//Durée du debuff des super boulettes
	protected long refreshTimer;	//Timer entre chaque mouvement des
	protected long refreshPeriod;	//Période entre chaque mouvement des grumpy
	protected long initialDelay;	//Délai (ms) avant que les grumpy ne se mettent à se déplacer.
	protected int num;				//Numéro du grumpy
	
	protected char nextDir;
	protected char lastDir;
	protected ArrayList<Character> possibleDir;
 	
	private final static char LEFT = 'l';
	private final static char RIGHT = 'r';
	private final static char UP = 'u';
	private final static char DOWN = 'd';
       
	public GrumpyCat(Game g, String nom, int x, int y, PacDoge p, int num) {
		super(g, nom, x, y);
		this.p = p;
		this.c = p.getMap();
		debuffTimer = 0;
		debuffDuration = p.getDuration();
		initialDelay = 0;
		//0 car les grumpy derpent pendant 1/2 heure dans leur base en début de partie...
		refreshTimer = - initialDelay;
		refreshPeriod = p.getRefreshPeriod();
		nextDir = 'n';
		lastDir = 'n';
		possibleDir = new ArrayList<Character>();
		this.num = num;
	}
	
	//Déplace les grumpy cats en tirant aléatoirement une direction parmi les directions possibles, sauf l'opposé
	//de la dernière direction empruntée (s'il est allé en haut la dernière fois, il ne pourra pas aller en bas).
	public void move(long dt)
	{
		refreshTimer += dt;
		if ((refreshTimer > refreshPeriod))
		{
			refreshTimer = 0;
			lastDir = nextDir;
			seekPossibleDir();
			int n = possibleDir.size();
			int ran = (int)(Math.random()*n);	
			nextDir = possibleDir.get(ran);
			bouger();
		}
	}
	
	public void seekPossibleDir()		//Regarde les possiblités du grumpy et stocke les directions dans une arraylist
	{
		possibleDir.clear();
		if(noWall(DOWN))
			possibleDir.add(DOWN);
		
		if(noWall(UP))
			possibleDir.add(UP);
		
		if(noWall(LEFT))
			possibleDir.add(LEFT);
		
		if(noWall(RIGHT))
			possibleDir.add(RIGHT);
		
		if(possibleDir.size() > 1)
			possibleDir.remove((Character)oppositeDir(lastDir));
	}
	
	public char oppositeDir(char dir)
	{
		char res;
		
		switch(dir)
		{
			case LEFT:
				res = RIGHT;
				break;
			case RIGHT:
				res = LEFT;
				break;
			case UP:
				res = DOWN;
				break;
			case DOWN:
				res = UP;
				break;
			default:				//Lignes pour faire plaisir au compilateur qui se plaint que res
				res = dir;			//pourrait ne pas avoir été initialisé, cas qui n'arrivera jamais.
		}
		
		return res;
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
		}
		return !(c.isWall(pos));				//Et on regarde si c'est un mur
	}

	protected void bouger()				//Méthode qui contrôle le mouvement du joueur
	{
		int sensX = 0;
		int sensY = 0;
		
		switch(nextDir)
		{
			case DOWN:
				sensY = 1;
				break;
			case UP:
				sensY = -1;
				break;
			case LEFT:
				sensX = -1;
				break;
			case RIGHT:
				sensX = 1;
				break;
		}
		
		moveX(sensX*c.dimX()); 
		moveY(sensY*c.dimY()); 
	}
	
	public Coordonnees getScreenCoord()		//Renvoie les coordonnées à l'écran du sprite
	{
		return new Coordonnees(getLeft(), getTop());
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
	
	public void resetTimer()
	{
		debuffTimer = 0;
	}
	
	public char getEtat()
	{
		return etat;
	}
	
	public void setRefreshPeriod(long n)
	{
		refreshPeriod = n;
	}

	public void publicMoveX(int dx)			//Les méthodes moveX et moveY sont protégées, on veut pouvoir y accéder depuis l'extérieur
	{
		moveX(dx);
	}
	
	public void publicMoveY(int dy)
	{
		moveY(dy);
	}
}