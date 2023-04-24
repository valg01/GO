package logika;

public enum Igralec {
	white, black;
	
	public Igralec nasportnik() {
		return (this == white ? black : white);
	}
	
/*  public Polje getPolje() {
		return (this == white ? Polje.white : Polje.black);
	}
*/	
	@Override
	public String toString() {
		return (this == white ? "Beli" : "Črni");
	}
}
