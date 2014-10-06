package Jeu;

import iut.Game;
import iut.Objet;
import java.util.HashSet;


/**
 * Le niveau du jeu. Contient les murs, l'emplacement des portails et le "spawn" des grumpycat
 */
public class Carte extends Objet {
	
        private int posX;               //Contiendront les coordonnées du sprite de la carte.
        private int posY;
        private int dimCaseX;           //Dimensions d'une case de la carte
        private int dimCaseY;
        private int mapOff;             //Décalage dû aux bords de la carte
		private int tailleCarte;		//Nombre de cases de la carte
		private int hCarte;				//Nombre de cases de haut de la carte
		private int wCarte;				//Nombre de cases de large de la carte
		private int nMurs;				//Nombre de murs de la carte
		private	int nBoulettes;			//Nombre de boulettes sur la carte
		private int nGrumpy;			//Nombre de grumpy sur la carte
		
        private int absoluteCur;        
        //Curseur référençant la position absolue de la "case" actuelle de la carte.
        //Utilisé lors du placement des boulettes
        //(position absolue = position pas en coordonnées)
        private HashSet walls;          //Set contenant la position absolue de tous les murs (+ spawn des grumpy)
		private HashSet portals;		//Set contenant la position absolue des portals
		private HashSet grumpy;			//Set contenant la position absolue du spawn des grumpy
		
		private int blueP;				//contient la position absolue du portal bleu
		private int orangeP;			//										  orange
		private int[] grumpySpawn;		//Tableau contenant l'ensemble des positions du spawn des grumpy
		private int playerSpawn;		//Tableau contenant le spawn du joueur
		
        public Carte(Game g, String nom, int x, int y) {
                super(g, nom, x, y);
				
				//INFORMATIONS "GRAPHIQUES" SUR LA CARTE
                posX = x;               //On récupère la position d'initialisation de la carte
                posY = y;               
                dimCaseX = 53;          //Le côté d'une case pour cette carte est 52px
                dimCaseY = 55;          //Le côté d'une case pour cette carte est 52px
                mapOff = -20;           //Décalage à -20
				
				//INFORMATIONS SUR LA CONFIGURATION DE LA CARTE
				wCarte = 20;
				hCarte = 11;
				tailleCarte = hCarte * wCarte;
				nMurs = 114;
				nGrumpy = 4;
				nBoulettes = tailleCarte - nMurs - nGrumpy - 5;
				// -2 pour les portals, -2 pour l'enclave au dessus du spawn grump et -1 pour le spawn joueur
				
                absoluteCur = 1;
                walls = new HashSet(nMurs);				//Set avec nMurs emplacements répertoriant la position des murs
				portals = new HashSet(2);				//Il y a 2 portals sur la carte
				blueP = toAbs(2,10);					//Initialisation de la position des portails
				orangeP = toAbs(19,2);
				grumpySpawn = new int[nGrumpy];			//Initialisation du tableau de position des spawn grumpy
				grumpy = new HashSet(nGrumpy);			//Initialisation du hashset de position des spawn grumpy
				
				initWalls();            //On remplit le HashSet des murs avec leurs positions absolues.
				initPortals();			//On remplit celui des portals
				initSpawns();			//Puis celui des spawn grumpy
		}
        
