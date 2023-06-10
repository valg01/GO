package logika;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Poteza;

public class Igra {
	
	public static int velikost = 9;
	
	public int stevec = 0;
	
	public Polje grid;
	
	public Stanje stanje;
	
	public Igralec igralecNaPotezi;
	
	public List<Koordinate> libertiesBeli = new ArrayList<Koordinate>();
	public List<Koordinate> libertiesCrni = new ArrayList<Koordinate>();
	
	public List<Koordinate> ogrozeneBele;
	public List<Koordinate> ogrozeneCrne;
	
	public List<List<Koordinate>> beleGrupe;
	public List<List<Koordinate>> crneGrupe;
	
	public List<List<Koordinate>> ujeteBele = new ArrayList<List<Koordinate>>();
	public List<List<Koordinate>> ujeteCrne = new ArrayList<List<Koordinate>>();
	
	public int stUjetihBelih;
	public int stUjetihCrnih;
	
	public int stBelihNaPlosci;
	public int stCrnihNaPlosci;
	
	public Koordinate zadnjaPoteza;
	
	public Igra() {
		grid = new Polje(velikost);
		for (int i = 0; i < velikost; i ++) {
			for (int j = 0; j < velikost; j ++) {
				grid.mreza[i][j] = null;
			}
		}
		igralecNaPotezi = Igralec.BLACK;
		stanje = Stanje.in_progress;
		
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
		if (poteza.pass()==true || grid.mreza[poteza.x()][poteza.y()] == null) return true;
		return false;
	}
	
	
	public void zamenjajIgralca() {
		if(igralecNaPotezi == Igralec.BLACK) {
			igralecNaPotezi = Igralec.WHITE;
		}
		
		else if(igralecNaPotezi == Igralec.WHITE){
			igralecNaPotezi = Igralec.BLACK;
		}
	}
	
	public boolean odigraj(Poteza p) {
		
		
		
		
		if (!jeVeljavna(p) || !(stanje == Stanje.in_progress || stanje == null)) return false; //pogleda če lahk odigra
		
		if (p.pass()) {
			zamenjajIgralca();
			return true;
		}
		
		int x = p.x();
		int y = p.y();
		if (igralecNaPotezi == Igralec.BLACK && grid.mreza[x][y] == null) {
			grid.mreza[x][y] = Zeton.BLACK;
			igralecNaPotezi = Igralec.WHITE;
			stCrnihNaPlosci += 1;
		}
		else if ( igralecNaPotezi == Igralec.WHITE && grid.mreza[x][y] == null) {
			grid.mreza[x][y] = Zeton.WHITE;
			igralecNaPotezi = Igralec.BLACK;
			stBelihNaPlosci += 1;
		}
		
		updateGrupe();
		updateUjete();
		updateLiberties();
		//System.out.print(zmagovalec());
		stevec++;
		//System.out.println(stevec);
		
		printInfo();
		return true;
	}

	
	

	
	public List<Koordinate> najdiSosede(Koordinate koord) {
        int x = koord.getX();
        int y = koord.getY();
        Zeton zeton = grid.mreza[x][y];
        List<Koordinate> sosedi = new ArrayList<>();
        //sosedi.add(koord);
        if (velikost - x - 1 > 0 && grid.mreza[x + 1][y] == zeton) {
            sosedi.add(koord.desna());
            //System.out.println("v sosedih");
            //System.out.println(x);
            //System.out.println(y);
            //System.out.println(zeton);
            //System.out.println(grid.mreza[x + 1][y]);
            //System.out.println();
        }
        if (x != 0 && grid.mreza[x - 1][y] == zeton) {
            sosedi.add(koord.leva());
        }
        if (velikost - y -1> 0 && grid.mreza[x][y + 1] == zeton) {
            sosedi.add(koord.spodnja());
        }
        if (y != 0 && grid.mreza[x][y - 1] == zeton) {
            sosedi.add(koord.zgornja());
        }
        //System.out.print(sosedi);
        return sosedi;
    }
	public boolean imaLiberties(Koordinate koord) {
        int x = koord.getX();
        int y = koord.getY();

        if (velikost - x -1 > 0 && grid.mreza[x + 1][y] == null) {
            return true;
        }
        if (x != 0 && grid.mreza[x - 1][y] == null) {
        	return true;
        }
        if (velikost - y -1 > 0 && grid.mreza[x][y + 1] == null) {
        	return true;
        }
        if (y != 0 && grid.mreza[x][y - 1] == null) {
        	return true;
        }
        
        return false;
    }
	
