/**
 * Ein Kreis kann sich selbst zeichnen im Programmfenster Zeichnung
 * 
 * @author Hans Witt
 * 
 * Version 1.1 (14.7.2008)
 *     Hinzuf�gen von Statusvariablen f�r Position ...
 * Version: 1.1.1 (17.7.2008) 
 *        Neue Komponenten werden von Unten nach Oben aufgebaut, d.h.vor die alten gesetzt
 * Version: 1.1.2 (23.7.2008) 
 *        setzeRadius verbessert ( Gr��e = radius * >>2<< )
 *        
 * Version: 3 (9.8.2008) 
 *        Containerklasse f�r GUI-Elemente
 * Version: 3.1 (14.8.2008) 
 *        Konstruktor auf int neuesX, int neuesY , int neueBreite, int neueHoehe angepasst      
 * Version: 3.2 (18.8.2008)
 *        Zustandsvariable auf protected gesetzt 
 * @version: 3.3 (29.8.2008)
 *        Zu den sonst �bliche Metheode setzeGr��e undd setzePosition kommen
 *        
 *         setzeRadius - beh�lt den Mittelpunkt bei !
 *         
 *         setzeMittelpunkt
 *         
 *         setzeMittelpunktUndRadius 
 *        
 */

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Himmelsk�rper  implements IComponente{
    
    private CKreis      obj;
    public   int    radius      =25;
    protected int    xPos        = 0;
    protected int    yPos        = 0;
    public double    realxPos    = 0;
    public double    realyPos    = 0;
    public double    fx          = 0; 
    public double    fy          = 0;
    public double    vx          = 0;
    public double    vy          = 0;
    public double    vx0         = 0;
    public double    vy0         = 0;
    public double    ax          = 0;
    public double    ay          = 0;
    public double    masse       = 0;
    public double    zoomxPos    = 0;
    public double    zoomyPos    = 0;
    protected boolean   sichtbar    = true;
    protected boolean   gefuellt    = true;
    protected String    farbe       = StaticTools.leseNormalfarbe();
    

    
    /**
     * Konstruktor f�r Hauptfenster
     */
    public Himmelsk�rper() {
        this(Zeichnung.gibZeichenflaeche());
    }
    
    /**
     * Konstruktor f�r Hauptfenster
     * 
     * @param neuerRadius
     */
    public Himmelsk�rper(int neuerRadius) {
        this(Zeichnung.gibZeichenflaeche(), 0, 0, neuerRadius);
    }
    
    /**
     * Konstruktor f�r Hauptfenster
     * 
     * @param neuesX
     * @param neuesY
     * @param neuerRadius
     */
    public Himmelsk�rper(int neuesX, int neuesY, int neuerRadius) {
        this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neuerRadius);
    }
    
    /**
     * Konstruktor
     * 
     * @param behaelter
     */
    public Himmelsk�rper(IContainer behaelter) {
        this(behaelter, 0, 0, 25);
    }
    
    /**
     * Konstruktor
     * 
     * @param behaelter
     * @param neuerRadius
     */
    public Himmelsk�rper(IContainer behaelter, int neuesX, int neuesY, int neuerRadius) {
        obj = new CKreis();
        behaelter.add(obj, 0);
        setzeDimensionen(neuesX, neuesY, neuerRadius);
        behaelter.validate();
    }
    
    /**
     *  Das Interface IComponente fordert eine Methode die eine BasisComponente zur�ckliefert.
     *  Sie wird ben�tigt, um ein Objekt zu einem anderen Beh�lter hinzuzuf�gen
     */
    public BasisComponente getBasisComponente() {
        return obj ;
    }
    
    public void sichtbarMachen() {
        sichtbar = true;
        obj.sichtbarMachen();
    }
    
    /**
     * Mache unsichtbar. Wenn es bereits unsichtbar ist, tue nichts.
     */
    public void unsichtbarMachen() {
        sichtbar = false;
        obj.unsichtbarMachen();
    }
    
    /**
     * Bewege horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void nachRechtsBewegen() {
        horizontalBewegen(20);
    }
    
    /**
     * Bewege einige Bildschirmpunkte nach links.
     */
    public void nachLinksBewegen() {
        horizontalBewegen(-20);
    }
    
    /**
     * Bewege einige Bildschirmpunkte nach oben.
     */
    public void nachObenBewegen() {
        vertikalBewegen(-20);
    }
    
    /**
     * Bewege einige Bildschirmpunkte nach unten.
     */
    public void nachUntenBewegen() {
        vertikalBewegen(20);
    }
    
    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void langsamVertikalBewegen(int entfernung) {
        int delta;
        
        if (entfernung < 0) {
            delta = -1;
            entfernung = -entfernung;
        } else {
            delta = 1;
        }
        
        for (int i = 0; i < entfernung; i++) {
            vertikalBewegen(delta);
            StaticTools.warte(10);
        }
    }
    
    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void langsamHorizontalBewegen(int entfernung) {
        int delta;
        
        if (entfernung < 0) {
            delta = -1;
            entfernung = -entfernung;
        } else {
            delta = 1;
        }
        
        for (int i = 0; i < entfernung; i++) {
            horizontalBewegen(delta);
            StaticTools.warte(10);
        }
    }
    
    /**
     * neuer Mittelpunkt
     * 
     * @param neuesMX
     * @param neuesMY
     */
    public void setzeMittelpunkt(int neuesMX, int neuesMY) {
        xPos = neuesMX - radius;
        yPos = neuesMY - radius ;
        obj.setzePosition(xPos, yPos);
    }

    
    /**
     * neuer radius *
     */
    public void setzeRadius(int neuerRadius) {
        int mx = xPos + radius;
        int my = yPos + radius ;
        radius = neuerRadius;
        xPos = mx - radius;
        yPos = my - radius ;
        obj.setzeDimensionen(xPos, yPos, 2 * radius, 2 * radius);
    }
    /**
     * 
     * @param neuesX
     * @param neuesY
     * @param neueBreite
     * @param neueHoehe
     */

    public void setzeMittelpunktUndRadius(int neuesMX, int neuesMY, int neuerRadius) {
        radius = neuerRadius;
        xPos = neuesMX - radius;
        yPos = neuesMY - radius ;
        obj.setzeDimensionen(xPos, yPos, 2 * radius, 2 * radius);
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

    
    // Methode n�tig zum Hinzuf�gen mit Anpassung beim Beh�lter
    // Die Enden werden relativ zur aktuellen position verschoben
    public void verschieben(int dx , int dy ) {
        setzePosition(xPos + dx, yPos + dy );
    }

    /**
     * neuer radius *
     */
    public void setzeGroesse(int neuerRadius) {
        radius = neuerRadius;
        obj.setzeGroesse(2 * radius, 2 * radius);
    }

    /**
     * 
     * @param neuesX
     * @param neuesY
     * @param neueBreite
     * @param neueHoehe
     */
    public void setzeDimensionen(int neuesX, int neuesY, int neuerRadius) {
        xPos = neuesX;
        yPos = neuesY;
        radius = neuerRadius;
        obj.setzeDimensionen(xPos, yPos, 2 * radius, 2 * radius);
    }
    
    /*
     * g�ltige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
     * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
     */
    public void setzeFarbe(String neueFarbe) {
        farbe = neueFarbe;
        obj.setzeBasisfarbe(neueFarbe);
    }
    
    /**
     * Bewege horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void horizontalBewegen(int entfernung) {
        xPos += entfernung;
        obj.setzePosition(xPos, yPos);
    }
    
    /**
     * Bewege vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void vertikalBewegen(int entfernung) {
        yPos += entfernung;
        obj.setzePosition(xPos, yPos);
    }
    
    /**
     * gef�llter Kreis
     */
    public void fuellen() {
        gefuellt = true;
        obj.fuellen();
    }
    
    /**
     * Kreislinie
     */
    public void rand() {
        gefuellt = false;
        obj.rand();
    }
    
}

class CKreis extends BasisComponente {
    
    /**
     * Konstruktor ohne Beschriftung
     */
    public CKreis() {
        
    }
    
    /**
     * Die Darstellung der Komponente wird hier programmiert.
     */
    
    public void paintComponentSpezial(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Graphik-Abmessungen
        breite = getSize().width;
        hoehe = getSize().height;
        g2.setColor(farbe);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));

        if (gefuellt) {
            g2.fillOval(0, 0, breite, hoehe);
        } else {
            
            g2.drawOval(1, 1, breite-3, hoehe-3);
//          g2.drawOval(1, 1, breite - 3, hoehe - 3);
        }
    }
}
