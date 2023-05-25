package vodja;

import java.util.Map;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.GlavnoOknoIgre;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;



import splosno.Poteza;
import splosno.KdoIgra;

public class Vodja {	//povezes igralce s clovekom,racunalnikom
	
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
				clovekNaVrsti = false;
				igrajRacunalnikovoPotezo ();
				break;
			}
		}
	}

	
	//private static Random random = new Random ();
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca();
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void> () {
			@Override
			protected Void doInBackground() {
				//Poteza poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(0);} catch (Exception e) {};
				return null;
			}
			@Override
			protected void done () {
				if (igra != zacetnaIgra) return;
				Poteza poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				igra.odigraj(poteza);
				igramo ();
			}
		};
		worker.execute();
	}
		
	public static void igrajClovekovoPotezo(Poteza poteza) {
		if (igra.odigraj(poteza) && clovekNaVrsti) {
			igra.odigraj(poteza);
			clovekNaVrsti = false;
			igramo();
			
		}

	}


}
