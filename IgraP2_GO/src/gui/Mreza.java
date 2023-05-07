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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import logika.Graf;
import logika.Igra;
import logika.Polje;
import logika.Zeton;

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
	
	protected double polmer;
	
	private int stevec = 0;
	
	public int velikost = Igra.velikost;
	
	int presecisceSirina = getWidth() / velikost;
	int presecisceVisina = getHeight() / velikost;
	
	private final static double LINE_WIDTH = 0.04;
	
	public Mreza(int visina, int sirina) {
		super();
		
		Graf graf = new Graf();
		NastaviMrezo();
		
		barvaPrvega = Color.BLACK;
		barvaDrugega = Color.WHITE;
		//barvaMreze = Color.LIGHT_GRAY;
		barvaRoba = Color.BLACK;
		
		polmer = 20;
		
		setPreferredSize(new Dimension(sirina, visina));
		//setBackground(barvaMreze);
		
		addMouseListener(this);
		//addMouseMotionListener(this);
		//addKeyListener(this);
		setFocusable(true);
		
	}
	
	public Polje NastaviMrezo() {
		Polje mreza = new Polje(velikost);
		return mreza;
	}
	
	Polje mreza = NastaviMrezo();
	
	private double Kvadratek() {
		return Math.min(getWidth(), getHeight()) / (velikost + 4);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double sirina = Kvadratek();
		
		g.setColor(barvaRoba);
		g2.setStroke(new BasicStroke((float) (sirina * LINE_WIDTH)));
		for (int i = 2; i < velikost + 3; i++) {
			g2.drawLine((int)(i * sirina),(int)(2 * sirina),
					(int)(i * sirina), (int)((velikost + 2) * sirina));
			g2.drawLine((int)(2 * sirina), (int)(i * sirina),
				   (int)((velikost + 2) * sirina), (int)(i * sirina));
			
		}
		
		for (int i = 0; i <= velikost; i++) {
			for (int j = 0; j <= velikost; j++) {
				int x = (int) ((i + 1) * sirina);
				int y = (int) ((j + 1) * sirina);
				if (mreza.mreza[i][j] == Zeton.BLACK) {
					g2.setColor(barvaPrvega);
					g2.fillOval(round(x - polmer), round(y - polmer),(int) (2* polmer),(int) (2 * polmer));
				}
				else if (mreza.mreza[i][j] == Zeton.WHITE) {
					g2.setColor(barvaDrugega);
					g2.fillOval(round(x - polmer), round(y - polmer),(int) (2* polmer),(int) (2 * polmer));
				}
			}
		}
		
		
	}
	
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
		double najblizja = polmer + 10;
		int x = e.getX();
		int y = e.getY();
		int najblizjiX = 0;
		int najblizjiY = 0;
		double sirina = Kvadratek();
		for (int i = 2; i < velikost +4 ;i ++) {
			for (int j = 2; j < velikost + 4; j++) {
				double razdalja = Math.sqrt((x - i * sirina ) * (x - i * sirina ) + (y - j * sirina ) * (y - j* sirina));
				if (razdalja < najblizja) {
					najblizjiX = i-1;
					najblizjiY = j-1;			
				}
			}
		}
		if ((najblizjiX != 0 || najblizjiY != 0) && stevec % 2 == 0 && mreza.mreza[najblizjiX][najblizjiY] == null) {
			mreza.mreza[najblizjiX][najblizjiY] = Zeton.BLACK;
			stevec++;
		}
		else if ((najblizjiX != 0 || najblizjiY != 0) && stevec % 2 == 1 && mreza.mreza[najblizjiX][najblizjiY] == null) {
			mreza.mreza[najblizjiX][najblizjiY] = Zeton.WHITE;
			stevec++;
		}
		System.out.println(stevec);
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
