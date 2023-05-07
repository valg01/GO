package logika;

public enum Igralec {
	WHITE, BLACK;
	
	public Igralec nasportnik() {
		return (this == WHITE ? BLACK : WHITE);
	}
	
/*  public Polje getPolje() {
		return (this == white ? Polje.white : Polje.black);
	}
*/	
	@Override
	public String toString() {
		return (this == WHITE ? "Beli" : "Črni");
	}
}
