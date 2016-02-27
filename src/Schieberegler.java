/**
 * Ein Schieberegler kann sich selbst zeichnen im Programmfenster Zeichnung </br>
 * 
 * Einstellungen:
 * setzeBereich(double neuesMin, double neuesMax, double neuerWert)
 * und 
 * setzeWert(double neuerWert)
 *
 * Lesen:
 * leseDoubleWert ()
 * und leseIntWert( ) 
 * 
 *  Über die Methode hatSichGeaendert() kann der Status abgefragt werden.
 *  Statusabfrage, Lesen und warteBisAenderung setzen den Status automatisch zurück
 *      
 *  Übergibt man Taste den Link auf eine Klasse mit dem Interface ITuwas,
 *  so wird bei Änderung tuWas(id) aufgerufen wird 
 * 
 * @author Hans Witt
 * 
 * @version: 3.2 (19.8.2008)
 *       
 */

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Schieberegler implements IComponente {
	
	private CSchieberegler	obj;
	protected int			breite				= 300;
	protected int			hoehe				= 30;
	protected int			xPos				= 0;
	protected int			yPos				= 0;
	protected String		hintergrundFarbe	= "dunkelgrau";
	
	protected double		min					= 0;
	protected double		max					= 100;
	protected double		wert				= 50;
	
	/**
	 * Konstruktor für Hauptfenster
	 */
	public Schieberegler() {
		this(Zeichnung.gibZeichenflaeche(), 'H');
	}
	
	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param ausrichtung
	 *            horizontal 'H' oder senkrecht 'S'
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public Schieberegler(char ausrichtung, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), ausrichtung, 0, 0, neueBreite,
				neueHoehe);
	}
	
	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param ausrichtung
	 *            horizontal 'H' oder senkrecht 'S'
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public Schieberegler(char ausrichtung, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), ausrichtung, neuesX, neuesY,
				neueBreite, neueHoehe);
	}
	
	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param ausrichtung
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 * @param min
	 * @param max
	 * @param wert
	 */
	public Schieberegler(char ausrichtung, int neuesX, int neuesY,
			int neueBreite, int neueHoehe, double min, double max, double wert) {
		this(Zeichnung.gibZeichenflaeche(), ausrichtung, neuesX, neuesY,
				neueBreite, neueHoehe, min, max, wert);
		
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 * @param ausrichtung
	 *            horizontal 'H' oder senkrecht 'S'
	 */
	public Schieberegler(IContainer behaelter, char ausrichtung) {
		this(behaelter, ausrichtung, 0, 0, 300, 30);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 * @param ausrichtung
	 *            horizontal 'H' oder senkrecht 'S'
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public Schieberegler(IContainer behaelter, char ausrichtung, int neuesX,
			int neuesY, int neueBreite, int neueHoehe) {
		this(behaelter, ausrichtung, 0, 0, 300, 30, 0, 100, 50);
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 * @param ausrichtung
	 *            horizontal 'H' oder senkrecht 'S'
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 * @param min
	 * @param max
	 * @param wert
	 */
	public Schieberegler(IContainer behaelter, char ausrichtung, int neuesX,
			int neuesY, int neueBreite, int neueHoehe, double min, double max,
			double wert) {
		obj = new CSchieberegler(ausrichtung);
		
		behaelter.add(obj, 0);
		setzeFarbe(hintergrundFarbe);
		setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
		setzeBereich(min, max, wert);
		behaelter.validate();
	}
	
	/**
	 * Das Interface IComponente fordert eine Methode die eine BasisComponente
	 * zurückliefert. Sie wird benötigt, um ein Objekt zu einem anderen Behälter
	 * hinzuzufügen
	 */
	public BasisComponente getBasisComponente() {
		return obj;
	}
	
	public void setzeBereich(double neuesMin, double neuesMax, double neuerWert) {
		min = neuesMin;
		max = neuesMax;
		wert = neuerWert;
		obj.setzeBereich(neuesMin, neuesMax, neuerWert);
	}
	
	public void setzeWert(double neuerWert) {
		wert = neuerWert;
		obj.setzeWert(neuerWert);
	}
	
	public double leseDoubleWert() {
		wert = obj.leseWert();
		ruecksetzenAenderung();
		return wert;
	}
	
	public int leseIntWert() {
		wert = obj.leseWert();
		ruecksetzenAenderung();
		return (int) wert;// Math.round(wert);
	}
	
	/**
	 * Eine Komponente mit Signalisierung muss eine eindeutige ID haben
	 * 
	 * @param ID
	 */
	public void setzeID(int ID) {
		obj.setzeID(ID);
	}
	
	/**
	 * Eine Komponente mit Signalisierung braucht eine Objekt, dem es
	 * signalisieren kann
	 * 
	 * @param linkObj
	 */
	public void setzeLink(ITuWas linkObj) {
		obj.setzeLink(linkObj);
	}
	
	/**
	 * Callback-Funktion wird aufgerufen, wenn die Taste gedrückt wird
	 * 
	 * @param linkObj
	 * 
	 * @param ID
	 *            Parameter, der als ID an die Callback-Funktion übergeben wird.
	 *            In der Callbackfunktion kann dann durch die ID das aufrufende
	 *            Objekt identifiziert werden.
	 * 
	 */
	public void setzeLink(ITuWas linkObj, int ID) {
		obj.setzeLink(linkObj, ID);
	}
	
	public void setzeOrientierungHorizontal() {
		obj.setzeOrientierungHorizontal();
	}
	
	public void setzeOrientierungSenkrecht() {
		obj.setzeOrientierungSenkrecht();
	}
	
	/*
	 * gültige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeFarbe(String neueFarbe) {
		hintergrundFarbe = neueFarbe;
		obj.setzeBasisfarbe(hintergrundFarbe);
	}
	
	public void setzeGroesse(int neueBreite, int neueHoehe) {
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeGroesse(breite, hoehe);
	}
	
	/**
	 * neue Position
	 * 
	 * @param x
	 * @param y
	 */
	public void setzePosition(int neuesX, int neuesY) {
		xPos = neuesX;
		yPos = neuesY;
		obj.setzePosition(xPos, yPos);
	}
	
	// Methode nötig zum Hinzufügen mit Anpassung beim Behälter
	// Die Enden werden relativ zur aktuellen position verschoben
	public void verschieben(int dx, int dy) {
		setzePosition(xPos + dx, yPos + dy);
	}
	
	/**
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public void setzeDimensionen(int neuesX, int neuesY, int neueBreite,
			int neueHoehe) {
		xPos = neuesX;
		yPos = neuesY;
		breite = neueBreite;
		hoehe = neueHoehe;
		obj.setzeDimensionen(xPos, yPos, breite, hoehe);
	}
	
	/**
	 * Status der Komponente abrufen und rücksetzen
	 */
	public boolean hatSichGeaendert() {
		boolean wert = obj.hatSichGeaendert();
		ruecksetzenAenderung();
		return wert;
	}
	
	public void ruecksetzenAenderung() {
		obj.resetAenderung();
	}
	
	public void warteBisAenderung() {
		while (!hatSichGeaendert()) {
			StaticTools.warte(20); // warte 20ms
		}
	}
	
}

class CSchieberegler extends BasisComponente implements ChangeListener {
	private JSlider		balken;
	
	// Zustand der Komponente
	protected double	min			= 0;
	protected double	max			= 100;
	protected double	wert		= 50;
	protected double	deltaWert	= 1;
	protected int		teilung	= 200;
	
	private boolean		aenderung	= false;
	
	/**
	 * Konstruktor für Objekte der Klasse Taste
	 */
	public CSchieberegler(char ausrichtung) {
		this.setLayout(new BorderLayout());
		if (ausrichtung == 'H') {
			balken = new JSlider(Adjustable.HORIZONTAL, 0, teilung, teilung/2);
		} else {
			balken = new JSlider(Adjustable.VERTICAL, 0, teilung, teilung/2);
		}
		balken.setPaintTicks(true);
		balken.setMinorTickSpacing(5);
		balken.setMajorTickSpacing(10);
		
		balken.setBackground(farbe);
		balken.addChangeListener(this);
		balken.updateUI();
		this.add(balken);
	}
	
	public void setzeOrientierungHorizontal() {
		balken.setOrientation(Adjustable.HORIZONTAL);
	}
	
	public void setzeOrientierungSenkrecht() {
		balken.setOrientation(Adjustable.VERTICAL);
	}
	
	public void setzeBereich(double neuesMin, double neuesMax, double neuerWert) {
		min = neuesMin;
		max = neuesMax;
		deltaWert = (max - min) / teilung;
		setzeWert(neuerWert);
	}
	
	public void setzeWert(double neuerWert) {
		wert = neuerWert;
		balken.setValue((int) ((wert - min) / deltaWert));
	}
	
	public double leseWert() {
		wert = balken.getValue() * deltaWert + min;
		return wert;
	}
	
	public void stateChanged(ChangeEvent arg0) {
		aenderung = true;
		if (linkObj != null) linkObj.tuWas(id);
	}
	
	/**
	 * @return gibt zurück, ob Sich der status geändert hat
	 */
	public boolean hatSichGeaendert() {
		return aenderung;
	}
	
	public void resetAenderung() {
		this.aenderung = false;
	}
	
	public void setzeBasisfarbe(String farbname) {
		farbe = StaticTools.getColor(farbname);
		balken.setBackground(farbe);
		repaint();
	}
	
	public void paintComponentSpezial(Graphics g) {
		// Hier nichts zu tun
		
	}
	
}
