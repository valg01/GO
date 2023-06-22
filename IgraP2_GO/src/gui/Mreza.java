package gui;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import logika.Graf;
import logika.Igra;
import logika.Polje;
import logika.Zeton;
import splosno.Poteza;
import vodja.Vodja;

@SuppressWarnings("serial")
public class Mreza extends JPanel implements MouseListener, MouseMotionListener, KeyListener  {
	
	/*public Mreza() {
		setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	
	@Override
	public Dimension getPrefferedSize() {
		return new Dimension(400,400);
	}
		
	private final static double LINE_WIDTH = 0.8;
	
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / 9;  //tukaj določi da je lahko več kot 9
	}
	
	private final static double PADDING = 0.18; */
	
	protected Graf graf;
		
	protected Color barvaPrvega;
	protected Color barvaDrugega;
	protected Color barvaMreze;
	protected Color barvaRoba;
	
 //dodana igra izven konstruktorja
	
	double polmer;
		

	public Polje mreza;
	int velikost = Igra.velikost;
	int presecisceSirina = getWidth() / velikost;
	int presecisceVisina = getHeight() / velikost;
	
	private final static double LINE_WIDTH = 0.04;
	
	public Mreza(int visina, int sirina) { //konstruktor
		super();
		
		//Graf graf = new Graf();
		//NastaviMrezo();
		
		
		barvaPrvega = Color.BLACK;
		barvaDrugega = Color.WHITE;
		barvaMreze = new Color(193, 154, 107);
		barvaRoba = Color.BLACK;
		
		setPreferredSize(new Dimension(sirina, visina));
		setBackground(barvaMreze);
		
		addMouseListener(this);
		//addMouseMotionListener(this);
		//addKeyListener(this);
		setFocusable(true);
		
	}
	
	//public Polje NastaviMrezo() {
	//	Polje mreza = new Polje(velikost);
	//	return mreza;
	//}
	
	//Polje mreza = NastaviMrezo();
	
	
	
	
	private double Kvadratek() {
		int velikost = Igra.velikost;
		return Math.min(getWidth(), getHeight()) / (velikost + 4);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double sirina = Kvadratek();
		polmer =  sirina / 2.5;
		int velikost = Igra.velikost;
		
		if(Vodja.igra == null) return;
		
		g.setColor(barvaRoba);
		g2.setStroke(new BasicStroke((float) (sirina * LINE_WIDTH)));
		for (int i = 1; i < velikost + 1 ; i++) {
			g2.drawLine((int)((i) * sirina),(int)(sirina),
					(int)((i) * sirina), (int)((velikost) * sirina));
			g2.drawLine((int)(1 * sirina) , (int)(i * sirina),
				   (int)((velikost) * sirina ), (int)(i * sirina));
			
		}
		
		if (velikost == 9) {
			g2.fillOval(round((3 * sirina) - 10), round((3 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((7 * sirina) - 10), round((3 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((3 * sirina) - 10), round((7 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((7 * sirina) - 10), round((7 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((5 * sirina) - 10), round((5 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
		}
		
		else if (velikost == 13) {
			g2.fillOval(round((4 * sirina) - 10), round((4 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((10 * sirina) - 10), round((4 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((4 * sirina) - 10), round((10 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((10 * sirina) - 10), round((10 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((7 * sirina) - 10), round((7 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
		}
		else if (velikost == 19) {
			g2.fillOval(round((4 * sirina) - 10), round((4 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((16 * sirina) - 10), round((4 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((4 * sirina) - 10), round((16 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((16 * sirina) - 10), round((16 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
			g2.fillOval(round((10 * sirina) - 10), round((10 * sirina) - 10),(int) (2* 10),(int) (2 * 10));
		} 
		
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				int x = (int) ((i + 1) * sirina);
				int y = (int) ((j + 1) * sirina);
				if (Vodja.igra.grid.mreza[i][j] == Zeton.BLACK) {
					g2.setColor(barvaPrvega);
					g2.fillOval(round(x - polmer), round(y - polmer),(int) (2* polmer),(int) (2 * polmer));
				}
				else if (Vodja.igra.grid.mreza[i][j] == Zeton.WHITE) {
					g2.setColor(barvaDrugega);
					g2.fillOval(round(x - polmer), round(y - polmer),(int) (2* polmer),(int) (2 * polmer));		
				}
				//if (Vodja.igra.ujetaGrupa != null) {
				//	g2.setColor(Color.RED);
				//	 for (Koordinate koord : Vodja.igra.ujetaGrupa) {
				//	    	int x1 = (int) ((koord.getX() + 1) * sirina);
					    	//			int y1 = (int) ((koord.getY() + 1) * sirina);
				//	        g.drawOval(round(x1 - polmer), round(y1 - polmer),(int) (2* polmer),(int) (2 * polmer));
				//	 }
				//	 GlavnoOknoIgre.displayWinMessage(Vodja.igra.zmagovalec());
				
					
				//}
				

			}
		}		
		
	}
	
	//public void paintCaptured(Graphics g) {
	//  g.setColor(Color.RED);
	//  double sirina = Kvadratek();
	//  for (Koordinate koord : Vodja.igra.ujetaGrupa) {
	//  	int x = (int) ((koord.getX() + 1) * sirina);
	//		int y = (int) ((koord.getY() + 1) * sirina);
	//      g.drawOval(round(x - polmer), round(y - polmer),(int) (2* polmer),(int) (2 * polmer));
	//  }
	//}


	
	//pobarva objete in konec igre, poglej od justina raispa kk je on nmapisu
	
	/*public /*  List<int[]> tocke(int velikost){
		List<int[]> iskaneTocke = new ArrayList<int[]>(); //funkcija naredi seznam tock (int[] predstavlja eno tocko pri cemer je 1. komponentna x in 2. komponenta y)
		double s = Kvadratek();
		double odmikDesno = getWidth()
	}*/

	private int round(double d) {
		return (int) (d + 0.5) ;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int velikost = Igra.velikost;
		
		double najblizja = polmer + 10;
		int x = e.getX();
		int y = e.getY();
		int najblizjiX = -100;
		int najblizjiY = -100;
		double sirina = Kvadratek();
		for (int i = 1; i < velikost +4 ;i ++) {
			for (int j = 1; j < velikost + 4; j++) {
				double razdalja = Math.sqrt((x - i * sirina ) * (x - i * sirina ) + (y  - j * sirina ) * (y  - j* sirina));
				if (razdalja < najblizja) {
					najblizjiX = i-1;
					najblizjiY = j-1;			
				}
			}
		}
		
		
		
		//System.out.print(Vodja.igra.stanje);
		if (najblizjiX < 0 || najblizjiY < 0) return;
		else {
			Poteza p = new Poteza(najblizjiX, najblizjiY);
			Vodja.igra.odigraj(p); //odigraj je logična ne grafična zadeva, vse zdej tu notr
		}
		
		Vodja.igramo();
		
		
		//System.out.print(Vodja.igra.libertiesIgralec(Igralec.BLACK));
		//System.out.print(Vodja.igra.najboljVerjetne());
		
		
		//System.out.print(Vodja.igra.ogrozenaCrna);
				
		//Koordinate koord = new Koordinate(3,3);
		//List<Koordinate> grupa = Vodja.igra.grupa(koord);
		//System.out.print(Vodja.igra.libertiesGrupa(grupa));
		//System.out.print(Vodja.igra.stLibertiesIgralec(Igralec.BLACK));
		
		
		repaint();
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
