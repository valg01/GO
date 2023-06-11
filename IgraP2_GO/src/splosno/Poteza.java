package splosno;
import logika.Koordinate;

public record Poteza(int x, int y, boolean pass) {
    // Constructor for moves that aren't a pass
    public Poteza(int x, int y) {
        this(x, y, false);
    }

    // Constructor for a pass move
    public Poteza() {
        this(-1, -1, true);
    }
    
    public Koordinate getKoordinate() {
    	return (pass ? null : new Koordinate(x,y));
    }
    
  
}



