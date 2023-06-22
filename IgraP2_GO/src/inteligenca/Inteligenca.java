package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Koordinate;
import logika.Stanje;
import splosno.KdoIgra;
import splosno.Poteza;

public class Inteligenca extends KdoIgra {
	
	private static final int ZMAGA = 100;
	private static final int PORAZ = -ZMAGA;
	
	public Inteligenca () {
		super("Alphan & Omega");
	}
	

	public static OcenjenaPoteza alphabeta(Igra igra, int globina, int alpha, int beta, Igralec igralec) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		

		
		if (igra.naPotezi() == igralec) {ocena = PORAZ;} else {ocena = ZMAGA;}
		
		//Koordinate ogrozenaNasprotnik = igralec.nasprotnik().getOgrozena();
		//if (ogrozenaNasprotnik != null) {
		//	int x = ogrozenaNasprotnik.getX();
		//	int y = ogrozenaNasprotnik.getY();
		//	
		//	Poteza p = new Poteza(x, y);
		//	return new OcenjenaPoteza (p, ocena);
		//}
		
		
		//Koordinate ogrozena = igralec.getOgrozena();
		//if (ogrozena != null) {
		//	//System.out.println(ogrozena);
		//	int x = ogrozena.getX();
		//	int y = ogrozena.getY();
		//	
		//	Poteza p = new Poteza(x, y);
		//	return new OcenjenaPoteza (p, ocena);
		//}
		
		
		
		
		//List<Koordinate> moznePoteze = igra.prostaMesta(); 
		List<Koordinate> verjetne = igra.prostaMesta(); //množica mest ki imajo zasedeno polje za soseda
		if (verjetne.size() == 0) {
			verjetne = igra.prostaMesta();
		}
		
		if (igra.prostaMesta().size() < 81 * 0.4 || igra.stUjetihBelihZetonov + igra.stUjetihCrnihZetonov > 20) {
			Igra copyIgra = new Igra(igra);
			Poteza p = new Poteza(-1,-1,true);
			copyIgra.odigraj(p);
			//copyIgra.odigraj(p);
			if (copyIgra.stanje == Stanje.win_white && igralec == Igralec.WHITE || copyIgra.stanje == Stanje.win_black && igralec == Igralec.BLACK) {
				
				return new OcenjenaPoteza(p, 1000);
			}
		}
		
		System.out.print(verjetne);
		Koordinate kandidat = verjetne.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		
		
		for (Koordinate k: verjetne) {
			if (igra.stevec < 1000) {
				if (true){ //verjetne.contains(k))     (k.getX() >= 2  && k.getX() <= 5 && k.getY() >= 2  && k.getY() <= 5) {
					Igra kopija = new Igra(igra); //kopija s konstruktorjem za kopujo
					//kopija.igralecNaPotezi = igra.igralecNaPotezi;
					//for (int i=0; i < Igra.velikost; i++) {
					//	for (int j=0; j < Igra.velikost; j++) {
					//		kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
					//	}
					//}
					//kopija.stevec = igra.stevec;
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
			Igra kopija = new Igra(igra);
			//kopija.igralecNaPotezi = igra.igralecNaPotezi;
			//for (int i=0; i < Igra.velikost; i++) {
			//	for (int j=0; j < Igra.velikost; j++) {
			//		kopija.grid.mreza[i][j] = igra.grid.mreza[i][j];
			//	}
			//}
			//kopija.stevec = igra.stevec;
			
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
		//igra.stevec++;
		return new OcenjenaPoteza (p, ocena);
	}

	
	public Poteza izberiPotezo (Igra igra) {
		if (igra.stevec == 0) return new Poteza(3,3);

		
		int globina = 0;
		OcenjenaPoteza najboljsaPoteza = null;
		
		if (Igra.velikost == 9){
			if (igra.stevec < 90) {
				globina = 1;
			}
			else if (igra.stevec >= 90) {
				globina = 2;
			}
			najboljsaPoteza = alphabeta(igra, globina,PORAZ,ZMAGA,igra.naPotezi());
		}
		
		else if (Igra.velikost == 13){
			if (igra.stevec < 110) {
				globina = 1;
			}
			else if (igra.stevec >= 110) {
				globina = 2;
			}
			najboljsaPoteza = alphabeta(igra, globina,PORAZ,ZMAGA,igra.naPotezi());
		}
		else if (Igra.velikost == 19){
			if (igra.stevec < 190) {
				globina = 1;
			}
			else globina = 2; 
			najboljsaPoteza = alphabeta(igra, globina,PORAZ,ZMAGA,igra.naPotezi());
		}
		System.out.println(igra.stevec);
		//OcenjenaPoteza najboljsaPoteza = alphabeta(igra, 2,PORAZ,ZMAGA,igra.naPotezi());
		return najboljsaPoteza.poteza;	
		
	};

}
