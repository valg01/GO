package gui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.*;

import logika.Igralec;

import java.awt.*;

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
	
	private static JLabel winMessageLabel;
	public GlavnoOknoIgre() {
		
		super();
	
		this.setTitle("Capture Go");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000, 1000); 
        
		mreza = new Mreza(1000, 1000);
		//add(mreza);
		mreza.setBounds(0, 0, 1000, 1000); 
		 // Initialize win message label
        winMessageLabel = new JLabel("", SwingConstants.CENTER);

        // Create a panel to hold both the win message label and the grid
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add win message label and grid to the main panel
        mainPanel.add(winMessageLabel, BorderLayout.NORTH);
        mainPanel.add(mreza, BorderLayout.CENTER);

        // Add the main panel to the frame
        this.add(mainPanel);
        //layeredPane.add(winMessageLabel, JLayeredPane.PALETTE_LAYER);

        //this.add(layeredPane);
		
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
	
	public static void displayWinMessage(Igralec igralec) {
        winMessageLabel.setText("Konec igre, zmaga " + igralec.toString() + "!");
        winMessageLabel.setVisible(true);
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