	public List<Koordinate> libertiesGrupa(List<Koordinate> grupa) {
		List<Koordinate> liberties = new ArrayList<>();
		for (Koordinate koord : grupa) {
			int x = koord.getX();
	        int y = koord.getY();
	        
	        

	        if (velikost - x -1 > 0 && grid.mreza[x + 1][y] == null && !liberties.contains(koord.desna()) ) {
	            liberties.add(koord.desna());
	        }
	        if (x != 0 && grid.mreza[x - 1][y] == null && !liberties.contains(koord.leva())) {
	        	liberties.add(koord.leva());
	        }
	        if (velikost - y -1 > 0 && grid.mreza[x][y + 1] == null && !liberties.contains(koord.spodnja())) {
	        	liberties.add(koord.spodnja());
	        }
	        if (y != 0 && grid.mreza[x][y - 1] == null && !liberties.contains(koord.zgornja())) {
	        	liberties.add(koord.zgornja());
	        }
	        
	        
		}

		
		return liberties;
	}
	
	
	
	public List<Koordinate> libertiesIgralec(Igralec igralec) {
		List<Koordinate> vsiLiberties = new ArrayList<>();
		if (igralec == Igralec.BLACK && crneGrupe != null) {
			for (List<Koordinate> grupa : crneGrupe) {
				for (Koordinate liberti : libertiesGrupa(grupa)) {
					if (!vsiLiberties.contains(liberti)) {
						vsiLiberties.add(liberti);
					}
				}
			}
		}
		
	
		if (igralec == Igralec.WHITE && beleGrupe != null) {
			for (List<Koordinate> grupa : beleGrupe) {
				for (Koordinate liberti : grupa) {
					if (!vsiLiberties.contains(liberti)) {
						vsiLiberties.add(liberti);
					}
				}
			}
		}
		return vsiLiberties;
	}
	
