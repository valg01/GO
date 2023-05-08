package logika;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import splosno.Poteza;

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
		igralecNaPotezi = Igralec.BLACK;
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
	
	public boolean odigraj(Poteza poteza) {
		if (grid.mreza[poteza.x()][poteza.y()] == null) return true;
		return false;
	}
	
	public boolean poteza(int x, int y) {
		Poteza p = new Poteza(x,y);
		if (!odigraj(p)) return false;
		else {
			if (igralecNaPotezi == Igralec.BLACK) {
				grid.mreza[x][y] = Zeton.BLACK;
				igralecNaPotezi = Igralec.WHITE;
			}
			else {
				grid.mreza[x][y] = Zeton.WHITE;
				igralecNaPotezi = Igralec.BLACK;
			}
		}
		return true;
	}
	
	public Map<Zeton, int[]> grupiranje(int x, int y) {   		//glede na x in y koordinato bo funkcija dala zeton v grupo zetonov
		Map<Zeton, int[]> grupa = new HashMap<Zeton, int[]>();
		if (grid.mreza[x][y] != null) {
			int[] koor = {x, y};
			grupa.put(grid.mreza[x][y], koor);
			while (true) {
				Map<Zeton, int[]> pomozni = new HashMap<Zeton, int[]>();
				if (x == 0 && y > 0) {
					if (grid.mreza[x][y] == grid.mreza[x+1][y]) {
						int[] koordinate = {x+1, y};
						grupa.put(grid.mreza[x+1][y], koordinate);
						pomozni = grupiranje(x+1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					if (grid.mreza[x][y] == grid.mreza[x][y+1]) {
						int[] koordinate = {x, y+1};
						grupa.put(grid.mreza[x][y+1], koordinate);
						pomozni = grupiranje(x, y+1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x][y-1]) {
						int[] koordinate = {x, y-1};
						grupa.put(grid.mreza[x][y-1], koordinate);
						pomozni = grupiranje(x, y-1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
				}
				else if (x > 0 && y == 0) {
					if (grid.mreza[x][y] == grid.mreza[x+1][y]) {
						int[] koordinate = {x+1, y};
						grupa.put(grid.mreza[x+1][y], koordinate);
						pomozni = grupiranje(x+1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					if (grid.mreza[x][y] == grid.mreza[x][y+1]) {
						int[] koordinate = {x, y+1};
						grupa.put(grid.mreza[x][y+1], koordinate);
						pomozni = grupiranje(x, y+1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x-1][y]) {
						int[] koordinate = {x-1, y};
						grupa.put(grid.mreza[x-1][y], koordinate);
						pomozni = grupiranje(x-1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
				}
				else if (x == grid.mreza.length && y > 0) {
					if (grid.mreza[x][y] == grid.mreza[x-1][y]) {
						int[] koordinate = {x-1, y};
						grupa.put(grid.mreza[x-1][y], koordinate);
						pomozni = grupiranje(x-1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					if (grid.mreza[x][y] == grid.mreza[x][y+1]) {
						int[] koordinate = {x, y+1};
						grupa.put(grid.mreza[x][y+1], koordinate);
						pomozni = grupiranje(x, y+1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x][y-1]) {
						int[] koordinate = {x, y-1};
						grupa.put(grid.mreza[x][y-1], koordinate);
						pomozni = grupiranje(x, y-1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
				}
				else if (x > 0 && y == grid.mreza[0].length) {
					if (grid.mreza[x][y] == grid.mreza[x+1][y]) {
						int[] koordinate = {x+1, y};
						grupa.put(grid.mreza[x+1][y], koordinate);
						pomozni = grupiranje(x+1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					if (grid.mreza[x][y] == grid.mreza[x-1][y]) {
						int[] koordinate = {x-1, y};
						grupa.put(grid.mreza[x-1][y], koordinate);
						pomozni = grupiranje(x-1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x][y-1]) {
						int[] koordinate = {x, y-1};
						grupa.put(grid.mreza[x][y-1], koordinate);
						pomozni = grupiranje(x, y-1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
				}
				else if (x > 0 && y > 0 && x < grid.mreza.length && y < grid.mreza[0].length) {
					if (grid.mreza[x][y] == grid.mreza[x+1][y]) {
						int[] koordinate = {x+1, y};
						grupa.put(grid.mreza[x+1][y], koordinate);
						pomozni = grupiranje(x+1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					if (grid.mreza[x][y] == grid.mreza[x][y+1]) {
						int[] koordinate = {x, y+1};
						grupa.put(grid.mreza[x][y+1], koordinate);
						pomozni = grupiranje(x, y+1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x][y-1]) {
						int[] koordinate = {x, y-1};
						grupa.put(grid.mreza[x][y-1], koordinate);
						pomozni = grupiranje(x, y-1);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
					
					if (grid.mreza[x][y] == grid.mreza[x-1][y]) {
						int[] koordinate = {x-1, y};
						grupa.put(grid.mreza[x-1][y], koordinate);
						pomozni = grupiranje(x-1, y);
						for (Zeton el : pomozni.keySet()) {
							if (!grupa.containsKey(el) && el != null) grupa.put(el, pomozni.get(el));
						}
						
					}
				}
			}
		}
		return grupa;
	}
	
	public Set<int[]> sosednjaMozna(Map<Zeton, int[]> grupa) {
		Set<int[]> koordinate = new HashSet<int[]>();
		for (Zeton zeton : grupa.keySet()) {
			
			int x1 = grupa.get(zeton)[0]; 
			int y1 = grupa.get(zeton)[1];  //prebere x in y koordinate zetona v grupi in nato preverja ali so sosednji prazni (grupiranje je narejeno tako, da ce so iste barve jih tako ali tako da v isto grupo). Če so prazni se lahko tja razširi
			if (x1 == 0 && y1 == 0) {
				if (grid.mreza[x1 + 1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1+1] != zeton && grid.mreza[x1][y1 + 1] == null) {
					int[] koor = {x1, y1+1};
					koordinate.add(koor);
				}
			}
			else if (x1 == grid.mreza.length && y1 == grid.mreza[0].length) {
				if (grid.mreza[x1 - 1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1-1] == null) {
					int[] koor = {x1, y1-1};
					koordinate.add(koor);
				}
			}
			else if (x1 == 0 && y1 == grid.mreza[0].length) {
				if (grid.mreza[x1 +1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1-1] == null) {
					int[] koor = {x1, y1-1};
					koordinate.add(koor);
				}
			}
			else if (y1 == 0 && x1 == grid.mreza.length) {
				if (grid.mreza[x1 -1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1+1] != zeton && grid.mreza[x1][y1+1] == null) {
					int[] koor = {x1, y1+1};
					koordinate.add(koor);
				}
			}
			else if (x1 == 0 && y1 > 0) {
				if (grid.mreza[x1 +1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1+1] != zeton && grid.mreza[x1][y1+1] == null) {
					int[] koor = {x1, y1+1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1 - 1] == null) {
					int[] koor = {x1, y1 -1};
					koordinate.add(koor);
				}
			}
			else if (x1 == grid.mreza.length && y1 > 0) {
				if (grid.mreza[x1 - 1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1+1] != zeton && grid.mreza[x1][y1 + 1] == null) {
					int[] koor = {x1, y1+1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1-1] == null) {
					int[] koor = {x1, y1 -1};
					koordinate.add(koor);
				}
			}
			else if (x1 > 0 && y1 == 0) {
				if (grid.mreza[x1 - 1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1+1] != zeton && grid.mreza[x1][y1 + 1] == null) {
					int[] koor = {x1, y1+1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1 + 1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
			}
			else if (x1 > 0 && y1 == grid.mreza.length) {
				if (grid.mreza[x1 - 1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1 - 1] == null) {
					int[] koor = {x1, y1-1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1 + 1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
			}
			else {
				if (grid.mreza[x1 - 1][y1] != zeton && grid.mreza[x1 - 1][y1] == null) {
					int[] koor = {x1 - 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1-1] != zeton && grid.mreza[x1][y1 - 1] == null) {
					int[] koor = {x1, y1-1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1 + 1][y1] != zeton && grid.mreza[x1 + 1][y1] == null) {
					int[] koor = {x1 + 1, y1};
					koordinate.add(koor);
				}
				if (grid.mreza[x1][y1 + 1] != zeton && grid.mreza[x1][y1 + 1] == null) {
					int[] koor = {x1, y1 + 1};
					koordinate.add(koor);
				}
			}
		}
		return koordinate;
	}
	
	public boolean konecIgre() {
		for (int i = 0; i < grid.mreza.length; i++) {
			for (int j = 0; j < grid.mreza[0].length; j++) {
				if (grid.mreza[i][j] == null) continue;
				else {
					Map<Zeton, int[]> grupa = grupiranje(i,j);
					Set<int[]> odprte = sosednjaMozna(grupa);
					if (odprte.isEmpty()) return true;
				}
			}
		}
		return false;
	}
}
