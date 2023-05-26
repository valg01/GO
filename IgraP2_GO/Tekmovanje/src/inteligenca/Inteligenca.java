package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Koordinate;
import splosno.KdoIgra;
import splosno.Poteza;

public class Inteligenca extends KdoIgra {
	
	private static final int ZMAGA = 100;
	private static final int PORAZ = -ZMAGA;
	
	public Inteligenca () {
		super("Alphan & Omega");
	}
	
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec igralec) {
		OcenjenaPoteza najboljsa = null;
		List<Koordinate> mozne = igra.prostaMesta();
		for (Koordinate k : mozne) {
			if (igra.stevec < 5) {
				if (k.getX() >= 3  && k.getX() <= 5 && k.getY() >= 3  && k.getY() <= 5) {
					Igra kopija = new Igra();
					kopija.igralecNaPotezi = igra.igralecNaPotezi;
					for (int i=0; i < Igra.velikost; i++) {
						for (int j=0; j < Igra.velikost; j++) {
							kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
						}
					}
					Poteza p = new Poteza(k.getX(), k.getY());
					kopija.odigraj(p);
					int ocena = 0;
					switch (kopija.stanje) {
		            case win_white: ocena = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
		            case win_black: ocena = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
					default:
						if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopija, igralec);
						else ocena = minimax(kopija, globina-1, igralec).ocena;
					}
		            if (najboljsa == null || igra.naPotezi()==igralec && ocena > najboljsa.ocena || igra.naPotezi() != igralec && ocena < najboljsa.ocena) {
		            	najboljsa = new OcenjenaPoteza(p, ocena);
		            }
				}
			}
			Igra kopija = new Igra();
			kopija.igralecNaPotezi = igra.igralecNaPotezi;
			for (int i=0; i < Igra.velikost; i++) {
				for (int j=0; j < Igra.velikost; j++) {
					kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
				}
			}
			Poteza p = new Poteza(k.getX(), k.getY());
			kopija.odigraj(p);
			int ocena = 0;
			switch (kopija.stanje) {
            case win_white: ocena = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
            case win_black: ocena = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
			default:
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopija, igralec);
				else ocena = minimax(kopija, globina-1, igralec).ocena;
			}
            if (najboljsa == null || igra.naPotezi()==igralec && ocena > najboljsa.ocena || igra.naPotezi() != igralec && ocena < najboljsa.ocena) {
            	najboljsa = new OcenjenaPoteza(p, ocena);
            }
		}
		return najboljsa;
	}
	

	public static OcenjenaPoteza alphabeta(Igra igra, int globina, int alpha, int beta, Igralec igralec) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		

		
		if (igra.naPotezi() == igralec) {ocena = PORAZ;} else {ocena = ZMAGA;}
		
		Koordinate ogrozenaNasprotnik = igralec.nasprotnik().getOgrozena();
		if (ogrozenaNasprotnik != null) {
			int x = ogrozenaNasprotnik.getX();
			int y = ogrozenaNasprotnik.getY();
			
			Poteza p = new Poteza(x, y);
			return new OcenjenaPoteza (p, ocena);
		}
		
		
		Koordinate ogrozena = igralec.getOgrozena();
		if (ogrozena != null) {
			//System.out.println(ogrozena);
			int x = ogrozena.getX();
			int y = ogrozena.getY();
			
			Poteza p = new Poteza(x, y);
			return new OcenjenaPoteza (p, ocena);
		}
		
		
		
		
		
		List<Koordinate> moznePoteze = igra.prostaMesta(); 
		List<Koordinate> verjetne = igra.najboljVerjetne(); //množica mest ki imajo zasedeno polje za soseda
		Koordinate kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		
		
		for (Koordinate k: verjetne) {
			if (igra.stevec < 1000) {
				if (true){ //verjetne.contains(k))     (k.getX() >= 2  && k.getX() <= 5 && k.getY() >= 2  && k.getY() <= 5) {
					Igra kopija = new Igra();
					kopija.igralecNaPotezi = igra.igralecNaPotezi;
					for (int i=0; i < Igra.velikost; i++) {
						for (int j=0; j < Igra.velikost; j++) {
							kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
						}
					}
					kopija.stevec = igra.stevec;
					Poteza p = new Poteza(k.getX(), k.getY());
					kopija.odigraj(p);
					int ocenap;
					switch (kopija.stanje) {
					case win_white: ocenap = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
		            case win_black: ocenap = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
					default:
						// Nekdo je na potezi
						if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopija, igralec);
						else ocenap = alphabeta (kopija, globina-1, alpha, beta, igralec).ocena;
					}
					if (igra.naPotezi() == igralec) { // Maksimiramo oceno
						if (ocenap > ocena) { // mora biti > namesto >=
							ocena = ocenap;
							kandidat = k;
							alpha = Math.max(alpha,ocena);
						}
					} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
						if (ocenap < ocena) { // mora biti < namesto <=
							ocena = ocenap;
							kandidat = k;
							beta = Math.min(beta, ocena);					
						}	
					}
					if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
						break;
				}

			}
			Igra kopija = new Igra();
			kopija.igralecNaPotezi = igra.igralecNaPotezi;
			for (int i=0; i < Igra.velikost; i++) {
				for (int j=0; j < Igra.velikost; j++) {
					kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
				}
			}
			kopija.stevec = igra.stevec;
			Poteza p = new Poteza(k.getX(), k.getY());
			kopija.odigraj(p);
			int ocenap;
			switch (kopija.stanje) {
			case win_white: ocenap = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
            case win_black: ocenap = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopija, igralec);
				else ocenap = alphabeta (kopija, globina-1, alpha, beta, igralec).ocena;
			}
			if (igra.naPotezi() == igralec) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = k;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = k;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				break;
		}
		Poteza p = new Poteza(kandidat.getX(), kandidat.getY());
		igra.stevec++;
		return new OcenjenaPoteza (p, ocena);
	}

	public static OcenjenaPoteza alphabetaStar(Igra igra, int globina, int alpha, int beta, Igralec igralec) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.naPotezi() == igralec) {ocena = PORAZ;} else {ocena = ZMAGA;}
		List<Koordinate> moznePoteze = igra.prostaMesta();
		
		
		Koordinate kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		
		for (Koordinate k: moznePoteze) {
			if (igra.stevec < 100) {
				if (k.getX() >= 2  && k.getX() <= 5 && k.getY() >= 2  && k.getY() <= 5) {
					Igra kopija = new Igra();
					kopija.igralecNaPotezi = igra.igralecNaPotezi;
					for (int i=0; i < Igra.velikost; i++) {
						for (int j=0; j < Igra.velikost; j++) {
							kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
						}
					}
					kopija.stevec = igra.stevec;
					Poteza p = new Poteza(k.getX(), k.getY());
					kopija.odigraj(p);
					int ocenap;
					switch (kopija.stanje) {
					case win_white: ocenap = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
		            case win_black: ocenap = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
					default:
						// Nekdo je na potezi
						if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopija, igralec);
						else ocenap = alphabeta (kopija, globina-1, alpha, beta, igralec).ocena;
					}
					if (igra.naPotezi() == igralec) { // Maksimiramo oceno
						if (ocenap > ocena) { // mora biti > namesto >=
							ocena = ocenap;
							kandidat = k;
							alpha = Math.max(alpha,ocena);
						}
					} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
						if (ocenap < ocena) { // mora biti < namesto <=
							ocena = ocenap;
							kandidat = k;
							beta = Math.min(beta, ocena);					
						}	
					}
					if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
						break;
				}
				else continue;
			}
			Igra kopija = new Igra();
			kopija.igralecNaPotezi = igra.igralecNaPotezi;
			for (int i=0; i < Igra.velikost; i++) {
				for (int j=0; j < Igra.velikost; j++) {
					kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
				}
			}
			kopija.stevec = igra.stevec;
			Poteza p = new Poteza(k.getX(), k.getY());
			kopija.odigraj(p);
			int ocenap;
			switch (kopija.stanje) {
			case win_white: ocenap = (igralec == Igralec.WHITE ? ZMAGA : PORAZ); break;
            case win_black: ocenap = (igralec == Igralec.BLACK ? ZMAGA : PORAZ); break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopija, igralec);
				else ocenap = alphabeta (kopija, globina-1, alpha, beta, igralec).ocena;
			}
			if (igra.naPotezi() == igralec) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = k;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = k;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				break;
		}
		Poteza p = new Poteza(kandidat.getX(), kandidat.getY());
		igra.stevec++;
		return new OcenjenaPoteza (p, ocena);
	}

	public Poteza izberiPotezo (Igra igra) {
		if (igra.stevec == 0) return new Poteza(3,3);

		
		int globina;
		if (igra.najboljVerjetne().size() > 10) {
		    globina = 3;
		}
		else globina = 4;
		
		OcenjenaPoteza najboljsaPoteza = alphabeta(igra, globina,PORAZ,ZMAGA,igra.naPotezi());

		//OcenjenaPoteza najboljsaPoteza = alphabeta(igra, 2,PORAZ,ZMAGA,igra.naPotezi());
		return najboljsaPoteza.poteza;	
	};

}
