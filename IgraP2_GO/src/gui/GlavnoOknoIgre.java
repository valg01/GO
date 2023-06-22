package gui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.EnumMap;

import javax.swing.*;

import logika.Igra;
import logika.Igralec;
import vodja.Vodja;
import vodja.VrstaIgralca;

import java.awt.*;

@SuppressWarnings("serial")
public class GlavnoOknoIgre extends JFrame implements ActionListener {
	
	protected Mreza mreza;
	
	private JMenuItem igraClovekClovek;
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem igraRacunalnikClovek;
		
	private JMenuItem menuOdpri, menuShrani, menuZapri;

	private JMenuItem barvaPlosce, barvaRoba;
	
	private JMenuItem devet, trinajst, devetnajst;
	
	private static JLabel winMessageLabel;
	
	private JLabel status;
	
	private JButton passButton;
	private JButton undoButton; 

	
	public GlavnoOknoIgre() {
		
		super();
		
		Vodja.okno = this;
	
		this.setTitle("Capture Go");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000, 1000); 
        
		
        winMessageLabel = new JLabel("", SwingConstants.CENTER);

        // Create a panel to hold both the win message label and the grid
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add win message label and grid to the main panel
        mainPanel.add(winMessageLabel, BorderLayout.NORTH);
        
        
      
        	
        
        mreza = new Mreza(1000, 1000);
		//add(mreza);
        mreza.setBounds(0, 0, 1000, 1000); 
		 // Initialize win message label
        mainPanel.add(mreza, BorderLayout.CENTER);
        
        
        // Add the main panel to the frame
        this.add(mainPanel);
        //layeredPane.add(winMessageLabel, JLayeredPane.PALETTE_LAYER);

        //this.add(layeredPane);
		
		//menu
		
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu menuDatoteka = dodajMenu(menu_bar, "Datoteka");
		JMenu menuNovaIgra = dodajMenu(menu_bar, "Igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
		JMenu VelikostMreze = dodajMenu(menu_bar, "Velikost mreže");
		
		menuOdpri = dodajMenuItem(menuDatoteka, "Odpri ...");
		menuShrani = dodajMenuItem(menuDatoteka, "Shrani ...");
		menuDatoteka.addSeparator();
		menuZapri = dodajMenuItem(menuDatoteka, "Zapri");
		
		igraClovekClovek = dodajMenuItem(menuNovaIgra, "Človek – človek");
		igraClovekRacunalnik = dodajMenuItem(menuNovaIgra, "Človek - Računalnik");
		igraRacunalnikClovek = dodajMenuItem(menuNovaIgra,"Računalnik – Človek");
		igraRacunalnikRacunalnik = dodajMenuItem(menuNovaIgra, "Računalnik – Računalnik");
		

		barvaPlosce = dodajMenuItem(menuNastavitve, "Barva Plošče");
		barvaRoba = dodajMenuItem(menuNastavitve, "Barva Roba");
		
        devet = dodajMenuItem(VelikostMreze, "9x9");
        trinajst = dodajMenuItem(VelikostMreze, "13x13");
        devetnajst = dodajMenuItem(VelikostMreze, "19x19");
        
		
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
			    status.getFont().getStyle(),
			    20));
		
		//GridBagConstraints status_layout = new GridBagConstraints();
		//status_layout.gridx = 0;
		//status_layout.gridy = 1;
		//status_layout.anchor = GridBagConstraints.CENTER;
		
		//getContentPane().setLayout(new GridBagLayout());
		//getContentPane().add(status);
				
		//status.setText("Izberite igro!");
		
		
		
		
		status = new JLabel("", SwingConstants.CENTER);

        // Create a panel to hold both the win message label and the grid
        //JPanel mainPanel = new JPanel(new BorderLayout());

        // Add win message label and grid to the main panel
        mainPanel.add(status, BorderLayout.NORTH);

        // Add the main panel to the frame
        //this.add(mainPanel);
        
        passButton = new JButton("Pass");
        
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                splosno.Poteza passMove = new splosno.Poteza(-1, -1, true);
               
                Vodja.igra.odigraj(passMove);
                Vodja.igramo();
            }
        });

        getContentPane().add(passButton, BorderLayout.SOUTH);
		
        
        
        
        
        
        

        undoButton = new JButton("Undo");
        
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	if (!Vodja.igra.gameHistory.isEmpty()) {
                    // restore the state to the previous one
                    Igra previousState = Vodja.igra.gameHistory.pop();
                    Vodja.igra = previousState;
                    Vodja.igramo();
                    osveziGUI();
            	}
               
                
                
            }
        });

        getContentPane().add(undoButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(passButton);
        buttonPanel.add(undoButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
		

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
		Object objekt = e.getSource();
		if (objekt == menuOdpri) {
			JFileChooser dialog = new JFileChooser();
			int izbira = dialog.showOpenDialog(this);
			if (izbira == JFileChooser.APPROVE_OPTION) {
				dialog.getSelectedFile().getPath();

			}
		}
		else if (objekt == menuShrani) {
			JFileChooser dialog = new JFileChooser();
			int izbira = dialog.showSaveDialog(this);
			if (izbira == JFileChooser.APPROVE_OPTION) {
				dialog.getSelectedFile().getPath();
			}
		}
		else if (objekt == menuZapri) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
		
		else if(objekt == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
			
		}
		
		 else if (objekt == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
			
		} else if (objekt == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
			
		} else if (objekt == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
			
		} else if (objekt == devet) {
			Igra.velikost = 9;
			repaint();
		} else if (objekt == trinajst) {
			Igra.velikost = 13;
			repaint();
		}  else if (objekt == devetnajst) {
			Igra.velikost = 19;
			repaint();
		} else if (objekt == barvaRoba) {
			Color barva = JColorChooser.showDialog(this, "Izberi barvo mreze", mreza.barvaRoba);
			if (barva != null) {
				mreza.barvaRoba = barva;
				repaint();
			}
		}else if (objekt == barvaPlosce) {
			Color barva = JColorChooser.showDialog(this, "Izberi barvo plošče", mreza.barvaMreze);
			if (barva != null) {
				mreza.barvaMreze = barva;
				repaint();
			}
		}
		
		repaint();
	}
	
	public void osveziGUI() {
		if (Vodja.igra == null) status.setText("Izberi igro");
		else {
			switch(Vodja.igra.stanje) {
			case draw:
				status.setText("Neodločeno!");
				break;
			case win_white:
				status.setText("Zmagal je beli");
				break;
			case win_black:
				status.setText("Zmagal je črni");
				break;
			case in_progress:
				status.setText("Na potezi je " + Vodja.igra.naPotezi() + " - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi()));
			}
		}
		mreza.repaint();
	}
}

