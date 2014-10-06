package Jeu;

import iut.*;
import java.awt.Graphics;
import Doge.*;
import Boulette.*;
import GrumpyCat.*;
import Menu.*;
import Portail.*;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 * L'objet qui sera instanci� en d�but de partie. Tout les objets y sont ajout�s.
 */
public class PacDoge extends Game {
    
		private Menu m;						//Le menu qui sera affiché au lancement
		private Audio musique;				//La musique de fond qui ne fonctionne pas
		private ScoreScreen s;				//L'écran des scores qui sera éventuellement affiché			
		private ScoreRecorder sr;			//L'objet charger d'enregistrer les scores et de les stocker dans fichier
		private int nHighScore;				//Nombres de meilleurs scores stockables par le ScoreRecorder
        private Carte c;					//La carte du niveau
        private GrumpyCat[] g;				//Un tableau de Grumpy Cats
        private Doge joueur;				//Le joueur
        private PortailBleu p1;				//Les portails
        private PortailOrange p2;
        private Score sc;					//L'objet Score, qui s'affiche et se rafraichit durant une partie
        private Boulette[] b;				//Un tableau de boulettes
        private Sprite bg;					//L'arrière plan du programme
		private long duration;				//Durée du buff des super boulettes
		private int nBoulettes;				//Nombre de boulettes dans le niveau
		private int chance;					//Il y aura 1 super boulette pour (chance - 1) boulettes normales
		private long refreshPeriod;			//Période de "rafraichissement" du mouvement des grumpy cats (ms)
		private Graphics graph;				//Graphics sur lequel tout est affiché

        private static boolean exists = false;	//Variables nécessaire pour que la classe soit un singleton
        private static PacDoge pDoge;
        public static PacDoge getInstance()
        {
            if (exists)
            {
                return pDoge;
            }
            else
            {
                pDoge = new PacDoge();
                exists = true;
                return pDoge;
            }
        }
        
	private PacDoge() {
		super(1280, 720, "Pac Doge");					//Création de la fenêtre
		duration = 5000;								//Durée (ms) du buff des super boulettes
		chance = 10;									//1 super boulette pour 9 normales (environ)
		refreshPeriod = 500;							//Les grumpy cats se déplacent toutes les 500 ms
		nHighScore = 5;									//5 meilleurs scores stockables
		sr = new ScoreRecorder(this, nHighScore);		//Création du scoreRecorder
		musique = new Audio("rickroll");				//Musique qui ne marche pas
	}
        
	/**
	 * cr�e tous les objets du jeu. Appel� en d�but de partie.
	 */
	protected void createObjects()
	{
		m = new Menu(this, "null", 0, 0, this);			//Création du menu pour commencer
		add(m);
		//musique.start();								//Musique qui ne fonctionne pas
	}
	
	protected void createGameObjects() {
            Coordonnees tmp = new Coordonnees();        //Variable Coordonnees d'utilité générale
            
            this.c = new Carte(this,"map",122,80);		//Création de la carte en (122,80)
            add(c);

			tmp.set(c.toCoord(c.getPlayerSpawn()));		//On récupère la position du spawn du joueur
            tmp = c.getRealCoord(tmp);
            this.joueur = new DogeNormal(this,"doge",tmp.x(),tmp.y(),this);
			
            this.b = new Boulette[c.nBoulettes()];                  //c.nBoulettes() sur la carte
            for (int i = 0; i < c.nBoulettes() ; i++) {
                double ran = Math.random()*chance;          //On créé toutes les boulettes avec 1/10 chances de faire une super boulette
                tmp = c.getNextCoord();                 //On récupère les coordonnées du prochain emplacement à boulettes indiqué par la carte
                this.b[i] = (ran >= (chance-1)) ? (new SuperBoulette(this, "boule+2",tmp.x(),tmp.y(),this)) : (new BouletteNormale(this, "boule2",tmp.x(),tmp.y(),this));
				add(this.b[i]);							//On créé les boulettes correspondantes avec des sprites différents, et on les ajoute au jeu
            }
            
            tmp.set(c.toCoord(c.bluePos()));							//On veut créer un portail bleu à la case correspondante (donnée par la carte)
            tmp = c.getRealCoord(tmp);									//On récupère les coordonnées réelles correspondantes.
            this.p1 = new PortailBleu(this,"pBleu",tmp.x(),tmp.y(),this);    //Enfin on créé le portail.
            //Même chose pour le portail orange à la case (2,10)
            tmp.set(c.toCoord(c.orangePos()));
            tmp = c.getRealCoord(tmp);
            this.p2 = new PortailOrange(this,"pOrange",tmp.x(),tmp.y(),this);

            this.sc = new Score(this,"",0,10);

			//On ajoute tout
			//Portails
            add(p1);
            add(p2);

			//Grumpy
			g = new GrumpyCat[c.nGrumpy()];
            for (int i = 0; i < c.nGrumpy(); i++)
			{
				String couleur;
				int ran = (int)(Math.random()*3);
				switch(ran)								//Couleur aléatoire
				{
					case 0: couleur = "gVert"; 
							break;
					case 1: couleur = "gBleu"; 
							break;
					case 2: couleur = "gOrange"; 
							break;
					default: couleur = "gOrange";
				}
				tmp = c.toCoord(c.getGrumpySpawn(i));
				tmp = c.getRealCoord(tmp);
				g[i] = new GrumpyTueur(this, couleur, tmp.x(), tmp.y(), this, i);
				add(g[i]);
			}
			
			//Joueur
			add(joueur);
            addKeyInteractiveObject(joueur);
            
			//Score
            add(sc);
			
			nBoulettes = c.nBoulettes();
	}
	
