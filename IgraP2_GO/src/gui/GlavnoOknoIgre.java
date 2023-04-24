package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GlavnoOknoIgre extends JFrame implements ActionListener {
	
	protected Mreza mreza;
	
	private JMenuItem igraClovekClovek;
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	
	private JMenu menu_AlgoritemRacunalnika;
	
	private JMenuItem menu_Odpri, menu_Shrani, menu_Zapri;
	private JMenuItem VelikostMreze;
	private JMenuItem barvaPlosce, barvaRoba;
	
	
	public GlavnoOknoIgre() {
		
		super();
	
		this.setTitle("Capture Go");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mreza = new Mreza(1000, 1000);
		add(mreza);
		
		//menu
		
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu menu_Datoteka = dodajMenu(menu_bar, "Datoteka");
		JMenu menu_NovaIgra = dodajMenu(menu_bar, "Igra");
		JMenu menu_AlgoritemRacunalnika = dodajMenu(menu_bar, "Algoritem računalnika");
		JMenu menu_Nastavitve = dodajMenu(menu_bar, "Nastavitve");
		
		menu_Odpri = dodajMenuItem(menu_Datoteka, "Odpri ...");
		menu_Shrani = dodajMenuItem(menu_Datoteka, "Shrani ...");
		menu_Datoteka.addSeparator();
		menu_Zapri = dodajMenuItem(menu_Datoteka, "Zapri");
		
		igraClovekClovek = dodajMenuItem(menu_NovaIgra, "Človek – človek");
		igraClovekRacunalnik = dodajMenuItem(menu_NovaIgra, "Človek - Računalnik");
		igraRacunalnikClovek = dodajMenuItem(menu_NovaIgra,"Računalnik – Človek");
		igraRacunalnikRacunalnik = dodajMenuItem(menu_NovaIgra, "Računalnik – Računalnik");
		
		VelikostMreze = dodajMenuItem(menu_Nastavitve, "Velikost mreže");
		barvaPlosce = dodajMenuItem(menu_Nastavitve, "Barva plošče");
		barvaRoba = dodajMenuItem(menu_Nastavitve, "Barva Roba");
		
	}
	
	private JMenu dodajMenu(JMenuBar menu_bar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_bar.add(menu);
		return menu;
	}
	
	private JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menu_item = new JMenuItem(naslov);
		menu.add(menu_item);
		menu_item.addActionListener(this);
		return menu_item;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
