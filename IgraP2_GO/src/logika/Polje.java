package logika;

public class Polje {
	
	public int velikost;
	public Zeton[][] mreza;
	
	public Polje(int velikost) {
		this.velikost = velikost;
		this.mreza = new Zeton[velikost + 1][velikost + 1];
	}
	
	public Zeton naMestu(int x, int y) {
		return mreza[x][y];
	}
	
	public void dodajZeton(Zeton zeton, int x, int y) {
		mreza[x][y] = zeton;
	}
}
