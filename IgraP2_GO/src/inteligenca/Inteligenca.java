package inteligenca;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Poteza;

public abstract class Inteligenca extends KdoIgra {
	
	public Inteligenca () {
		super("Tim & Val");
	}
	
	public abstract Poteza izberiPotezo (Igra igra);

}
