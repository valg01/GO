package logika;

import vodja.Vodja;
import java.util.List;


public enum Igralec { //tk ko da bi par konstant si definiru in not daš par možnih vrednosti
	WHITE, BLACK;
	
	public Igralec nasprotnik() {
		return (this == WHITE ? BLACK : WHITE);
	}
	
	public Zeton getZeton() {
		return (this == WHITE ? Zeton.WHITE : Zeton.BLACK);
	}
	
	@Override
	public String toString() {
		return (this == WHITE ? "Beli" : "Črni");
	}
	
	public List<List<Koordinate>> getGrupe(){
		return (this == WHITE ? Vodja.igra.beleGrupe : Vodja.igra.crneGrupe);
	}
	
	public List<List<Koordinate>> getUjete(){
		return (this == WHITE ? Vodja.igra.ujeteBele : Vodja.igra.ujeteCrne);
	}
	
	
	
	
	//public Koordinate getOgrozena() {
	//	return (this == WHITE ? Vodja.igra.ogrozenaBela : Vodja.igra.ogrozenaCrna);
	//}
}
