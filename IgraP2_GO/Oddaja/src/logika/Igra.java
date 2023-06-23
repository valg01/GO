package logika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import splosno.Poteza;

public class Igra {
	
public static int velikost = 19;
    
	public Igra igraPredZadnjoPotezo;
    public int stevec;
    public Polje grid;
    public Stanje stanje;
    public Igralec igralecNaPotezi;
    public List<Koordinate> libertiesBeli;
    public List<Koordinate> libertiesCrni;
    public List<Koordinate> ogrozeneBele;
    public List<Koordinate> ogrozeneCrne;
    public List<List<Koordinate>> beleGrupe;
    public List<List<Koordinate>> crneGrupe;
    public List<List<Koordinate>> nullGrupe;
    public List<List<Koordinate>> ujeteBele;
    public List<List<Koordinate>> ujeteCrne;
    public int stUjetihBelihZetonov;
    public int stUjetihCrnihZetonov;
    public int stBelihNaPlosci;
    public int stCrnihNaPlosci;
    public int vrednostPozicijeBeli;
    public Poteza zadnjaPoteza;
    public Poteza predzadnjaPoteza;
    public Stack<Igra> gameHistory;
    
	public List<List<Koordinate>> zascitene;

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 1. KONSTRUKTOR IN OSNOVNE FUNKCIJE IGRE
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public Igra() {
        stevec = 0;
        grid = new Polje(velikost);
        for (int i = 0; i < velikost; i ++) {
            for (int j = 0; j < velikost; j ++) {
                grid.mreza[i][j] = null;
            }
        }
        stanje = Stanje.in_progress;
        igralecNaPotezi = Igralec.BLACK;

        libertiesBeli = new ArrayList<>();
        libertiesCrni = new ArrayList<>();
        ogrozeneBele = new ArrayList<>();
        ogrozeneCrne = new ArrayList<>();
        beleGrupe = new ArrayList<>();
        crneGrupe = new ArrayList<>();
        nullGrupe = new ArrayList<>();
        ujeteBele = new ArrayList<>();
        ujeteCrne = new ArrayList<>();

        stUjetihBelihZetonov = 0;
        stUjetihCrnihZetonov = 0;
        stBelihNaPlosci = 0;
        stCrnihNaPlosci = 0;
        vrednostPozicijeBeli = stBelihNaPlosci;
        zadnjaPoteza = null;
        predzadnjaPoteza = null;
        zascitene = new ArrayList<>();
        gameHistory = new Stack<Igra>();
     
        
        
    }

