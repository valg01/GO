package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Koordinate;
import splosno.Poteza;

public class Minimax extends Inteligenca{
	private static final int ZMAGA = 1000;
	private static final int PORAZ = -ZMAGA;
	
private int globina;
	
	public Minimax (int globina) {
		this.globina = globina;
	}


	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec igralec) {
		OcenjenaPoteza najboljsa = null;
		List<Koordinate> mozne = igra.prostaMesta();
		for (Koordinate k : mozne) {
			Igra kopija = new Igra();
			kopija.igralecNaPotezi = igra.igralecNaPotezi;
			for (int i=0; i < Igra.velikost; i++) {
				for (int j=0; j < Igra.velikost; j++) {
					kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
				}
			}
			kopija.odigraj(k.getX(), k.getY());
			int ocena = 0;
			switch (kopija.stanje) {
            case win_white: ocena = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
            case win_black: ocena = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
			default:
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopija, igralec);
				else ocena = minimax(kopija, globina-1, igralec).ocena;
			}
            if (najboljsa == null || igra.naPotezi()==igralec && ocena > najboljsa.ocena || igra.naPotezi() != igralec && ocena < najboljsa.ocena) {
            	Poteza p = new Poteza(k.getX(), k.getY());
            	najboljsa = new OcenjenaPoteza(p, ocena);
            }
		}
		return najboljsa;
	}
	
	@Override
	public Poteza izberiPotezo (Igra igra) {
		OcenjenaPoteza najboljsaPoteza = minimax(igra, this.globina, igra.naPotezi());
		return najboljsaPoteza.poteza;	
	}
	
}