	public void updateLiberties() {
		libertiesBeli = libertiesIgralec(Igralec.WHITE);
		libertiesCrni = libertiesIgralec(Igralec.BLACK);
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
	    
	    ogrozeneBele = new ArrayList<>();
	    ogrozeneCrne = new ArrayList<>();

	    Set<HashSet<Koordinate>> beleSet = new HashSet<>();
	    Set<HashSet<Koordinate>> crneSet = new HashSet<>();
	    
	    //boolean naselOgrozenoBelo = false;
	    //boolean naselOgrozenoCrno = false;
	    
	    
	    		
	    
	    for (int i = 0; i < velikost; i++) {
	        for (int j = 0; j < velikost; j++) {
	            Zeton zeton = grid.mreza[i][j];
	            List<Koordinate> grup = grupa(new Koordinate(i, j));
	            HashSet<Koordinate> grupSet = new HashSet<>(grup);
	            
	            
	            List<Koordinate> libertiesGrupa = libertiesGrupa(grup);
	            
	            if (zeton == zeton.BLACK && !crneSet.contains(grupSet)) {
	                crneGrupe.add(grup);
	                crneSet.add(grupSet);
	                if (libertiesGrupa.size()==1) {
	                	ogrozeneCrne.add(libertiesGrupa.get(0));
	                	//ogrozenaCrna = libertiesGrupa.get(0);
	                
	     
	                }
	            
	                
	            } else if (zeton == zeton.WHITE && !beleSet.contains(grupSet)) {
	                beleGrupe.add(grup);
	                beleSet.add(grupSet);
	                if (libertiesGrupa.size()==1) {
	                	ogrozeneBele.add(libertiesGrupa.get(0));
	                }
	            }
	            
	        }
	    }
	    //if (!naselOgrozenoBelo) {
	    //	ogrozeneBele = null;
	    //}
	    //if (!naselOgrozenoCrno) {
	    //	ogrozeneCrne = null;
	    //}
	    
		
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
				ujeteBele.add(grupa);
				stanje = Stanje.win_black;
				return Stanje.win_black;
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (ujeta(grupa)) {
				ujeteCrne.add(grupa);
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
	
	public Stanje updateUjete() {
		for (List<Koordinate> grupa : beleGrupe) {
			if (ujeta(grupa)) {
				ujeteBele.add(grupa);
				for (Koordinate koord : grupa) {
					grid.dodajZetonKoord(null, koord);
					stUjetihBelih += 1;
					stBelihNaPlosci -= 1;
				}
				updateGrupe();
				
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (ujeta(grupa)) {
				ujeteCrne.add(grupa);
				for (Koordinate koord : grupa) {
					grid.dodajZetonKoord(null, koord);
					stUjetihCrnih += 1;
					stCrnihNaPlosci -= 1;
				}
				updateGrupe();
				
			}
		}
		
		if (prostaMesta().isEmpty()) {
			stanje = Stanje.draw;
			return Stanje.draw;
		}
		stanje = Stanje.in_progress;
		return Stanje.in_progress;
		
	}
	
	public void nakljucnaPoteza() {
		int max = prostaMesta().size();
		int i = (int)Math.floor(Math.random() * (max + 1));  //generira naključni indeks 
		Koordinate koor = prostaMesta().get(i);
		Poteza p = new Poteza(koor.getX(), koor.getY());
		odigraj(p);
	}
	
	//public boolean konecIgre()
	
	public List<Koordinate> najboljVerjetne(){
		ArrayList<Koordinate> merge = new ArrayList<Koordinate>();
		merge.addAll(libertiesIgralec(Igralec.BLACK));
        merge.addAll(libertiesIgralec(Igralec.WHITE));
        
        return merge;
	}
	
	public int stLibertiesIgralec(Igralec igralec) {
		return libertiesIgralec(igralec).size();
	}
	
	public void printInfo() {
	    System.out.println("----- Information -----");
	    
	    System.out.println("Liberties of Beli:");
	    for (Koordinate k : libertiesBeli) {
	        System.out.println(k);
	    }
	    
	    System.out.println("Liberties of Crni:");
	    for (Koordinate k : libertiesCrni) {
	        System.out.println(k);
	    }
	    
	    System.out.println("Ogrozene Bele:");
	    for (Koordinate k : ogrozeneBele) {
	        System.out.println(k);
	    }
	    
	    System.out.println("Ogrozene Crne:");
	    for (Koordinate k : ogrozeneCrne) {
	        System.out.println(k);
	    }
	    
	    System.out.println("Bele Grupe:");
	    for (List<Koordinate> list : beleGrupe) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Crne Grupe:");
	    for (List<Koordinate> list : crneGrupe) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Ujete Bele:");
	    for (List<Koordinate> list : ujeteBele) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Ujete Crne:");
	    for (List<Koordinate> list : ujeteCrne) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Number of captured Belih: " + stUjetihBelih);
	    System.out.println("Number of captured Crnih: " + stUjetihCrnih);
	    System.out.println("Number of Belih on board: " + stBelihNaPlosci);
	    System.out.println("Number of Crnih on board: " + stCrnihNaPlosci);
	    
	    System.out.println("-----------------------");
	}

}







	   

	
	
	
	
	
