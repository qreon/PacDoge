package Jeu;

import iut.Game;
import java.awt.Graphics;
import iut.Objet;
import java.awt.Color;
import java.awt.Font;

/**
 * Le score du joueur. Est affich� � l'�cran.
 */
public class Score extends Objet {
	private long score;

	public Score(Game g, String nom, int x, int y) {
		super(g, nom, x, y);
	}
	/**
	 * Permet de recuperer le score courant
	 */
	public long getScore() {
		return this.score;
	}

	/**
	 * Permet d'augmenter le score de la valeur de i
	 */
	public void upScore(int i) {
		this.score += i;
	}

	//Dessine l'objet sur la surface 2D
	public void draw(Graphics g) throws Exception {
            String s = "Score : "+score;
            g.setFont(new Font("Comic Sans MS",Font.BOLD,36));
            g.setColor(Color.CYAN);
            g.drawString(s, 870, 50);
	}

	//Deplace l'objet
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