	protected void createScoreScreenObjects()		//Créé et affiche l'écran des scores
	{
		s = null;
		s = new ScoreScreen(this, "null", 0, 0, this);
		add(s);
	}
	
	public Carte getMap()		//Renvoie une référence sur la carte
	{
		return c;
	}

	public Score getScore()		//Renvoie une référence sur le score
	{
		return sc;
	}
	
	public long getDuration()	//Renvoie la durée du bonus de la super boulette
	{
		return duration;
	}
	
	public long getRefreshPeriod()	//Renvoie la période de rafraichissement des grumpy
	{
		return refreshPeriod;
	}
	
	public boolean playerHasJustTPed()	//Indique si le joueur vient tout juste de se téléporter (pour éviter les boucles infernales)
	{
		return joueur.hasJustTPed();
	}
	
	public void setPlayerTPState(boolean state)	//Modifie le paramètre ci dessus
	{
		joueur.tpState(state);
	}
	
	public ScoreRecorder getScoreRecorder() //Renvoie le scoreRecorder
	{
		return sr;
	}
	
	public ScoreScreen getScoreScreen()	//Renvoie une référence sur l'écran des scores
	{
		return s;
	}
	
	
	
	public void upgradePlayer()	//Transforme le joueur en super doge
	{
		Coordonnees tmp = joueur.getScreenCoord();	//On stocke les coordonnées à l'écran du joueur
		System.out.println("PLAYER UP: "+joueur.getRelPos());
		remove(joueur);								//On le retire de l'écran
		removeKeyListener(joueur);
		joueur = new SuperDoge(this,"doge+",tmp.x(),tmp.y(),this);	//On lui assigne le sprite de super doge
		add(joueur);								//On l'ajoute au jeu
		addKeyInteractiveObject(joueur);
		System.out.println("DONE: "+joueur.getRelPos());
	}
	
	public void downgradePlayer()	//Fait redevenir le joueur en doge normal
	{
		Coordonnees tmp = joueur.getScreenCoord();
		System.out.println("PLAYER DOWN: "+joueur.getRelPos());
		remove(joueur);
		removeKeyListener(joueur);
		joueur = new DogeNormal(this,"doge",tmp.x(),tmp.y(),this);
		add(joueur);
		addKeyInteractiveObject(joueur);
		System.out.println("DONE: "+joueur.getRelPos());
	}
	
	
	
	public void downgradeGrumpy()			//Rend les grumpy mangeables
	{
		for(int i = 0; i < c.nGrumpy(); i++)
		{
			Coordonnees tmp = g[i].getScreenCoord();
			System.out.println("GRUMPY DOWN: "+g[i].getRelPos());
			remove(g[i]);
			g[i] = new GrumpyMangeable(this,"gMangeable",tmp.x(),tmp.y(),this,i);
			add(g[i]);
			System.out.println("DONE: "+g[i].getRelPos());
		}
	}
	
	public void restoreGrumpy()				//Fait redevenir les grumpy méchants
	{
		for(int i = 0; i < c.nGrumpy(); i++)
		{
			Coordonnees tmp = g[i].getScreenCoord();
			System.out.println("GRUMPY RESTORED: "+g[i].getRelPos());
			remove(g[i]);
			String couleur;
			int ran = (int)(Math.random()*3);
			switch(ran)
			{
				case 0: couleur = "gVert"; 
						break;
				case 1: couleur = "gBleu"; 
						break;
				case 2: couleur = "gOrange"; 
						break;
				default: couleur = "gOrange";
			}
			g[i] = new GrumpyTueur(this,couleur,tmp.x(),tmp.y(),this,i);
			add(g[i]);
			System.out.println("DONE: "+g[i].getRelPos());
		}
	}
	
	public void backGrumpy(int i)			//Ramène les grumpy au spawn
	{
		Coordonnees tmp = g[i].getScreenCoord();
		int caseRespawn = 9 + (int)(Math.random()*4); //Tire un nombre entre 9 et 12
		Coordonnees delta = c.getRealCoord(new Coordonnees(caseRespawn,6));
		g[i].publicMoveX(delta.x()-tmp.x());
		g[i].publicMoveY(delta.y()-tmp.y());
	}
	
	
	
