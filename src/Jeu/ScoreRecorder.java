/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Jeu;

import Menu.ScoreScreen;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 *
 * @author François
 */
public class ScoreRecorder {
	private PacDoge p;
	private ScoreScreen s;
	private ArrayList<Long> scores;
	private File save;
	private FileOutputStream saveOut;
	private ObjectOutputStream os;
	private FileInputStream saveIn;
	private ObjectInputStream is;
	private int nRecords;
	
	public ScoreRecorder(PacDoge p, int nHighScore)
	{
		this.p = p;																//On créé la référence sur le jeu
		s = p.getScoreScreen();													//et on en récupère le scoreScreen
		nRecords = nHighScore;
		
		save = new File("scores.dat");											//On pointe sur le fichier scores.dat
		if (save.exists())														//S'il existe, on déclare des flux entrants depuis ce fichier
		{
			try 
			{
				saveIn = new FileInputStream(save.getName());
				is = new ObjectInputStream(saveIn);
			}
			catch(Exception e)
			{
				scores = new ArrayList<Long>(nRecords);
				scores.clear();
				System.out.println("Exception on istreams declaration");
				System.out.println(e);
			}
			
			try
			{
				scores = (ArrayList<Long>)is.readObject();						//On essaye de faire rentrer le contenu du fichier dans l'array list
			}
			catch (Exception e)
			{
				System.out.println("Can't read from file");
				System.out.println(e);
				scores = new ArrayList<Long>(nRecords);
				scores.clear();
				System.out.println("New array list created");
			}
			
			try																	//On essaye de ferme le fichier
			{
				is.close();
				saveIn.close();
			}
			catch(Exception e)
			{
				System.out.println("Can't close streams :"+e);
			}
			
			try																	//Puis on l'efface
			{
				save.delete();
			}
			catch(Exception e)
			{
				System.out.println("Can't delete file :"+e);
			}
		}
		if (!save.exists())														//S'il s'est bien supprimé, on le recréé.
		{
			try
			{
				save.createNewFile();
			}
			catch(Exception e)
			{
				System.out.println("Can't create new file");
				System.out.println(e);
			}
		}
		
		try																		//Enfin, on déclare des flux sortants sur le fichier
		{
			saveOut = new FileOutputStream(save.getName());
			os = new ObjectOutputStream(saveOut);
		}
		catch(Exception e)
		{
				System.out.println("Exception on ostreams declaration");
		}
	}
	
	public long getMax()
	{
		if(scores.size() == 0)
		{
			return 0;				//S'il n'y a aucun score enregistré, on renvoie 0
		}
		else
		{
			return scores.get(0);	//Sinon on renvoie la case 0 car on part du principe que le tableau est toujours trié par ordre décroissant
		}
	}
	
	public void record(long number)		//Enregistre un score dans l'array list
	{
		int wIndex = getRank(number);	//On cherche un indice disponible (0-4)
		shift(number, wIndex);			//On écrit le nombre à cet index en décalant les autres
	}
	
	public int getRank(long number)		//Renvoie l'indice ou pourrait être stocké le score
	{
		int i;
		for (i = 0; i < scores.size(); i++)		//Pour tous les scores existants
		{
			if (number > scores.get(i))			//on les compare au score passé en paramètre,
			{
				return i;						//Et on renvoie l'indice correspondant si nécéssaire
			}
		}
		
		if (scores.size() < nRecords)			//S'il n'a dépassé aucun score mais qu'il reste de la place dans l'array list
		{
			return i;							//On renvoie i, qui vaut à ce moment scores.size()
		}
		
		return nRecords;						//Sinon, on renvoie nRecords, qui est un indice hors champ de l'array list
	}
	
	public void shift(long number, int index)
	{
		if (index < nRecords)			//Si l'index indiqué n'est pas hors champ
		{
			ArrayList<Long> tmp = new ArrayList<Long>(5);	//On créé une array list vide
			for(int i = 0; i < index; i++)
			{
				tmp.add(i, scores.get(i));	//Jusqu'à l'index indiqué, on recopie le contenu de l'original dans tmp
			}
			tmp.add(index, number);		//On insère le score dans l'array list
			for (int i = index+1; ((i <= scores.size()) && i < nRecords); i++)	//Puis on remplit le reste en recopiant l'original à indice-1
			{
				tmp.add(i, scores.get(i-1));
			}
			scores = (ArrayList<Long>) tmp.clone();		//Enfin on remet tout dans score
		}
	}
	
	public void display()	//Affiche le contenu de l'array list (debug)
	{
		System.out.println("Scores :");
		for (int i = 0; i < scores.size(); i++)
		{
			System.out.println("  "+scores.get(i));
		}
	}
	
	public ArrayList<Long> getScores()	//Renvoie l'array list
	{
		return scores;
	}
	
	public void clearScores()			//Reset les scores
	{
		scores.clear();
		scores = null;
		scores = new ArrayList<Long>(nRecords);
		//Certainement trop de lignes, mais au moins c'est safe
	}
	
	public void writeRecords()		//Sérialise dans le fichier de scores
	{
		try
		{
			os.writeObject(scores);
			os.flush();
			os.close();
		}
		catch(Exception e)
		{
			System.out.println("Can't write on file:"+e);
		}
	}
}
