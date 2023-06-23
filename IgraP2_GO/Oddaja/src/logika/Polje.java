package logika;

public class Polje { //polje je mreža 
	
	public int velikost;
	public Zeton[][] mreza;
	
	public Polje(int velikost) {
		this.velikost = velikost;
		this.mreza = new Zeton[velikost][velikost];
	}
	
	public Polje(Polje original) { //copy constructor
        this.velikost = original.velikost;
        this.mreza = new Zeton[original.velikost][original.velikost];
        for (int i = 0; i < original.velikost; i++) {
            for (int j = 0; j < original.velikost; j++) {
                this.mreza[i][j] = original.mreza[i][j];
            }
        }
    }
	
	
	
	public Zeton naMestu(int x, int y) {
		return mreza[x][y];
	}
	
	public Zeton naMestuKoord(Koordinate koord) {
		int x = koord.getX();
		int y = koord.getY();
		
		return mreza[x][y];
	}
	
	public void dodajZeton(Zeton zeton, int x, int y) {
		mreza[x][y] = zeton;
	}
	public void dodajZetonKoord(Zeton zeton, Koordinate koord) {
		int x = koord.getX();
		int y = koord.getY();
		mreza[x][y] = zeton;
	}
}