    public Igra(Igra original) { //copy constructor, ki ga potrebujemo za suicide move
        stevec = original.stevec;
        grid = new Polje(original.grid); //Assuming Polje has a copy constructor
        stanje = original.stanje;
        igralecNaPotezi = original.igralecNaPotezi;

        libertiesBeli = new ArrayList<>(original.libertiesBeli);
        libertiesCrni = new ArrayList<>(original.libertiesCrni);
        ogrozeneBele = new ArrayList<>(original.ogrozeneBele);
        ogrozeneCrne = new ArrayList<>(original.ogrozeneCrne);
        beleGrupe = new ArrayList<>(original.beleGrupe);
        crneGrupe = new ArrayList<>(original.crneGrupe);
        nullGrupe = new ArrayList<>(original.nullGrupe);
        ujeteBele = new ArrayList<>(original.ujeteBele);
        ujeteCrne = new ArrayList<>(original.ujeteCrne);

        stUjetihBelihZetonov = original.stUjetihBelihZetonov;
        stUjetihCrnihZetonov = original.stUjetihCrnihZetonov;
        stBelihNaPlosci = original.stBelihNaPlosci;
        stCrnihNaPlosci = original.stCrnihNaPlosci;
        vrednostPozicijeBeli = original.vrednostPozicijeBeli;
        zadnjaPoteza = original.zadnjaPoteza; // Assuming Poteza is immutable or has a copy constructor
        predzadnjaPoteza = original.predzadnjaPoteza; // Assuming Poteza is immutable or has a copy constructor
        zascitene = original.zascitene;
        gameHistory = original.gameHistory;
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
				if (grid.mreza[i][j] == null && jeVeljavna(new Poteza(i,j))) {
					ps.add(new Koordinate(i, j));
				}
			}
		}
		return ps;
	}
	
	public boolean jeVeljavna(Poteza poteza) { //pogleda, če je ta ko ga igraš null, če ne nemorš odigrat
		if (poteza.pass()==true) return true;
		if (!poteza.pass() && poteza.equals(predzadnjaPoteza)) return false; // KO RULE 
		if (poteza.equals(predzadnjaPoteza)) return false;
		if (jeSuicideMove(poteza)) return false;
		if (grid.mreza[poteza.x()][poteza.y()] == null) return true;
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
		
		gameHistory.push(new Igra(this));
		
		predzadnjaPoteza = zadnjaPoteza;
		zadnjaPoteza = p;
		
		if (p.pass()) {
			zamenjajIgralca();
			
			
			
		} else {
			Boolean jeDovoljenSsm = jeDovoljenSuicideMove(p);
			
			
			
			int x = p.x(); 
			int y = p.y();
			
			grid.mreza[x][y] = igralecNaPotezi.getZeton();
			
			if (jeDovoljenSsm) { //posebej obravnavan suicide move, ki je mogoč samo v primeru če capturaš nasprotnik
				zascitene.add(grupa(p.getKoordinate()));
			} 
			
			zamenjajIgralca();
			
			
			
			//if (igralecNaPotezi == Igralec.BLACK && grid.mreza[x][y] == null) {
			//	grid.mreza[x][y] = Zeton.BLACK;
			//	igralecNaPotezi = Igralec.WHITE;
			//	stCrnihNaPlosci += 1;
			//}
			//else if ( igralecNaPotezi == Igralec.WHITE && grid.mreza[x][y] == null) {
			//	grid.mreza[x][y] = Zeton.WHITE;
			//	igralecNaPotezi = Igralec.BLACK;
			//	stBelihNaPlosci += 1;
			//}
			
			updateGrupe();
			updateNullGrupe(); //to bo nakoncu sam ob koncu igre
			updateUjete();
			updateLiberties();
			
			if (jeDovoljenSsm) {
				zascitene.remove(grupa(p.getKoordinate())); //ko se odigra ni več zaščitena
			}
		}
		
		updateStanje();
		stevec++;
		//printInfo();
		return true;
	}

	public boolean OdigrajVKopiji(Poteza p) {
		//if (!jeVeljavna(p) || !(stanje == Stanje.in_progress || stanje == null)) return false; //pogleda če lahk odigra
		
		predzadnjaPoteza = zadnjaPoteza;
		zadnjaPoteza = p;
		
		if (p.pass()) {
			zamenjajIgralca();
		
		} else {
		
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
		}
		
		//updateGrupe();
		updateNullGrupe(); //to bo nakoncu sam ob koncu igre
		updateUjeteBrezOdstranjevanja(p.getKoordinate());
		updateGrupe();
		updateLiberties();
		//updateStanje(); //stanje odstranjuje grupe kar tu nočemo, zato ga zakomentiramo
		stevec++;
		
		printInfo();
		System.out.println("-------To smo bli v kopiji--------");
		return true;
	}


		
		
		
		
		
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2. GRUPIRANJE IN UJETOST
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
 	public List<Koordinate> najdiSosedeIsteBarve(Koordinate koord) {
        int x = koord.getX();
        int y = koord.getY();
        Zeton zeton = grid.mreza[x][y];
        List<Koordinate> sosedi = new ArrayList<>();
        //sosedi.add(koord);
        if (velikost - x - 1 > 0 && grid.mreza[x + 1][y] == zeton) {
            sosedi.add(koord.desna());
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
	
	public boolean jeDovoljenSuicideMove(Poteza p) {
		if (imaLiberties(p.getKoordinate())) return false;
		
		if (p.pass()) return false;
		
	    Igra copyIgra = new Igra(this);
	    
	    

	    Igralec igralec = igralecNaPotezi;

	    

	    int stUjetihPrej = copyIgra.getUjete(igralec).size();   
	    List<List<Koordinate>> ujetePrejNasprotnik = copyIgra.getUjete(igralec.nasprotnik());
	    int stUjetihPrejNasprotnik = ujetePrejNasprotnik.size();

	    copyIgra.OdigrajVKopiji(p);

	    int stUjetihPotem = copyIgra.getUjete(igralec).size();
	    copyIgra.getUjete(igralec.nasprotnik());
	    int stUjetihPotemNasprotnik = copyIgra.getUjete(igralec.nasprotnik()).size();

	    //System.out.println("Number of captured before move for current player: " + stUjetihPrej);
	    //System.out.println("Number of captured before move for opponent: " + stUjetihPrejNasprotnik);
	    //System.out.println("Number of captured after move for current player: " + stUjetihPotem);
	    //System.out.println("Number of captured after move for opponent: " + stUjetihPotemNasprotnik);
	    //System.out.println();

	    if (stUjetihPotem > stUjetihPrej && stUjetihPotemNasprotnik > stUjetihPrejNasprotnik) {
	        // Create a copy of ujetePotemNasprotnik and remove elements from it that are in ujetePrejNasprotnik
	    	//System.out.println(igralec);
	    	//System.out.println(ujetePrejNasprotnik);
	    	//System.out.println(ujetePotemNasprotnik);
	    	
	        //List<List<Koordinate>> copyUjetePotemNasprotnik = new ArrayList<>(ujetePotemNasprotnik);
	        //copyUjetePotemNasprotnik.removeAll(ujetePrejNasprotnik);
	        
	        // Now, copyUjetePotemNasprotnik contains the extra captured group of the opponent
	        //List<Koordinate> novaUjeta = copyUjetePotemNasprotnik.get(0);  // get the remaining element
	        //ujetaSSuecidom = novaUjeta;
	        
	        
	        return true;
	    }
	    return false;
	}
	public boolean jeSuicideMove(Poteza p) {
		
		if (imaLiberties(p.getKoordinate())) return false;
		Igra copyIgra = new Igra(this);

	    Igralec igralec = igralecNaPotezi;

	    if (p.pass()) return false;

	    int stUjetihPrej = copyIgra.getUjete(igralec).size();   
	    List<List<Koordinate>> ujetePrejNasprotnik = copyIgra.getUjete(igralec.nasprotnik());
	    int stUjetihPrejNasprotnik = ujetePrejNasprotnik.size();

	    copyIgra.OdigrajVKopiji(p);

	    int stUjetihPotem = copyIgra.getUjete(igralec).size();
	    List<List<Koordinate>> ujetePotemNasprotnik = copyIgra.getUjete(igralec.nasprotnik());
	    int stUjetihPotemNasprotnik = ujetePotemNasprotnik.size();


	    if (stUjetihPotem > stUjetihPrej && stUjetihPotemNasprotnik <= stUjetihPrejNasprotnik) {
	        
	        return true;
	    }
	    return false;
	}
	
	public Zeton barvaGrupe(List<Koordinate> grupa) {
		Koordinate koord = grupa.get(0);
		return grid.naMestuKoord(koord);
	}
	
	public void odstraniGrupo(List<Koordinate> grupa) {
		for (Koordinate koord : grupa) {
			grid.dodajZetonKoord(null, koord);
		}
		updateVse();
	}
	
 	public List<Koordinate> grupa(Koordinate koord) {
	    List<Koordinate> grupa = new ArrayList<>();
	    grupa.add(koord);
	    return grupaAux(koord, grupa);
	}

	private List<Koordinate> grupaAux(Koordinate koord, List<Koordinate> grupa) {
	    List<Koordinate> sosedi = najdiSosedeIsteBarve(koord);

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
	    
	    stBelihNaPlosci = 0;
	    stCrnihNaPlosci = 0;

	    Set<HashSet<Koordinate>> beleSet = new HashSet<>();
	    Set<HashSet<Koordinate>> crneSet = new HashSet<>();
	  
	    for (int i = 0; i < velikost; i++) {
	        for (int j = 0; j < velikost; j++) {
	            Zeton zeton = grid.mreza[i][j];
	            List<Koordinate> grup = grupa(new Koordinate(i, j));
	            HashSet<Koordinate> grupSet = new HashSet<>(grup);
	            List<Koordinate> libertiesGrupa = libertiesGrupa(grup);
	            
	            if (zeton == Zeton.BLACK && !crneSet.contains(grupSet)) {
	            	stCrnihNaPlosci += 1;
	                crneGrupe.add(grup);
	                crneSet.add(grupSet);
	                if (libertiesGrupa.size()==1) {
	                    ogrozeneCrne.add(libertiesGrupa.get(0));
	                }
	            } else if (zeton == Zeton.WHITE && !beleSet.contains(grupSet)) {
	            	stBelihNaPlosci += 1;
	                beleGrupe.add(grup);
	                beleSet.add(grupSet);
	                if (libertiesGrupa.size()==1) {
	                    ogrozeneBele.add(libertiesGrupa.get(0));
	                }
	            }
	        }
	    }
	}
	
	
	public boolean staSosednjiGrupi(List<Koordinate> g1, List<Koordinate> g2) {
		for (Koordinate koord : g1) {
			for (Koordinate sosed : koord.sosedi()) {
				if (g2.contains(sosed)) return true;
			}
		}
		return false;
	}
	
	
	public boolean imaDveUcki(List<Koordinate> grupa) {
		Koordinate koord = grupa.get(0);
		Igralec igralec = grid.mreza[koord.getX()][koord.getY()].getIgralec();
		
		int stVsebovanihNullGrup = 0;
		for (List<Koordinate> ngrup : nullGrupe) {
			if (lastnikNullGrupe(ngrup) == igralec && staSosednjiGrupi(ngrup, grupa)) {
				stVsebovanihNullGrup += 1;
			}
		}
		if (stVsebovanihNullGrup >= 2) return true;
		return false;
	}
	
	public boolean ujeta(List<Koordinate> grupa) {
		for (Koordinate koord : grupa) {
			if (imaLiberties(koord)) return false;
			//if (imaDveUcki(grupa)) return false;
		}
		return true;
	}
	
	
	public void updateUjete() {
		for (List<Koordinate> grupa : beleGrupe) {
			if (zascitene.contains(grupa)) {
				continue;
			}
			if (ujeta(grupa)) {
				ujeteBele.add(grupa);
				
				for (Koordinate koord : grupa) {
					
					grid.dodajZetonKoord(null, koord);
					stUjetihBelihZetonov += 1;
					stBelihNaPlosci -= 1;
				}
				updateGrupe();
				
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (zascitene.contains(grupa)) continue;
			if (ujeta(grupa)) {
				ujeteCrne.add(grupa);	
				for (Koordinate koord : grupa) {
					grid.dodajZetonKoord(null, koord);
					stUjetihCrnihZetonov += 1;
					stCrnihNaPlosci -= 1;
				}
				updateGrupe();
				
			}
		}
	
		
		
	}
	
	public void updateUjeteBrezOdstranjevanja(Koordinate koord) {
		if (koord == null) return;
		
		List<Koordinate> zascitena = grupa(koord);
		
		for (List<Koordinate> grupa : beleGrupe) {
			if (ujeta(grupa)) {
				if (grupa != zascitena) {
					ujeteBele.add(grupa);
				}
				//updateGrupe();
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (ujeta(grupa)) {
				if (grupa != zascitena) {
					ujeteCrne.add(grupa);
				}
				//updateGrupe();
			}
		}
	}
	
	
	public void updateVse() {
		updateGrupe();
		updateNullGrupe();
		updateUjete();
		updateLiberties();
	}
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3. VREDNOTENJE POZICIJE NA KONCU IN STANJE IGRE //
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public HashSet<Zeton> barveSosedov(Koordinate koord){ //se aplicira na nullGrupo, da ugotovi kdo jo kontrolira
		
		int x = koord.getX();
		int y = koord.getY();
		HashSet<Zeton> colorSet = new HashSet<>();

		// Check the neighbor to the right
		if (x + 1 < 9 && (grid.mreza[x + 1][y] == Zeton.BLACK || grid.mreza[x + 1][y] == Zeton.WHITE)) {
		    colorSet.add(grid.mreza[x + 1][y]);
		}

		// Check the neighbor to the left
		if (x - 1 >= 0 && (grid.mreza[x - 1][y] == Zeton.BLACK || grid.mreza[x - 1][y] == Zeton.WHITE)) {
		    colorSet.add(grid.mreza[x - 1][y]);
		}

		// Check the neighbor below
		if (y + 1 < 9 && (grid.mreza[x][y + 1] == Zeton.BLACK || grid.mreza[x][y + 1] == Zeton.WHITE)) {
		    colorSet.add(grid.mreza[x][y + 1]);
		}

		// Check the neighbor above
		if (y - 1 >= 0 && (grid.mreza[x][y - 1] == Zeton.BLACK || grid.mreza[x][y - 1] == Zeton.WHITE)) {
		    colorSet.add(grid.mreza[x][y - 1]);
		}

		return colorSet;
	}
	
	
	
	public List<List<Koordinate>> sosednjeNullGrupe(List<Koordinate> grupa){
		List<List<Koordinate>> sosednje = new ArrayList<List<Koordinate>>();
		for (List<Koordinate> ngrup : nullGrupe) {
			if (staSosednjiGrupi(ngrup, grupa)) sosednje.add(ngrup);
		}
		return sosednje;
	}
	
	public List<Koordinate> praznoOkoli(List<Koordinate> grupa){
		List<Koordinate> praznoOkol = new ArrayList<Koordinate>();
		List<List<Koordinate>> sosednje = sosednjeNullGrupe(grupa);
		for (List<Koordinate> sosednja : sosednje) {
			praznoOkol.addAll(sosednja);
		}
		return praznoOkol;
	}
	
	public boolean jeObkoljenaZNasprotnikom1(List<Koordinate> grupa) {
		Igra copyIgra = new Igra(this);
		
		Igralec igralec = barvaGrupe(grupa).getIgralec();
		for (Koordinate koord : praznoOkoli(grupa)) {
			//igralecNaPotezi = igralec;
			//OdigrajVKopiji(koord.getPoteza());
			copyIgra.igralecNaPotezi = igralec;
			copyIgra.OdigrajVKopiji(koord.getPoteza());
		}
		if (copyIgra.ujeta(grupa)) return true;
		return false;
		
		
		
		
	}
	
	//public boolean jeObkoljenaZNasprotnikom2(List<Koordinate> grupa) {
	//	Igralec igralec = barvaGrupe(grupa).getIgralec();
	//	for (List<Koordinate> grupa : sosednjeNullGrupe(grupa)) {
	//		if (lastnikNullGrupe(grupa) != igralec) {
	//			
	//		}
	//	}
	//	
	//}
	
	public boolean jeObkoljenaZNasprotnikom3(List<Koordinate> grupa) {
		Igralec igralec = barvaGrupe(grupa).getIgralec();
		Igra copyIgra = new Igra(this);
		copyIgra.odstraniGrupo(grupa);
		
		List<Koordinate> novaNullGrupa = new ArrayList<Koordinate>();
		for (List<Koordinate> ngrup : copyIgra.nullGrupe) {
			if (ngrup.containsAll(grupa)) {
				novaNullGrupa = ngrup;
				//System.out.print("evonas");
			}
		}
		
	
		if (copyIgra.lastnikNullGrupe(novaNullGrupa) == igralec.nasprotnik())return true;
		return false;
		
		
	}
	
	public boolean jeObkoljenaZNasprotnikom(List<Koordinate> grupa) {
		Igralec igralec = barvaGrupe(grupa).getIgralec();
		Igra copyIgra = new Igra(this);
		
		if (praznoOkoli(grupa).size() > 20) return false;
		
		//1. Odstranimo grupo iz mreže
		copyIgra.odstraniGrupo(grupa);
		
		
		// 2. poiščemo novonastalo nullGrupo, ki vsebuje koordinate odstranjenne 
		List<Koordinate> novaNullGrupa = new ArrayList<Koordinate>();
		for (List<Koordinate> ngrup : copyIgra.nullGrupe) {
			if (ngrup.containsAll(grupa)) {
				novaNullGrupa = ngrup;
				break;
			}
		}
		
		// 3. odstranimo vse grupe iz tega omejenega območja in jih damo v seznam
		List<List<Koordinate>> grupeVIstemObmocju = new ArrayList<List<Koordinate>>();
		
		
		for (List<Koordinate> grup : copyIgra.getGrupe(igralec)) {
			if (staSosednjiGrupi(novaNullGrupa, grup)) {
				copyIgra.odstraniGrupo(grup);
				grupeVIstemObmocju.add(grup);
			}
		}
		
		
		// 4. najdemo novo null grupo po vseh odstranjenih in pogledamo kdo je lastnik 
		List<Koordinate> novaNullGrupaPoVsehOdstranjenih = new ArrayList<Koordinate>();
		for (List<Koordinate> ngrup : copyIgra.nullGrupe) {
			if (ngrup.containsAll(grupa)) {
				novaNullGrupaPoVsehOdstranjenih = ngrup;
				break;
			}
		}
		
		//if (novaNullGrupaPoVsehOdstranjenih.size() > 10) return false;
		
		
		

		
		if (copyIgra.lastnikNullGrupe(novaNullGrupaPoVsehOdstranjenih) == igralec.nasprotnik())return true;
		return false;
		
		
		
	}
	
	//public List<List<Koordinate>> vseGrupeVIstemObmocju(List<Koordinate> grupa){
		
	//}
	
 	public boolean lahkoImaDveUcki(List<Koordinate> grupa) {
		// 1.
		if (libertiesGrupa(grupa).size() < 2) return false;
		
		sosednjeNullGrupe(grupa);
		
		if (praznoOkoli(grupa).size() > 10) return true;
		//if (maxVelikost < 2) return false;
		
		// 3. 
		
		
		//if (maxVelikost > 10) return true; //to ni nujno res ampak je poenostavitev, ker čene preveč časovno zahtevno, pokrije večino primerov
		
		// 5. v najslabšem primeru pogledamo vse možnosti
		return false;
		
		
	}
	
	public void updateNullGrupe() {
	    nullGrupe = new ArrayList<>();
	    Set<HashSet<Koordinate>> nullSet = new HashSet<>();

	    for (int i = 0; i < velikost; i++) {
	        for (int j = 0; j < velikost; j++) {
	            Zeton zeton = grid.mreza[i][j];
	            List<Koordinate> grup = grupa(new Koordinate(i, j));
	            HashSet<Koordinate> grupSet = new HashSet<>(grup);

	            if (zeton == null && !nullSet.contains(grupSet)) {
	                nullGrupe.add(grup);
	                nullSet.add(grupSet);
	            }
	        }
	    }
	}
	
	public Igralec lastnikNullGrupe(List<Koordinate> grupa) { //kdo kontrolira teritorij, tudi to se preveri samo na koncu
		HashSet<Zeton> barve = new HashSet<>();
		for (Koordinate koord : grupa) {
			barve.addAll(barveSosedov(koord));
		}
		
		if (barve.size() == 1) {
			return barve.iterator().next().getIgralec(); //pobere prvi (edini element iz barve, v primeru da je ta samo ena tj ena barva kontrolira nnullGrupo)
		} else return null;
		
	}

	public void odstraniHopelessGroups() {
		for (List<Koordinate> grupa : beleGrupe) {
			if (jeObkoljenaZNasprotnikom(grupa)  && !lahkoImaDveUcki(grupa)) { // && !lahkoImaDveUcki(grupa)
				odstraniGrupo(grupa);
				
			}
		}
		for (List<Koordinate> grupa : crneGrupe) {
			if (jeObkoljenaZNasprotnikom(grupa) && !lahkoImaDveUcki(grupa)) { //
				odstraniGrupo(grupa);
				
			}
		}
	}

	public int vrednostPozicije(Igralec igralec) { //vrednost pozicije je dejanski score count, ni enako kot ocena pozicije ki upošteva še ugodnost postavitve in ostalo
		int vrednost = 0;
		
		//koliko praznega teritorija obvladuje
		for (List<Koordinate> grupa : nullGrupe) {
			if (lastnikNullGrupe(grupa) == igralec) {
				vrednost += grupa.size();
			}
		}	
		
		//koliko ima žetonov
		if (igralec == Igralec.WHITE) {
			vrednost += stBelihNaPlosci;
		} else {
			vrednost += stCrnihNaPlosci;
		}
	
		return vrednost;
	}
	

	public void updateStanje() {
		
		//1. igra se konča
		if (zadnjaPoteza != null && predzadnjaPoteza != null && zadnjaPoteza.pass() && predzadnjaPoteza.pass() || prostaMesta().isEmpty()) {
			
			odstraniHopelessGroups();
			
			int beli = vrednostPozicije(Igralec.WHITE);
			int crni = vrednostPozicije(Igralec.BLACK);
			
			if (beli > crni) stanje = Stanje.win_white;
			else if (beli < crni) stanje = Stanje.win_black;
			else if (beli == crni) stanje = Stanje.draw;
		} else { //2. igra se nekonča
			stanje = Stanje.in_progress;
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 4. FUNKCIJE ZA INTELIGENCO
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
				for (Koordinate liberti : libertiesGrupa(grupa)) {
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
	
	public void nakljucnaPoteza() {
		int max = prostaMesta().size();
		int i = (int)Math.floor(Math.random() * (max + 1));  //generira naključni indeks 
		Koordinate koor = prostaMesta().get(i);
		Poteza p = new Poteza(koor.getX(), koor.getY());
		odigraj(p);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public List<Koordinate> najboljVerjetne(){
		ArrayList<Koordinate> merge = new ArrayList<Koordinate>();
		merge.addAll(libertiesBeli);
        merge.addAll(libertiesCrni);
        merge.removeAll(ujeteCrne);
        merge.removeAll(ujeteBele);
        
        return merge;
	}
	
	public int stLibertiesIgralec(Igralec igralec) {
		return libertiesIgralec(igralec).size();
	}
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// 5. IZPISOVANJE INFORMACIJ IN POMOŽNE FUNKCIJE
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<List<Koordinate>> getGrupe(Igralec igralec){
		return (igralec == Igralec.WHITE ? beleGrupe : crneGrupe);
	}
	
	public List<List<Koordinate>> getUjete(Igralec igralec){
		return (igralec == Igralec.WHITE ? ujeteBele : ujeteCrne);
	}
	
	public static void permute(List<Koordinate> array, int i, List<List<Koordinate>> perms) {
        if (i == array.size() - 1) {
            perms.add(new ArrayList<>(array));
        } else {
            for (int j = i; j < array.size(); j++) {
                Collections.swap(array, i, j);
                permute(array, i + 1, perms);
                Collections.swap(array, i, j);
            }
        }
    }

    public static List<List<Koordinate>> permute(List<Koordinate> array) {
        List<List<Koordinate>> perms = new ArrayList<>();
        permute(array, 0, perms);
        return perms;
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
	        System.out.print(list);
	        //System.out.println("prazno okoli: " + praznoOkoli(list));
	        
	    }

	    
	    System.out.println("Crne Grupe:");
	    for (List<Koordinate> list : crneGrupe) {
	        System.out.println(list);
	        //System.out.println(", obkoljena z nasprotnikom: " + jeObkoljenaZNasprotnikom(list));
	    }
	    
	    
	    System.out.println("Null Grupe (velikosti, lastnik):");
	    
	    for (List<Koordinate> list : nullGrupe) {
	        System.out.print(list.size());
	        System.out.println(", " + lastnikNullGrupe(list));
	    }
	    
	    System.out.println("Ujete Bele:");
	    for (List<Koordinate> list : ujeteBele) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Ujete Crne:");
	    for (List<Koordinate> list : ujeteCrne) {
	        System.out.println(list);
	    }
	    
	    System.out.println("Zascitene:");
	    for (List<Koordinate> list : zascitene) {
	        System.out.println(list);
	    }
	    
	    
	    System.out.println("Number of captured Belih: " + stUjetihBelihZetonov);
	    System.out.println("Number of captured Crnih: " + stUjetihCrnihZetonov);
	    System.out.println("Number of Belih on board: " + stBelihNaPlosci);
	    System.out.println("Number of Crnih on board: " + stCrnihNaPlosci);
	    System.out.println("Stanje: " + stanje);
	    System.out.println("Predzadnja: " + predzadnjaPoteza);
	    System.out.println("Zadnja: " + zadnjaPoteza);
	    
	    //for (List<Koordinate> list : beleGrupe) {
	    //    if (imaDveUcki(list)) System.out.println(list + " ima dve ucki");
	    //}
	    
	    
	    System.out.println("-----------------------");
	}

}







	   

	
	
	
	
	
