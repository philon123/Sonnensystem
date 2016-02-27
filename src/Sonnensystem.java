public class Sonnensystem implements ITuWas
{
    //Daten für die planeten...
    // m in kg, entfernung und radius in pixel
    
    //Planet:        Farbe:
    //  0 - Sonne      - orange
    //  1 - Venus      - rot
    //  2 - Erde       - blau
    //  3 - Mars       - rot
    //  4 - Jupiter    - schwarz
    //  5 - Saturn     - schwarz
    //  6 - Uranus     - schwarz
    //  7 - Neptun     - blau
    //  8 - Der Komet! -schwarz
    
    Himmelskörper[] planet;
    
    Schieberegler[] sbrKometDaten;
    Ausgabe[] ausSbrBeschr;
    
    Schieberegler sbrTaktZeit;
    Ausgabe ausTaktZeit;
    
    Schieberegler sbrZoom;
    Ausgabe ausZoom;
    
    Eingabefeld einCamWaehlen;
    Ausgabe ausCamInfo;
    
    Ausgabe ausInfo1;
    Ausgabe ausInfo2;
    Taktgeber takt;
    Taste tstRestart;
    Rechteck hintergrund1;
    Rechteck hintergrund2;
    
    
 
    double mSonne=2.0*Math.pow(10,30);
    int rSonne=4, eSonne=0;
    
    double vMerkur=47870, mMerkur=3.3*Math.pow(10,23);
    int rMerkur=2, eMerkur=4;
    
    double vVenus=35020, mVenus=4.9*Math.pow(10,24);
    int rVenus=2, eVenus=7;
    
    double vErde=29780, mErde=6.0*Math.pow(10,24);
    int rErde=2, eErde=10;
    
    double vMars=24130, mMars=6.4*Math.pow(10,23);
    int rMars=2, eMars=15;
    
    double vJupiter=13070, mJupiter=1.9*Math.pow(10,27);
    int rJupiter=3, eJupiter=52;
    
    double vSaturn=9690, mSaturn=5.7*Math.pow(10,26);
    int rSaturn=3, eSaturn=95;
    
    double vUranus=6810, mUranus=8.7*Math.pow(10,25);
    int rUranus=2, eUranus=191;
    
    double vNeptun=5430, mNeptun=1*Math.pow(10,26);
    int rNeptun=2, eNeptun=299;
    
    
    double mKomet=1*Math.pow(10,20);
    int rKomet=3, xKomet=0, yKomet=0, vxKomet=0, vyKomet=0;
    
    double maßstab=15*Math.pow(10,5);
    int xEntfernung=1024/2;
    int yEntfernung=768/2;
    double r, rx, ry, fins, G=0.0000000000667;
    int t=0;
    int zoomfaktor=1;
    int cam=-1;
    
    
    
    /**
     * ###########################################
     */
    public static void main(String[] args){
        Sonnensystem simulation=new Sonnensystem();
    }

    public Sonnensystem(){

        
        
        //Taktgeber
        takt= new Taktgeber();
        takt.setzeLink(this,1);
        takt.setzteZeitZwischenAktionen(1);
        takt.endlos();
        
        //Himmelskörper
        planet = new Himmelskörper[10];
        
        planet[0] = new Himmelskörper(rSonne);
        planet[1] = new Himmelskörper(rMerkur);
        planet[2] = new Himmelskörper(rVenus);
        planet[3] = new Himmelskörper(rErde);
        planet[4] = new Himmelskörper(rMars);
        planet[5] = new Himmelskörper(rJupiter);
        planet[6] = new Himmelskörper(rSaturn);
        planet[7] = new Himmelskörper(rUranus);
        planet[8] = new Himmelskörper(rNeptun);
        planet[9] = new Himmelskörper(rKomet);
        
        planet[0].setzeFarbe("orange");
        planet[1].setzeFarbe("schwarz");
        planet[2].setzeFarbe("rot");
        planet[3].setzeFarbe("blau");
        planet[4].setzeFarbe("rot");
        planet[5].setzeFarbe("schwarz");
        planet[6].setzeFarbe("orange");
        planet[7].setzeFarbe("schwarz");
        planet[8].setzeFarbe("schwarz");
        planet[9].setzeFarbe("rot");
        
        planet[0].masse = mSonne;
        planet[1].masse = mMerkur;
        planet[2].masse = mVenus;
        planet[3].masse = mErde;
        planet[4].masse = mMars;
        planet[5].masse = mJupiter;
        planet[6].masse = mSaturn;
        planet[7].masse = mUranus;
        planet[8].masse = mNeptun;
        planet[9].masse = mKomet;
        
        
        
        //Rechtecke
        hintergrund1=new Rechteck(0,0,1024,40);
        hintergrund1.setzeFarbe("gelb");
        
        hintergrund2=new Rechteck(0,40,1024,20);
        hintergrund2.setzeFarbe("gruen");
        
        //Taste
        tstRestart=new Taste("Restart", 450, 90, 100, 40);
        tstRestart.setzeSchriftgroesse(20);
        
        //Schieberegler
        sbrKometDaten=new Schieberegler[5];
        
        for (int i=0; i<5; i++){
            sbrKometDaten[i]= new Schieberegler('H',0,0,0,0);
            sbrKometDaten[i].setzeGroesse(250,30); //der Konstruktor funktioniert nicht
        }
        sbrKometDaten[0].setzeGroesse(333,30);
        sbrKometDaten[0].setzePosition(0,665);
        sbrKometDaten[1].setzePosition(0,60);
        sbrKometDaten[2].setzePosition(250,60);
        sbrKometDaten[3].setzePosition(500,60);
        sbrKometDaten[4].setzePosition(750,60);
        
        sbrKometDaten[0].setzeBereich(1,0.5*Math.pow(10,30),1);
        sbrKometDaten[1].setzeBereich(0,1024,0);
        sbrKometDaten[2].setzeBereich(0,768,170);
        sbrKometDaten[3].setzeBereich(-2,2,1);
        sbrKometDaten[4].setzeBereich(-2,2,0.5);
        
        sbrTaktZeit=new Schieberegler('H',0,0,0,0);
        sbrTaktZeit.setzeBereich(500,1,1);
        sbrTaktZeit.setzePosition(333,665);
        sbrTaktZeit.setzeGroesse(333,30);
        
        sbrZoom=new Schieberegler('H',0,0,0,0);
        sbrZoom.setzeBereich(1,10,1);
        sbrZoom.setzePosition(666,665);
        sbrZoom.setzeGroesse(333,30);
        
        //Ausgaben
        ausInfo1=new Ausgabe("Simulation des Sonnensystems mit einfallendem Kometen",200,0,1000,40);
        ausInfo1.setzeSchriftgroesse(25);
        
        ausInfo1=new Ausgabe("-alle Entfernungen maßstabsgetreu  -Massen korrekt  -Verschiebungen ausschließlich durch Gravitationskräfte  -Alle Eigenschaften des Kometen durch Schieberegler verstellbar ",0,40,1000,20);
        ausInfo1.setzeSchriftgroesse(12);
        
        ausSbrBeschr=new Ausgabe[5];
        ausSbrBeschr[0]=new Ausgabe("Masse (1kg bis 1/5 Masse der Sonne)", 50, 645, 250, 20);
        ausSbrBeschr[1]=new Ausgabe("x-Position (0 bis 1024 in Pixel)",    30,  90, 200, 20);
        ausSbrBeschr[2]=new Ausgabe("y-Position (0 bis 768 in Pixel)",    280,  90, 200, 20);
        ausSbrBeschr[3]=new Ausgabe("x-Geschwindigkeit (Mitte ist 0)",    550,  90, 200, 20);
        ausSbrBeschr[4]=new Ausgabe("y-Geschwindigkeit (Mitte ist 0)",    800,  90, 200, 20);
        for (int i=0; i<5; i++){
            ausSbrBeschr[i].setzeSchriftgroesse(12);
        }
        
        ausTaktZeit=new Ausgabe("Fps (2 bis 1000)", 450, 645, 200, 20);
        ausTaktZeit.setzeSchriftgroesse(12);
        
        ausZoom=new Ausgabe("Zoom - Faktor (1 bis 10)", 750, 645, 200, 20);
        ausZoom.setzeSchriftgroesse(12);
        
        ausCamInfo=new Ausgabe("Dieses Objekt Zentrieren:", 870, 600, 200, 20);
        ausCamInfo.setzeSchriftgroesse(12);
        
        //Eingabefeld
        einCamWaehlen= new Eingabefeld("z.B. Komet",900,620,100,30);
        einCamWaehlen.setzeSchriftgroesse(12);
        
        
        this.setzeAnfangsZustand();
        
    }

    public void setzeAnfangsZustand(){
        
        planet[0].setzePosition(xEntfernung,yEntfernung);
        planet[1].setzePosition(xEntfernung,planet[0].yPos+eMerkur);
        planet[2].setzePosition(xEntfernung-eVenus,yEntfernung);
        planet[3].setzePosition(xEntfernung,planet[0].yPos-eErde);
        planet[4].setzePosition(xEntfernung+eMars,yEntfernung);
        planet[5].setzePosition(xEntfernung,planet[0].yPos+eJupiter);
        planet[6].setzePosition(xEntfernung-eSaturn,yEntfernung);
        planet[7].setzePosition(xEntfernung,planet[0].yPos-eUranus);
        planet[8].setzePosition(xEntfernung+eNeptun,yEntfernung);
        
        planet[9].setzePosition(sbrKometDaten[1].leseIntWert(),sbrKometDaten[2].leseIntWert());
        
        for (int i=0; i<9; i++){
            planet[i].vx=0;
            planet[i].vy=0;
            planet[i].fx=0;
            planet[i].fy=0;
            planet[i].ax=0;
            planet[i].ax=0;
            planet[i].realxPos=planet[i].xPos;
            planet[i].realyPos=planet[i].yPos;
        }
        
        planet[1].vx0=-100*(vMerkur/maßstab);
        planet[2].vy0=-100*(vVenus/maßstab);
        planet[3].vx0=+100*(vErde/maßstab);
        planet[4].vy0=+100*(vMars/maßstab);
        planet[5].vx0=-100*(vJupiter/maßstab);
        planet[6].vy0=-100*(vSaturn/maßstab);
        planet[7].vx0=+100*(vUranus/maßstab);
        planet[8].vy0=+100*(vNeptun/maßstab);

        planet[9].vx0=sbrKometDaten[3].leseDoubleWert();
        planet[9].vy0=sbrKometDaten[4].leseDoubleWert();

        
        for (int i=0; i<10; i++){
            planet[i].realxPos=planet[i].xPos;
            planet[i].realyPos=planet[i].yPos;
        }
        
    }
    
    public void reset(){
        
        this.setzeAnfangsZustand();
                
        planet[9].masse    = sbrKometDaten[0].leseDoubleWert();
        planet[9].realxPos = sbrKometDaten[1].leseDoubleWert();
        planet[9].realyPos = sbrKometDaten[2].leseDoubleWert();
        planet[9].vx0      = sbrKometDaten[3].leseDoubleWert();
        planet[9].vy0      = sbrKometDaten[4].leseDoubleWert();
        
        t=0;

    }
    
    public void verschiebeKoerper() {

        for (int i=0; i<10; i++) {
            for (int n=0; n<10; n++) {
                if (!(i==n)){
               
                    //Unterschiede der Koordinaten
                    rx=planet[n].realxPos-planet[i].realxPos;
                    ry=planet[n].realyPos-planet[i].realyPos;
                    rx=rx*maßstab;
                    ry=ry*maßstab;
                    
                    r=java.lang.Math.sqrt((rx*rx)+(ry*ry));
                    
                    //Berechnung der Gravitationskräfte
                    fins=G*((planet[i].masse*planet[n].masse)/(r*r));
                    
                    planet[i].fx=planet[i].fx+fins*(rx/r);
                    if (rx==0){planet[i].fx=0;}
                    planet[i].fy=planet[i].fy+fins*(ry/r);
                    if (ry==0){planet[i].fy=0;}
                }
            }

        }
        
        for (int i=0; i<10; i++) {
            
           //setzen der Anfangsgeschwindigkeit
           if (t==0){
                planet[1].vx=planet[1].vx0;
                planet[2].vy=planet[2].vy0;
                planet[3].vx=planet[3].vx0;
                planet[4].vy=planet[4].vy0;
                planet[5].vx=planet[5].vx0;
                planet[6].vy=planet[6].vy0;
                planet[7].vx=planet[7].vx0;
                planet[8].vy=planet[8].vy0;
                
                
                planet[9].vx=planet[9].vx0;
                planet[9].vy=planet[9].vy0;
                t=1;
            }
            
            //Verschiebungsberechnungen
            planet[i].ax=planet[i].fx/planet[i].masse;
            planet[i].ay=planet[i].fy/planet[i].masse;
            
            planet[i].ax=planet[i].ax/maßstab;
            planet[i].ay=planet[i].ay/maßstab;
            
            planet[i].vx=planet[i].vx+planet[i].ax;
            planet[i].vy=planet[i].vy+planet[i].ay;
            
            planet[i].realxPos=planet[i].realxPos+planet[i].vx;
            planet[i].realyPos=planet[i].realyPos+planet[i].vy; 
            
            planet[i].setzeMittelpunkt((int) planet[i].realxPos+5,(int) planet[i].realyPos+5);
            
            this.zoomen();
            
            planet[i].fx=0;
            planet[i].fy=0;
        }
        if (tstRestart.wurdeGedrueckt()==true){
            this.reset();
        }
    }
    
    public void zoomen(){
        
        if (cam>-1){
            planet[cam].zoomxPos=xEntfernung;
            planet[cam].zoomyPos=yEntfernung;
            planet[cam].setzeMittelpunkt((int) planet[cam].zoomxPos,(int) planet[cam].zoomyPos);
        }
            
        for (int i=0; i<10; i++) {
            if ((!(i==cam)) && (cam>-1)){
                //Abstand zum Körper
                rx=planet[i].realxPos-planet[cam].realxPos;
                ry=planet[i].realyPos-planet[cam].realyPos;
            
                //zoom anwenden
                rx=rx*zoomfaktor;
                ry=ry*zoomfaktor;
            
                //entsprechende Verschiebung
                planet[i].zoomxPos=planet[cam].zoomxPos+rx;
                planet[i].zoomyPos=planet[cam].zoomyPos+ry;
                
                planet[i].setzeMittelpunkt((int) planet[i].zoomxPos,(int) planet[i].zoomyPos);
            }
            if (cam<0){
                //Abstand zum Körper
                rx=planet[i].realxPos-xEntfernung;
                ry=planet[i].realyPos-yEntfernung;
            
                //zoom anwenden
                rx=rx*zoomfaktor;
                ry=ry*zoomfaktor;
            
                //entsprechende Verschiebung
                planet[i].zoomxPos=xEntfernung+rx;
                planet[i].zoomyPos=yEntfernung+ry;
                
                planet[i].setzeMittelpunkt((int) planet[i].zoomxPos,(int) planet[i].zoomyPos);
                
                
                
            }
        }
    }
    
    public void einCamAuswerten(){

        String eingegeben=einCamWaehlen.leseText();
        if (eingegeben.equalsIgnoreCase("Sonne"))   { cam= 0; }
        if (eingegeben.equalsIgnoreCase("Merkur"))  { cam= 1; }
        if (eingegeben.equalsIgnoreCase("Venus"))   { cam= 2; }
        if (eingegeben.equalsIgnoreCase("Erde"))    { cam= 3; }
        if (eingegeben.equalsIgnoreCase("Mars"))    { cam= 4; }
        if (eingegeben.equalsIgnoreCase("Jupiter")) { cam= 5; }
        if (eingegeben.equalsIgnoreCase("Saturn"))  { cam= 6; }
        if (eingegeben.equalsIgnoreCase("Uranus"))  { cam= 7; }
        if (eingegeben.equalsIgnoreCase("Neptun"))  { cam= 8; }
        if (eingegeben.equalsIgnoreCase("Komet"))   { cam= 9; }
        
        if (eingegeben.equalsIgnoreCase(""))        { cam=-1; }
        
    }
    
    public void nehmeAenderungenVor(){
        
        takt.setzteZeitZwischenAktionen(sbrTaktZeit.leseIntWert());
        zoomfaktor=sbrZoom.leseIntWert();
        this.einCamAuswerten();
        
    }

    public void tuWas(int ID) {
        if (ID==1){
            this.verschiebeKoerper();
            this.nehmeAenderungenVor();
        }
    }


}