package logika;
import java.util.Objects;


public class Koordinate {
    private int x;
    private int y;

    public Koordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Koordinate desna() {
        return new Koordinate(x+1, y);
    }

    public Koordinate leva() {
        return new Koordinate(x-1, y);
    }

    public Koordinate zgornja() {
        return new Koordinate(x, y-1);
    }

    public Koordinate spodnja() {
        return new Koordinate(x, y+1);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Koordinate koordinate = (Koordinate) obj;
        return x == koordinate.x && y == koordinate.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}