	public void tpToOrange()				//Téléporte le joueur au portail orange
	{
		Coordonnees j = joueur.getScreenCoord();
		Coordonnees p = c.getRealCoord(c.orangePos());
		System.out.println("Player coord: "+joueur.getRelPos());
		System.out.println("Orange portal coord:"+c.toCoord(c.orangePos()));
		int dx = p.x() - j.x();
		int dy = p.y() - j.y();
		System.out.println("Moving player...");
		joueur.publicMoveX(dx);
		joueur.publicMoveY(dy);
		System.out.println("Player post coord: "+joueur.getRelPos()+"\n");
	}
	
	public void tpToBlue()					//Téléporte le joueur au portail bleu
	{
		Coordonnees j = joueur.getScreenCoord();
		Coordonnees p = c.getRealCoord(c.bluePos());
		System.out.println("Player coord: "+joueur.getRelPos());
		System.out.println("Blue portal coord:"+c.toCoord(c.bluePos()));
		int dx = p.x() - j.x();
		int dy = p.y() - j.y();
		System.out.println("Moving player...");
		joueur.publicMoveX(dx);
		joueur.publicMoveY(dy);
		System.out.println("Player post coord: "+joueur.getRelPos()+"\n");
	}
	
	
	
	public void publicAddKeyInteractiveObject(KeyListener o)	//Méthode publique pour le protected addKeyInteractiveObject(KeyListener o);
	{
		addKeyInteractiveObject(o);
	}
	
	public void play()			//Lance une partie
	{
		drawBackground(graph);
		createGameObjects();
		m.removeObjects();
		remove(m);
		m = null;
	}
	
	public void scores()		//Dessine l'écran des scores
	{
		drawBackground(graph);
		createScoreScreenObjects();
		m.removeObjects();
		remove(m);
		m = null;
	}
	
	public void quit()			//Quitte le jeu
	{
		sr.writeRecords();
		System.exit(0);
	}
	
	public void upScore(int i)	//Augment le score de i
	{
		sc.upScore(i);
	}
	
	public void rmBoulette()	//Retire une boulette du compteur
	{
		nBoulettes--;
		if(nBoulettes == 0)		//S'il n'y a plus de boulettes, on a gagné.
		{
			gagne();
		}
	}
	
	/**
	 * Dessine le fond d'�cran
	 * @param g la surface d'affichage
	 */
	protected void drawBackground(Graphics g) {
		try {
			SpriteStore s = SpriteStore.get();
			bg = s.getSprite("bg");
		} catch (Exception ex) {
			Logger.getLogger(PacDoge.class.getName()).log(Level.SEVERE, null, ex);
		}
		graph = g;
		bg.draw(g, 0, 0);
	}

	public void perdu()		//Actions a effectuer quand on perd
	{
		JOptionPane.showMessageDialog(null,"Vous avez perdu.\n(veuillez quitter cette fenêtre à la\nsouris afin de ne pas faire bugger le menu)");
		sr.record(sc.getScore());	//Envoi du score au scoreRecorder
		invokeMenuFromGame();
	}

	protected void gagne()	//Et quand on gagne
	{
		JOptionPane.showMessageDialog(null,"Vous avez gagné.\n(veuillez quitter cette fenêtre à la\nsouris afin de ne pas faire bugger le menu)");
		sr.record(sc.getScore());
		invokeMenuFromGame();
	}
	
	public void invokeMenuFromGame()	//Fait apparaître le menu (appel spécifique depuis une partie)
	{
		drawBackground(graph);
		createObjects();
		removeGameObjects();
		sr.display();					//Affiche le contenu de l'array list du scoreRecorder
	}
	
	public void invokeMenuFromScores()	//Fait apparaitre le menu (appel spécifique depuis l'écran des scores)
	{
		drawBackground(graph);
		createObjects();
		removeScoreObjects();
	}
	
	public void removeGameObjects()		//remove les objets du jeu et les mets à null
	{
		System.out.println("Remove game objects");
		//Score
		remove(sc);
		sc = null;
		
		//Joueur
		removeKeyListener(joueur);
		remove(joueur);
		joueur = null;
		
		//Grumpy
		for (int i = 0; i < c.nGrumpy(); i++)
		{
			remove(g[i]);
			g[i] = null;
		}
		g = null;
		
		//Portails
		remove(p1);
		remove(p2);
		p1 = null;
		p2 = null;
		
		//Boulettes
		for (int i = 0; i < c.nBoulettes(); i++)
		{
			remove(b[i]);
			b[i] = null;
		}
		b = null;
		
		//Carte
		remove(c);
		c = null;
		System.out.println("Removed game objects");
	}

	public void removeScoreObjects()		//Vire les objets du score
	{
		remove(s);
	}
}