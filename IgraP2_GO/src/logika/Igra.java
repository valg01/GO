package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {
	
	public static int velikost = 9;
	
	public static final List<Vrsta> VRSTE = new LinkedList<Vrsta>();
	
	private Polje grid;
	
	private Igralec igralecNaPotezi;
	
	public Igra() {
		grid = new Polje(velikost);
		for (int i = 0; i < velikost; i ++) {
			for (int j = 0; j < velikost; j ++) {
				grid.mreza[i][j] = null;
			}
		}
		igralecNaPotezi = Igralec.black;
	}
	
	public Igralec naPotezi() {
		return igralecNaPotezi;
	}
	
	public Polje getPlosca() {
		return grid;
	}
	
	public List<Koordinate> poteze() {
		LinkedList<Koordinate> ps = new LinkedList<Koordinate>();
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				if (grid.mreza[i][j] == null) {
					ps.add(new Koordinate(i, j));
				}
			}
		}
		return ps;
	}
	
}
