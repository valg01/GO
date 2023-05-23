package vodja;

import java.util.Map;
import java.util.Random;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.GlavnoOknoIgre;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;

import inteligenca.Minimax;


import splosno.Poteza;
import splosno.KdoIgra;

public class Vodja {	
	
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	public static GlavnoOknoIgre okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
		
	public static void igramoNovoIgro () {
//		okno = new GlavnoOknoIgre();
		igra = new Igra ();
		igramo ();
	}
	
	public static void igramo () {
		okno.osveziGUI(); 
		switch (igra.stanje) {
		case win_white: 
		case win_black: 
		case draw: 
			return; // odhajamo iz metode igramo
		case in_progress: 
			Igralec igralec = igra.naPotezi();
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				igrajRacunalnikovoPotezo ();
				break;
			}
		}
	}

	
	private static Random random = new Random ();
	public static Inteligenca racunalnikovaInteligenca = new Minimax(3);
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void> () {
			@Override
			protected Void doInBackground() {
				Poteza poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(100);} catch (Exception e) {};
				return null;
			}
			@Override
			protected void done () {
				if (igra != zacetnaIgra) return;
				Poteza poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				igra.odigraj(poteza.x(), poteza.y());
				igramo ();
			}
		};
		worker.execute();
	}
		
	public static void igrajClovekovoPotezo(Poteza poteza) {
		if (igra.odigraj(poteza.x(), poteza.y()) && clovekNaVrsti) {
			igra.odigraj(poteza.x(), poteza.y());
			clovekNaVrsti = false;
			igramo();
			
		}

	}


}
