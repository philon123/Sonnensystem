/**
 * Ein Eingabefeld kann sich selbst zeichnen im Programmfenster Zeichnung </br>
 * 
 * Eingabe und Ausgabe von Text|Integer|Double-Werten über get- und set-methoden 
 *  
 * Übergibt man dem Eingabefeld den Link auf eine Klasse mit dem Interface ITuwas,
 * so wird bei einem Druck auf die <ENTER>-Taste die dort definierte Methode aufgerufen 
 * 
 * @author Hans Witt
 * 
 *  * Version 1.1 (14.7.2008)
 *     Hinzufügen von Statusvariablen für Position ...
 * Version: 1.1.1 (17.7.2008) 
 *        Neue Komponenten werden von Unten nach Oben aufgebaut, d.h.vor die alten gesetzt
 * Version: 3.1 (14.8.2008) 
 *        Konstruktor auf int neuesX, int neuesY , int neueBreite, int neueHoehe angepasst		
 * @version: 3.2 (18.8.2008)
 *        Zustandsvariable auf protected gesetzt 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Eingabefeld implements IComponente {

	private CEingabefeld obj;
	protected int breite = 0;
	protected int hoehe = 0;
	protected int xPos = 0;
	protected int yPos = 0;
	protected String anzeigeText = "Eingabe...";
	protected int fontGroesse = -1;
	protected String schriftFarbe = "schwarz";
	protected String hintergrundFarbe = "weiss";

	/**
	 * Konstuktor für Hauptfenster
	 */
	public Eingabefeld() {
		this(Zeichnung.gibZeichenflaeche());
	}

	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public Eingabefeld(String neuerText, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuerText, 0, 0, neueBreite,
				neueHoehe);
	}

	/**
	 * Konstruktor für Hauptfenster
	 * 
	 * @param neuerText
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public Eingabefeld(String neuerText, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuerText, neuesX, neuesY,
				neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public Eingabefeld(IContainer behaelter) {
		this(behaelter, "Eingabe...", 0, 0, 150, 80);
	}

	public Eingabefeld(IContainer behaelter, String neuerText, int neuesX,
			int neuesY, int neueBreite, int neueHoehe) {
		anzeigeText = neuerText;
		obj = new CEingabefeld(anzeigeText);
		behaelter.add(obj, 0);
		setzeSchriftfarbe(schriftFarbe);
		setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);
		behaelter.validate();
	}

	/**
	 *  Das Interface IComponente fordert eine Methode die eine BasisComponente zurückliefert.
	 *  Sie wird benötigt, um ein Objekt zu einem anderen Behälter hinzuzufügen
	 */
	public BasisComponente getBasisComponente() {
		return obj ;
	}

	/**
	 * Eine Komponente mit Signalisierung muss eine eindeutige ID haben
	 * @param ID
	 */
	public void setzeID(int ID) {
		obj.setzeID(ID);
	}

	/**
	 * Callback-Funktion wird aufgerufen, wenn <ENTER> gedrückt wird
	 * Eine Komponente mit Signalisierung braucht eine Objekt, dem es signalisieren kann
	 * @param linkObj
	 */
	public void setzeLink(ITuWas linkObj) {
		obj.setzeLink(linkObj);
	}


	/**
	 * Callback-Funktion wird aufgerufen, wenn <ENTER> gedrückt wird
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

	public void setzeSchriftgroesse(int neueFontgroesse) {
		fontGroesse = neueFontgroesse;
		obj.setzeSchriftgroesse(fontGroesse);
	}

	/*
	 * gültige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeSchriftfarbe(String neueFarbe) {
		schriftFarbe = neueFarbe;
		obj.setzeSchriftfarbe(schriftFarbe);
	}

	/*
	 * gültige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeHintergrundfarbe(String neueFarbe) {
		hintergrundFarbe = neueFarbe;
		obj.setzeHintergrundfarbe(hintergrundFarbe);
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
	public void verschieben(int dx , int dy ) {
		setzePosition(xPos + dx, yPos + dy );
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

	public void setzeAusgabetext(String neuerText) {
		anzeigeText = neuerText;
		obj.linksbuendig();
		obj.setText(neuerText);
	}

	public String leseText() {
		anzeigeText = obj.getText();
		return anzeigeText;
	}

	public void zentrieren() {
		obj.zentrieren();
	}

	public void setzeInteger(int zahl) {
		obj.rechtsbuendig();
		obj.setText("" + zahl + " ");
	}

	public int leseInteger(int def) {
		int value = 0;
		String neu = obj.getText();
		neu = neu.trim();
		try {
			value = Integer.parseInt(neu);
		} catch (Exception e) {
			value = def;
		}
		setzeInteger(value);
		return value;
	}
	
	public int leseIntegerGerundet(int def) {
		int value = 0;
		value = (int) Math.round(leseDouble(def));
		setzeInteger(value);
		return value;
	}
	

	public void setzeDouble(double zahl) {
		obj.rechtsbuendig();
		obj.setText("" + zahl + " ");

	}

	public double leseDouble(double def) {
		double value = 0;
		String neu = obj.getText().replace(',', '.');
		try {
			value = Double.parseDouble(neu);
		} catch (Exception e) {
			value = def;
		}
		setzeDouble(value);
		return value;
	}
}

class CEingabefeld extends BasisComponente implements ActionListener {
	private JTextField eingabe;

	public void setzeSchriftgroesse(int i) {
		setFontsize(i);
		eingabe.setFont(f);
		repaint();
	}

	/**
	 * Konstruktor für Objekte der Klasse Taste
	 */
	public CEingabefeld(String text) {
		this.setLayout(new BorderLayout());
		eingabe = new JTextField(text);
		setzeBasisfarbe("weiss");
		eingabe.setFont(f);
		eingabe.setHorizontalAlignment(SwingConstants.CENTER);
		eingabe.addActionListener(this);
		eingabe.updateUI();
		this.add(eingabe);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (linkObj != null)
			linkObj.tuWas(id);
	}

	public void setText(String txt) {
		eingabe.setText(txt);
	}

	public String getText() {
		return eingabe.getText();
	}

	public void zentrieren() {
		eingabe.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void linksbuendig() {
		eingabe.setHorizontalAlignment(SwingConstants.LEFT);
	}

	public void rechtsbuendig() {
		eingabe.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	protected Color schriftFarbe = StaticTools.getColor("schwarz");

	public void setzeSchriftfarbe(String farbname) {
		schriftFarbe = StaticTools.getColor(farbname);
		eingabe.setForeground(schriftFarbe);
		repaint();
	}

	public void setzeHintergrundfarbe(String farbname) {
		farbe = StaticTools.getColor(farbname);
		eingabe.setBackground(farbe);
		repaint();
	}

	public void paintComponentSpezial(Graphics g) {
		// Hier nichts zu tun

	}
}
