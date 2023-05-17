package logika;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Poteza;

public class Igra {
	
	public static int velikost = 9;
	
	private int stevec = 0;
	
	public static final List<Vrsta> VRSTE = new LinkedList<Vrsta>();
	
	public Polje grid;
	
	public Stanje stanje;
	
	private Igralec igralecNaPotezi;
	
	private List<List<Koordinate>> beleGrupe;
	private List<List<Koordinate>> crneGrupe;
	public List<Koordinate> ujetaGrupa;
	
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
	
	public List<Koordinate> prostaMesta() { //kire koordinate še lahk igraš ps so prosta mesta, pogleda če je null, če je null ga doda
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
	
	public boolean jeVeljavna(Poteza poteza) { //pogleda, če je ta ko ga igraš null, če ne nemorš odigrat
		if (grid.mreza[poteza.x()][poteza.y()] == null) return true;
		return false;
	}
	
	
	public boolean odigraj(int x, int y) {
		Poteza p = new Poteza(x,y);
		if (!jeVeljavna(p) || !(stanje == Stanje.in_progress || stanje == null)) return false; //pogleda če lahk odigrap
		
		if ((x != 0 || y != 0) && stevec % 2 == 0 && grid.mreza[x][y] == null) {
			grid.mreza[x][y] = Zeton.BLACK;
			stevec++;
		}
		else if ((x != 0 || y != 0) && stevec % 2 == 1 && grid.mreza[x][y] == null) {
			grid.mreza[x][y] = Zeton.WHITE;
			stevec++;
		}
		
		updateGrupe();
		updateStanje();
		//System.out.print(zmagovalec());
		
		return true;
	}

	
	

	
	public List<Koordinate> najdiSosede(Koordinate koord) {
        int x = koord.getX();
        int y = koord.getY();
        Zeton zeton = grid.mreza[x][y];
        List<Koordinate> sosedi = new ArrayList<>();
        //sosedi.add(koord);
        if (velikost - x > 0 && grid.mreza[x + 1][y] == zeton) {
            sosedi.add(koord.desna());
            //System.out.println("v sosedih");
            //System.out.println(x);
            //System.out.println(y);
            //System.out.println(zeton);
            //System.out.println(grid.mreza[x + 1][y]);
            //System.out.println();
        }
        if (x != 1 && grid.mreza[x - 1][y] == zeton) {
            sosedi.add(koord.leva());
        }
        if (velikost - y > 0 && grid.mreza[x][y + 1] == zeton) {
            sosedi.add(koord.spodnja());
        }
        if (y != 1 && grid.mreza[x][y - 1] == zeton) {
            sosedi.add(koord.zgornja());
        }
        
        return sosedi;
    }
	public boolean imaLiberties(Koordinate koord) {
        int x = koord.getX();
        int y = koord.getY();

        if (velikost - x > 0 && grid.mreza[x + 1][y] == null) {
            return true;
        }
        if (x != 1 && grid.mreza[x - 1][y] == null) {
        	return true;
        }
        if (velikost - y > 0 && grid.mreza[x][y + 1] == null) {
        	return true;
        }
        if (y != 1 && grid.mreza[x][y - 1] == null) {
        	return true;
        }
        
        return false;
    }
	
	public List<Koordinate> grupa(Koordinate koord) {
	    List<Koordinate> grupa = new ArrayList<>();
	    grupa.add(koord);
	    return grupaAux(koord, grupa);
	}

	private List<Koordinate> grupaAux(Koordinate koord, List<Koordinate> grupa) {
	    List<Koordinate> sosedi = najdiSosede(koord);

	    for (Koordinate sosed : sosedi) {
	        if (!grupa.contains(sosed)) {
	            grupa.add(sosed);
	            //System.out.println(grupa);
	            grupa = grupaAux(sosed, grupa);
	            
	        }
	    }
	    return grupa;
	}


	
	
	
	
	public void updateGrupe() {
	    beleGrupe = new ArrayList<>();
	    crneGrupe = new ArrayList<>();

	    Set<HashSet<Koordinate>> beleSet = new HashSet<>();
	    Set<HashSet<Koordinate>> crneSet = new HashSet<>();

	    for (int i = 1; i < velikost; i++) {
	        for (int j = 1; j < velikost; j++) {
	            Zeton zeton = grid.mreza[i][j];
	            List<Koordinate> grup = grupa(new Koordinate(i, j));
	            HashSet<Koordinate> grupSet = new HashSet<>(grup);

	            if (zeton == zeton.BLACK && !crneSet.contains(grupSet)) {
	                crneGrupe.add(grup);
	                crneSet.add(grupSet);
	            } else if (zeton == zeton.WHITE && !beleSet.contains(grupSet)) {
	                beleGrupe.add(grup);
	                beleSet.add(grupSet);
	            }
	            
	        }
	    }
	    //System.out.print(crneGrupe);
	}
	
	
	public boolean ujeta(List<Koordinate> grupa) {
		for (Koordinate koord : grupa) {
			if (imaLiberties(koord)) return false;
		}
		return true;
	}
	
	public Igralec zmagovalec() {
		for (List<Koordinate> grupa : beleGrupe) {
			if (ujeta(grupa)) return Igralec.BLACK;
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (ujeta(grupa)) return Igralec.WHITE;
		}
		return null;
	}
	
	public Stanje updateStanje() {
		for (List<Koordinate> grupa : beleGrupe) {
			if (ujeta(grupa)) {
				ujetaGrupa = grupa;
				stanje = Stanje.win_black;
				return Stanje.win_black;
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (ujeta(grupa)) {
				ujetaGrupa = grupa;
				stanje = Stanje.win_white;
				return Stanje.win_white;
			}
		}
		
		if (prostaMesta().isEmpty()) {
			stanje = Stanje.draw;
			return Stanje.draw;
		}
		stanje = Stanje.in_progress;
		return Stanje.in_progress;
		
	}
	
	//public boolean konecIgre()
}







	   

	
	
	
	
	
