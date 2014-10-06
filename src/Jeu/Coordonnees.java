/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

/**
 *
 * @author François
 */
public class Coordonnees {
    private int x;
    private int y;
    
    public Coordonnees() {
        x = 0;
        y = 0;
    }
    
    public Coordonnees(Coordonnees c) {
        x = c.x();
        y = c.y();
    }
    
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int x()
    {
        return x;
    }
    
    public int y()
    {
        return y;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void set(Coordonnees c)
    {
        this.x = c.x();
        this.y = c.y();
    }
    
    public String toString()
    {
        return "x: "+x+", y: "+y;
    }
}