        public void initWalls()         //Place les positions absolues de tous les murs ou autres cases non accessibles dans un set.
        //Les placer dans un set permet de vérifier rapidement plus tard si une case est un mur
		//Place aussi les positions absolues des portails, ainsi que les positions de spawn du joueur et des grumpy
        {
            int i,j;
            for(j = 1; j <= 11; j++)    //Ajout des murs du contour de la carte
            {
                for(i = 1; i <=20; i++)
                {
                    if (j == 1 || i == 1 || j == 11 || i == 20)
                        walls.add(toAbs(i,j));
                }
            }
            //Ajout des murs restants d'une manière assez crade.
            walls.add(toAbs(6,2));      //Murs reliés aux murs externes
            walls.add(toAbs(6,3));
            walls.add(toAbs(15,2));
            walls.add(toAbs(15,3));
            walls.add(toAbs(6,9));
            walls.add(toAbs(6,10));
            walls.add(toAbs(15,9));
            walls.add(toAbs(15,10));
            
            walls.add(toAbs(3,3));      //Partie verticale des L latéraux
            walls.add(toAbs(3,4));
            walls.add(toAbs(3,5));
            walls.add(toAbs(3,7));
            walls.add(toAbs(3,8));
            walls.add(toAbs(3,9));
            walls.add(toAbs(18,3));
            walls.add(toAbs(18,4));
            walls.add(toAbs(18,5));
            walls.add(toAbs(18,7));
            walls.add(toAbs(18,8));
            walls.add(toAbs(18,9));
            
            walls.add(toAbs(4,3));      //Fin des L latéraux
            walls.add(toAbs(4,9));
            walls.add(toAbs(17,3));
            walls.add(toAbs(17,9));
            
            walls.add(toAbs(5,5));      //Petits murs paumés au milieu
            walls.add(toAbs(6,5));
            walls.add(toAbs(5,7));
            walls.add(toAbs(6,7));
            walls.add(toAbs(15,5));
            walls.add(toAbs(16,5));
            walls.add(toAbs(15,7));
            walls.add(toAbs(16,7));
            
            walls.add(toAbs(8,3));      //Grands murs paumés au milieu
            walls.add(toAbs(9,3));
            walls.add(toAbs(10,3));
            walls.add(toAbs(11,3));
            walls.add(toAbs(12,3));
            walls.add(toAbs(13,3));
            walls.add(toAbs(8,9));
            walls.add(toAbs(9,9));
            walls.add(toAbs(10,9));
            walls.add(toAbs(11,9));
            walls.add(toAbs(12,9));
            walls.add(toAbs(13,9));
            
            walls.add(toAbs(9,5));      //Spawn des grumpy (murs)
            walls.add(toAbs(8,5));
            walls.add(toAbs(8,6));
            walls.add(toAbs(8,7));
            walls.add(toAbs(9,7));
            walls.add(toAbs(10,7));
            walls.add(toAbs(11,7));
            walls.add(toAbs(12,7));
            walls.add(toAbs(13,7));
            walls.add(toAbs(13,6));
            walls.add(toAbs(13,5));
            walls.add(toAbs(12,5));
        }

		public void initPortals()
		{
			portals.add(blueP);     //Portails
            portals.add(orangeP);
		}
		
		public void initSpawns()
		{
			grumpySpawn[0] = toAbs(9,6);
			grumpySpawn[1] = toAbs(10,6);
			grumpySpawn[2] = toAbs(11,6);
			grumpySpawn[3] = toAbs(12,6);
			for (int i = 0; i < nGrumpy; i++)
			{
				grumpy.add(grumpySpawn[i]);
			}
			grumpy.add(toAbs(10,5));
			grumpy.add(toAbs(11,5));
			playerSpawn = toAbs(10,2);
		}
		
		
		
        public int toAbs(int x, int y)  //Convertit des coordonnees fournies en une position absolue
        {
            return x + ((y-1)*wCarte);
        }
        
        public int toAbs(Coordonnees c)
        {
            return c.x() + ((c.y()-1)*wCarte);
        }
        
        public Coordonnees toCoord(int a)   //Convertit une position absolue en des coordonnees
        {
            return new Coordonnees(a%wCarte,((int)((a/wCarte)+1)));
        }
		
        public Coordonnees getRealCoord(int a)  //Renvoie les coordonnées réelles (au niveau de l'écran) d'un élément à la case spécifiée
        {
            Coordonnees res = new Coordonnees(toCoord(a));  //On récupère les coordonnées relatives correspondant à la position transmise
            return toRealCoord(res);
        }
        
        public Coordonnees getRealCoord(int x, int y)
        {
            Coordonnees res = new Coordonnees(x, y);
            return toRealCoord(res);
        }
        
        public Coordonnees getRealCoord(Coordonnees c)
        {
            return toRealCoord(c);
        }
		
		private Coordonnees toRealCoord(Coordonnees c)
		{
            c.set((c.x()-1)*dimCaseX, (c.y()-1)*dimCaseY);
            c.set(c.x()+posX+mapOff, c.y()+posY+mapOff);
			return c;
		}
        
		
		
