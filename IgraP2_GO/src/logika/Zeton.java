package logika;

public enum Zeton {
	WHITE, BLACK, NULL;
	
	public Igralec getIgralec() {
		return (this == WHITE ? Igralec.WHITE : Igralec.BLACK);
	}
}
