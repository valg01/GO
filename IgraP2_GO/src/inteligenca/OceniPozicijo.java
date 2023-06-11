package inteligenca;

import java.util.List;
import java.util.ArrayList;

import logika.Igra;
import logika.Igralec;
import logika.Koordinate;
import logika.Zeton;

public class OceniPozicijo {
	
	Igra igra;
	static int ocena;
	
	public static int oceniPozicijo(Igra igra, Igralec igralec){
		
		int ocena = 0;
		
		//st zetonov na plosci
		int stBelih = igra.stBelihNaPlosci;
		int stCrnih = igra.stCrnihNaPlosci;
		
		//st ujetih zetonov
		
		
		
		//liberties count
		int libertiesCrni = igra.stLibertiesIgralec(igralec.BLACK);
		int libertiesBeli = igra.stLibertiesIgralec(igralec.WHITE);
		
		
		
		//iskanje najvecje grupe
		
		int najvecjaGrupaCrni = 0;
		int najvecjaGrupaBeli = 0;
		
		if (igra.crneGrupe != null) {
			for (List<Koordinate> grupa : igra.crneGrupe) {
				if (grupa.size() > najvecjaGrupaCrni) najvecjaGrupaCrni = grupa.size();
			}
		}
			
			
		if (igra.beleGrupe != null) {
			for (List<Koordinate> grupa : igra.beleGrupe) {
				if (grupa.size() > najvecjaGrupaBeli) najvecjaGrupaBeli = grupa.size();
			}
		}
		
		
		//st zetonov ob robu 
		int steviloCrnihNaRobu = 0;
		int steviloBelihNaRobu = 0;
		for (int i = 1; i < Igra.velikost - 1; i++ ) {
			if (igra.grid.mreza[i][1] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][1] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][2] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][2] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][6] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][6] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][7] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][7] == Zeton.WHITE) steviloBelihNaRobu++; 
		}
		for (int j = 1; j < Igra.velikost - 1; j++ ) {
			if (igra.grid.mreza[1][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[1][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[2][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[2][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[6][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[6][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[7][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[7][j] == Zeton.WHITE) steviloBelihNaRobu++; 
		}
		if (igralec == Igralec.BLACK && najvecjaGrupaCrni > najvecjaGrupaBeli ||igralec == Igralec.WHITE && najvecjaGrupaBeli > najvecjaGrupaCrni) { //ce je najvecja grupa vecja od najvecje grupe nasprotnika je ugodno
			ocena += Math.abs(najvecjaGrupaBeli - najvecjaGrupaCrni) * 2;
		}
		else if (igralec == Igralec.BLACK && najvecjaGrupaCrni < najvecjaGrupaBeli ||igralec == Igralec.WHITE && najvecjaGrupaBeli < najvecjaGrupaCrni) {
			ocena -= Math.abs(najvecjaGrupaBeli - najvecjaGrupaCrni) * 2;
		}
		if (igralec == Igralec.BLACK && libertiesCrni > libertiesBeli ||igralec == Igralec.WHITE && libertiesBeli > libertiesCrni) { //če ima manj liberties kot nasportnik je ugodna poteza
			ocena += Math.abs(libertiesBeli - libertiesCrni) * 6;
		}
		else if (igralec == Igralec.BLACK && libertiesCrni < libertiesBeli ||igralec == Igralec.WHITE && libertiesBeli < libertiesCrni) {
			ocena -= Math.abs(libertiesBeli - libertiesCrni) * 6;
		}
		if (igralec == Igralec.BLACK && steviloCrnihNaRobu > steviloBelihNaRobu ||igralec == Igralec.WHITE && steviloBelihNaRobu > steviloCrnihNaRobu) { //ugodno če ima več na robu kot sredini
			ocena += Math.abs(steviloCrnihNaRobu - steviloBelihNaRobu) * 1;
		}
		else if (igralec == Igralec.BLACK && steviloCrnihNaRobu < steviloBelihNaRobu ||igralec == Igralec.WHITE && steviloBelihNaRobu < steviloCrnihNaRobu) {
			ocena -= Math.abs(steviloCrnihNaRobu - steviloBelihNaRobu) * 1;
		}
		return ocena;
		
	}

	
	public static int oceniPozicijoStar(Igra igra, Igralec igralec){
		Igralec naPotezi = igra.igralecNaPotezi;
		if (igralec == Igralec.BLACK) {
			//if (igra.stanje == igra.stanje.win_black) return 1000;
			//if (igra.stanje == igra.stanje.win_white) return -1000;
			
			if (igra.igralecNaPotezi == Igralec.WHITE) {
				if (igra.ogrozeneBele == null && igra.ogrozeneCrne != null) return -1000;
				//if (igra.ogrozeneBele != null && igra.ogrozeneCrne == null) ocena += 200;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne != null) return -1000;
			}
			else if (igra.igralecNaPotezi == Igralec.BLACK) {
				//if (igra.ogrozeneBele == null && igra.ogrozeneCrne != null) ocena -= 200;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne == null) return 1000;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne != null) return 1000;
			}
			
			
		}
		if (igralec == Igralec.WHITE) {
			if (igra.stanje == igra.stanje.win_white) return 1000;
			if (igra.stanje == igra.stanje.win_black) return -1000;
			
			if (igra.igralecNaPotezi == Igralec.WHITE) {
				if (igra.ogrozeneBele == null && igra.ogrozeneCrne != null) return 1000;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne == null) return -200;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne != null) return 1000;
			}
			else if (igra.igralecNaPotezi == Igralec.BLACK) {
				if (igra.ogrozeneBele == null && igra.ogrozeneCrne != null) return -200;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne == null) return -1000;
				if (igra.ogrozeneBele != null && igra.ogrozeneCrne != null) return -1000;
			}
			
			
		}
		
	
		
		

		int ocena = 0;
		int najvecjaGrupaCrni = 0;
		int libertiesCrni = igra.stLibertiesIgralec(igralec.BLACK);
		//System.out.println();
		//System.out.println(libertiesCrni);
		//System.out.println();
		int libertiesBeli = igra.stLibertiesIgralec(igralec.WHITE);
		int najvecjaGrupaBeli = 0;
		if (igra.crneGrupe != null) {
			for (List<Koordinate> grupa : igra.crneGrupe) {
				if (grupa.size() > najvecjaGrupaCrni) najvecjaGrupaCrni = grupa.size();
			}
		}
			
			
		if (igra.beleGrupe != null) {
			for (List<Koordinate> grupa : igra.beleGrupe) {
				if (grupa.size() > najvecjaGrupaBeli) najvecjaGrupaBeli = grupa.size();
			}
		}
		int steviloCrnihNaRobu = 0;
		int steviloBelihNaRobu = 0;
		for (int i = 1; i < Igra.velikost - 1; i++ ) {
			if (igra.grid.mreza[i][1] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][1] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][2] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][2] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][6] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][6] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[i][7] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[i][7] == Zeton.WHITE) steviloBelihNaRobu++; 
		}
		for (int j = 1; j < Igra.velikost - 1; j++ ) {
			if (igra.grid.mreza[1][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[1][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[2][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[2][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[6][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[6][j] == Zeton.WHITE) steviloBelihNaRobu++;
			if (igra.grid.mreza[7][j] == Zeton.BLACK) steviloCrnihNaRobu++;
			else if(igra.grid.mreza[7][j] == Zeton.WHITE) steviloBelihNaRobu++; 
		}
		if (igralec == Igralec.BLACK && najvecjaGrupaCrni > najvecjaGrupaBeli ||igralec == Igralec.WHITE && najvecjaGrupaBeli > najvecjaGrupaCrni) { //ce je najvecja grupa vecja od najvecje grupe nasprotnika je ugodno
			ocena += Math.abs(najvecjaGrupaBeli - najvecjaGrupaCrni) * 2;
		}
		else if (igralec == Igralec.BLACK && najvecjaGrupaCrni < najvecjaGrupaBeli ||igralec == Igralec.WHITE && najvecjaGrupaBeli < najvecjaGrupaCrni) {
			ocena -= Math.abs(najvecjaGrupaBeli - najvecjaGrupaCrni) * 2;
		}
		if (igralec == Igralec.BLACK && libertiesCrni > libertiesBeli ||igralec == Igralec.WHITE && libertiesBeli > libertiesCrni) { //če ima manj liberties kot nasportnik je ugodna poteza
			ocena += Math.abs(libertiesBeli - libertiesCrni) * 6;
		}
		else if (igralec == Igralec.BLACK && libertiesCrni < libertiesBeli ||igralec == Igralec.WHITE && libertiesBeli < libertiesCrni) {
			ocena -= Math.abs(libertiesBeli - libertiesCrni) * 6;
		}
		if (igralec == Igralec.BLACK && steviloCrnihNaRobu > steviloBelihNaRobu ||igralec == Igralec.WHITE && steviloBelihNaRobu > steviloCrnihNaRobu) { //ugodno če ima več na robu kot sredini
			ocena += Math.abs(steviloCrnihNaRobu - steviloBelihNaRobu) * 1;
		}
		else if (igralec == Igralec.BLACK && steviloCrnihNaRobu < steviloBelihNaRobu ||igralec == Igralec.WHITE && steviloBelihNaRobu < steviloCrnihNaRobu) {
			ocena -= Math.abs(steviloCrnihNaRobu - steviloBelihNaRobu) * 1;
		}
		return ocena;
		
	}
	
	
}