        public boolean isWall(int a)        //Indique si la case à la position fournie est un mur
        {
            return walls.contains(a);
        }
        
        public boolean isWall(int x, int y)
        {
            return walls.contains(toAbs(x,y));
        }
        
        public boolean isWall(Coordonnees c)
        {
            return walls.contains(toAbs(c));
        }
        
        public boolean isPortal(int a)        //Indique si la case à la position fournie est un portail
        {
            return portals.contains(a);
        }
        
        public boolean isPortal(int x, int y)
        {
            return portals.contains(toAbs(x,y));
        }
        
        public boolean isPortal(Coordonnees c)
        {
            return portals.contains(toAbs(c));
        }
		
		public boolean isGrumpySpawn(int a)
		{
			return grumpy.contains(a);
		}
		
		public boolean isGrumpySpawn(int x, int y)
		{
			return grumpy.contains(toAbs(x,y));
		}
		
		public boolean isGrumpySpawn(Coordonnees c)
		{
			return grumpy.contains(toAbs(c));
		}
        
		
		
        public int posX()                   //Indique la position x de la carte
        {
            return posX;
        }
        
        public int posY()                   //Indique la position y de la carte
        {
            return posY;
        }
        
        public Coordonnees pos()            //Indique la position de la carte
        {
            return new Coordonnees(posX, posY);
        }
        
        public int dimX()					//Indique la dimension horizontale d'une case
        {
            return dimCaseX;
        }
        
        public int dimY()					//Indique la dimension horizontale d'une case
        {
            return dimCaseY;
        }
		
		public int off()					//Indique le décalage des cases dû aux bordures de la carte
		{
			return mapOff;
		}
        
        public Coordonnees dim()			//Indique les dimensions d'une case
        {
            return new Coordonnees(dimCaseX, dimCaseY);
        }
        
		
		
		public int nMurs()					//Indique le nombre de murs sur la carte
		{
			return nMurs;
		}
		
		public int nBoulettes()				//Indique le nombre par défaut de boulettes sur la carte
		{
			return nBoulettes;
		}
		
		public int nGrumpy()				//Indique le nombre de grumpy présents
		{
			return nGrumpy;
		}
		
		public int tailleCarte()			//Indique le nombre de cases de la carte
		{
			return tailleCarte;
		}
		
		public int bluePos()				//Renvoie la position absolue du portail bleu
		{
			return blueP;
		}
		
		public int orangePos()				//										 orange
		{
			return orangeP;
		}
		
		public int[] getPortalPos()			//Renvoie un tableau contenant les positions absolues des portails
		{
			return new int[]{blueP, orangeP};
		}

		public int[] getGrumpySpawn()		//Renvoie un tableau contenant les positions absolues des emplacements pour spawn de grumpy
		{
			return grumpySpawn.clone();
		}
		
		public int getGrumpySpawn(int i)	//Renvoie la position absolue de la ieme case de spawn de Grumpy
		{
			return grumpySpawn[i];
		}
		
		public int getPlayerSpawn()			//Renvoie la position absolue de la case de spawn du joueur
		{
			return playerSpawn;
		}
		
		
		
        public int getNextAbsolute()        //Recherche et renvoie la prochaine position absolue qui n'est pas un mur, un portail ou une case du spawn
        {
            do
            {
                absoluteCur++;
            } while(isWall(absoluteCur) || isPortal(absoluteCur) || absoluteCur == playerSpawn || isGrumpySpawn(absoluteCur)); 
            return absoluteCur;
        }
        
        public Coordonnees getNextCoord()   //Renvoie les coordonnées réelles correspondant à la position précédemment trouvée.
        {
            Coordonnees res = new Coordonnees(toCoord(getNextAbsolute()));
            return getRealCoord(res);
        }

	//Déplace l'objet
	public void move(long dt) {
	}

	/**
	 * Teste la collision entre deux objets
	 * @return true si la collision a eu lieu
	 */
	public boolean collision(Objet o) {
		return false;
	}

	/**
	 * Action : effet d'une collision entre l'objet et le param�tre
	 */
	public void effect(Objet o) {
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