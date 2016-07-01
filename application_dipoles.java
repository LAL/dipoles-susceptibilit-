import java.util.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;
public class application_dipoles {
    static  dielectriques_ferromagnetisme diel;
    public static void main(String[] args) {
	diel = new dielectriques_ferromagnetisme("Fenetre de choix de programmes");
	diel.run();
    }
}

class dielectriques_ferromagnetisme extends Frame{
    static final float pi=(float)3.141592652;
    static final float eps0=1/(36*pi*10*(float)Math.pow((float)10000.0,2));
    static final float mu0=4*pi/(10*(float)Math.pow((float)1000.0,2));
    boolean creation_d_un_ensemble_venue_d_un_ensemble=false,electrostatique=true;
    int ensemble_a_demarrer=-1;
    int ensemble_d_ou_vient_l_ordre_de_creer=-1;
    static final int top_demarre = 300,left_demarre = 50,bottom_demarre = 480,right_demarre = 850;
    suggest class_sugg;
    point zer;
    public ensemble_de_dipoles ens_de_dipoles[]=new ensemble_de_dipoles[4];
    int ppmouseh;int ppmousev;boolean relachee,pressee,cliquee,draguee;
    boolean vas_y=false,signal_d_ou_je_reviens_fait=false;
    Image image;
    boolean ensemble_identiques=false;
    long temps_en_sec=0;int i_run;boolean creation_cylindres;
    long temps_initial_en_secondes,temps_minimum=3600,temps_initial_en_secondes_prec=0,temps_maximum=3600;
    String titre_menu[]=new String [12];
    String titre[]=new String [12];
    String String_elect[]=new String[2];
    MediaTracker tracker;
    Graphics gr;
    boolean dessiner_menu_principal_ou_fin=false;
    boolean demo_faite=false,demo_a_faire=true;
    private SimpleDateFormat formatter;String d_ou_je_reviens;
    private MouseMotion motion;Color orange_pale;
    boolean terminer_demo=false,demo=true,premiere_demo=true;
    int n_ensembles,n_passage=0;
    private MouseStatic mm;
    boolean toutdebut,run_applet,peindre;
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    int jkk=0;
    dielectriques_ferromagnetisme (String s){
	super(s);
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    //rajouter ici les fenetres qui pourraient être ouvetes, du type systt.dispose();
		    if(class_sugg!=null)
			class_sugg.dispose();
		    for(int ii=0;ii<n_ensembles;ii++)
			eliminer(ii);

			    dispose();
		    System.exit(0);  // pour ne pas laisser trainer des applications qui ne sont pas actives mais prennent de la place en mémoire
		};
	    });

	toutdebut=true;
	run_applet=true;peindre=true;
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this); setBackground(Color.white);
	this.addMouseMotionListener(motion);
	formatter = new SimpleDateFormat ("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
	Date maintenant=new Date();orange_pale=new Color(140,90,0);
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	temps_initial_en_secondes_prec=temps_initial_en_secondes;
	System.out.println("maintenant "+maintenant+" s "+temps_initial_en_secondes);
	titre[0]="Dipole vu de pres";
	titre[1]="Quadripole vu de pres.";
	titre[2]="Quadripole vu de loin.";
	titre[3]="Barreau cylindrique dans un champ unitaire uniforme";
	titre[4]="Boule dans un champ unitaire uniforme";
	titre[5]="Ellipsoïde(ballon de rugby)dans un champ unitaire uniforme";
	titre[6]="Barreau infini dans un champ unitaire uniforme";
	String_elect[0]=".Electrostatique ";
	String_elect[1]=".Magnetostatique ";
	for (int j=0;j<6;j++){
	    for (int jj=0;jj<2;jj++){
		titre_menu[j*2+jj]=titre[j]+String_elect[jj];
		System.out.println(" j*2+jj "+(j*2+jj)+" titre_menu[j*2+jj] "+titre_menu[j*2+jj]);	
	    }
	}
	creation_cylindres=true;d_ou_je_reviens="";
	zer=new point((float)0.,(float)0.);
	pack();setVisible(true);	
	setSize(1200,800);
	setLocation(0,0);
	gr= getGraphics();    //(la size est donnée dans le html pour l'applet)

	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/dipoles_premiere_page.jpg";
	image=createImage(400,400);
	Graphics gTTampon=image.getGraphics();
	image=Toolkit.getDefaultToolkit().getImage(name);
	tracker=new MediaTracker(this);
	tracker.addImage(image,0);
	try {tracker.waitForID(0);}
	catch (InterruptedException e) {
	    System.out.println(" image2 pas arrivee?");
	}
    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h = Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return (h*3600+m*60+s);
    }    
    public void run(){
	
	int isleep=2;
	//menu_principal_et_fin();
	fin_de_programme:
	while (run_applet){
	    Date now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_secondes>temps_maximum){
		run_applet=true;break fin_de_programme; 
	    }
	    //if((dessiner_menu_principal_ou_fin&&(!creation_cylindress))||creation_cylindres)
	    boolean virage=false;
	    if(toutdebut){
		System.out.println(" toutdebut "+toutdebut+" d_ou_je_reviens "+d_ou_je_reviens);
		d_ou_je_reviens="";
		dessiner_menu_principal_ou_fin=false;
		premiere_demo=true;
		terminer_demo=false;
		cliquee=false;
		if(demo_a_faire&&!cliquee){
		    demo=true;
		    gr.drawImage(image,610,0,this);
		    n_ensembles=0;
		    String toto_string[]=new String[4];
		    premiere_demo=true;
		    toto_string[0]="Dipole electrique vu de pres";
		    toto_string[1]="Dipole magnetique vu de pres";
		    toto_string[2]="Dipole electrique vu de loin";
		    toto_string[3]="Dipole magnetique vu de loin";
		    for(int iii=3;iii>=0;iii--){
			ens_de_dipoles[iii]=new ensemble_de_dipoles(toto_string[iii],this,iii/2*2,iii,demo,iii/2*2==iii);
			n_ensembles++;
		    }
		    cliquee=false;
		    vas_y=false;
		    while(!vas_y&&!cliquee)
			for(int ii=0;ii<n_ensembles;ii++){
			    vas_y|=ens_de_dipoles[ii].activer_boule;
			    vas_y|=ens_de_dipoles[ii].le_virer;
			    if(!premiere_demo)
				System.out.println(" ii "+ii+" le_virer "+ens_de_dipoles[ii].le_virer+" vas_y "+vas_y); 
			}
		    if(vas_y){
			for(int ii=0;ii<n_ensembles;ii++){
			    ens_de_dipoles[ii].setVisible(false);
			    ens_de_dipoles[ii].grp_c=null;
			    ens_de_dipoles[ii]=null;
			}
			System.out.println(" avant creation ");
			if(premiere_demo){
			    n_ensembles=1;
			    premiere_demo=false;
			    ens_de_dipoles[0]=new ensemble_de_dipoles(titre[4],this,4,0,demo,true);
			    ens_de_dipoles[0].bill_ou_barreau.fais_lignes_de_champ_barreaux();
			    System.out.println(" apres creation ens_de_dipoles[0]"+ens_de_dipoles[0]);
			    while(!cliquee){
				vas_y=ens_de_dipoles[0].du_nouveau;
				
				if(ens_de_dipoles[0].du_nouveau){
				    if(ens_de_dipoles[0].le_virer){
					ens_de_dipoles[0].ellipsoid.charges_et_courants.setVisible(false);
					ens_de_dipoles[0].ellipsoid.charges_et_courants=null;
					ens_de_dipoles[0].setVisible(false);
					ens_de_dipoles[0].grp_c=null;
					ens_de_dipoles[0]=null;
					n_ensembles=0;
					cliquee=true;	
				    }else{
					System.out.println(" dans le principal"); 
					ens_de_dipoles[0].du_nouveau=false;
					ens_de_dipoles[0].multipol.fais_les_calculs(false);
				    }
				}
			    }
			}
		    }
		}
		if(cliquee){
		    cliquee=false;
		    terminer_demo=true;
		}
		System.out.println("%%%%terminer_demo "+terminer_demo);
		demo_a_faire=false;
		if(terminer_demo){
		    cliquee=false;pressee=false;
		    eraserect(gr,0,0,1200,1200,Color.white);
		    d_ou_je_reviens="je reviens de num_fen";
		    for(int ik=0;ik<n_ensembles;ik++)
			eliminer(ik);
		    n_ensembles=0;
		    Date maintenant=new Date();
		    temps_initial_en_secondes_prec=temps_en_secondes(maintenant);
		    toutdebut=false;
		    System.out.println(" on a detruit les fenetres de demo ");
		    ppmouseh=-100;ppmousev=-100;
		    creation_cylindres=true;
		    menu_principal_et_fin();
		    dessiner_menu_principal_ou_fin=false;					    
		}
		demo=false;
		toutdebut=false;
	    }else{
		if(dessiner_menu_principal_ou_fin)
		    menu_principal_et_fin();
		if(creation_d_un_ensemble_venue_d_un_ensemble){
		    System.out.println(" creation_d_un_ensemble_venue_d_un_ensemble "+creation_d_un_ensemble_venue_d_un_ensemble+" ensemble_d_ou_vient_l_ordre_de_creer "+ensemble_d_ou_vient_l_ordre_de_creer); 
		    creation_d_un_ensemble_venue_d_un_ensemble=false;
		    
		    int iii=1-ensemble_d_ou_vient_l_ordre_de_creer;
		    int j=0;
		    if(!electrostatique)
			j=1;
		    ens_de_dipoles[iii]=new ensemble_de_dipoles(titre[ensemble_a_demarrer]+String_elect[j],this,ensemble_a_demarrer,iii,demo,electrostatique);
		    //}
		    for(int jj=0;jj<2;jj++)
			System.out.println(" jj "+jj+" ens_de_dipoles[jj] "+ens_de_dipoles[jj]);
		    if(ensemble_d_ou_vient_l_ordre_de_creer==0)
			ens_de_dipoles[ensemble_d_ou_vient_l_ordre_de_creer].multipol.paint(true);
		    //ens_de_dipoles[ensemble_d_ou_vient_l_ordre_de_creer].multipole_ou_barreau.paint(true);
		}

		if(d_ou_je_reviens!=""){
		    System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles+" dessiner_menu_principal_ou_fin "+dessiner_menu_principal_ou_fin );
		    System.out.println(" creation_d_un_ensemble_venue_d_un_ensemble "+creation_d_un_ensemble_venue_d_un_ensemble);
		    n_ensembles=0;
		    setVisible(true);
		    if(dessiner_menu_principal_ou_fin)
			menu_principal_et_fin();
		}
		if(creation_cylindres&&!toutdebut){
		    if(!signal_d_ou_je_reviens_fait){
			System.out.println("***....d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles+" creation_cylindres "+creation_cylindres+" toutdebut "+toutdebut );
			signal_d_ou_je_reviens_fait=true;
		    }
		    demarrer_application();
		}
		if(d_ou_je_reviens!=""){
		    //System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles+" creation_cylindres "+creation_cylindres+" toutdebut "+toutdebut );
		    d_ou_je_reviens="";
		}
		if(n_ensembles!=0){
		    /*
		    if(jkk==0)
			System.out.println(" ici programme principal ");
		    jkk++;
		    if(jkk==300)
			jkk=0;
		    */
		    for(int ii=0;ii<n_ensembles;ii++){
			//System.out.println(" ens_de_dipoles[ii].cliquee "+ens_de_dipoles[ii].cliquee);
			if(ens_de_dipoles[ii].ecrit_aide_fait){
			    if(ens_de_dipoles[ii].cliquee){
				ens_de_dipoles[ii].ecrit_aide_fait=false;
				ens_de_dipoles[ii].cliquee=false;
				ens_de_dipoles[ii].multipol.paint(true);
				ens_de_dipoles[ii].ellipsoid.charges_et_courants.setVisible(true);
				ens_de_dipoles[ii].command="";
			    }
			}
			if(ens_de_dipoles[ii].ecris_suggestions){
			    ens_de_dipoles[ii].ecris_suggestions=false;
			    if(class_sugg==null)
				class_sugg=new suggest(" Suggestions ");
			    class_sugg.ecrit_suggestions(ens_de_dipoles[ii].i_demarre);
			}
		    }
		    vas_y=false;
		    for(int ii=0;ii<n_ensembles;ii++){
			vas_y|=ens_de_dipoles[ii].du_nouveau;
		    }
		    if(vas_y){
			dessiner_menu_principal_ou_fin=false;
			for(int ii=0;ii<n_ensembles;ii++){
			    if(!ens_de_dipoles[ii].en_train_de_peindre){				
				if(ens_de_dipoles[ii].le_virer){
				    toutdebut=ens_de_dipoles[ii].command=="Revenir a la page initiale avec infos";
				    if(toutdebut){
					System.out.println(" ii "+ii+" ens_de_dipoles[ii].command "+ens_de_dipoles[ii].command);
					demo_a_faire=true;
					terminer_demo=false;
					dessiner_menu_principal_ou_fin=false;
					creation_cylindres=false;
					n_ensembles=0;

					eraserect(gr,0,0,1200,1200,Color.white);
				    }else{
					dessiner_menu_principal_ou_fin=true;
					creation_cylindres=true;
					signal_d_ou_je_reviens_fait=false;
				    }
				    //dessiner_menu_principal_ou_fin=true;
				    relachee=false;
				    pressee=false;
				    draguee=false;
				    cliquee=false;
				    d_ou_je_reviens=ens_de_dipoles[ii].command;
				    n_ensembles=0;				
				    eliminer(1-ii);
				    eliminer(ii);
				    break;				
				}
				if(ens_de_dipoles[ii].du_nouveau){
				    ens_de_dipoles[ii].du_nouveau=false;
				    ens_de_dipoles[ii].multipol.fais_les_calculs(false);
				    break;
				}
			    }			    
			}
		    }
		}
	    }
	    i_run++;if(i_run==20)i_run=0;
	    //System.out.println("isleep");
	    try {Thread.sleep(isleep);}
	    catch (InterruptedException signal){System.out.println("catch ");}
	}
	if(class_sugg!=null)
	    class_sugg.dispose();
	for(int ii=0;ii<n_ensembles;ii++){
	    eliminer(ii);
	}
	//	stop();this.destroy();
	dispose();
	System.exit(0);
    }
    void demarrer_application(){ 
	//System.out.println("dem relachee "+relachee+" pressee "+pressee);
	if(cliquee){
	//if(relachee&&pressee){
	    cliquee=false;
	    System.out.println("11");
	    int xi=ppmouseh;int yi=ppmousev;
	    System.out.println(" top_demarre-50 "+(top_demarre-50)+" xi "+yi+" yi "+yi);
	    if ((xi > left_demarre)&&(xi < right_demarre)){
		for(int i=0;i<9;i++){
		    if ((yi > top_demarre-50+i*30)&&(yi < top_demarre-50+(i+1)*30)){
			n_ensembles=2;
			for(int iii=1;iii>=0;iii--){
			    System.out.println(" iii "+iii+" String_elect[iii] "+String_elect[iii]);
			    ens_de_dipoles[iii]=new ensemble_de_dipoles(titre[i]+String_elect[iii],this,i,iii,demo,iii==0);
			}
			relachee=false;pressee=false;
			creation_cylindres=false;
		    }
		}
	    }
	}		
    }

    void eliminer(int num_ens){
	if(ens_de_dipoles[num_ens].command=="Sortir du programme")
	    run_applet=false;
	cliquee=false;
	relachee=false;
	pressee=false;
	
	//ens_de_dipoles[[num_ens].dispose();
	//ens_de_dipoles[[num_ens]=null;
	System.out.println(" num_ens "+num_ens+" ens_de_dipoles[num_ens].multipole_ou_barreau "+ens_de_dipoles[num_ens].bill_ou_barreau);
	if(ens_de_dipoles[num_ens].bill_ou_barreau!=null){
	    ens_de_dipoles[num_ens].bill_ou_barreau.elimine();
	}
	    //ens_de_dipoles[num_ens].elimine();
	
	ens_de_dipoles[num_ens].dispose();
	ens_de_dipoles[num_ens]=null;

	System.out.println("apres dispose() ");
    }
    public  void menu_principal_et_fin(){
	int ix,iy;
      if(run_applet){
	  gr.setColor(Color.red);gr.setFont(times14);
	  
	  //System.out.println(" debut paint creation_cylindres "+creation_cylindres);
	  if(creation_cylindres){
	      gr.setFont(times_gras_24);
	      gr.setColor(Color.blue);
	      gr.drawString("Choisissez les dipoles ou la geometrie d'un systeme physique.",left_demarre, top_demarre-140);	 
	      gr.drawString("Vous aurez a gauche les dipôles dielectriques,",left_demarre, top_demarre-110);
	      gr.drawString(" a droite les dipôles ferromagnetiques.",left_demarre, top_demarre-80);	 
	      paintrect_couleur(gr,top_demarre-50,left_demarre,top_demarre+130,right_demarre,Color.red);
	      for (int kk=0;kk<6;kk++){
		  if(kk>=3)
		      gr.setColor(Color.red);
		  else
		      gr.setColor(Color.green);
		  gr.drawString(titre[kk],left_demarre, top_demarre-30+kk*30);	 
		  drawline_couleur(gr,left_demarre, top_demarre-20+kk*30, right_demarre, top_demarre-20+kk*30, Color.red);
	      }
	  }
      }else{
	  eraserect(gr,0,0,1200,1200,Color.white);
	  gr.setFont(times_gras_24);gr.setColor(Color.black);
	  if(temps_en_sec-temps_initial_en_secondes>temps_maximum)
	      gr.drawString("TEMPS MAXIMUM EXPIRE",100,100);
	  else
	      gr.drawString("FIN DU PROGRAMME",100,100);
      }
    }
    void on_s_en_va(){
	for(int iii=1;iii>=0;iii--){
	    if(ens_de_dipoles[iii]!=null){
		ens_de_dipoles[iii].dispose();
		ens_de_dipoles[iii]=null;
	    }
	}
	n_ensembles=0;
    }
    void drawline_couleur(Graphics g,int xin, int yin, int xfin, int yfin, Color couleur)
    {
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void paintrect_couleur(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.drawRect(x,y,width,height);
    }    

    void
	drawline_pt_entier(Graphics g,point pt1, point pt2){
	g.drawLine((int)pt1.x,(int)pt1.y,(int)pt2.x,(int)pt2.y);
    }    
    void eraserect(Graphics g, int top, int left, int bot, int right,Color couleur){
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.fillRect(x,y,width,height);
    }
    void paintcircle_couleur (Graphics g,long x,long y, long r,Color couleur){
	g.setColor(couleur);g.fillOval((int)x,(int)y,(int)r,(int)r);
    }
    public void	traite_click(){
	//System.out.println("entree traite_click ");
	Date maintenant=new Date();
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	if(cliquee){
	    if(temps_initial_en_secondes<temps_initial_en_secondes_prec+2){
		cliquee=false;pressee=false;relachee=false;
	    }else
		temps_initial_en_secondes_prec=temps_initial_en_secondes;
	}
	if(cliquee&&!toutdebut&&n_ensembles!=0){
	    cliquee=false;pressee=false;relachee=false;
	    for(int ik=0;ik<n_ensembles;ik++){    
		ens_de_dipoles[ik].le_virer=true;
		ens_de_dipoles[ik].command="Revenir a la fenetre principale";
	    }
	}
    }
    class MouseMotion extends MouseMotionAdapter
    {
	dielectriques_ferromagnetisme subject;
	public MouseMotion (dielectriques_ferromagnetisme a)
	{
	    subject=a;
	}
	public void mouseMoved(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=false;}
	public void mouseDragged(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	//System.out.println("draguee dans Mousemove "+draguee);
	traite_click();
	}
    }
    class MouseStatic extends MouseAdapter
    {
	dielectriques_ferromagnetisme subject;
	public MouseStatic (dielectriques_ferromagnetisme a)
	{
	    subject=a;
	}
	public void mouseClicked(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_dipoles[icylindre].nb_el_ens "+ens_de_dipoles[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_dipoles[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
	    System.out.println("relachee "+relachee);
	}
    }
    class suggest extends Frame{
	int left_sug=325,bot_sug=600,right_sug=875,top_sug=400;
	Graphics grp_sugg;
	suggest(String s){
	    super(s);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			    if(class_sugg!=null){
				class_sugg.dispose();
				class_sugg=null;
			    }
		    };
		});
	    setVisible(true);
	    setSize(right_sug-left_sug,bot_sug-top_sug);
	    setLocation(left_sug,top_sug);
	    grp_sugg=getGraphics();
	    grp_sugg.setFont(times_gras_14);   
	    System.out.println(grp_sugg);   
	    System.out.println(" left_sug "+left_sug+" right_sug "+right_sug+" top_sug "+top_sug+" bot_sug "+bot_sug);
	}
	void ecrit_suggestions(int i_dem){
	    setVisible(true);
	    eraserect(grp_sugg,top_sug,left_sug,bot_sug,right_sug,Color.green);
	    grp_sugg.setColor(Color.black);
	    grp_sugg.drawString("Si vous etes en mode agrandi, utilisez le menu 'Revenir ou sortir'",10,50);
	    grp_sugg.drawString("Vous pouvez faire une comparaison avec un autre systeme, ou le meme,",10,65);
	    grp_sugg.drawString(" que vous modifierez, menu 'Comparaisons",10,80);
	    if(i_dem<3){
		grp_sugg.drawString("ou déplacer les charges du ou des dipoles, menu 'modifier l'ensemble'",10,95);
	    }else{
		grp_sugg.drawString("ou demander les lignes de champ, ou inclure le champ exterieur ",10,95);
		grp_sugg.drawString(" dans le dessin, menu 'Montrer les champs', ",10,110);
		grp_sugg.drawString("ou changer la direction du champ exterieur (unitaire), ",10,125);
		grp_sugg.drawString("ou changer les parametres de l'objet  menu 'Modifier l'ensemble' '.",10,140);
	    }
	}
    }   
}
class ensemble_de_dipoles extends Frame implements ActionListener{
    int top_ens = 20,left_ens = 0,bottom_ens = 420,right_ens = 600;
    int bottom_fenetre = 700;
    static final float pi=(float)3.141592652;    vecteur vct;
    float coco=0,cece=0,caca=0,cucu=0,cici=0;
    MenuItem itab[]=new MenuItem [12];
    ellipsoide  ellipsoid,ellipsoid_clone;
    String derniere_commande="";
    final String demi_axe_vertical_egal_a[]=new String[7];
    final String demi_axe_horiz_egal_a[]=new String[7];
    final String demie_largeur_egale_a[]=new String[7];
    MenuItem demi_axe_horiz[]=new MenuItem [8];
    MenuItem demi_axe_vertical[]=new MenuItem [8];
    MenuItem demie_largeur[]=new MenuItem [8];
    MenuItem suscept[]=new MenuItem [8];
    MenuItem direction_champ_ext[]=new MenuItem [5];
    MenuItem itep_agrandir=new MenuItem("Agrandir cette fenetre d'un facteur 2");
    MenuItem itep_agrandir_densite=new MenuItem("Agrandir la fenetre densites d'un facteur 2");
    boolean inclue_champ_exterieur=false,ecrit_aide_fait=false,ecris_suggestions=false,prendre_l_autre_ensemble=false;
    MenuItem item_total=new MenuItem("montrer les champs totaux");
    MenuItem item_induit=new MenuItem("montrer les champs induits");
    MenuItem item_lignes=new MenuItem("montrer les lignes de champ");
    Menu echantillon=new Menu("dielectrique/metal");
    MenuItem dielec=new MenuItem("dielectrique");
    MenuItem metall=new MenuItem("metal");
    final String direction_champ_ext_egale_a[]=new String[5];
    String suscept_egale_a[]=new String[6];
    float suscepti[]=new float[6];
    boolean imprime=false,deja_venu=false,grande_taille=false;
    boolean grande_taille_densite=false;
    float cos_angle[]=new float[64];float sin_angle[]=new float[64];
    String str_increm,toto_string;
    float zed=(float)0.,flun=(float)1.,fldeux=(float)2.;
    float kk_plus_precis=zed;boolean booboo=false;
    boolean diff_signes=false;
    float distance_avant_fin=zed;
     float susceptibilite=(float)2.;
    //barreau_cylindrique barreau_cyl;
    multipl_ou_barreau multipole_ou_barreau;
    bille_ou_barreau_cylindrique bill_ou_barreau;
     //quadripole multipol;
    point zer=new point(zed,zed);
    point elec_centre_q_totales=new point(zer);
    point elec_centre_de_plus=new point(zer);
    point toto=new point(zer);
    point titi=new point(zer);
    point tata=new point(zer);
    point tutu=new point(zer);
    point tete=new point(zer);
    point dist_reelle=new point(zer);
    point difference_pt=new point(zer);
    point difference_chp=new point(zer);
    point pt_essai=new point(zer);
    point pt_ss=new point(zer);
    vecteur chp_dpt_prec=new vecteur(zer,zer);
    vecteur chp_dpt=new vecteur(zer,zer);
    point pt_d_intersec=new point(zer);
    point dist_pt_init=new point(zer);
    point dist_pt_fin=new point(zer);
    point cchhpp=new point(zer);

    multipl_ou_barreau multipol;
    boolean hors_cadre=false,a_reculon=false;float prd_vect=zed,prd_vect1=zed;
    boolean demo,ligne_coupee=false,ligne_coupee_d_emblee=false;
    float angle_champs=zed,angle_positions=zed,diff_angles=zed,diff_angles0=zed,diff_angles_prec=zed,increment_angles=(float)0.1;
    float somme_inc=zed;
    int iq_arrivee=0,iq_arrivee_prec=0, numero_zone_arrivee=0;
    boolean calculs_faits=false,lignes_de_champ_faites=false,calculating=false;
    int iter,n_dipoles_ou_barreaux,num_secteur=0;
    float distance_quadrillage=(float)12.;
    float champ_de_ref=zed;
    boolean du_nouveau,en_train_de_peindre;
    boolean ret,trouve_deplacement;
    float x_essai=zed,y_essai=zed,dtet,dttt;
    boolean montrer_un_champ_sur_2=false;
    boolean draw_point=false,activer_boule=false;
;
    boolean totologic=false,titilogic=false,tetelogic=false,tutulogic=false,tatalogic=false;
    point_y_z zer_y_z;
    int tata_int=0,tete_int=0,titi_int=0,toto_int=0,tutu_int=0;
    int ppmouseh,ppmousev,ppmouseh_prec,ppmousev_prec;
    float echelle=(float)0.1;boolean relachee,pressee,cliquee,draguee;
    String command,comment,comment_init;
    point champ[]=new point [64];
    boolean le_virer=false;
    boolean dejaext[]=new boolean[10];
    int  numeroens[]=new int [10];int i_demarre,n_stoppages;
    int fact_zoom_suppl;float fct_zm_sspl=flun;
    Graphics grp_c,gr_de_cote;
    Image image_ell;
    Graphics gTampon_ell,graph,graph_de_cote;
    MediaTracker tracker_ell;
    dielectriques_ferromagnetisme subject;int iq_dep=0,idip_dep=0;
    MouseStatic mm;MouseMotion motion;
    boolean conseil,cree_wires,zoom;int i_ens;
    //MenuBar mb1[]=new MenuBar[2];
    MenuBar mb1=null;
    point_xyz zer_xyz=new point_xyz(zed,zed,zed);
    point_x_z zer_x_z=new point_x_z(zed,zed);
    boolean electrique=true,electrique0=electrique;
    public ensemble_de_dipoles(String s,dielectriques_ferromagnetisme a,int i_demarre1,int i_ens1,boolean demo1,boolean electrique1){	
	super(s);
	subject=a;i_ens=i_ens1;i_demarre=i_demarre1;du_nouveau=true;
	cree_wires=false;n_stoppages=0;
	en_train_de_peindre=false;calculating=false;
	demo=demo1;
	electrique=electrique1;
	electrique0=electrique;
	zer_y_z=new point_y_z(zed,zed);
	if(demo&&subject.premiere_demo){
	    if(i_ens/2==1)
		top_ens +=350;
	    bottom_ens = 640/2+top_ens;
	    right_ens = 600/2;
	}else{
	    if( i_demarre<3)
		bottom_ens = 700;
	    right_ens = 600;
	}
	if(demo&&subject.premiere_demo)
	    bottom_fenetre=bottom_ens;
       dttt=dtet/(float)10.0;
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    le_virer=true;du_nouveau=true;
		    command="Revenir a la page principale";
		};
	    });
	toto_int=0;
	for (int j=0;j<6;j++){
	    if(j<4)
	    toto_int+=20;
	    else
	    toto_int+=40;
	    demi_axe_vertical_egal_a[j]=""+toto_int+"pixels";
	    demi_axe_horiz_egal_a[j]=""+toto_int+"pixels.";
	    demie_largeur_egale_a[j]=" "+toto_int+" pixels.";
	}
	direction_champ_ext_egale_a[0]="horizontale";
	direction_champ_ext_egale_a[1]="30 degres";
	direction_champ_ext_egale_a[2]="45 degres";
	direction_champ_ext_egale_a[3]="60 degres";
	direction_champ_ext_egale_a[4]="verticale";
	if(electrique){
	    suscept_egale_a[0]="0.1";
	    suscept_egale_a[1]="0.2";
	    suscept_egale_a[2]="0.5";
	    suscept_egale_a[3]="1";
	    suscept_egale_a[4]="2";
	    suscept_egale_a[5]="5";
	    suscepti[0]=(float)0.1;
	    suscepti[1]=(float)0.2;
	    suscepti[2]=(float)0.5;
	    suscepti[3]=(float)1;
	    suscepti[4]=fldeux;
	    suscepti[5]=(float)5;
	}else{
	    suscept_egale_a[0]=" 0.5";
	    suscept_egale_a[1]=" 1";
	    suscept_egale_a[2]=" 5";
	    suscept_egale_a[3]=" 10";
	    suscept_egale_a[4]=" 40";
	    suscept_egale_a[5]=" 80";
	    suscepti[0]=(float)0.5;
	    suscepti[1]=flun;
	    suscepti[2]=(float)5;
	    suscepti[3]=(float)10;
	    suscepti[4]=(float)40;
	    suscepti[5]=(float)80;
	}
	for (int i=0;i<6;i++){
	    demi_axe_vertical[i]=new MenuItem(demi_axe_vertical_egal_a[i]);
	    demi_axe_horiz[i]=new MenuItem(demi_axe_horiz_egal_a[i]);
	    demie_largeur[i]=new MenuItem(demie_largeur_egale_a[i]);
	    suscept[i]=new MenuItem(suscept_egale_a[i]);
	}
	for (int i=0;i<5;i++)
	    direction_champ_ext[i]=new MenuItem(direction_champ_ext_egale_a[i]);
       fact_zoom_suppl=1;
	trouve_deplacement=false;comment="";
 	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this); setBackground(Color.white);
	this.addMouseMotionListener(motion);
	setLayout(new FlowLayout());
	pack();
	setVisible(true);
	grp_c=getGraphics();
	grp_c.setFont(subject.times_gras_14);
	setSize(right_ens-left_ens,bottom_fenetre-top_ens);
	if(demo&&subject.premiere_demo){
	    if(i_ens<=1)
		toto_int=i_ens;
	    else
		toto_int=i_ens-2;
	    left_ens+=toto_int*600/2;
	    right_ens=left_ens+320;
	    setLocation(left_ens,top_ens);
	}else{
	    setLocation(left_ens+i_ens*600,top_ens);
	}
	if(!demo){
	    image_ell=createImage(right_ens-left_ens,bottom_ens-top_ens);
	    gTampon_ell=image_ell.getGraphics();
	    tracker_ell=new MediaTracker(this);
	    tracker_ell.addImage(image_ell,1);
	}
	if(i_demarre<3||demo)
	    graph=grp_c;
	else
	    graph=gTampon_ell;
	barre_des_menus();
	comment_init="";
	cree_dipoles_ou_barreau();
	if(i_demarre>=3){
	    demi_axe_vertical[3].setEnabled(false);
	    if(i_demarre==3)
		demie_largeur[3].setEnabled(false);
	    if(i_demarre==4)
		demi_axe_horiz[3].setEnabled(false);
	    else if (i_demarre==5)
		demi_axe_horiz[5].setEnabled(false);
	    direction_champ_ext[bill_ou_barreau.index_direction_champ].setEnabled(false);
	}
	//	Mediatracker trackerr=new MediaTracker(subject.ens_de_dipoles[i_ens]);
    }
    public void barre_des_menus(){
	System.out.println("i_demarre  "+i_demarre);
	mb1=new MenuBar();
	if(demo&&subject.premiere_demo){
	    Menu boule=new Menu("Boule dans un champ");
	    MenuItem b=new MenuItem("Activer");
	    boule.add(b);
	    b.addActionListener(this);
	    mb1.add(boule);
	}
	if(!demo){
	    Menu sortir;
	    if(grande_taille_densite||grande_taille)
		sortir= new Menu("Revenir ou sortir");
	    else	
		sortir= new Menu("Agrandir ou sortir");
	    if(!grande_taille_densite){
		sortir.add(itep_agrandir);	
		itep_agrandir.addActionListener(this);
	    }
	    if(!grande_taille){
		sortir.add(itep_agrandir_densite);	
		itep_agrandir_densite.addActionListener(this);
	    }
	    MenuItem iteb1=new MenuItem("Revenir a la page principale");
	    sortir.add(iteb1);
	    iteb1.addActionListener(this);
	    MenuItem iteb11=new MenuItem("Revenir a la page initiale avec infos");
	    sortir.add(iteb11);
	    iteb11.addActionListener(this);
	    MenuItem iteb12=new MenuItem("Sortir du programme");
	    sortir.add(iteb12);iteb12.addActionListener(this);
	    mb1.add(sortir);
	    if(!(grande_taille_densite||grande_taille))
	    if(!demo){
		Menu ajouter= new Menu("Comparer avec");
		MenuItem itabb=new MenuItem("Ensemble identique, a modifier");
		ajouter.add(itabb);
		itabb.addActionListener(this);
		for (int i=0;i<12;i++){
		    itab[i]=new MenuItem(subject.titre_menu[i]);
		    ajouter.add(itab[i]);
		    itab[i].addActionListener(this);
		}
		mb1.add(ajouter);
	    }
	}
	if(!(grande_taille_densite))
	    if(!demo||demo&&!subject.premiere_demo){
		Menu champs= new Menu("Montrer les champs");
		item_total.setEnabled(true);
		champs.add(item_total);
		item_total.addActionListener(this);
		item_induit.setEnabled(false);
		champs.add(item_induit);
		item_induit.addActionListener(this);
		if(!(grande_taille_densite)){
		    champs.add(item_lignes);
		    item_lignes.addActionListener(this);
		}
		mb1.add(champs);
	    }
	Menu operations_sur_elements= new Menu("Modifier l'ensemble");
	if(i_demarre>3){
	    Menu varier_demie_axe_horiz=new Menu("varier le demi-axe horizontal");
	    for (int i=0;i<6;i++){
		varier_demie_axe_horiz.add(demi_axe_horiz[i]);
		demi_axe_horiz[i].addActionListener(this);
	    }
	    operations_sur_elements.add(varier_demie_axe_horiz);
	}
	if(i_demarre>=3){
	    Menu varier_demi_axe_vertical=new Menu("varier le demi-axe vertical");
	    for (int i=0;i<6;i++){
		varier_demi_axe_vertical.add(demi_axe_vertical[i]);
		demi_axe_vertical[i].addActionListener(this);
	    }
	    operations_sur_elements.add(varier_demi_axe_vertical);
	    if(i_demarre==3){
		Menu varier_demie_largeur=new Menu("varier la demie-largeur centrale");
		for (int i=0;i<6;i++){
		    varier_demie_largeur.add(demie_largeur[i]);
		    demie_largeur[i].addActionListener(this);
		}
		
		operations_sur_elements.add(varier_demie_largeur);
	    }
	    if(electrique){
		Menu varier_susceptibilite=new Menu("varier la susceptibilite");
		for (int i=0;i<6;i++){
		    varier_susceptibilite.add(suscept[i]);
		    suscept[i].addActionListener(this);
		}
		suscept[5].setEnabled(false);
		operations_sur_elements.add(varier_susceptibilite);
	    }
	    
	    Menu varier_direction_champ_ext=new Menu("varier la direction du champ exterieur");
	    for (int i=0;i<5;i++){
		varier_direction_champ_ext.add(direction_champ_ext[i]);
		direction_champ_ext[i].addActionListener(this);
	    }
	    operations_sur_elements.add(varier_direction_champ_ext);
	    if(electrique){
		//echantillon=new Menu("dielectrique/metal");
		//dielec=new MenuItem("dielectrique");
		echantillon.add(dielec);
		dielec.addActionListener(this);
		dielec.setEnabled(false);
		//metall=new MenuItem("metal");
		echantillon.add(metall);
		metall.addActionListener(this);
		operations_sur_elements.add(echantillon);
	    }
	}
	if(!demo&&i_demarre<3){
	    if((i_demarre==1||i_demarre==2)){
		MenuItem deplacer_centre=new MenuItem("deplacer un dipole par son centre");
		deplacer_centre.addActionListener(this);
		operations_sur_elements.add(deplacer_centre);
		if(i_ens==0){
		    MenuItem deplacer_charge=new MenuItem("deplacer une charge d'un dipole");
		    deplacer_charge.addActionListener(this);
		    operations_sur_elements.add(deplacer_charge);
		}
	    }
	}
	mb1.add(operations_sur_elements);
	Menu aide= new Menu("Aide,suggestions");
	if(!(grande_taille_densite||grande_taille)){
	    MenuItem itab1=new MenuItem("Methode de calcul");
	    aide.add(itab1);
	    itab1.addActionListener(this);
	}
	MenuItem itabS=new MenuItem("Suggestions");
	aide.add(itabS);
	itabS.addActionListener(this);
	mb1.add(aide);
	setMenuBar(mb1);
    }
    public void actionPerformed(ActionEvent ev){
	command=ev.getActionCommand();
	/*
	    int jkk=0;
	    while((pressee||cliquee||relachee)){
		if(jkk==0)
		    System.out.println(" ev "+ev);
		jkk++;
		if(jkk==300000)
		    jkk=0;
	    }
	*/
	    pressee=false;
	    cliquee=false;
	    relachee=false;
	System.out.println("i_ens "+i_ens+" n_stoppages "+n_stoppages+" command "+command);
	//if(command!="")n_stoppages=0;
	if(command!="")
	    traite_commande ();
    }
    public void traite_commande (){
	boolean dg=false;
	System.out.println(" ccccccccccccccccccccccccccccc command "+command);
	if(command!=""){
	    du_nouveau=false;
	}
	if(command!=""){
	    if(i_demarre>=3&&ellipsoid!=null)
		ellipsoid.charges_et_courants.setVisible(true);
	    Date maintenant=new Date();
	    derniere_commande=command;
	    subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	    if (command=="Revenir a la page principale"||command=="Sortir du programme"||command=="Revenir a la page initiale avec infos"){
		du_nouveau=true;
		le_virer=true;
		cliquee=true;
		if(command=="Sortir du programme")
		    subject.run_applet=false;	    
	    }
	}
	if(ellipsoid!=null)
	    ellipsoid.images_deja_faites=false;
	if(i_demarre==1||i_demarre==2){
	    if(command=="deplacer un dipole par son centre"||command=="deplacer une charge d'un dipole"){
		trouve_deplacement=false;
		multipol.paint(false);
		comment="Faites glisser le point noir du centre du dipole choisi";
		if(command=="deplacer un dipole par son centre")
		    for (int i=0;i<2;i++)
			subject.paintcircle_couleur(grp_c,(int)Math.round(multipol.bipole[i].centre.x),(int)Math.round(multipol.bipole[i].centre.y),4,Color.black);
		du_nouveau=true;
	    }
	}
	if(i_demarre>=3){
	    if(ellipsoid!=null)
		ellipsoid.charges_et_courants.setVisible(true);

	    if(!demo){
		if(!(grande_taille||grande_taille_densite))
		if(command=="Agrandir cette fenetre d'un facteur 2"||command=="Re_Agrandir cette fenetre d'un facteur 2"){
		    grande_taille=true;
		    itep_agrandir=null;
		    itep_agrandir=new MenuItem("Revenir à la taille initiale de cette fenetre");
		    if(ellipsoid!=null){
			ellipsoid.images_deja_faites=true;
			ellipsoid.peindre_champs=true;
			ellipsoid.peindre_densites=false;
		    }		    
		    //dessine_l_image_de_l_appareil();
		    barre_des_menus();
		    du_nouveau=true;
		    if(command=="Agrandir cette fenetre d'un facteur 2")
			command="Re_Agrandir cette fenetre d'un facteur 2";
		    else
			command="";
		}
		if(command=="Revenir à la taille initiale de cette fenetre"){
		    setSize(right_ens-left_ens,bottom_ens-top_ens);
		    //setLocation(left_ens,top_ens+i_ens*600);
		    if(ellipsoid!=null){
			ellipsoid.images_deja_faites=true;
			ellipsoid.peindre_champs=true;
			ellipsoid.peindre_densites=false;
		    }
		    
		    grande_taille=false;
		    itep_agrandir=null;
		    itep_agrandir=new MenuItem("Agrandir cette fenetre d'un facteur 2");
		    barre_des_menus();
		    du_nouveau=true;
		    //subject.ensemble[1-i_ens].du_nouveau_a_voir=true;
		    //}
		    command="";
		}
		if(!(grande_taille||grande_taille_densite))
		if(command=="Agrandir la fenetre densites d'un facteur 2"||command=="Re_Agrandir la fenetre densites d un facteur 2"){
		    grande_taille_densite=true;
		    itep_agrandir_densite=null;
		    itep_agrandir_densite=new MenuItem("Revenir à la taille initiale de la fenetre densites");
		    if(ellipsoid!=null){
			ellipsoid.images_deja_faites=true;
			ellipsoid.peindre_champs=false;
			ellipsoid.peindre_densites=true;
		    }		    
		    //setLocation(left_ens,top_ens);
		    //dessine_l_image_de_l_appareil();
		    barre_des_menus();
		    du_nouveau=true;
		    if(command=="Agrandir la fenetre densites d'un facteur 2")
			command="Re_Agrandir la fenetre densites d'un facteur 2";
		    else
			command="";
		}
		if(command=="Revenir à la taille initiale de la fenetre densites"){
		    setSize(right_ens-left_ens,bottom_ens-top_ens);
		    grande_taille_densite=false;
		    if(ellipsoid!=null){
			ellipsoid.images_deja_faites=true;
			ellipsoid.peindre_champs=true;
			ellipsoid.peindre_densites=true;
		    }
		    itep_agrandir_densite=null;
		    itep_agrandir_densite=new MenuItem("Agrandir la fenetre densites d'un facteur 2");
		    barre_des_menus();
		    du_nouveau=true;
		    command="";
		}
	    }
	    if(command=="montrer les champs totaux"||command=="montrer les champs induits"){
		totologic=(command=="montrer les champs totaux");
		
		bill_ou_barreau.lignes_de_champ_faites=false;
		item_lignes.setEnabled(true); 
		inclue_champ_exterieur=totologic;
		item_induit.setEnabled(totologic);
		item_total.setEnabled(!totologic);
		command="";
		bill_ou_barreau.chp_clc.remplis();
		bill_ou_barreau.erase();
		bill_ou_barreau.paint(true);
	    }
	    if(command=="montrer les lignes de champ"){
		command="";
		if(bill_ou_barreau==ellipsoid){
		    ellipsoid.charges_et_courants.setVisible(true);
		    System.out.println(" bill_ou_barreau.lignes_de_champ_faites "+bill_ou_barreau.lignes_de_champ_faites);
		    if(!bill_ou_barreau.lignes_de_champ_faites)
			bill_ou_barreau.fais_lignes_de_champ_barreaux();
		    bill_ou_barreau.lignes_de_champ_faites=true;
		    item_lignes.setEnabled(false); 
		}
	    }
	}
	if(command=="Ensemble identique, a modifier"){
	    prendre_l_autre_ensemble=true;
	    subject.ensemble_a_demarrer=i_demarre;
	    subject.electrostatique=electrique;
	    annexe_traite_commande();
	}                   
	for (int i=0;i<12;i++){
	    if(command==subject.titre_menu[i]){
		System.out.println(subject.titre_menu[i]);
		subject.ensemble_a_demarrer=i/2;
		subject.electrostatique=(i/2*2==i);
		if(i_demarre>=3&&i/2==i_demarre&&electrique==subject.electrostatique){
		   prendre_l_autre_ensemble=true;
		}
		annexe_traite_commande();
		break;
	    }
	}
	toto_int=0;
	for (int i=0;i<6;i++){
		if(i<4)
		    toto_int+=20;
		else
		    toto_int+=40;
	    if(command==demi_axe_vertical_egal_a[i]||command==demi_axe_horiz_egal_a[i]||command==demie_largeur_egale_a[i]){
		System.out.println(" i_ens "+i_ens+" toto_int "+toto_int );
		if(command==demi_axe_horiz_egal_a[i]){
		    for (int j=0;j<6;j++)
			demi_axe_horiz[j].setEnabled(true);
		    demi_axe_horiz[i].setEnabled(false);
		}else if(command==demi_axe_vertical_egal_a[i]){
		    for (int j=0;j<6;j++)
			demi_axe_vertical[j].setEnabled(true);
		    demi_axe_vertical[i].setEnabled(false);
		}else if(command==demie_largeur_egale_a[i]){
		    for (int j=0;j<6;j++)
			demie_largeur[j].setEnabled(true);
		    demie_largeur[i].setEnabled(false);
		}
		bill_ou_barreau.change_la_demie_(toto_int,command==demi_axe_vertical_egal_a[i],command==demie_largeur_egale_a[i]);
		du_nouveau=true;
		command="";
		break;
	    }
	}
	for (int i=0;i<6;i++){
	    if(command==suscept_egale_a[i]){
		for (int j=0;j<6;j++)
		    suscept[j].setEnabled(true);
		suscept[i].setEnabled(false);
		System.out.println("ùùùùùùùùùùùùùùùùùùùùùùùùù i_ens "+i_ens+" i "+i+" electrique "+electrique);
		susceptibilite=suscepti[i];
		command="";
		du_nouveau=true;
		break;
	    }
	}
	for (int i=0;i<5;i++){
	    if(command==direction_champ_ext_egale_a[i]){
		for (int j=0;j<5;j++)
		    direction_champ_ext[j].setEnabled(true);
		direction_champ_ext[i].setEnabled(false);
		System.out.println("ùùùùùùùùùùùùùùùùùùùùùùùùù i_ens "+i_ens+" i "+i+" electrique "+electrique);
		command="";
		bill_ou_barreau.determine_la_direction_ext(i);
		du_nouveau=true;
		break;
	    }
	}
	if(electrique)
	    if(command=="dielectrique"||command=="metal"){
		if(command=="dielectrique"){
		    bill_ou_barreau.metal=false;
		    metall.setEnabled(true);
		    dielec.setEnabled(false);
		}
		if(command=="metal"){
		    bill_ou_barreau.metal=true;
		    metall.setEnabled(false);
		    dielec.setEnabled(true);
		}
		command="";
		du_nouveau=true;
	    }

	if (command=="Methode de calcul"){
	    System.out.println(" multipol "+multipol);
	    setVisible(true);
	    multipol.ecrit_aide();
	    command="";
	}
	if (command=="Suggestions"){
	    System.out.println(" multipol "+multipol);
	    ecris_suggestions=true;
	    command="";
	}
	if (command=="Activer"){
	    System.out.println(multipol);
	    	activer_boule=true;
		command="";
	}
    }
    void annexe_traite_commande(){
	subject.creation_d_un_ensemble_venue_d_un_ensemble=true;
	subject.ensemble_d_ou_vient_l_ordre_de_creer=i_ens;
	subject.d_ou_je_reviens="";
	bill_ou_barreau.effacer=false;
	if(bill_ou_barreau==ellipsoid)
	    ellipsoid.charges_et_courants.setVisible(true);
	//annule_l_autre_ensemble();
	subject.n_ensembles=2;
	command="";
    }
    public float calcule_echelle(){

	float ec=flun;float vec2_max=zed;
	for(int iq=0;iq<n_dipoles_ou_barreaux;iq++){
	    /*///////
	    multipole_ou_barreau[iq].chp_clc.remplis();
	    for(int k=0;k<subject.dim;k++)
		if(multipole_ou_barreau[iq].chp_clc.vecteur_champ[k].chp.longueur_carre()>vec2_max)
		    vec2_max=multipole_ou_barreau[iq].chp_clc.vecteur_champ[k].chp.longueur_carre();
	    *////////
	}
	float sqq=(float)Math.sqrt(vec2_max);
	ec=(float)20./(float)Math.sqrt(vec2_max);
	System.out.println(" ec"+ec+" sqq "+sqq);
	//System.out.println("Calculs termines echelle"+echelle);
	return ec;
    }
    public void	traite_click(){
	for(int ii=0;ii<20;ii++){
	    System.out.println("entree traite_click ");
	}
	if(cliquee||draguee||relachee||pressee){
	    Date maintenant=new Date();
	    subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	    if(command=="deplacer un dipole par son centre"||command=="deplacer une charge d'un dipole"){
        	multipol.erase();
		multipol.paint(false);
		if(command=="deplacer un dipole par son centre")
		    for (int i=0;i<2;i++)
			subject.paintcircle_couleur(grp_c,(int)Math.round(multipol.bipole[i].centre.x),(int)Math.round(multipol.bipole[i].centre.y),4,Color.black);
		totologic=multipol.gere_menus_souris ();
		System.out.println(" totologic "+totologic);
		if(totologic){
		    System.out.println(" vers fais les calculs ");
		    multipol.fais_les_calculs(false);
		    du_nouveau=true;
		    command="";
		}
	    }
	}
	/*
	if(cliquee){
	    if(!((ppmouseh==ppmouseh_prec)&&(ppmousev==ppmousev_prec))){
		ppmouseh_prec=ppmouseh;ppmousev_prec=ppmousev;	
	    }else
		cliquee=false;
	}
	*/
	//if(draguee||relachee||pressee||cliquee)
	//   du_nouveau=true;
	//System.out.println("fin_gere_menus_souris "+fin_gere_menus_souris);
	//System.out.println(" dans le traite click  3");
	}
    class MouseMotion extends MouseMotionAdapter
    {
	ensemble_de_dipoles subj;
	public MouseMotion (ensemble_de_dipoles a)
	{
	    subj=a;
	}
	public void mouseMoved(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=false;}
	public void mouseDragged(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	//System.out.println("draguee dans Mousemove "+draguee);
	traite_click();
	}
    }
    
    class MouseStatic extends MouseAdapter{
	ensemble_de_dipoles subj;
	public MouseStatic (ensemble_de_dipoles a){
	    subj=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();

	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_dipoles[icylindre].nb_el_ens "+ens_de_dipoles[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_dipoles[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;relachee=false;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;relachee=true;pressee=false;
	    System.out.println("relachee "+relachee);
	    traite_click();
	}
    }
    abstract class multipl_ou_barreau{
	dipole bipole[]=new dipole[2];
	boolean raccourci_symetrique0=false,raccourci_symetrique=raccourci_symetrique0;
	int right_cote=right_ens+i_ens*600,left_cote=left_ens+i_ens*600,bottom_cote=bottom_ens+340,top_cote=bottom_ens-30;
	float b_prime=0,c_prime=0,a_prime=0,delta_prime=0;
	boolean spherique=false;
	float rc=zed;float scale=0,aaa=0;
	float distance_minimum_au_bord=(float)10.;//pixels
	boolean stop=false,imprimer_resultats=false,montrer_les_champ=false;
	boolean de_loin=false,symetrie_centre_suffisante=true,ecrire_faute=false,fin_ligne=false;
	float cos_phi[]=new float[64];
	float sin_phi[]=new float[64];
	solution_eq_lineaires sol_sigma_ou_I_32;
	solution_eq_lineaires sol_sigma_ou_I_64;
	point centre,centre_multipole;int rayon,rayon0,longueur,largeur;
	point unitaire_suivant_champ=new point(zer);
	point chpt=new point(zer);
	point unitaire_init=new point(zer);
	float sintet[]=new float[64];
	float costet[]=new float[64];
	point point_de_depart=new point(zer);
	float pt_x_prec=0,pt_y_prec=0,diff_x=0,diff_x_prec=0,diff_y=0,diff_y_prec=0;
	point pt_prec=new point(zer);
	point diff_point_de_depart=new point(zer);
	point diff_point_de_depart_prec=new point(zer);
	point limite_min=new point((float)10000.,(float)10000.);
	boolean inside=false,inside_prec=false;
	float angle=zed,residu=0;
	point position,posit;point_y_z position_y_z;
	point D[]=new point[2];point D_prec[]=new point[2];
	boolean tour_complet[]=new boolean [64];
	float facteur_fondamental=0,champ_exterieur_scal=zed;
	float mu0_ou_un_sur_eps0;
	point q_dist_plus=new point(zer);
	point q_dist_moins=new point(zer);
	float distance_minimum_a_l_arc=0;
	point_xyz vec_un_xyz=new point_xyz(zer_xyz);
	point_xyz posit_xyz=new point_xyz(zer_xyz);
	point_xyz pt_xyz=new point_xyz(zer_xyz);
	point_xyz toto_xyz=new point_xyz(zer_xyz);
	point_xyz titi_xyz=new point_xyz(zer_xyz);
	point_xyz tata_xyz=new point_xyz(zer_xyz);
	point_xyz tutu_xyz=new point_xyz(zer_xyz);
	point_xyz tete_xyz=new point_xyz(zer_xyz);
	point_y_z toto_y_z=new point_y_z(zed,zed);
	point_y_z titi_y_z=new point_y_z(zed,zed);
	point_y_z tata_y_z=new point_y_z(zed,zed);
	point_y_z tutu_y_z=new point_y_z(zed,zed);
	point_y_z tete_y_z=new point_y_z(zed,zed);
	point_xyz po_xyz=new point_xyz(zer,zed);
	point_y_z point_central_arc=new point_y_z(zed,zed);
	point_y_z courant=new point_y_z(zed,zed);
	float aa=0,bb=0,aa2=0,bb2=0,tgteta_ext=0,costeta_normale,sinteta_normale,tgteta_normale=0;
	point_y_z distance_a_point_central_arc=new point_y_z(zed,zed);
	point point_sur_la_ligne=new point(zer);
	point pt_lg_prec=new point(zer);
	boolean ecrire_champ=false;
	float rapport_des_axes=flun;
	champs_points_calcules chp_clc;
	abstract boolean pas_trop_proche_des_bords(point dist);
	abstract public boolean gere_menus_souris();
	abstract public void ecrit_aide();
	abstract void fais_les_calculs(boolean initi);
	abstract void paint(boolean avec_champs);
	abstract point_xyz champ_total_en_un_point(point_xyz pt_xyz,boolean inclu);
	abstract boolean est_dedans(point dist,int d);
	multipl_ou_barreau(){
	    position=new point(zer);
	    posit=new point(zer);
	    centre_multipole=new point(zer);
	    position_y_z=new point_y_z(zer_y_z);
	    chp_clc=new champs_points_calcules();
	    float teta=-pi/(float)128.;
	    for (int itet=0;itet<64;itet++){	
		teta+=pi/(float)64.;
		costet[itet]=(float)Math.cos(teta);
		sintet[itet]=(float)Math.sin(teta);
	    }
	    if(electrique)
		mu0_ou_un_sur_eps0=1/subject.eps0;
	    else
		mu0_ou_un_sur_eps0=subject.mu0;
	    for (int n=0;n<n_dipoles_ou_barreaux;n++){
		D[n]=new point(zer);
		D_prec[n]=new point(zer);
	    }

	    facteur_fondamental=mu0_ou_un_sur_eps0/(4*pi);
	    System.out.println(" i_ens "+i_ens+" top_ens "+top_ens+" left_ens "+left_ens+" bottom_ens "+bottom_ens+" right_ens "+right_ens);
	    if(!demo)
		System.out.println(" top_cote "+top_cote+" left_cote "+left_cote+" bottom_cote "+bottom_cote+" right_cote "+right_cote);
	}
	void erase(){
	    System.out.println(" debut erase ");
	    if(i_demarre>=3)
		if(derniere_commande!="montrer les champs totaux"||derniere_commande=="montrer les champs induits"||derniere_commande=="montrer les lignes de champ")
		    subject.eraserect(gr_de_cote,0,0,1000,1000,Color.white);
	    subject.eraserect(grp_c,top_ens,left_ens,bottom_fenetre,right_ens,Color.white);

	}
	class champs_points_calcules{
	    int index_x=0,index_y=0,index_x_centre=0,index_y_centre=0,nb_chp_x=0,nb_chp_y=0;
	    vecteur vecteur_champ[][]=new vecteur[32][32] ;
	    public champs_points_calcules(){
		for (int i=0;i<32;i++)
		    for (int j=0;j<32;j++)
			vecteur_champ[i][j]=new vecteur(zer,zer);
	    }
	    public void remplis(){
		montrer_les_champ=true;
		float d=0;
		//position.assigne((float)(centre_multipole.x-(right_ens-left_ens)/2),(float)(centre_multipole.y- (bottom_ens-top_ens)/2));
		position.assigne((float)20,(float)40);
		position.print_sl(" ppppppppppppposition ");
		toto.assigne((float)(centre_multipole.x+(right_ens-left_ens)/2),(float)(centre_multipole.y+ (bottom_ens-top_ens)/2));
		toto.print_sl(" toto ");
		scale=0;
		point dist=new point(position);
		index_x=0;index_y=0;index_x_centre=0;index_y_centre=0;nb_chp_x=0;nb_chp_y=0;
		boolean pos_x_a_determiner=true,pos_y_a_determiner=true;
		centre_multipole.print_sl(" scale "+scale+" centre_multipole ");

		for (int kk=0;kk<10000;kk++){
		    dist.assigne_soustrait(position,centre_multipole);
		    //ecrire_champ=(Math.abs(position.x-centre.x)<10.1&&Math.abs(position.y-centre.y)<rayon);
		    totologic=true;
		    if(i_demarre==1||i_demarre>=3)
			totologic=pas_trop_proche_des_bords(dist);
		    vecteur_champ[index_x][index_y].pnt.assigne(position);
		    po_xyz.assigne(position,zed);
		    if(totologic)
			vecteur_champ[index_x][index_y].chp.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
		    else
			vecteur_champ[index_x][index_y].chp.assigne((float)1000.,(float)1000);
		    if(ecrire_champ)
			vecteur_champ[index_x][index_y].print(" vecteur_champ[index_x][index_y] ");
		    //ecrire_champ=kkk/100*100==kkk;
		    if(i_demarre>=3)
			if(est_dedans(dist,0))
			    vecteur_champ[index_x][index_y].print(" V ");
		    toto.assigne(vecteur_champ[index_x][index_y].chp);
		    if(toto.longueur_carre()>scale&&!(Math.abs(toto.x)>999&&Math.abs(toto.y)>999))
			if(!(i_demarre==2&&dist.longueur_carre()<5*5))
			    scale=(float)vecteur_champ[index_x][index_y].chp.longueur_carre();
		    //if(kk/10*10==kk)
		    //vecteur_champ[kk].print("kk "+kk+" vecteur_champ[kk] ");
		    position.x+=20;
		    index_x++;
		    if(position.x>centre.x-1&&pos_x_a_determiner){
			index_x_centre=index_x;
			pos_x_a_determiner=false;
		    }
		    nb_chp_x=index_x;
		    if(position.x>centre.x-1+(right_ens-left_ens)/2){
			position.x=20;
			index_x=0;
			nb_chp_x--;
			position.y+=20;
			//position.print_sl("hhhhhhhhhhhhhhhhposition ");
			index_y++;
			if(position.y>centre.y-1&&pos_y_a_determiner){
			    index_y_centre=index_y;
			    pos_y_a_determiner=false;
			}
			nb_chp_y++;
			if(!pos_y_a_determiner)
			    if(!raccourci_symetrique){
				toto_int=2*index_y_centre-1;
				if(demo)
				    toto_int=2*index_y_centre+4;
				if(index_y>toto_int){
				    nb_chp_y--;
				    //System.out.println(" bbbbbbrrrrrrrrreak index_y "+index_y+" toto_int "+toto_int);
				    break;
				}
			    }else{
				if(index_y>index_y_centre+1){
				    nb_chp_y-=1;
				    break;
				}
			    }
		    }
		}
		System.out.println(" nb_chp_x "+nb_chp_x+" nb_chp_y "+nb_chp_y+" index_x_centre "+index_x_centre+" index_y_centre "+index_y_centre);
		vecteur_champ[nb_chp_x][nb_chp_y].print(" vecteur_champ "); 
		scale=(float)Math.sqrt(scale);
		System.out.println(" ssssssssssssssssscale "+scale);
		montrer_les_champ=false;
	    }
	    void dessiner_les_champs(float fct){
		fct_zm_sspl=fct;
		vecteur v=new vecteur(zer,zer);
		/*
		if(i_demarre<=2){
		    scale=0;
		    for (int index_x=0;index_x<nb_chp_x;index_x++)
			for (int index_y=0;index_y<nb_chp_y;index_y++){
			    v.assigne(vecteur_champ[index_x][index_y]);
			    if(v.chp.longueur_carre()>scale&&!(Math.abs(v.chp.x)>(float)999.&&Math.abs(v.chp.y)>(float)999))
				scale=(float)v.chp.longueur_carre(); 
			}
		    scale=(float)Math.sqrt(scale);
		}
		*/
		System.out.println(" scale dans dessiner_les champs "+scale);
		int i_lim=1,i_x=0,i_y=0;
		if(raccourci_symetrique)
		    i_lim=2;
		for (int index_y=0;index_y<nb_chp_y;index_y++){
		    for (int index_x=0;index_x<nb_chp_x;index_x++){
			for (int i=0;i<i_lim;i++){
			    if(i==0){	
				i_x=index_x;
				i_y=index_y;
				v.pnt.assigne(vecteur_champ[i_x][i_y].pnt);
			    }
			    if(i==1){
				i_x=2*index_x_centre-index_x;
				i_y=2*index_y_centre-index_y;
				toto.assigne_facteur(centre,fldeux);
				v.pnt.assigne_soustrait(toto,vecteur_champ[index_x][index_y].pnt);
			    }
			    v.chp.assigne(vecteur_champ[index_x][index_y].chp);
			    /*
			    if(electrique&&i==1){
				v.chp.assigne_oppose();
			    }else{
				//				v.chp.assigne(vecteur_champ[i_x][i_y].chp);
			    }
			    */
			    if(i_demarre>2){
				v.chp.x=Math.round(v.chp.x*100)/(float)100;
				v.chp.y=Math.round(v.chp.y*100)/(float)100;
			    }
			    //if(est_dedans(toto;0))
			    //v.print("i "+i+"i_x "+i_x+" i_y "+i_y+" VV ");
	    
			    if(!(Math.abs(v.chp.x)>999&&Math.abs(v.chp.y)>999)){
				if(i_demarre<=2)
				    v.chp.divise_cst(100);
				titi.assigne((float)240.,(float)280.);
				toto.assigne_soustrait(v.pnt,titi);
				titi.assigne((float)240.,(float)160.);
				tutu.assigne_soustrait(v.pnt,titi);
				if(tutu.longueur_carre()<100.||toto.longueur_carre()<100.)
				    v.print(" scale "+(float)scale+" vvvvvvvvvvvv ");
				v.dessine(scale,fct_zm_sspl,graph,Color.green);
				//toto.assigne_soustrait(v.pnt,centre_multipole);
				//if(Math.abs(toto.y-40)<10&&Math.abs(toto.x)<300)
				//v.print(" vvvvvvvvvvvv ");
			    }
			}
		    }
		}
	    }
	}
	boolean est_dans_la_fenetre(point pt){
	    return (pt.x>left_ens&&pt.x<right_ens&&pt.y>top_ens&&pt.y<bottom_ens);
	}
    }
    abstract class bille_ou_barreau_cylindrique extends multipl_ou_barreau{
	int dim=16,k_fin=0;
	boolean metal=false,lignes_de_champ_faites=false;
	float norme=zed,V_v1=0,V_v2=0;
	point_xyz chp_xyz;
	palette paalette;
	boolean voir_les_lignes_de_champ=false,calculer_centres_charges=false;
	point champ_suscept_1=new point(zer);
	point_xyz  champ_total_x_constant= new point_xyz(zer_xyz);
	point_xyz  champ_total_y_constant= new point_xyz(zer_xyz);
	point_y_z projections_du_rayon[]=new point_y_z[64];
	point_y_z projections_perp_au_rayon[]=new point_y_z[64];
	point_xyz u_xyz=new point_xyz(zer_xyz); 
	point_xyz v_xyz=new point_xyz(zer_xyz);
	bi_point_xyz fieldd=new bi_point_xyz();
	point_xyz vecteur_unite_xyz=new point_xyz(zer_xyz); 
	boolean imprimer_sigma_ou_I=true,imprimer_lignes_de_champ=true,effacer=true;
	point_xyz champ_en_un_point=new point_xyz(zer_xyz);
	point champ_au_centre=new point(zer);
	point point_final_droite=new point(zer);
	point point_final_gauche=new point(zer);
	boolean  point_final_gauche_sur_verticale_gauche=false;
	boolean  point_final_droite_sur_verticale_droite=false;
	boolean point_final_droite_sur_horizontale_basse=false;
	boolean point_final_gauche_sur_horizontale_haute=false;
	point champ_exterieur=new point(zer);
	point_xyz champ_exterieur_xyz=new point_xyz(zer_xyz);
	float tg_teta_exterieur=0,sin_teta_exterieur=0,cos_teta_exterieur=0;
	point point_limite_gauche=new point(zer);
	point point_limite_droite=new point(zer);
	int x_int=0,y_int=0,x_int1=0,y_int1=0;
	point champ_au_c[]=new point[100];
	point_xyz normale_xyz=new point_xyz(zer_xyz);
	int index_direction_champ=2,index_direction_champ0=index_direction_champ;
	point_xyz field[]=new point_xyz[64];
	point_xyz unite_B=new point_xyz(zer_xyz);
	int i_trouve_max=-1;
	bi_point_xyz bi_feld_xyz=new bi_point_xyz();
	centres_charges center_charges;
	int i_horloge=0; 
	point p_horloge=new point(zer);
	point p_horloge_prec=new point(zer);
	String toto_titre="";
	float alfa=0,beta=0;
	float rapport_renormalisation=zed;
	point rapport_renor=new point(zer);
	boolean impr=false,sortie=false,reentree=false,affiche_les_64_memoires=false;
	bille_ou_barreau_cylindrique(){
	    super();
	    if(!electrique){
		susceptibilite=(float)80.;
		toto_titre="densites de courant";
	    }else{
		susceptibilite=(float)5.;
		toto_titre="densites de charge";
		center_charges=new centres_charges();
	    }
	    float phi=-pi/(float)64.;
	    for (int i=0;i<64;i++){	
		phi+=2*pi/(float)64.;
		cos_phi[i]=(float)Math.cos(phi);
		sin_phi[i]=(float)Math.sin(phi);		
		projections_du_rayon[i]=new point_y_z(cos_phi[i],sin_phi[i]);
		projections_perp_au_rayon[i]=new point_y_z(-sin_phi[i],cos_phi[i]);
		field[i]=new point_xyz(zer_xyz);
	    }
	    for (int i=0;i<10;i++)
		champ_au_c[i]=new point(zer);
	}
	abstract void change_la_demie_(int demie_h,boolean h_pas_v,boolean tt);
	abstract void elimine();
	abstract void appelle_VAR_B(boolean in);
	abstract public void determine_la_direction_ext(int i);
	boolean pas_trop_proche_des_bords(point dist){
	    return far_from_boarders(dist);
	}
	public boolean gere_menus_souris(){
	    return true;
	}
	float sigma_ou_I(point_xyz vect,point_xyz chpp_xyz,float sig_ou_I){
	    cici=chpp_xyz.scalaire(vect);
	    champ_exterieur_scal=champ_exterieur_xyz.scalaire(vect);
	    //return (champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
	    if(!metal){
		if(electrique){
		    return susceptibilite/(1+susceptibilite/2)*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
		}else{
		    //return (champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
		    return 2*susceptibilite*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
		}
	    }else
		return 2*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
	    //return cici/mu0_ou_un_sur_eps0;
	}
	point_xyz vecteur_unite_tangent_et_normal_a_un_troisieme(point_xyz V,point_xyz v1,point_xyz v2){
	    /*
	     les deux vecteurs (normalises) v1 et v2 déterminent le plan tangent, le troisieme est V (normalise lui aussi), qui peut aussi être dans ce plan, le sens final n'est pas donné 
	    */
	    V_v1=V.scalaire(v1);
	    V_v2=V.scalaire(v2);
	    if(Math.abs(V_v2)>0.1){
		alfa=(float)Math.sqrt(V_v2*V_v2/(V_v1*V_v1+V_v2*V_v2-2*V_v1*V_v2*v1.scalaire(v2)));
		beta=-V_v1/V_v2*alfa;
		titi_xyz.assigne_facteur(v1,alfa);
		titi_xyz.additionne_facteur(v2,beta);
	    }else{
		alfa=(float)Math.sqrt(V_v1*V_v1/(V_v1*V_v1+V_v2*V_v2-2*V_v1*V_v2*v1.scalaire(v2)));
		beta=-V_v2/V_v1*alfa;
		titi_xyz.assigne_facteur(v2,alfa);
		titi_xyz.additionne_facteur(v1,beta);
	    }
	    return titi_xyz;
	}

	void ecrit_champ_au_centre_sur_ecran(){
	    //subject.eraserect(gr_de_cote,280+i*15,10,300+i*15,200,Color.white)
	    point chp =new point((champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point()));

	    toto_int=(int)Math.round(chp.x*100);
	    coco=toto_int/(float)100.;
	    toto_int=(int)(chp.y*100);
	    cucu=toto_int/(float)100;
	    graph.setColor(Color.black);
	    graph.setFont(subject.times_gras_14);
	    graph.drawString(" champ_au_centre x "+coco+" y "+cucu,10,150);
	    if(inclue_champ_exterieur)
		graph.drawString(" champ_exterieur inclus ",10,200);
	    else
		if(electrique){
		    graph.drawString(" seulement champ des charges induites ",10,200);		    
		}else
		    graph.drawString(" seulement champ des courants induits ",10,200);
	    graph.drawString(" susceptibilite "+susceptibilite,10,100);

	}
	/*
	void montre_champ_au_centre(point chp_c){
	    float chp_c_arrondi_x=(int)(chp_c.x*100)/(float)100.;
	    float chp_c_arrondi_y=(int)(chp_c.y*100)/(float)100.;
	    graph.drawString(" Champ au centre horiz. "+chp_c_arrondi_x+" vert. "+chp_c_arrondi_x,20,200);
	}
	*/
	void fais_lignes_de_champ_barreaux(){
	    System.out.println(" entree fais_lignes_de_champ_barreaux ");
	    if(grande_taille)
		setVisible(true);
	    point point_initial=new point(zer);
	    point point_initial_sur_la_surface=new point(zer);
	    float ecart=40;
	    if(inclue_champ_exterieur)
		ecart=20;
	    /*
	      if(i_demarre==4||i_demarre==5){
	      ecart=(float)aa/4;
	      if(bb>aa)
	      ecart=(float)bb/4;
	      if(i_demarre==5)
	      ecart/=(2*rapport_des_axes);
	      }else if(i_demarre==3)
	      ecart=(float)longueur/8;
	    */
	    int n_pts_limite_droite=0,n_pts_limite_gauche=0;
	    if(cos_teta_exterieur>0.0001&&sin_teta_exterieur>0.0001){
		point_final_droite.assigne((float)right_ens,centre.y+tg_teta_exterieur*(right_ens-centre.x));
		point_final_droite_sur_verticale_droite=point_final_droite.y<bottom_ens;
		point_final_droite_sur_horizontale_basse=!point_final_droite_sur_verticale_droite;
		if(point_final_droite_sur_horizontale_basse)
		    point_final_droite.assigne(centre.x+(right_ens-centre.x)/tg_teta_exterieur,(float)bottom_ens);
		point_final_gauche.assigne((float)left_ens,centre.y-tg_teta_exterieur*(centre.x-(float)left_ens));
		point_final_gauche_sur_verticale_gauche=point_final_gauche.y>top_ens;
		point_final_gauche_sur_horizontale_haute=(!point_final_gauche_sur_verticale_gauche);
		if(point_final_gauche_sur_horizontale_haute)
		    point_final_gauche.assigne(centre.x-(centre.x-(float)left_ens)/tg_teta_exterieur,(float)top_ens);
	    }else if(cos_teta_exterieur<0.0001){
		point_final_droite.assigne(centre.x,(float)bottom_ens);
		point_final_droite_sur_verticale_droite=false;
		point_final_gauche.assigne(centre.x,(float)top_ens);
		point_final_gauche_sur_verticale_gauche=false;
		point_final_gauche_sur_horizontale_haute=true;
	    }else if(sin_teta_exterieur<0.0001){
		point_final_droite.assigne((float)right_ens,centre.y);
		point_final_droite_sur_verticale_droite=true;
		point_final_gauche.assigne((float)left_ens,centre.y);
		point_final_gauche_sur_verticale_gauche=true;
		point_final_gauche_sur_horizontale_haute=false;
	    }
	    if(imprimer_lignes_de_champ)
		System.out.println(" ecart "+ecart);
	    if(!inclue_champ_exterieur){
		po_xyz.assigne(centre,zed);
		champ_au_centre.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
		float tt=champ_au_centre.y/champ_au_centre.x;
		point_initial_sur_la_surface.assigne(pt_de_depart_surf(tt));	
		for (int i=1;i<=20;i++){
		    grp_c.setColor(Color.green);
		    gTampon_ell.setColor(Color.green);
		    tutu.assigne(point_initial_sur_la_surface);
		    coco=tutu.longueur();
		    point_initial.assigne_facteur(tutu,(coco+ecart*i)/coco);
		    //point_initial.assigne_additionne(tutu,toto);
		    point_initial.additionne(centre);
		    tete.assigne(point_initial);
		    if(grande_taille)
			subject.paintrect_couleur(grp_c,2*((int)tete.y-1),2*((int)tete.x-1),2*((int)tete.y+1),2*((int)tete.x+1),Color.green);
			//subject.paintrect_couleur(gTampon_ell,2*((int)tete.y-1),2*((int)tete.x-1),2*((int)tete.y+1),2*((int)tete.x+1),Color.green);
		     else
			subject.paintrect_couleur(gTampon_ell,(int)tete.y-1,(int)tete.x-1,(int)tete.y+1,(int)tete.x+1,Color.green);
		    subject.paintrect_couleur(grp_c,(int)tete.y-1,(int)tete.x-1,(int)tete.y+1,(int)tete.x+1,Color.green);
			//			tete.print_sl(" tete ");
		    if(imprimer_lignes_de_champ)
			point_initial.print_sl(" i  $$$$$$ "+i+" point_initial ");
		    if(!est_dans_la_fenetre(point_initial)){
			if(point_initial.y>bottom_ens||point_initial.x<left_ens){
			    if(point_initial.y>bottom_ens)
				toto.assigne(centre.x,(float)bottom_ens);
			    if(point_initial.x<left_ens)
				toto.assigne((float)left_ens,centre.y);
			    point_limite_gauche.assigne(toto);
			    point_limite_droite.assigne(toto);
			    break;
			}
		    }
		    sortie=false;reentree=false;
		    for (int i_sens=-1;i_sens<2;i_sens+=2)
			fais_la_vraiment(i_sens,true,point_initial);
		    if(sortie&&reentree)
			break;
		}
	    }else{
		point_limite_gauche.assigne(centre.x,(float)bottom_ens);
		point_final_gauche.assigne(centre.x,(float)top_ens+10);
		if(inclue_champ_exterieur){
		    if(index_direction_champ==4||index_direction_champ==0){
			point_limite_gauche.assigne(zed,(float)bottom_ens);
			if(index_direction_champ==4)
			    point_final_gauche.assigne(centre.x+120,(float)top_ens+10);
			if(index_direction_champ==0)
			    point_final_gauche.assigne(left_ens,(float)top_ens);
		    }
		    if(index_direction_champ==2)
			point_final_gauche.assigne(centre.x-40,(float)top_ens);
		    if(index_direction_champ==1)
			point_final_gauche.assigne(centre.x-140,(float)top_ens);
		} 
		//point_limite_droite.assigne(centre.x,(float)bottom_ens);
		//point_final_droite.assigne(centre.x,(float)top_ens+50);

	    }
	    if(imprimer_lignes_de_champ){
		point_final_gauche_sur_horizontale_haute=true;
		point_limite_gauche.print_sl(" point_limite_gauche ");
		point_final_gauche.print_sl(" point_final_gauche ");
		if(!inclue_champ_exterieur){
		    point_final_droite.print_sl(" point_final_droite ");
		    point_limite_droite.print_sl(" point_limite_droite ");
		}
		System.out.println(" point_final_droite_sur_verticale_droite "+point_final_droite_sur_verticale_droite +" point_final_gauche_sur_verticale_gauche "+point_final_gauche_sur_verticale_gauche+" point_final_gauche_sur_horizontale_haute "+point_final_gauche_sur_horizontale_haute);
	    }
	    impr=true;
	    point pt_lgn=new point(point_limite_gauche);
	    if(!du_nouveau)
	    if(Math.abs(point_limite_gauche.y-bottom_ens)<2){
		pt_lgn.y=bottom_ens;
		for (int i=1;i<40;i++){
		    if(Math.abs(pt_lgn.y-bottom_ens)<2){
			pt_lgn.x-=ecart;
			if(imprimer_lignes_de_champ)
			    pt_lgn.print_sl( "$ i "+i+" pt_lgn ");
			if(pt_lgn.x-point_final_gauche.x<0&&point_final_gauche.y>centre.y||pt_lgn.x-left_ens<10){
			    pt_lgn.assigne(left_ens,bottom_ens-40);
			    break;
			}
		    }
		    if(imprimer_lignes_de_champ)
			pt_lgn.print_sl("$$ i "+i+" pt_lgn a gauche " );
		    fais_la_vraiment(1,false,pt_lgn);
		}
	    }
	    if(!du_nouveau)
	    if(point_final_gauche_sur_verticale_gauche||point_final_gauche_sur_horizontale_haute){
		pt_lgn.x=left_ens;
		for (int i=1;i<40;i++){
		    pt_lgn.y-=ecart;
		    if(imprimer_lignes_de_champ)
			pt_lgn.print_sl( "$$$ i "+i+" pt_lgn ");
		    if(pt_lgn.y-point_final_gauche.y<0||pt_lgn.y<top_ens){
			if(pt_lgn.y<top_ens){
			    pt_lgn.y=top_ens;
			    pt_lgn.x=left_ens+40;
			}
			break;
		    }
		    fais_la_vraiment(1,false,pt_lgn);
		}
	    }
	    if(!du_nouveau)
	    if(imprimer_lignes_de_champ)
		System.out.println(" point_final_gauche_sur_horizontale_haute "+point_final_gauche_sur_horizontale_haute);	    
	    if(point_final_gauche_sur_horizontale_haute){
		pt_lgn.assigne(left_ens,top_ens);
		for (int i=1;i<40;i++){
		    pt_lgn.x+=ecart;
		    if(pt_lgn.x-point_final_gauche.x>0){
			break;
		    }
		    point_sur_la_ligne.assigne(pt_lgn);
		    if(imprimer_lignes_de_champ)
			pt_lgn.print_sl("$$$$ i "+i+" pt_lgn a droite ");
		    fais_la_vraiment(1,false,pt_lgn);
		}
	    }
	    if(!inclue_champ_exterieur){
		pt_lgn=new point(point_limite_droite);
		if(imprimer_lignes_de_champ)
		    point_limite_droite.print_sl(" avant utilisation point_limite_droite ");
		if(!du_nouveau)
		if(Math.abs(point_limite_droite.x-left_ens)<2){
		    for (int i=1;i<40;i++){
			pt_lgn.y+=ecart;
			if(pt_lgn.y-bottom_ens>0){
			    pt_lgn.assigne((float)left_ens,(float)bottom_ens);
			    break;
			}
			if(imprimer_lignes_de_champ)
			    pt_lgn.print_sl("$$$$$ i "+i+" pt_lgn a droite ");
			fais_la_vraiment(-1,false,pt_lgn);
		    }
		}
		if(!du_nouveau)
		if(Math.abs(pt_lgn.y-bottom_ens)<2){
		    pt_lgn.x-=ecart;
		    for (int i=1;i<40;i++){
			pt_lgn.x+=ecart;
			if(pt_lgn.x-right_ens>0||pt_lgn.x-point_final_droite.x>0)
			    break;
			if(imprimer_lignes_de_champ)
			    pt_lgn.print_sl("$$$$$$ i "+i+" pt_lgn a droite ");
			fais_la_vraiment(-1,false,pt_lgn);
		    }
		}
	    if(imprimer_lignes_de_champ)
		    System.out.println(" point_final_droite_sur_verticale_droite "+point_final_droite_sur_verticale_droite); 
	    if(!du_nouveau)
		if(point_final_droite_sur_verticale_droite){
		    pt_lgn.assigne(right_ens,bottom_ens);
		    for (int i=1;i<40;i++){
			pt_lgn.y-=ecart;
			if(pt_lgn.y-point_final_droite.y<0)
			    break;
			if(imprimer_lignes_de_champ)
			    pt_lgn.print_sl("2 i "+i+" pt_lgn a droite ");
			fais_la_vraiment(-1,false,pt_lgn);
		    }
		}
	    }
	}
	/*
	  if(!electrique)
	  D_prec[1000]=null;
	*/
	void fais_la_vraiment(int i_sens1,boolean determiner_limites,point pt_lg1){
	    point pt_lg=new point(pt_lg1);
	    int i_sens=i_sens1;
	    inside_prec=inclue_champ_exterieur;
	    if(imprimer_lignes_de_champ)
		pt_lg.print_sl(" i_sens "+i_sens+" pt_lg ");
	    for (int kk=0;kk<1500;kk++){
		po_xyz.assigne(pt_lg,zed);
		chpt.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());

		unitaire_suivant_champ.assigne_diviseur(chpt,i_sens*chpt.longueur());
		//unitaire_init.assigne_diviseur(chpt,i_sens*chpt.longueur());
		unitaire_init.assigne_diviseur(champ_exterieur,i_sens*champ_exterieur.longueur());
		pt_lg_prec.assigne(pt_lg);
		pt_lg.additionne(unitaire_suivant_champ);
		inside=est_dans_la_fenetre(pt_lg);
		if(inclue_champ_exterieur&&!inside&&!inside_prec)
		    break;
		if(determiner_limites){
		    if(kk!=0){
			if(!inside&&inside_prec){
			    point_limite_gauche.assigne(pt_lg_prec);
			    if(imprimer_lignes_de_champ)
				pt_lg.print_sl("££ kk "+kk+" pt_lg_prec x "+pt_lg_prec.x+" y "+pt_lg_prec.y+"pt_lg "); 
			    sortie=true;
			}
			if(inside&&!inside_prec){
			    point_limite_droite.assigne(pt_lg);
			    if(imprimer_lignes_de_champ)
				pt_lg.print_sl("$$ kk "+kk+" pt_lg_prec x "+pt_lg_prec.x+" y "+pt_lg_prec.y+"pt_lg "); 
			    reentree=true;
			}
			//if(inside_prec&&!inside)
			//break;
		    }
		}
		toto.assigne_soustrait(pt_lg,centre);
		if(imprimer_lignes_de_champ)
		    if(impr&&(kk/100*100==kk||est_dedans(toto,0)))
			pt_lg.print_sl(" kk  "+kk+" inside_prec "+inside_prec+" inside "+inside+" pt_lg ");
		inside_prec=inside;
		if(est_dedans(toto,10))
		    break;
		if(inside){
		    x_int=(int)Math.round(pt_lg.x);
		    y_int=(int)Math.round(pt_lg.y);
		    if(grande_taille)
			//gTampon_ell.drawLine(2*x_int,2*y_int,2*x_int,2*y_int);
			//grp_c.drawLine(2*x_int,2*y_int,2*x_int,2*y_int);
			subject.paintrect_couleur(grp_c,2*(y_int-1),2*(x_int-1),2*(y_int+1),2*(x_int+1),Color.green);
		    else
			subject.paintrect_couleur(grp_c,(y_int-1),(x_int-1),(y_int+1),(x_int+1),Color.green);
		    subject.paintrect_couleur(gTampon_ell,(y_int-1),(x_int-1),(y_int+1),(x_int+1),Color.green);
			//gTampon_ell.drawLine(x_int,y_int,x_int,y_int);
			//grp_c.drawLine(x_int,y_int,x_int,y_int);
		    //System.out.println(" x_int "+x_int+" y_int "+y_int); 
		}
		toto.assigne_soustrait(pt_lg,centre);
		titi.assigne_soustrait(centre,toto);
		if(est_dans_la_fenetre(titi)){
		    x_int1=(int)Math.round(titi.x);
		    y_int1=(int)Math.round(titi.y);
		    if(grande_taille)
			//grp_c.drawLine(2*x_int1,2*y_int1,2*x_int1,2*y_int1);
			subject.paintrect_couleur(grp_c,2*(y_int1-1),2*(x_int1-1),2*(y_int1+1),2*(x_int1+1),Color.green);
		    //gTampon_ell.drawLine(2*x_int1,2*y_int1,2*x_int1,2*y_int1);
		    else
			subject.paintrect_couleur(grp_c,(y_int1-1),(x_int1-1),(y_int1+1),(x_int1+1),Color.green);
		    subject.paintrect_couleur(gTampon_ell,(y_int1-1),(x_int1-1),(y_int1)-1,(x_int1+1),Color.green);
		    //gTampon_ell.drawLine(x_int1,y_int1,x_int1,y_int1);
		    //grp_c.drawLine(x_int1,y_int1,x_int1,y_int1);
		    //stem.out.println(" x_int1 "+x_int1+" y_int1 "+y_int1); 
		}

		k_fin=kk;
	    }
	}

	void dessine_champ_exterieur(Graphics g){
	    toto.assigne(100*cos_teta_exterieur,100*sin_teta_exterieur);
	    titi.assigne(centre.x-1,centre.y-1);
	    tutu.assigne(zed,flun);
	    vecteur v=new vecteur(toto,titi);
	    for (int i=-1;i<=2;i++){
		v.dessine((float)40.,flun,g,Color.black);
		v.pnt.additionne_facteur(tutu,(float)i);
	    }
	    gr_de_cote.setColor(Color.black);
	    g.setColor(Color.black);
	    g.setFont(subject.times_gras_14);
	    g.drawString(" Fleche en noir: direction du champ exterieur ",230,60);
	    if(electrique)
		g.drawString(" Carres en noir: centres des charges + et - ",230,80);
	}
	class centres_charges{
	    point centre_charges_plus=new point(zer);
	    point centre_charges_moins=new point(zer);
	    float total_charges_plus=0,total_charges_moins=0;
	    centres_charges(){
	    }
	    void reset(){
		centre_charges_plus.assigne(zer);
		centre_charges_moins.assigne(zer);
		total_charges_plus=0;
		total_charges_moins=0;
	    }
	    void incrementer(point posi,float densite, float surface){
		if(densite>0){
		    centre_charges_plus.additionne_facteur(posi,densite*surface);
		    total_charges_plus+=densite*surface;
		}else{
		    centre_charges_moins.additionne_facteur(posi,densite*surface);
		    total_charges_moins+=densite*surface;
		}
	    }
	    void calcule(){
		centre_charges_plus.divise_cst(total_charges_plus);
		centre_charges_moins.divise_cst(total_charges_moins);
		centre_charges_plus.print(" centre_charges_plus ");	
		centre_charges_moins.print(" centre_charges_moins ");	
	    }
	    void dessine(Graphics g){
		    g.setColor(Color.black);
		    g.fillRect((int)centre_charges_plus.x-4,(int)centre_charges_plus.y-4,8,8);
		    g.fillRect((int)centre_charges_moins.x-4,(int)centre_charges_moins.y-4,8,8);
	    }
	}
	class palette{
	    final static int top_palette=60,left_palette=10,right_palette=110,bot_palette=88;
	    int indice_lamb=0;
	    //int min_palette=20,max_palette=50;
	    int min_palette=20,max_palette=89;
	    float I_ou_Q_min=zed,I_ou_Q_max=zed;
	    couleur couleur_de_la_lumiere;
	    final couleur farbe_bei_laenge[]=new couleur [100]; 
	    String toto_string="";
	    String string_min="",string_max="";
	    palette(){
		couleur_de_la_lumiere=new couleur(255,255,255);
		int v=0,w=0;
		for(int i=0;i<100;i++){
		    if(i<27)  //ultra bleu
			farbe_bei_laenge[i]= new couleur(0,0,(int)(255*i/27.));
		    else if(i<50){ //entre bleu et vert
			v=i-27;
			w=50-i;
			if(v>w){
			    w=(int)(255.*w)/v;
			    v=255;
			}else{
			    v=(int)(255.*v)/w;
			    w=255;		    
			}
			farbe_bei_laenge[i]= new couleur(0,v,w);
		    }
		    else if(i<83){ //entre vert et rouge
			v=i-50;
			w=83-i;
			if(v>w){
			    w=(int)(255.*w)/v;
			    v=255;
			}else{
			    v=(int)(255.*v)/w;
			    w=255;		    
			}
			farbe_bei_laenge[i]= new couleur(v,w,0);		
			//farbe_bei_laenge[i]= new couleur((int)(255*(i-50)/33.),(int)(255*(83-i)/33.),0);		
		    }else if(i>=83)//infra rouge
			farbe_bei_laenge[i]= new couleur((int)(255*(100-i)/17.),0,0);
		}
		for(int i=0;i<20;i++)
		    farbe_bei_laenge[i].assigne(farbe_bei_laenge[i+20]);	     
	    }
	    void initie(){
		System.out.println(" palette initiee ");
		if(!electrique){
		    I_ou_Q_max=zed;
		    I_ou_Q_min=(float)1.e8;
		}else{
		    I_ou_Q_max=0;
		    I_ou_Q_min=0;
		}
	    }
	    void met_a_jour(float I_ou_Q){
		/*
		if(!electrique)
		    if(index_direction_champ<4&&I_ou_Q<0||index_direction_champ==4&&I_ou_Q>0)
		    return;
		*/
		if(I_ou_Q>I_ou_Q_max)
		    I_ou_Q_max=I_ou_Q;
		if(I_ou_Q<I_ou_Q_min)
		    I_ou_Q_min=I_ou_Q;
	    }
	    void met_a_jour_par_64_memoires(float[] I_ou_Q,int nb_mem){
		i_trouve_max=-1;
		for (int i=0;i<nb_mem;i++)
		    if(!electrique&&I_ou_Q[i]>0||electrique){
			if(I_ou_Q[i]>I_ou_Q_max){
			    I_ou_Q_max=I_ou_Q[i];
			    i_trouve_max=i;
			}
			if(I_ou_Q[i]<I_ou_Q_min)
			    I_ou_Q_min=I_ou_Q[i];
			if(affiche_les_64_memoires&&i==31)
			    System.out.println("i "+i+" I_ou_Q[i] "+I_ou_Q[i]+" I_ou_Q_max "+I_ou_Q_max+" i_trouve_max "+i_trouve_max); 
		    }
	    }
	    void dessine(Graphics g_cote){
		coco= (float)Math.round(I_ou_Q_min*mu0_ou_un_sur_eps0*100)/100;
		string_min=""+coco;
		coco= (float)Math.round(I_ou_Q_max*mu0_ou_un_sur_eps0*100)/100;
		string_max=""+coco;
		if(electrique) 
		    toto_string=" Cb/pixel2 /eps0 ";
		else
		    toto_string=" A/pixel *µ0";

		g_cote.setColor(Color.black);
		g_cote.setFont(subject.times_gras_14);
		g_cote.drawString(string_min,left_palette+20,bot_palette+12);
		g_cote.drawString(string_max,left_palette+170,bot_palette+12);
		g_cote.drawString(toto_string,left_palette+35,bot_palette-32);
		if(electrique) 
		    toto_string=" Densite de charge /eps0 ";
		else
		    toto_string=" Densite de courant *µ0";
		g_cote.drawString(toto_string,left_palette+35,bot_palette+27);
		for(int i=min_palette;i<max_palette;i++)
		    for(int j=0;j<2;j++)
			subject.drawline_couleur(g_cote,left_palette+2*i+j,bot_palette,left_palette+2*i+j,top_palette,farbe_bei_laenge[i].col);
		//ppv=left_palette+(int)Math.round((right_palette-left_palette)*(lambda-min_palette)/(max_palette-min_palette));
		//subject.drawline_couleur(g_cote,ppv,bot_palette,ppv,top_palette,Color.black );
	    }
	    couleur determine_la_couleur(float courant_ou_charge){
		//System.out.println(" courant_ou_charge "+(float)courant_ou_charge);
		couleur_de_la_lumiere.assigne(0,0,0);
		if((courant_ou_charge>=I_ou_Q_min&&courant_ou_charge<=I_ou_Q_max)){
		    coco=(courant_ou_charge-I_ou_Q_min)/(I_ou_Q_max-I_ou_Q_min);
		    indice_lamb=min_palette+(int)(coco*(max_palette-min_palette));
		    if(indice_lamb<min_palette)
			indice_lamb=min_palette;
		    if(indice_lamb>=max_palette)
			indice_lamb=max_palette;
		    //System.out.println(" indice_lamb "+indice_lamb+" coco "+(float)coco);
		    couleur_de_la_lumiere.assigne(farbe_bei_laenge[indice_lamb]);
		}
		return couleur_de_la_lumiere;
	    }
	}
	abstract boolean far_from_boarders(point dist);
	abstract point pt_de_depart_surf(float tt);
    }
    class ellipsoide extends bille_ou_barreau_cylindrique  implements Cloneable{
	variables_bouchons VAR_B;
	variables_geometriques_tuyau VAR_T;
	courants_et_charges charges_et_courants;
	boolean tuyau_central=false,images_deja_faites=false;
	boolean peindre_champs=true,peindre_densites=true;
	point pt_aux;
	bi_point_xyz bi_chp_later=new bi_point_xyz();
	point_xyz mu0_sig_sur_2=new point_xyz(zer_xyz);
	point_xyz sig_sur_2=new point_xyz(zer_xyz);
	bi_point_xyz I_B_xyz=new  bi_point_xyz();
	bi_point_xyz I_T_xyz=new  bi_point_xyz();
	bi_point_xyz bi_fieldd =new  bi_point_xyz();
	point_xyz u_cr_x_constant=new  point_xyz(zer_xyz);
	point_xyz u_cr_y_constant=new  point_xyz(zer_xyz);
	point_xyz mu0_sig_sur_2_x_constant= new point_xyz(zer_xyz);
	point_xyz mu0_sig_sur_2_y_constant= new point_xyz(zer_xyz);
	bi_point_xyz fluss=new bi_point_xyz();
	float tg_tet_tg=0,reduction_ellipse=0,ccxy=0;
	float sum_x_avant=0,ss=0;
	point facteur_central=new point(zer);
	point champ_au_centre_avant=new point(zer);
	point ssg=new point(zer),sisig=new point(zer);
	int iteration=0;
	boolean vraiment_boule=false,vraiment_boule0=vraiment_boule;
	point_xyz pt_xyz,pt_aux_xyz;
	ellipsoide(point ctr1, float rayon1, float longueur1,float largeur1){
	    super();
	    centre=new point(ctr1);
	    centre_multipole.assigne(centre);
	    rayon=(int)rayon1;
	    longueur=(int)longueur1;
	    largeur=(int)largeur1;
	    tuyau_central=(largeur!=0);
	    //if(electrique)
	    //	field[1000]=null;
	    index_direction_champ0=index_direction_champ;
	    determine_la_direction_ext(index_direction_champ);
	    for (int k=0;k<=4;k++){
		determine_la_direction_ext(k);
		champ_exterieur_xyz.print("k "+k+" champ_exterieur_xyz "); 
	    }
	    index_direction_champ=index_direction_champ0;
	    determine_la_direction_ext(index_direction_champ);	    
	    champ_en_un_point=new point_xyz(zer,zed);
	    pt_xyz=new point_xyz(zer_xyz);
	    pt_aux_xyz=new point_xyz(zer_xyz);
	    pt_aux=new point(zer);
	    raccourci_symetrique=raccourci_symetrique0&&(index_direction_champ==0||index_direction_champ==4);
	    rapport_des_axes=(float)2.;
	    if(i_demarre==4)
		rapport_des_axes=flun;
	    if(i_demarre==3)
		rapport_des_axes=(float)0.5;
	    bb=rayon1;
	    aa=bb*rapport_des_axes;
	    /*
	      aa=rayon1;
	      bb=aa*rapport_des_axes;
	    */
	    aa2=aa*aa;
	    bb2=bb*bb;
	    coco=(float)Math.pow((aa-bb)/(aa+bb),2);

	    //if(Math.abs(rapport_des_axes-flun)<0.05&&!tuyau_central)
	    //vraiment_boule=Math.abs(rapport_des_axes-flun)<0.05;
	    vraiment_boule0=vraiment_boule;
	    champ_exterieur.print_sl(" index"+index_direction_champ+" electrique "+electrique+" champ_exterieur ");
	    if(index_direction_champ==4&&!electrique){
		subject.n_passage++;
		if(subject.n_passage==2){
		    System.out.println(" subject.n_passage "+subject.n_passage);
		}
	    }
	    if(electrique)
		toto_string=" Densites de charges par pixel carre ";
	    else
		toto_string=" Densites de courants par pixel ";
	    //if(i_ens==0)
	    //subject.ens_de_dipoles[1].ellipsoid.iteration=0;
	    //if(i_ens==1||subject.creation_d_un_ensemble_venue_d_un_ensemble){
	    appelle_VAR_B(true);
	    if(tuyau_central)
		appelle_VAR_T();
		//if(i_demarre==3)
	    subject.creation_d_un_ensemble_venue_d_un_ensemble=false;
	    charges_et_courants=new courants_et_charges(toto_titre);
	    du_nouveau=false;

	    fais_les_calculs(true);
	}
	void cloner(){
	    System.out.println(" ellipsoid "+ellipsoid);
	    ellipsoid_clone=(ellipsoide)subject.ens_de_dipoles[i_ens].ellipsoid.clone();
	    System.out.println(" ellipsoid_clone "+ellipsoid_clone);
	}
	public Object clone(){
	    try{	      
	     return (ellipsoide)super.clone();
	    }
	    catch (CloneNotSupportedException e){
		return null;
	    }
	}
	void appelle_VAR_B(boolean initiale){
	    VAR_B=null;
	    VAR_B=new variables_bouchons();
	}
	void appelle_VAR_T(){
		VAR_T=null;
		VAR_T=new variables_geometriques_tuyau();
	}
	void fais_les_calculs(boolean initi){
	    boolean totolog=false;
	    if(subject.ens_de_dipoles[1-i_ens]!=null)
		if(subject.ens_de_dipoles[1-i_ens].ellipsoid!=null)
		    totolog=subject.ens_de_dipoles[1-i_ens].prendre_l_autre_ensemble;
	    if(!images_deja_faites&&!totolog){
		cliquee=false;
		pressee=false;
		relachee=false;
		electrique=electrique0;
		facteur_central.assigne(zer);
		//if(!electrique&&initi)
		//return;
		raccourci_symetrique=raccourci_symetrique0&&(index_direction_champ==0||index_direction_champ==4);
		//vraiment_boule=Math.abs(rapport_des_axes-flun)<0.05;
		if(effacer)
		    subject.eraserect(graph,0,0,600,600,Color.white);
		graph.setColor(Color.black);
		champ_exterieur.print(" champ_exterieur ");
		//if(vraiment_boule!=vraiment_boule0)
		if(!initi){
		    appelle_VAR_B(false);
		    if(tuyau_central)
			appelle_VAR_T();
		}
		po_xyz.assigne(centre,zed);
		//ecrire_champ=true;
		champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
		ecrire_champ=false;
		champ_au_centre.print(" champ_au_centre initial ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("FIN INITIAL ");
		if(index_direction_champ!=4)
		    facteur_central.x=champ_exterieur.x/champ_au_centre.x;
		if(index_direction_champ!=0)
		    facteur_central.y=champ_exterieur.y/champ_au_centre.y;
		facteur_central.print(" facteur_central ");
	    
	    
		System.out.println(" ");
		System.out.println(" ");
	    
		recalcule_les_sig_ou_I();
	    
		System.out.println(" ");
		System.out.println(" ");
		po_xyz.assigne(centre,zed);
		champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
		champ_au_centre.print(" champ_au_centre ");
		for (int k=0;k<2;k++){
		    System.out.println(" ");
		    System.out.println(" ");
		    champ_au_centre_avant.assigne(champ_au_centre);	
		    modifie_les_Q_ou_I();
		    System.out.println(" ");
		    System.out.println("APRES MODIFIE k"+k);
		    po_xyz.assigne(centre,zed);
		    champ_au_centre.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
		    champ_au_centre.print("  Finies modifs champ_au_centre par les charges finales ");
		    System.out.println(" ");
		    if(index_direction_champ!=4)
			facteur_central.x*=(champ_au_centre_avant.x/champ_au_centre.x);
		    if(index_direction_champ!=0)
			facteur_central.y*=(champ_au_centre_avant.y/champ_au_centre.y);
		    facteur_central.print(" facteur_central apres modifie");

		}
		  champ_au_centre.print("  Finies modifs champ_au_centre par les charges finales ");

		po_xyz.assigne(centre,zed);
		champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
		champ_au_centre.print(" champ_au_centre par les charges finales ");
		if(!du_nouveau||initi)
		    chp_clc.remplis();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("APRES REMPLIS ");
		System.out.println(" ");
		System.out.println(" ");
		if(!du_nouveau||initi)
		    paint(true);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("APRES PAINT, AVANT DESSINE DE COTE ");
		System.out.println(" ");
		charges_et_courants.dessine_de_cote();
		System.out.println(" ");
		images_deja_faites=true;
		peindre_champs=false;
		peindre_densites=false;
	    }else{
		if(!totolog){
		    if(peindre_champs){
			peinture(image_ell);
			if(!grande_taille&&charges_et_courants!=null)
			    charges_et_courants.setVisible(true);      
		    }
		    if(peindre_densites)
			charges_et_courants.peinture_densite(charges_et_courants.image_densite);
		}else{
		    System.out.println("ùùùùùùùùùùù i_ens "+i_ens);
			peinture(subject.ens_de_dipoles[1-i_ens].image_ell);
			charges_et_courants.peinture_densite(subject.ens_de_dipoles[1-i_ens].ellipsoid.charges_et_courants.image_densite);
			subject.ens_de_dipoles[1-i_ens].prendre_l_autre_ensemble=false;
		}
	    }
	    System.out.println(" grande_taille "+grande_taille+" grande_taille_densite "+grande_taille_densite);
	    //suscepti[1000]=0;
	    pressee=false;
	    relachee=false;
	    cliquee=false;

	}
	void horloge(){
	    toto_int=(int)Math.round(p_horloge_prec.y-1);
	    titi_int=(int)Math.round(p_horloge_prec.x-1);
	    subject.paintrect_couleur(grp_c,toto_int,titi_int,toto_int+2,titi_int+2,Color.white);
	    p_horloge_prec.assigne(p_horloge);  
	    i_horloge++;
	    if(i_horloge==64)
		i_horloge=0;
	    toto.assigne(cos_phi[i_horloge],sin_phi[i_horloge]);
	    p_horloge.assigne_facteur(toto,(float)20);
	    p_horloge.additionne(centre);
	    toto_int=(int)Math.round(p_horloge.y-1);
	    titi_int=(int)Math.round(p_horloge.x-1);
	    subject.paintrect_couleur(grp_c,toto_int,titi_int,toto_int+2,titi_int+2,Color.black);
	}

	void peinture(Image imag){
	    System.out.println(" debut peinture  grande_taille "+grande_taille);
	    if(grande_taille){
		/*
		subject.ens_de_dipoles[i_ens].setSize((right_ens-left_ens)*2,2*(bottom_ens-top_ens));
		if(i_ens==1)
		    subject.ens_de_dipoles[i_ens].setLocation(left_ens,top_ens);
		*/
		setSize((right_ens-left_ens)*2,2*(bottom_ens-top_ens));
		if(i_ens==1)
		    setLocation(left_ens,top_ens);
		//		Point loc=new Point(subject.ens_de_dipoles[i_ens].getLocation());
		Point loc=new Point(getLocation());
		System.out.println(" loc.x "+loc.x+" loc.y "+loc.y);
		//	Dimension dim=new Dimension(subject.ens_de_dipoles[i_ens].getSize());
		Dimension dim=new Dimension(getSize());
		System.out.println(" dim "+dim);
		grp_c.drawImage(imag,0,0,2*(right_ens-left_ens),2*(bottom_ens-top_ens),subject.ens_de_dipoles[i_ens]);

	    }else{
		System.out.println("µµµµµµµµµµ left_ens "+left_ens+" i_ens "+i_ens+" top_ens "+top_ens);
		//		subject.ens_de_dipoles[i_ens].setLocation(left_ens+i_ens*600,top_ens);
		//subject.ens_de_dipoles[i_ens].setSize((right_ens-left_ens),(bottom_ens-top_ens));
		setLocation(left_ens+i_ens*600,top_ens);
		setSize((right_ens-left_ens),(bottom_fenetre-top_ens));
		Point loc=new Point(getLocation());
		System.out.println(" loc.x "+loc.x+" loc.y "+loc.y);
		Dimension dim=new Dimension(getSize());
		System.out.println(" dim "+dim);
		grp_c.drawImage(imag,0,0,subject.ens_de_dipoles[i_ens]);
	    }
	}
	float sigma_ou_I_xy(point_xyz vect,point_xyz chpp_xyz,float sig_ou_I,boolean x_pas_y){
	    cici=chpp_xyz.scalaire(vect);
	    if(x_pas_y){
		champ_exterieur_scal=champ_exterieur_xyz.x*vect.x;
		//cici=chpp_xyz.x*vect.x;
	    }else{
		champ_exterieur_scal=champ_exterieur_xyz.y*vect.y;
		//cici=chpp_xyz.y*vect.y;
	    }
	    if(!metal){
		if(electrique){
		    return susceptibilite/(1+susceptibilite/2)*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
		}else{
		    //return (champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;

		    //return 2*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
		    //return 6*(cici)/mu0_ou_un_sur_eps0;
		    //po_xyz.assigne(centre,zed);
		    //champ_au_centre.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
		    if(x_pas_y){
			mu0_sig_sur_2_x_constant.assigne_facteur(vect,sig_ou_I*mu0_ou_un_sur_eps0/2);
			champ_total_x_constant.assigne_additionne(mu0_sig_sur_2_x_constant,bi_chp_later.p_x);
			//coco=(champ_total_x_constant.x*vect.x+champ_exterieur_scal)/mu0_ou_un_sur_eps0;
			coco=(champ_total_x_constant.x*vect.x)/mu0_ou_un_sur_eps0*facteur_central.x;
		    }else{
			mu0_sig_sur_2_y_constant.assigne_facteur(vect,sig_ou_I*mu0_ou_un_sur_eps0/2);
			champ_total_y_constant.assigne_additionne(mu0_sig_sur_2_y_constant,bi_chp_later.p_y);
			//	coco=(champ_total_y_constant.y*vect.y+champ_exterieur_scal)/mu0_ou_un_sur_eps0;
			coco=(champ_total_y_constant.y*vect.y)/mu0_ou_un_sur_eps0*facteur_central.y;
		    }
		    return coco;
		}
	    }else
		return 2*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
	    //return cici/mu0_ou_un_sur_eps0;
	}
	public void determine_la_direction_ext(int i){
	    index_direction_champ=i;
	    float angle=i*pi/6;
	    if(i==2)
		angle=pi/4;
	    if(i>=3)
		angle=pi/2-(4-i)*pi/6;
	    champ_exterieur.assigne((float)Math.cos(angle),(float)Math.sin(angle));
	    calc_ext();
	}
	void calc_ext(){
	    cos_teta_exterieur=champ_exterieur.x/champ_exterieur.longueur();
	    sin_teta_exterieur=champ_exterieur.y/champ_exterieur.longueur();
	    if(cos_teta_exterieur>0.0001)
		tg_teta_exterieur=cos_teta_exterieur/sin_teta_exterieur;
	    champ_exterieur_xyz=new point_xyz(champ_exterieur,zed);
	}
	public void ecrit_aide(){
	    
	    System.out.println(" debut ecrit_aide i_demarre "+i_demarre );
	    for(int ik=0;ik<2;ik++)    
		subject.eraserect(graph,top_ens,left_ens+i_ens*600,bottom_fenetre,right_ens,Color.red);
	    String name_comm="";
	    if(electrique)
		name_comm="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/dipoles_methode_electricite.jpg";
	    else
		name_comm="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/dipoles_methode_magnetisme.jpg";

	    //name_comm="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/dipoles_premiere_page.jpg";
	    System.out.println(name_comm);	    
	    //image_comm=createImage(400,400);
	    Image image_comm=createImage(400,700);
	    Graphics gTTampon=image_comm.getGraphics();
	    image_comm=Toolkit.getDefaultToolkit().getImage(name_comm);
	    //Mediatracker trackerr=new MediaTracker(subject);
	    //MediaTracker trackerr=new MediaTracker(this);
	    subject.tracker.addImage(image_comm,1);
	    try {subject.tracker.waitForID(1); }
	    catch (InterruptedException e) {
		System.out.println(" image_comm pas arrivee?");
	    }
	    //int imagew=image_comm.getWidth(this);
	    //int imageh=image_comm.getHeight(this);
	    //System.out.println(image_comm+" imagew "+imagew+" imageh "+imageh );
		
	    setVisible(true);	    
	    grp_c.setColor(Color.black);
	    System.out.println(" image_comm "+image_comm);	    
	    for(int ik=0;ik<1;ik++)    
		grp_c.drawImage(image_comm,20,50,subject.ens_de_dipoles[i_ens]);
	    //gTTampon.dispose();
	    //gTTampon=null;
	    ecrit_aide_fait=true;
	}
	class variables_geometriques_tuyau{
	    electricite ELEC;
	    magnetisme MAG;
	    int ittet[]=new int[1024];
	    int iiLL[]=new int[1024];
	    int index[][]=new int[32][32];
	    boolean prrint=false;
	    variables_geometriques_tuyau(){
		int ii=0;
		for (int itet=0;itet<32;itet++)
		    for (int ifi=0;ifi<32;ifi++){
			ittet[ii]=itet;
			iiLL[ii]=ifi;
			index[itet][ifi]=ii++; 			    
		    }
		if(electrique){
		    ELEC=null;
		    ELEC=new electricite();
		    ELEC.calculs_initiaux();
		}else{
		    MAG=null;
		    MAG=new magnetisme();
		    MAG.calculs_initiaux();
		}
	    }
	    void renorme(){
		for (int i=0;i<1024;i++){
		    if(electrique){
			ELEC.charges_tuyau[i].sigma_laterale.x*=rapport_renor.x;//en fait sigma_laterale_x_constant 
			ELEC.charges_tuyau[i].sigma_laterale.y*=rapport_renor.y;//en fait sigma_laterale_y_constant
		    }else{
			MAG.courants_tuyau[i].courant_lateral.p_x.y*=rapport_renor.x;
			MAG.courants_tuyau[i].courant_lateral.p_x.z*=rapport_renor.x;
			MAG.courants_tuyau[i].courant_lateral.p_y.x*=rapport_renor.y;
			MAG.courants_tuyau[i].courant_lateral.p_y.z*=rapport_renor.y;
		    }
		}
	    }
	    class electricite{
		hb_tet_av_fi charges_tuyau[]=new hb_tet_av_fi[1024]; 
		hb_tet_av_fi q_tuyau=new hb_tet_av_fi(); 
		boolean initial=true;
		electricite(){
		    for (int i=0;i<1024;i++)
			charges_tuyau[i]=new hb_tet_av_fi();
		}
		void calculs_initiaux(){
		    if(!initial)
			for (int i=0;i<1024;i++)
			    charges_tuyau[i].zero();
		    int i=0;
		    for (int itetafi=0;itetafi<32;itetafi++){
			vecteur_unite_xyz.assigne(zed,VAR_B.geom.proj_du_ray[itetafi]);
			champ_exterieur_scal=champ_exterieur_xyz.scalaire(vecteur_unite_xyz)/mu0_ou_un_sur_eps0;
			for (int iL=0;iL<32;iL++){
			    i=index[itetafi][iL];
			    charges_tuyau[i].sigma_laterale.assigne(champ_exterieur.x*vecteur_unite_xyz.x,champ_exterieur.y*vecteur_unite_xyz.y);//en fait sigma_laterale_x_constant et  sigma_laterale_y_constant
			    charges_tuyau[i].sigma_laterale.divise_cst(mu0_ou_un_sur_eps0);
			}
		    }
		}
		    void calcule_composantes_charges(int i){
			int itet=ittet[i];
			int iL=iiLL[i];
			q_tuyau=charges_tuyau[i];
			vecteur_unite_xyz.assigne(zed,VAR_B.geom.proj_du_ray[itet]);
			q_tuyau.sigma_reca_tuyau.x=sigma_ou_I_xy(vecteur_unite_xyz,bi_chp_later.p_x,q_tuyau.sigma_laterale.x,true);//en fait sigma_reca_tuyau_x_constant
			q_tuyau.sigma_reca_tuyau.y=sigma_ou_I_xy(vecteur_unite_xyz,bi_chp_later.p_y,q_tuyau.sigma_laterale.y,false);//en fait sigma_reca_tuyau_y_constant
			mu0_sig_sur_2_x_constant.assigne_facteur(vecteur_unite_xyz,q_tuyau.sigma_laterale.x*(-mu0_ou_un_sur_eps0/2));//en fait sigma_laterale_x_constant
			champ_total_x_constant.assigne_additionne(mu0_sig_sur_2_x_constant,bi_chp_later.p_x);
			vecteur_unite_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[itet]);
			mu0_sig_sur_2_y_constant.assigne_facteur(vecteur_unite_xyz,q_tuyau.sigma_laterale.y*(-mu0_ou_un_sur_eps0/2));//en fait sigma_laterale_y_constant
			champ_total_y_constant.assigne_additionne(mu0_sig_sur_2_y_constant,bi_chp_later.p_y);
			if(prrint)
			if(((itet==7||itet==24))&&(iL==31||iL==63||iL==7||iL==0)){
			    titi.assigne_facteur(q_tuyau.sigma_laterale,mu0_ou_un_sur_eps0);
			    tutu.assigne_facteur(q_tuyau.sigma_reca_tuyau,mu0_ou_un_sur_eps0);
			    titi.print(" mu0.sigma_laterale ");
			    tutu.print("mu0.sigma_reca_tuyau "); 
			    vecteur_unite_xyz.print(" normale laterale ");
			    bi_chp_later.print(" bi_chp_later ");
			    if(prrint)
			    if(index_direction_champ!=4){
				mu0_sig_sur_2_x_constant.print(" mu0_sig_sur_2_x_constant ");
				champ_total_x_constant.print(" champ_total_x_constant ");
			    }
			    if(prrint)
			    if(index_direction_champ!=0){
				mu0_sig_sur_2_y_constant.print(" mu0_sig_sur_2_y_constant ");
				champ_total_y_constant.print(" champ_total_y_constant ");
			    }
			}
		    }
		class hb_tet_av_fi{
		    point sigma_laterale=new point(zer);//en fait sigma_laterale_x_constant et  sigma_laterale_y_constant
		    point sigma_reca_tuyau=new point(zer);//en fait sigma_reca_tuyau_x_constant et  sigma_reca_tuyauy_constant
		    hb_tet_av_fi(){
		    }
		    void zero(){
			sigma_laterale.assigne(zer);
			sigma_reca_tuyau.assigne(zer);
		    }
		}
	    }
	    class magnetisme{
		hb_tet_av_fi courants_tuyau[]=new hb_tet_av_fi[1024]; 
		hb_tet_av_fi i_tuyau=new hb_tet_av_fi(); 
		point_xyz courant_lateral_droit=new point_xyz(zer_xyz);
		point_xyz courant_lateral_circ=new point_xyz(zer_xyz);
		boolean initial=true;
		magnetisme(){
		    for (int i=0;i<1024;i++)
			courants_tuyau[i]=new hb_tet_av_fi();
		}
		void calculs_initiaux(){
		    if(!initial)
			for (int i=0;i<1024;i++)
			    courants_tuyau[i].zero();
		    initial=false;
		    point_xyz u_courant=new point_xyz(zer_xyz);
		    point_xyz tangente_lat_droit=new point_xyz(zer_xyz);
		    point_xyz tangente_lat_circ=new point_xyz(zer_xyz);
		    for (int itetafi=0;itetafi<32;itetafi++){
			tangente_lat_droit.assigne(zed,VAR_B.geom.proj_perp_au_ray[itetafi]);		
			tangente_lat_droit.assigne_oppose();
			u_courant.assigne(flun,zed,zed);
			//coco=champ_exterieur_xyz.scalaire(tangente_lat_droit[itetafi])/mu0_ou_un_sur_eps0;
			coco=champ_exterieur_xyz.y*tangente_lat_droit.y/mu0_ou_un_sur_eps0;
			courant_lateral_droit.assigne_facteur(u_courant,coco);	

			u_courant.assigne(zed,VAR_B.geom.proj_perp_au_ray[itetafi]);
			//tangente_lat_circ.assigne(flun,zed,zed);
			//coco=champ_exterieur_xyz.scalaire(tangente_lat_circ)/mu0_ou_un_sur_eps0;
			coco=champ_exterieur_xyz.x/mu0_ou_un_sur_eps0;
			//if(index_direction_champ==0)
			courant_lateral_circ.assigne_facteur(u_courant,coco);
			int ii=0;
			bi_point_xyz bi_toto=new bi_point_xyz();
			for (int iL=0;iL<32;iL++){
			    ii=index[itetafi][iL];
			    courants_tuyau[ii].courant_lateral.assigne(courant_lateral_circ,courant_lateral_droit);
			    bi_toto.assigne(courants_tuyau[ii].courant_lateral);
			    bi_toto.multiplie_cst(mu0_ou_un_sur_eps0);
			    if(prrint)
			    if(iL==31&&(itetafi==0||itetafi==7||itetafi==15||itetafi==24||itetafi==31)){
				bi_toto.print(" itetafi "+itetafi+" mu0.courant_lateral ");
			    }
			}
		    }
		    //courant_lateral[-1][0][0][0]=null;
		}
		void calcule_composantes_courants(int i){
		    int itet=ittet[i];
		    int iL=iiLL[i];
		    i_tuyau=courants_tuyau[i];
		    i_tuyau.courant_recalcu_xyz.assigne(zer_xyz,zer_xyz);
		    if(!(index_direction_champ==0)){
			vec_un_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[itet]);
			vec_un_xyz.assigne_oppose();
			mu0_sig_sur_2_y_constant.assigne_facteur(vec_un_xyz,i_tuyau.courant_lateral.p_y.x*mu0_ou_un_sur_eps0/2);
			champ_total_y_constant.assigne_additionne(mu0_sig_sur_2_y_constant,bi_chp_later.p_y);
			//VAR_T.MAG.courants_tuyau[i].courant_recalcu_xyz.x=champ_total_y_constant.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0;
			i_tuyau.courant_recalcu_xyz.p_y.x=sigma_ou_I_xy(vec_un_xyz,bi_chp_later.p_y,i_tuyau.courant_lateral.p_y.x,false);
			float Bt_y_constant=bi_chp_later.p_y.scalaire(vec_un_xyz);
		    }
		    if(!(index_direction_champ==4)){
			vec_un_xyz.assigne(flun,zed,zed);
			mu0_sig_sur_2_x_constant.assigne_facteur(vec_un_xyz,i_tuyau.courant_lateral.p_x.longueur()*mu0_ou_un_sur_eps0/2);
			champ_total_x_constant.assigne_additionne(mu0_sig_sur_2_x_constant,bi_chp_later.p_x);
			//cucu=2*fac_f_x_constant*(chp_later).scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0;
			if(Math.abs(bi_chp_later.p_x.longueur_carre())>0.01)
			    cucu=sigma_ou_I_xy(vec_un_xyz,bi_chp_later.p_x,i_tuyau.courant_lateral.p_x.longueur(),true);
			//cucu=flun/bi_chp_later.p_x.longueur()*bi_chp_later.p_x.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0;
			else
			    cucu=0;
			i_tuyau.courant_recalcu_xyz.p_x.y=cucu*VAR_B.geom.proj_perp_au_ray[itet].y;
			i_tuyau.courant_recalcu_xyz.p_x.z=cucu*VAR_B.geom.proj_perp_au_ray[itet].z;
		    }
		    if(prrint)
		    if(((itet==7||itet==24))&&(iL==31||iL==63||iL==7||iL==0)){
			bi_chp_later.print(" bi_chp_later ");
			if(index_direction_champ!=4){
			    mu0_sig_sur_2_x_constant.print_sl(" mu0*sig_x_constant/2 ");
			    champ_total_x_constant.print_sl(" champ_total_x_cons ");
			}
			if(index_direction_champ!=0){
			    mu0_sig_sur_2_y_constant.print_sl(" mu0*sig_y_constant/2 ");
			    champ_total_y_constant.print_sl(" champ_total_y_cons ");
			}
			i_tuyau.courant_recalcu_xyz.print(" courant_recalcu_xyz ");
			i_tuyau.courant_lateral.print(" courant_lateral ");
		    }
		}

		class hb_tet_av_fi{
		    bi_point_xyz courant_lateral=new bi_point_xyz();
		    bi_point_xyz courant_recalcu_xyz=new bi_point_xyz();
		    hb_tet_av_fi(){
		    }
		    void zero(){
			courant_lateral.assigne(zer_xyz,zer_xyz);
			courant_recalcu_xyz.assigne(zer_xyz,zer_xyz);
		    }
		}
	    }		
	}
	class variables_bouchons{
	    geometrie_bouchons geom;
	    magnetisme MAG;
	    electricite ELEC;
	    float alph=0;
	    variables_bouchons(){
		/*
		geom=null;
		if(subject.ens_de_dipoles[1-i_ens]!=null&&i_demarre>=3)
		    if(subject.ens_de_dipoles[1-i_ens].i_demarre==i_demarre)
			geom=subject.ens_de_dipoles[1-i_ens].ellipsoid.VAR_B.geom;
		if(geom==null&&i_demarre>=3)
		*/
			geom =new geometrie_bouchons();

		if(electrique){
		    ELEC=new electricite();
		    ELEC.calculs_initiaux();
		}else{
		    MAG=new magnetisme();			
		    MAG.calculs_initiaux();
		}
	    }
	    class geometrie_bouchons{
		boolean obsolete=true;
		float d_red_ell_sur_dy[]=new float[32];
		float d_surface[][]=new float[32][32];
		float dtet_ellipse[]=new float[64];
		point_y_z proj_du_ray[]=new point_y_z[32];
		point_y_z proj_perp_au_ray[]=new point_y_z[32];
		//[haut_bas][teta][avant_arriere]phi[]
		//[direction du champ][haut_bas][teta][avant_arriere][phi]
		float dl_ellipse_x_y=0;
		tet_fi deux_indices[][]=new tet_fi[32][32]; 
		tet_fi deux_ind=new tet_fi(); 
		vecteur x_y_ellipse[]=new vecteur[32];
		vecteur_x_z x_z_ellipse[][]=new vecteur_x_z[32][32];
		point_y_z proj_normale[][]=new point_y_z[32][32];
		point_y_z proj_tangente[][]=new point_y_z[32][32];
		float phi_partie_centrale[]=new float[32];
		float d_phi_partie_centrale[]=new float[32];
		float dl_ellipse_y[]=new float[32];
		int nombre_x_z[]=new int[32];
		int nb_fi_total=0;
		float perimetre=0;
		float somme_ddx=0,somme_ddy=0;
		boolean pprint=false;
		geometrie_bouchons(){
		    float x=0,y=0,z=0,dx=0,dy=0,dz=0,dt_sur_dx=0,dt_sur_dy=0,t=0,dY=0,S=0,L=0;
		    float aa_prime2=0,proj_t_sur_x=0,proj_t_sur_z=0,r_tet=0;
		    point_xyz vectt=new point_xyz(zed,zed,zed);
		    obsolete=false;
		    for (int itet=0;itet<32;itet++){
			x_y_ellipse[itet]=new vecteur(zer,zer);
		    }

		    perimetre=perimeter(aa,bb);
		    dl_ellipse_x_y=perimetre/128;
		    vecteur intermed[]=new vecteur[32];
		    int nb_trouve=decouper_ellipse(aa,bb,perimetre,intermed,dl_ellipse_x_y,-1);
		    for (int itet=0;itet<32;itet++)
			x_y_ellipse[itet]=intermed[itet];
		    if(pprint)
			System.out.println(" nb_trouve "+nb_trouve+" dl_ellipse_x_y "+dl_ellipse_x_y+" somme_ddx "+somme_ddx+" somme_ddy "+somme_ddy+" aa "+aa+" bb "+bb);
		    //x_y_ellipse[1000]=null;
		    float perim=0,annb=0,cos_ph=0,sin_ph=0,phi=0;int nnb=0;
		    for (int itet=0;itet<32;itet++){
			if(itet!=31){
			    y=x_y_ellipse[itet].p1.y;
			}else{
			    coco=dl_ellipse_x_y/2;
			    y=bb*(float)Math.sqrt(flun-coco*coco/aa2);
			}
			reduction_ellipse=(float)Math.sqrt(flun-y*y/bb2);
			d_red_ell_sur_dy[itet]=-2*y/bb2/(2*reduction_ellipse);
			caca=aa*reduction_ellipse;
			cece=bb*reduction_ellipse;
			perim=perimeter(caca,cece);
			annb=perim/(4*dl_ellipse_x_y);
			nnb=(int)annb;
			if(nnb==0)
			    nnb=1;
			dl_ellipse_y[itet]=perim/(4*nnb);
			if(pprint)
			    System.out.println("nnb "+nnb+" itet "+itet);
			nombre_x_z[itet]=decouper_ellipse(caca,cece,perim,intermed,dl_ellipse_y[itet],itet);
			for (int ifi=0;ifi<nombre_x_z[itet];ifi++){
			    x_z_ellipse[itet][ifi]=new vecteur_x_z(zer_x_z,zer_x_z); 
			    x_z_ellipse[itet][ifi].p1.x=intermed[ifi].p1.x;
			    x_z_ellipse[itet][ifi].p1.z=intermed[ifi].p1.y;
			    x_z_ellipse[itet][ifi].p2.x=intermed[ifi].p2.x;
			    x_z_ellipse[itet][ifi].p2.z=intermed[ifi].p2.y;
			}
			if(pprint){
			    System.out.println(" itet "+itet+" nnb "+nnb+" annb "+annb+" nombre_x_z[itet] "+nombre_x_z[itet]+" dl_ellipse_y[itet] "+dl_ellipse_y[itet]);
			    System.out.println("somme_ddx "+somme_ddx+" somme_ddy "+somme_ddy+" a "+aa*reduction_ellipse+" b "+bb*reduction_ellipse);
			}
			/*
			  if(itet==31){
			  x=dl_ellipse_x_y/2;
			  y=(float)Math.sqrt(flun-dl_ellipse_x_y*dl_ellipse_x_y/aa2);
			  z=(float)Math.sqrt(flun-dl_ellipse_x_y*dl_ellipse_x_y/aa2-y*y/bb2)/2;
			  dx=-dl_ellipse_x_y;
			  float surface=pi*dl_ellipse_x_y*(2*z)/4;//un quart de la surface de l'ellipse
			  dz=-surface/dx;// pour avoir ma surface dxdz correcte
			  if(x_z_ellipse[31][0]==null)
			  x_z_ellipse[31][0]=new vecteur(x,z,dx,dz);
			  else
			  x_z_ellipse[31][0].assigne(x,z,dx,dz);
			  nombre_x_z[itet]=1;
			  x_z_ellipse[31][0].print(" y "+y+" x_z_ellipse[31][0] ");
			  }
			*/
			for (int ifi=0;ifi<nombre_x_z[itet];ifi++){
			    deux_indices[itet][ifi]=new tet_fi();
			    z=x_z_ellipse[itet][ifi].p1.z;
			    phi=(float)Math.atan2(z,y);
			    if(phi<0)
				phi+=2*pi;
			    cos_ph=(float)Math.cos(phi);
			    sin_ph=(float)Math.sin(phi);		
			    proj_normale[itet][ifi]=new point_y_z(cos_ph,sin_ph);
			    proj_tangente[itet][ifi]=new point_y_z(-sin_ph,cos_ph);
			}


			nb_fi_total+=nombre_x_z[itet];
		    }
		    if(pprint)
			System.out.println(" nb_fi_total "+nb_fi_total);
		    //		x_z_ellipse[1000][0]=null;
		    float tot_d_phi=0,phi_p=0,d_S=0,d_S_y=0,S_y=0,dS_prime_y=0,S_prime_y=0,LL=0,S_prime=0,S_seconde=0;
		    for (int itet=0;itet<32;itet++){
			y=x_y_ellipse[itet].p1.y;
			d_S_y=0;
			d_S=0;
			LL+=x_y_ellipse[itet].p2.longueur();
			for (int ifi=0;ifi<nombre_x_z[itet];ifi++){//on ne prend que la partie avant (phi>0)
			    x=x_z_ellipse[itet][ifi].p1.x;
			    z=x_z_ellipse[itet][ifi].p1.z;
			    y=x_y_ellipse[itet].p1.y;
			    dx=x_z_ellipse[itet][ifi].p2.x;
			    dz=x_z_ellipse[itet][ifi].p2.z;
			    dy=x_y_ellipse[itet].p2.y;
			    if(ifi==nombre_x_z[itet]-1){
				phi_partie_centrale[itet]=(float)Math.atan2(z,y);
				coco=(float)Math.cos(phi_partie_centrale[itet]);
				cici=(float)Math.sin(phi_partie_centrale[itet]);
				proj_du_ray[itet]=new point_y_z(coco,cici);
				proj_perp_au_ray[itet]=new point_y_z(-cici,coco);
				//d_phi_partie_centrale[itet]=Math.abs(x_y_ellipse[itet].p2.y/(flun+z*z/(y*y))*(x_z_ellipse[itet][ifi].p1.y/y*d_red_ell_sur_dy[itet]-z/(y*y)));
				d_phi_partie_centrale[itet]=Math.abs(dy/(flun+z*z/(y*y))*(bb/y*d_red_ell_sur_dy[itet]-z/(y*y)));
				tot_d_phi+=d_phi_partie_centrale[itet]; 
			    }
			    
			    //System.out.println("x*x/aa2+(y*y+z*z)/bb2 "+(x*x/aa2+(y*y+z*z)/bb2));
			    //à y constant [ihb=1 y>0][0=avant ou z>0]
			    //deux_indices[itet][ifi].u2_xyz.assigne(x_z_ellipse[itet][ifi].p2.x,zed,x_z_ellipse[itet][ifi].p2.y);
			    if(itet!=31){
				deux_indices[itet][ifi].u2_xyz.assigne(dx,zed,dz);
				//à gamma constant, angle entre xoy et toy, t=x*cos(gamma)+z*sin(gamma), gamma=(float)Math.atan2(z,x) 
				cucu=x*x+z*z;
				t=(float)Math.sqrt(cucu);
				//cos(gamma)=x/coco, sin(gamma)=z/coco; 
				aa_prime2=cucu/(x*x/aa2+z*z/bb2);//=1/(cos2(gamma)/aa2+sin2(gamma)/bb2) on a aussi: (x*x/aa2+z*z/bb2)=1.-y*y/(bb2)=reduction_ellipse[itet]*reduction_ellipse[itet]
				proj_t_sur_x=x/t;
				proj_t_sur_z=z/t;
				//t2/aa_prime2+y2/bb2=1
				dt_sur_dy=-aa_prime2*y/(bb2*t);
				//deux_indices[itet][ifi].v2_xyz.assigne(proj_t_sur_x*dt_sur_dy*x_y_ellipse[itet].p2.y,x_y_ellipse[itet].p2.y,proj_t_sur_z*dt_sur_dy*x_y_ellipse[itet].p2.y);
				deux_indices[itet][ifi].v2_xyz.assigne(proj_t_sur_x*dt_sur_dy*dy,dy,proj_t_sur_z*dt_sur_dy*dy);
				deux_indices[itet][ifi].v2_xyz.assigne_oppose();
				vectt.assigne(deux_indices[itet][ifi].u2_xyz.vectoriel(deux_indices[itet][ifi].v2_xyz));
				d_surface[itet][ifi]=vectt.longueur();
				if(pprint)
				    if((itet==0||itet==11||itet==23)&&(ifi==0||ifi==nombre_x_z[itet]/2||ifi==nombre_x_z[itet]-1)){
					System.out.println(" itet "+itet+" ifi "+ifi+"u2_xyz.v2_xyz "+deux_indices[itet][ifi].u2_xyz.scalaire(deux_indices[itet][ifi].v2_xyz));
					deux_indices[itet][ifi].u2_xyz.print(" dx "+dx+" dz "+dz+"u2_xyz ");
					deux_indices[itet][ifi].v2_xyz.print(" dy "+dy+"v2_xyz ");
					//deux_indices[itet][ifi].normale.print("normale ");
				    }
				normale_xyz.assigne_normalise(vectt);
				deux_indices[itet][ifi].u2_xyz.normalise();
				deux_indices[itet][ifi].v2_xyz.normalise();
				deux_indices[itet][ifi].normale.assigne(normale_xyz);
			    }else{
				deux_indices[31][0].u2_xyz.assigne(-flun,zed,zed);
				deux_indices[31][0].v2_xyz.assigne(flun,zed,zed);
				deux_indices[31][0].normale.assigne(zed,flun,zed);
				d_surface[31][0]=Math.abs(pi*x_z_ellipse[31][0].p2.x*x_z_ellipse[31][0].p2.z)/4;
				if(pprint)
				    x_z_ellipse[31][0].print(" x_z_ellipse[31][0] ");
			    }
			    S+=d_surface[itet][ifi];
			    d_S+=d_surface[itet][ifi];
			    d_S_y+=x_z_ellipse[itet][ifi].p2.longueur()*dl_ellipse_x_y;
			}
			dS_prime_y=pi/2*rayon*(float)Math.sqrt(flun-y*y/bb2)*dl_ellipse_x_y;
			S_seconde+=pi/2*y;

			S_y+=d_S_y;
			S_prime_y+=dS_prime_y;
			if(pprint)
			    System.out.println(" itet "+itet+" nombre_x_z "+nombre_x_z[itet]+" d_S "+d_S+" d_S_y "+d_S_y+" dS_prime_y "+dS_prime_y+" rapports "+d_S/d_S_y+" cumulé "+S/S_y);
		    }
		    if(pprint){
			System.out.println(" S "+S+" S_y "+S_y+" S_prime_y "+S_prime_y+" pi*rayon*rayon "+pi*rayon*rayon/2+" LL "+LL+" LL attendue "+pi/2*rayon);
			System.out.println(" tot_d_phi "+tot_d_phi+" pi/64 "+pi/64+" S_seconde "+S_seconde+" pi/2*rayon*largeur "+pi/2*(float)(rayon*largeur));
			for (int itet=0;itet<32;itet++)
			    System.out.println(" itet "+itet+"d_hi_partie_centrale[itet] "+d_phi_partie_centrale[itet]+" phi_partie_centrale "+phi_partie_centrale[itet]);
		    }
		    // nombre_x_z[1000]=0;
		}
		/*
		  int phi_dichoto(float fi){
		  float phi=phi_partie_centrale[15],intervalle=pi/4;
		  int i_phi=15,isign=1;;
		  for (int iter=0;iter<10;iter++){
		  intervalle/=2;
		  if(fi<phi){
		  isign=-isign;
		  }
		  phi+=(isign*intervalle);
		  i_phi/
		  }
		  }
		  return i_phi;
		  }
		*/
		int decouper_ellipse(float a,float b,float perimeter,vecteur[] w,float dl_ellps,int numero_ellipse){
		    somme_ddx=0;somme_ddy=0;
		    float somme_cucu=0,ddx=0,ddy=0;
		    if(pprint)
			System.out.println(" dl_ellps "+dl_ellps+" perimeter/4 "+perimeter/4+" a "+a+" b "+b);
		    int nb=0,nb_de_pas_a_faire=1;
		    if(numero_ellipse>=24)
			nb_de_pas_a_faire=(numero_ellipse-24)/2*2+3;
		    float dl_ell=dl_ellps/nb_de_pas_a_faire;
		    int nb_de_pas_faits=0;
		    float l=0,dx=0,dy=0;
		    float y=-dl_ell/2,x=a;
		    float theta=(float)Math.atan(dl_ell/(2*a));
		    float dtheta=0;
		    //float theta1=y1/x1,d_theta1=dl_ell/(float)(float)Math.sqrt((x1-a)*(x1-a),y1*y1);
		    float r2=0,r=0;
		    float	xx=0,yy=0,derivee_seconde=0,B=0;
		    float a4=a*a*a*a,b4=b*b*b*b;
		    float a2=a*a,b2=b*b;
		    ddx=0;ddy=0;
		    for (int k=0;k<=1000;k++){
			cici=(float)Math.sin(theta);
			coco=(float)Math.cos(theta);
			r2=flun/(cici*cici/b2+coco*coco/a2);
			r=(float)Math.sqrt(r2);
			y=r*cici;
			x=r*coco;
			B=(float)Math.sqrt(cici*cici/b4+coco*coco/a4);
			dtheta=dl_ell/(r2*r*B);
			derivee_seconde=cici*coco/(r2*r2*B*B)*(-3/a2+3/b2+1/(B*B*r2)*(1/a4-1/b4));
			dtheta+=dl_ell*dl_ell/2*derivee_seconde;
			theta+=dtheta;
			if(numero_ellipse==15)
			    if(pprint)
				System.out.println(" nb "+nb+" nb_de_pas_faits "+nb_de_pas_faits+" theta "+theta+" dtheta "+dtheta);	
			dx=-y*r2*dtheta/b2;
			//dx+=dtheta*dtheta/2*r2*x*(-1/b2+3*y*y*(1/b4-1/(a2*b2)));
			dy=x*r2*dtheta/a2;
			//dy+=dtheta*dtheta/2*r2*y*(-1/a2+3*x*x*(1/a4-1/(a2*b2)));
			//x+=dx;
			//y+=dy;
			if(nb_de_pas_a_faire==1||nb_de_pas_faits==nb_de_pas_a_faire/2){
			    //if(nb_de_pas_faits==nb_de_pas_a_faire){
			    xx=x;
			    yy=y;
			}
			ddx+=dx;
			ddy+=dy;
			l+=dl_ell;
			nb_de_pas_faits++;
			if(nb_de_pas_faits==nb_de_pas_a_faire){
			    if(xx>-ddx){
				w[nb]=new vecteur(xx,yy,ddx,ddy);
				somme_ddx+=ddx;
				somme_ddy+=ddy;
				cucu=(float)Math.sqrt(ddy*ddy+ddx*ddx);
				somme_cucu+=cucu;
				if(pprint){
				    cece=xx*xx/(a*a)+yy*yy/(b*b);
				    w[nb].print("nb "+nb+" w ");
				    System.out.println(" theta "+theta+" cucu "+cucu+" cece "+cece+" l "+l+" somme_cucu "+somme_cucu+" somme_ddx"+somme_ddx+" somme_ddy "+somme_ddy);
				}
			    }else{
				if(pprint)
				    System.out.println(" xx "+xx+" yy "+yy+" ddx "+ddx+" ddy "+ddy);
				xx-=(xx+ddx/2)/2;
				yy=b;
				ddx=-xx*2;
				w[nb]=new vecteur(xx,yy,ddx,ddy);
				cucu=(float)Math.sqrt(ddy*ddy+ddx*ddx);
				somme_cucu+=cucu;
				l+=(cucu-dl_ell);
				somme_ddx+=ddx;
				somme_ddy+=ddy;
				if(pprint){
				    w[nb].print("nb "+nb+" w ");
				    System.out.println(" ddx "+ddx+" ddy "+ddy+" theta "+theta+" l "+l+" somme_cucu "+somme_cucu+" somme_ddx"+somme_ddx+" somme_ddy "+somme_ddy);
				}
				break;   
			    }
			    nb++;
			    ddx=0;ddy=0;nb_de_pas_faits=0;
			}
		    }

		    return nb+1; 
		}
		float perimeter(float aa,float bb){
		    coco=(float)Math.pow((aa-bb)/(aa+bb),2);
		    return pi*(aa+bb)*(flun+3*coco/(10+(float)Math.sqrt(4-3*coco)));
		
		}
		class tet_fi{
		    point_xyz u2_xyz=new point_xyz(zer_xyz);
		    point_xyz v2_xyz=new point_xyz(zer_xyz);
		    point_xyz normale=new point_xyz(zer_xyz);
		    tet_fi(){
		    }
		}
	    }

	    class electricite{
		tet_fi_elec deux_indices_elec[][]=new tet_fi_elec[32][32];
		tet_fi_elec deux_elec=new tet_fi_elec(); 
     		boolean initial=true;
		electricite(){
		    for (int itet=0;itet<32;itet++)
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){
			    deux_indices_elec[itet][ifi]=new tet_fi_elec();
			}
		}
		void renorme(){
		    for (int itet=0;itet<32;itet++)
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){//on ne prend que la partie avant (phi>0)
			    deux_indices_elec[itet][ifi].sigma.x*=(rapport_renor.x);
			    deux_indices_elec[itet][ifi].sigma.y*=(rapport_renor.y);
		    }
		}
		void calculs_initiaux(){
		    if(!initial)
			for (int itet=0;itet<32;itet++)
			    for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++)//on ne prend que la partie avant (phi>0)
				deux_indices_elec[itet][ifi].zero();

		    for (int itet=0;itet<32;itet++){
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){//on ne prend que la partie avant (phi>0)
			    deux_indices_elec[itet][ifi].sigma.assigne(champ_exterieur_xyz.x*geom.deux_indices[itet][ifi].normale.x,champ_exterieur_xyz.y*geom.deux_indices[itet][ifi].normale.y);//densite de charge d'essai
			    deux_indices_elec[itet][ifi].sigma.divise_cst(mu0_ou_un_sur_eps0);
			    //if((ifi==0||ifi==geom.nombre_x_z[itet]/2||ifi==geom.nombre_x_z[itet]-1)&&(itet==7||itet==30||itet==31)){
			    if(!initial){
				System.out.println(" itet "+itet+" ifi "+ifi);
				titi.assigne_facteur(deux_indices_elec[itet][ifi].sigma,mu0_ou_un_sur_eps0);
				titi.print(" sigma/eps0 ");
				geom.deux_indices[itet][ifi].normale.print(" normale ");
			    }
			}
		    }
		    if(!initial)
			geom.nombre_x_z[1000]=0;
		    initial=false;
		}
		float facteur_renor(point chp_ctr,bi_point_xyz bi_chp_late,boolean x_pas_y,point_xyz normalle,int itet,int ifi){
		    deux_elec=deux_indices_elec[itet][ifi];
		    toto_xyz.assigne(chp_ctr,zed);
		    if(x_pas_y){
			sig_sur_2.assigne_facteur(normalle,deux_elec.sigma.x*mu0_ou_un_sur_eps0/2);
			if(deux_elec.sigma.x>1e-20){
			    alph=bi_chp_late.p_x.x/(deux_elec.sigma.x*mu0_ou_un_sur_eps0);
			    return (-(champ_exterieur.x/mu0_ou_un_sur_eps0)/(alph-((1+susceptibilite/2)/susceptibilite))/deux_elec.sigma.x);
			}else 
			    return zed;
		    }else{
			sig_sur_2.assigne_facteur(normalle,deux_elec.sigma.y*mu0_ou_un_sur_eps0/2);
			if(deux_elec.sigma.y>1e-20){
			    alph=bi_chp_late.p_y.y/(deux_elec.sigma.y*mu0_ou_un_sur_eps0);
			    return (-(champ_exterieur.y/mu0_ou_un_sur_eps0)/(alph-((1+susceptibilite/2)/susceptibilite))/deux_elec.sigma.y);
			}else 
			    return zed;
		    }
		}
		
		void calcule_composantes_charges(int itet,int ifi){
		    deux_elec=deux_indices_elec[itet][ifi];
		    vec_un_xyz.assigne(geom.deux_indices[itet][ifi].normale);
		    deux_elec.sigma_reca.x=sigma_ou_I_xy(vec_un_xyz,bi_chp_later.p_x,deux_elec.sigma.x,true);
		    deux_elec.sigma_reca.y=sigma_ou_I_xy(vec_un_xyz,bi_chp_later.p_y,deux_elec.sigma.y,false);
		    mu0_sig_sur_2_x_constant.assigne_facteur(vec_un_xyz,deux_elec.sigma.x*(-mu0_ou_un_sur_eps0/2));
		    champ_total_x_constant.assigne_additionne(mu0_sig_sur_2_x_constant,bi_chp_later.p_x);
		    mu0_sig_sur_2_y_constant.assigne_facteur(vec_un_xyz,deux_elec.sigma.y*(-mu0_ou_un_sur_eps0/2));
		    champ_total_y_constant.assigne_additionne(mu0_sig_sur_2_y_constant,bi_chp_later.p_y);
		    if((ifi==0||ifi==geom.nombre_x_z[itet]/2||ifi==geom.nombre_x_z[itet]-1)&&(itet==2||itet==7||itet==15||itet==29)){
			titi.assigne_facteur(deux_elec.sigma,mu0_ou_un_sur_eps0);
			tutu.assigne_facteur(deux_elec.sigma_reca,mu0_ou_un_sur_eps0);
			titi.print(" mu0*sigma ");
			tutu.print(" mu0*sigma_reca ");
			vec_un_xyz.print(" normale ");
			bi_chp_later.print(" bi_chp_later ");
			if(index_direction_champ!=4){
			    mu0_sig_sur_2_x_constant.print(" mu0_sig_sur_2_x_constant ");
			    champ_total_x_constant.print(" champ_total_x_constant ");
			}
			if(index_direction_champ!=0){
			    mu0_sig_sur_2_y_constant.print(" mu0_sig_sur_2_y_constant ");
			    champ_total_y_constant.print(" champ_total_y_constant ");
			}
		    }
		}
		class tet_fi_elec{
		    point sigma=new point(zer);
		    point sigma_reca=new point(zer);
		    tet_fi_elec(){
		    }
		    void zero(){
			sigma.assigne(zer);
			sigma_reca.assigne(zer);
		    }
		}
	    }
	    class magnetisme{
		tet_fi_mag deux_indices_mag[][]=new tet_fi_mag[32][32];
		tet_fi_mag deux_mag=new tet_fi_mag();
		boolean initial=true;float alpha_mag=0;
		magnetisme(){
		    for (int itet=0;itet<32;itet++)
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){
			    deux_indices_mag[itet][ifi]=new tet_fi_mag();
			}
		}
		void renorme(){
		    for (int itet=0;itet<32;itet++){
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){//on ne prend que la partie avant (phi>0)
			    deux_mag=deux_indices_mag[itet][ifi];
			    deux_mag.I_x_constant*=rapport_renor.x;
			    deux_mag.I_y_constant*=rapport_renor.y;
			}
		    }
		}
		void calculs_initiaux(){
		    float x=0,y=0,sintett=0;int ik=0; 
		    for (int itet=0;itet<32;itet++){
			y=geom.x_y_ellipse[itet].p1.y;
			for (int ifi=0;ifi<geom.nombre_x_z[itet];ifi++){
			    if(itet==31){
				deux_indices_mag[itet][0].u_courant_x_constant.assigne(zed,zed,flun);
				deux_indices_mag[itet][0].tangente_x_constant.assigne(flun,zed,zed);
			    }else{
				geom.deux_ind=geom.deux_indices[itet][ifi];
				deux_mag=deux_indices_mag[itet][ifi];
				deux_mag.u_courant_x_constant.assigne(zed,geom.proj_tangente[itet][ifi]);
				deux_mag.tangente_x_constant.assigne(vecteur_unite_tangent_et_normal_a_un_troisieme(deux_mag.u_courant_x_constant,geom.deux_ind.v2_xyz,geom.deux_ind.u2_xyz));						
				if(deux_mag.tangente_x_constant.vectoriel(deux_mag.u_courant_x_constant).scalaire(geom.deux_ind.normale)>0)
				    deux_mag.tangente_x_constant.assigne_oppose();
			    }
			    champ_exterieur_scal=champ_exterieur_xyz.x*deux_mag.tangente_x_constant.x;
			    deux_mag.I_x_constant=champ_exterieur_scal/mu0_ou_un_sur_eps0;
			    
			    if(itet==31){
				geom.deux_indices[itet][0].u2_xyz.assigne(zer_xyz);
				deux_indices_mag[itet][0].tangente_x_constant.assigne(zer_xyz);
			    }else{
				deux_mag.tangente_y_constant.assigne(vecteur_unite_tangent_et_normal_a_un_troisieme(geom.deux_ind.u2_xyz,geom.deux_ind.v2_xyz,geom.deux_ind.u2_xyz));						
				if(deux_mag.tangente_y_constant.vectoriel(geom.deux_ind.u2_xyz).scalaire(geom.deux_ind.normale)<0)
				    deux_mag.tangente_y_constant.assigne_oppose();
				champ_exterieur_scal=Math.abs(champ_exterieur_xyz.y*deux_mag.tangente_y_constant.y);
				deux_mag.I_y_constant=champ_exterieur_scal/mu0_ou_un_sur_eps0;
			    }				
			    if((ifi==0||ifi==geom.nombre_x_z[itet]/2||ifi==geom.nombre_x_z[itet]-1)&&(itet==0||itet==15||itet==30||itet==31)){
				System.out.println(" ");
				System.out.println(" itet "+itet+" ifi "+ifi);
				System.out.println(" I_x_constant "+deux_mag.I_x_constant+" mu0*I_x_constant "+ mu0_ou_un_sur_eps0*deux_mag.I_x_constant);
				cucu=deux_mag.tangente_x_constant.scalaire(deux_mag.u_courant_x_constant);
				coco=deux_mag.tangente_x_constant.scalaire(geom.deux_ind.normale); 
				deux_mag.tangente_x_constant.print_sl(" d_surface "+geom.d_surface[itet][ifi]+"cucu "+cucu+"coco "+coco+" tangente_x_constant ");
				deux_mag.u_courant_x_constant.print(" u_courant_x_constant ");
				geom.proj_tangente[itet][ifi].print_sl(" y "+y+" z "+geom.x_z_ellipse[itet][ifi].p1.z+"proj_tangente ");
				System.out.println(" I_y_constant "+deux_mag.I_y_constant+" mu0*I_y_constant "+ mu0_ou_un_sur_eps0*deux_mag.I_y_constant);
				deux_mag.tangente_y_constant.print_sl(" d_surface "+geom.d_surface[itet][ifi]+" tangente_y_constant ");
				
				geom.deux_ind.u2_xyz.print(" u2_xyz ou u_courant_y_constant ");
				deux_mag.tangente_y_constant.print( " tangente_y_constant ");
				cici=(geom.deux_ind.u2_xyz.vectoriel(geom.deux_ind.v2_xyz)).scalaire(geom.deux_ind.normale);
				geom.deux_ind.v2_xyz.print(" cici "+cici+" v2_xyz "); 
			    }
			}
		    }
		    //		    deux_indices[1000][1000]=null;
		}
		float facteur_renor(point chp_ctr,bi_point_xyz bi_chp_late,boolean x_pas_y,int itet,int ifi){
		    deux_mag=deux_indices_mag[itet][ifi];		    
		    toto_xyz.assigne(chp_ctr,zed);
		    if(x_pas_y){
			sig_sur_2.assigne_facteur(deux_mag.tangente_x_constant,deux_mag.I_x_constant*mu0_ou_un_sur_eps0/2);
			if(deux_mag.I_x_constant>1e-3){
			    alpha_mag=bi_chp_late.p_x.scalaire(deux_mag.tangente_x_constant)/(deux_mag.I_x_constant*mu0_ou_un_sur_eps0);
			    return susceptibilite*toto_xyz.scalaire(deux_mag.tangente_x_constant)/mu0_ou_un_sur_eps0/(alpha_mag+(float)0.5)/deux_mag.I_x_constant;
			}else 
			    return zed;
		    }else{
			sig_sur_2.assigne_facteur(deux_mag.tangente_y_constant,deux_mag.I_y_constant*mu0_ou_un_sur_eps0/2);
			if(deux_mag.I_y_constant>1e-3){
			    alpha_mag=bi_chp_late.p_y.scalaire(deux_mag.tangente_y_constant)/(deux_mag.I_y_constant*mu0_ou_un_sur_eps0);
			    return susceptibilite*toto_xyz.scalaire(deux_mag.tangente_y_constant)/mu0_ou_un_sur_eps0/(alpha_mag+(float)0.5)/deux_mag.I_y_constant;
			}else 
			    return zed;
			
		    }
		}

		void calcule_composantes_courants(int itet,int ifi){
		    deux_mag=deux_indices_mag[itet][ifi];
		    deux_mag.I_reca_x=0;deux_mag.I_reca_y=0;
		    float alpha=0;
		    if(index_direction_champ!=4){
			alpha=bi_chp_later.p_x.scalaire(deux_mag.tangente_x_constant)/(deux_mag.I_x_constant*mu0_ou_un_sur_eps0);
			deux_mag.I_reca_x=sigma_ou_I_xy(deux_mag.tangente_x_constant,bi_chp_later.p_x,deux_mag.I_x_constant,true);
			//I_reca_x=bi_chp_later.p_x.scalaire(tangente_x_constant)/mu0_ou_un_sur_eps0/(alpha+(float)0.5);
			ss+=Math.abs(geom.d_surface[itet][ifi]);
			sum_x_avant+=(champ_total_x_constant.x*Math.abs(geom.d_surface[itet][ifi]));
		    }
		    if(index_direction_champ!=0){
			alpha=bi_chp_later.p_y.scalaire(deux_mag.tangente_y_constant)/(deux_mag.I_y_constant*mu0_ou_un_sur_eps0);
			//I_reca_y=bi_chp_later.p_y.scalaire(tangente_y_constant)/mu0_ou_un_sur_eps0/(alpha+(float)0.5);
			deux_mag.I_reca_y=sigma_ou_I_xy(deux_mag.tangente_y_constant,bi_chp_later.p_y,deux_mag.I_y_constant,false);

		    }
		    if((ifi==0||ifi==geom.nombre_x_z[itet]/2||ifi==geom.nombre_x_z[itet]-1)&&(itet==2||itet==7||itet==15||itet==29)){
			System.out.println(" mu0*I_reca_x "+mu0_ou_un_sur_eps0*deux_mag.I_reca_x+" mu0*I_x_constant "+mu0_ou_un_sur_eps0*deux_mag.I_x_constant+" B.t "+bi_chp_later.p_x.scalaire(deux_mag.tangente_x_constant)+" B0.t "+champ_exterieur_xyz.scalaire(deux_mag.tangente_x_constant)+" B.t/B0.t "+bi_chp_later.p_x.scalaire(deux_mag.tangente_x_constant)/champ_exterieur_xyz.scalaire(deux_mag.tangente_x_constant) );
			coco=bi_chp_later.p_x.scalaire(deux_mag.tangente_x_constant)/(mu0_ou_un_sur_eps0*deux_mag.I_x_constant);
			deux_mag.tangente_x_constant.print_sl("cici "+cici+" coco "+coco+" B.t/I_x "+coco+" tangente_x_constant ");
			System.out.println(" mu0*I_reca_y "+mu0_ou_un_sur_eps0*deux_mag.I_reca_y+" mu0*I_y_constant "+mu0_ou_un_sur_eps0*deux_mag.I_y_constant+" B.t "+bi_chp_later.p_y.scalaire(deux_mag.tangente_y_constant)+" E0.t "+champ_exterieur_xyz.scalaire(deux_mag.tangente_y_constant));;
			deux_mag.tangente_y_constant.print(" alpha "+alpha+" tangente_y_constant ");
			coco=(bi_chp_later.somme()).scalaire(geom.deux_indices[itet][ifi].normale);
			bi_chp_later.print("B.n "+coco+" E0.n "+champ_exterieur_xyz.scalaire(geom.deux_indices[itet][ifi].normale)+"bi_chp_later ");
			//System.out.println(" B.tangente/mu0 "+chp_later.scalaire(VAR_B.MAG.tangente_x_constant[itet][ifi])/mu0_ou_un_sur_eps0)
			mu0_sig_sur_2_x_constant.print(" mu0*sig_x_constant/2 ");
			champ_total_x_constant.print(" champ_total_x_constant ");
			mu0_sig_sur_2_y_constant.print(" sig_y_constant.longueur()/2 "+mu0_sig_sur_2_y_constant.longueur()+" mu0*sig_y_constant/2 ");
			champ_total_y_constant.print(" champ_total_y_constant ");
		    }
		}

		class tet_fi_mag{
		    float I_reca_x,I_reca_y,I_x_constant,I_y_constant;
		    point_xyz tangente_x_constant,tangente_y_constant,u_courant_x_constant;
		    //point_xyz u_courant_y_constant=u2_xyz;
		    tet_fi_mag(){
			zero();
		    }
		    void zero(){
			I_reca_x=0;I_reca_y=0;I_x_constant=0;I_y_constant=0;
			tangente_x_constant=new point_xyz(zer_xyz);
			tangente_y_constant=new point_xyz(zer_xyz);
			u_courant_x_constant=new point_xyz(zer_xyz);
		    }
		}
	    }
	}
	point pt_de_depart_surf(float tt){
	    if(sin_teta_exterieur>0.0001&&cos_teta_exterieur>0.0001){
		//tg_tet_tg=-1/(rapport_des_axes*rapport_des_axes*tg_teta_exterieur);
		//tg_tet_tg=-1/(rapport_des_axes*rapport_des_axes*tt);
		//tg_tet_tg=-1/tg_teta_exterieur;
		tg_tet_tg=-1/tt;
		
		//toto.assigne(zer);
		//graph.drawLine((int)tata.x,(int)tata.y,(int)tete.x,(int)tete.y);
		if(!tuyau_central){
		    a_prime=(1/aa2+tg_tet_tg*tg_tet_tg/bb2);
		    //b_prime=(toto.y-toto.x*tg_tet_tg)*tg_tet_tg/bb2;
		    //c_prime=(float)Math.pow(toto.y-toto.x*tg_tet_tg,2)/bb2-flun;
		    //c_prime=-1;
		    tete.x=-1/(float)Math.sqrt(a_prime);
		}else{
		    cucu=Math.abs(bb/tg_tet_tg);
		    if(cucu<largeur/2)
			tete.x=-Math.abs(cucu);
		    else{
			float x0=0;
			float y0=largeur/2*tg_tet_tg;
			a_prime=(1/aa2+tg_tet_tg*tg_tet_tg/bb2);
			b_prime=y0;
			c_prime=y0*y0/bb2-1;
			delta_prime=b_prime*b_prime-a_prime*c_prime;
			cucu=(float)Math.sqrt(delta_prime);
			tete.x=-(-b_prime+cucu)/a_prime+largeur/2;
		    }
      		}
		//tete.y=toto.y+(tete.x-toto.x)*tg_tet_tg;
		tete.y=tete.x*tg_tet_tg;
	    }else{
		if(sin_teta_exterieur<0.0001){
		    tete.assigne(zed,bb);
		}else if(cos_teta_exterieur<0.0001){
		    tete.assigne(-aa,zed);
		    if(tuyau_central)
			tete.x-=(largeur/2);
		}
	    }
	    cucu=tete.x*tete.x/aa2+tete.y*tete.y/bb2;
	    caca=tete.y/tete.x;
	    tete.print(" cucu "+cucu+" caca "+caca+" tete ");
	    return tete;
	}
	public void change_la_demie_(int demie_h,boolean v_pas_h,boolean largeur_centrale){
	    if(largeur_centrale){
		largeur=2*demie_h;
		if(largeur!=0)
		    tuyau_central=true;
		tuyau_central=true;
		VAR_T=null;
		VAR_T=new variables_geometriques_tuyau();
	    }else{
		if(v_pas_h){
		    bb=demie_h;
		    rayon=demie_h;
		    if(i_demarre==3)
			aa=bb*rapport_des_axes;
		}else
		    aa=demie_h;
		VAR_B.geom.perimetre=VAR_B.geom.perimeter(aa,bb);
		rapport_des_axes=aa/bb;
		aa2=aa*aa;
		bb2=bb*bb;
		bill_ou_barreau.appelle_VAR_B(false);
	    }
	}
	bi_point_xyz champ_des_autres(int ihaut_bas,int itet,int iavar,int ifi_ou_iL,boolean dans_tuyau){
	    bi_point_xyz bp=new bi_point_xyz();
	    if(!dans_tuyau){
		po_xyz.assigne(-iavar*VAR_B.geom.x_z_ellipse[itet][ifi_ou_iL].p1.x,ihaut_bas*VAR_B.geom.x_y_ellipse[itet].p1.y,-iavar*VAR_B.geom.x_z_ellipse[itet][ifi_ou_iL].p1.z);
		if(ecrire_champ)
		    po_xyz.print(" po_xyz ");
		
		po_xyz.additionne(centre,zed);
		if(tuyau_central)
		    po_xyz.x+=(largeur/2);
	    }else{
		float dix=centre.x-largeur/fldeux+(ifi_ou_iL+(float)0.5)*(float)largeur/(float)64.;
		toto_y_z.assigne_facteur(VAR_B.geom.proj_du_ray[itet],(float)rayon);
		toto_y_z.y+=centre.y;
		po_xyz.assigne(dix,toto_y_z);
	    }
	    bp.assigne(champ_en_un_point_ellipse(po_xyz,ihaut_bas,itet,iavar,ifi_ou_iL,dans_tuyau));
	    
	    return bp;
	}
	void recalcule_les_sig_ou_I(){
	    po_xyz.assigne(centre,zed);
	    champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
	    champ_au_centre.print_sl(" champ_au_centre. ");
	    float alp=0;
	    rapport_renor.assigne(zer);
	    if(metal){
		//	po_xyz.assigne(centre.x,centre.y+bb,zed);
		//bi_chp_later.assigne(champ_en_un_point_ellipse(po_xyz,-10,-10,-10,-10,false));
		if(Math.abs(champ_au_centre.x)>0.000001)		
		    rapport_renor.x=Math.abs(champ_exterieur.x/champ_au_centre.x);
		if(Math.abs(champ_au_centre.y)>0.000001)		
		    rapport_renor.y=Math.abs(champ_exterieur.y/champ_au_centre.y);
	    }else{
		if(electrique){
		    if(Math.abs(VAR_B.ELEC.deux_indices_elec[0][0].sigma.x)>1e-20){
			bi_chp_later.assigne(champ_des_autres(1,0,-1,0,false));
			rapport_renor.x=VAR_B.ELEC.facteur_renor(champ_au_centre,bi_chp_later,true,VAR_B.geom.deux_indices[0][0].normale,0,0);
		    }
		    if(!tuyau_central){
			bi_chp_later.assigne(champ_des_autres(1,31,-1,0,false));
			rapport_renor.y=VAR_B.ELEC.facteur_renor(champ_au_centre,bi_chp_later,false,VAR_B.geom.deux_indices[31][0].normale,31,0);
		    }else{
			int i=VAR_T.index[31][31];
			if(Math.abs(VAR_T.ELEC.charges_tuyau[i].sigma_laterale.y)>1e-20){//en fait sigma_laterale_y_constant
			    bi_chp_later.assigne(champ_des_autres(1,31,-1,31,true));
			    alp=bi_chp_later.p_y.y/(VAR_T.ELEC.charges_tuyau[i].sigma_laterale.y*mu0_ou_un_sur_eps0);//en fait sigma_laterale_y_constant
			    rapport_renor.y=-(champ_exterieur.y/mu0_ou_un_sur_eps0)/(alp-((1+susceptibilite/2)/susceptibilite))/VAR_T.ELEC.charges_tuyau[i].sigma_laterale.y;
			}
			//vec_un_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[31]);
			//sig_sur_2.assigne_facteur(vec_un_xyz,VAR_T.ELEC.charges_tuyau[i].sigma_laterale*mu0_ou_un_sur_eps0/2);			    
		    }			
		}else{
		    int itet=0;
		    int  ifi=VAR_B.geom.nombre_x_z[itet]-1;
		    if(!tuyau_central){
			bi_chp_later.assigne(champ_des_autres(1,itet,-1,ifi,tuyau_central));
			bi_chp_later.print(" ifi "+ifi+" chp_later ");		
			rapport_renor.x=VAR_B.MAG.facteur_renor(champ_au_centre,bi_chp_later,true,itet,ifi);
			System.out.println(" x_pas_y "+true+" alpha_mag "+VAR_B.MAG.alpha_mag);
			rapport_renor.y=VAR_B.MAG.facteur_renor(champ_au_centre,bi_chp_later,false,itet,ifi);
			System.out.println(" x_pas_y "+false+" alpha_mag "+VAR_B.MAG.alpha_mag);
		    }else{
			itet=0;int iL=31;
			int i=VAR_T.index[itet][iL];
			bi_chp_later.assigne(champ_des_autres(1,itet,-1,iL,true));
			if(index_direction_champ!=0){
			    //vec_un_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[VAR_B.nombre_x_z[31]-1]);
			    vec_un_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[itet]);
			    vec_un_xyz.assigne_oppose();
			    cici=VAR_T.MAG.courants_tuyau[i].courant_lateral.p_y.longueur();
			    alp=bi_chp_later.p_y.scalaire(vec_un_xyz)/(cici*mu0_ou_un_sur_eps0);
			    toto_xyz.assigne(champ_au_centre,zed);
			    float J=susceptibilite*(toto_xyz.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0/(alp+(float)0.5));
			    rapport_renor.y=J/VAR_T.MAG.courants_tuyau[i].courant_lateral.p_y.longueur();
			    sig_sur_2.assigne_facteur(vec_un_xyz,cici*mu0_ou_un_sur_eps0/2);
			}

			if(index_direction_champ!=4){
			    bi_chp_later.assigne(champ_des_autres(1,31,-1,31,true));
			    //vec_un_xyz.assigne(zed,VAR_B.geom.proj_perp_au_ray[VAR_B.nombre_x_z[31]-1]);
			    vec_un_xyz.assigne(flun,zed,zed);
			    cici=VAR_T.MAG.courants_tuyau[i].courant_lateral.p_x.longueur();
			    alp=bi_chp_later.p_x.scalaire(vec_un_xyz)/(cici*mu0_ou_un_sur_eps0);
			    sig_sur_2.assigne_facteur(vec_un_xyz,cici*mu0_ou_un_sur_eps0/2);
			    champ_total_x_constant.assigne_additionne(sig_sur_2,bi_chp_later.p_x);	
			    //toto_xyz.assigne(champ_au_centre,zed);
			    float J=susceptibilite*(champ_total_x_constant.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0/(alp+(float)0.5));
			    rapport_renor.x=J/cici;
			    toto_xyz.print(" toto_xyz ");
			    champ_total_x_constant.print("J "+J*mu0_ou_un_sur_eps0+" courant_lateral.p_x.longueur() "+cici*mu0_ou_un_sur_eps0+" champ_total_x_constant ");
			}
		    }
		}
	    }
	    if(electrique)
		VAR_B.ELEC.renorme();
	    else
		VAR_B.MAG.renorme();
	    if(tuyau_central)
		VAR_T.renorme();
	    sig_sur_2.print_sl(" susceptibilite "+susceptibilite+" alp "+alp+" sig_sur_2 ");
	    bi_chp_later.print(" rapport_renor x "+rapport_renor.x+" y "+rapport_renor.y+" bi_chp_later ");
	    po_xyz.assigne(centre,zed);
	    champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
	    champ_au_centre.print_sl(" fin recalcule champ_au_centre. ");
	    //VAR_B.x_ellps[1000]=0;
	    
	}
        void modifie_les_Q_ou_I(){
	    if(du_nouveau)
		return;
	    centre.print_sl(" centre ");
	    pt_xyz.assigne(centre,zed);
	    champ_au_centre.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
	    
	    champ_au_centre.print(" champ_au_centre  ");
	    int ihb=1,iav=0;
	    float y=0,dix=0;
	    System.out.println();
	    boolean calculer=false;
	    int ihaut_bas=1;
	    for (int itet=0;itet<32;itet++){
		horloge();
		y=VAR_B.geom.x_y_ellipse[itet].p1.y;
		for (int ifi=0;ifi<VAR_B.geom.nombre_x_z[itet];ifi++){
		    cucu=VAR_B.geom.x_z_ellipse[itet][ifi].p1.x;
		    if(tuyau_central)//%%%%attention
			cucu+=largeur/2;
		    pt_xyz.assigne(cucu,+y,VAR_B.geom.x_z_ellipse[itet][ifi].p1.z);
		    pt_xyz.additionne(centre,zed);
		    //ecrire_champ=iav==0&&ihb==1&&ifi==32&&itet==15;
		    //if(ecrire_champ)
			if((ifi==0||ifi==VAR_B.geom.nombre_x_z[itet]/2||ifi==VAR_B.geom.nombre_x_z[itet]-1)&&(itet==2||itet==7||itet==15||itet==29)){
			//if(ifi==7&&itet==7&&iav==0){
			System.out.println();
			System.out.println(" ihb "+ihb+" iav "+iav+" itet "+itet+" ifi "+ifi);
			pt_xyz.print_sl("VAR_B.x_z_ellipse.x "+VAR_B.geom.x_z_ellipse[itet][ifi].p1.x+" y "+y+" pt_xyz ");
		    }
		    //ecrire_champ=(itet==15&&ifi==VAR_B.geom.nombre_x_z[itet]/2);
		    bi_chp_later.assigne(champ_en_un_point_ellipse(pt_xyz,ihaut_bas,itet,-1,ifi,false));
		    ecrire_champ=false;
		    if(electrique){
			VAR_B.ELEC.calcule_composantes_charges(itet,ifi);
		    }else{
			VAR_B.MAG.calcule_composantes_courants(itet,ifi);
		    }
		}
	    }
	    //VAR_B.y_ellps[1000]=0; 
	    if(tuyau_central){
		int i=0;
		for (int itet=0;itet<32;itet++){
		    horloge();
		    y=VAR_B.geom.x_y_ellipse[itet].p1.y;
		    dix=centre.x-largeur/fldeux-(float)largeur/(float)128.;
		    tutu_y_z.assigne(centre.y+y,bb*VAR_B.geom.proj_du_ray[itet].z);
		    for (int iL=0;iL<32;iL++){
			dix+=largeur/(float)64.;
			po_xyz.assigne(dix,tutu_y_z);
			//if(ecrire_champ)
			//ecrire_champ=(ihb==0&&iav==0&&itet==7&&iL==32);
			//tetelogic=((itet==7||itet==24))&&(iL==31||iL==7);
			bi_chp_later.assigne(champ_en_un_point_ellipse(po_xyz,ihaut_bas,itet,-1,iL,true));
			/*
			if(((itet==7||itet==24))&&(iL==31||iL==63||iL==7||iL==0)){
			    System.out.println("");
			    System.out.println("ihb "+ihb+"iav "+iav+" itet "+itet+" iL "+iL);
			    po_xyz.print_sl(" y "+y+" po_xyz ");
			}
			*/
			i=VAR_T.index[itet][iL];
			if(electrique){
			    VAR_T.ELEC.calcule_composantes_charges(i);
			}else{
			    //mu0_sig_sur_2.assigne_facteur(VAR_B.MAG.tangente_x_constant,VAR_B.MAG.I_x_constant*mu0_ou_un_sur_eps0/2);
			    VAR_T.MAG.calcule_composantes_courants(i);
			}
		    }
		}
	    }
	    System.out.println();
	    for (int itet=0;itet<32;itet++){
		for (int ifi=0;ifi<VAR_B.geom.nombre_x_z[itet];ifi++){//on ne prend que la partie avant (phi>0)
		    if(!electrique){
			VAR_B.MAG.deux_indices_mag[itet][ifi].I_x_constant=VAR_B.MAG.deux_indices_mag[itet][ifi].I_reca_x;
			VAR_B.MAG.deux_indices_mag[itet][ifi].I_y_constant=VAR_B.MAG.deux_indices_mag[itet][ifi].I_reca_y;
		    }else{
			VAR_B.ELEC.deux_indices_elec[itet][ifi].sigma.assigne(VAR_B.ELEC.deux_indices_elec[itet][ifi].sigma_reca);
		    }
		}
	    }
	    if(!electrique){
		sum_x_avant/=ss;
		System.out.println(" sum_x_avant "+sum_x_avant+" ss "+ss+" pi*rayon*rayon/2 "+pi*rayon*rayon/2);
		System.out.println(); 
	    }
	    if(tuyau_central){
		for (int i=0;i<1024;i++){
		    if(!electrique){
			VAR_T.MAG.courants_tuyau[i].courant_lateral.assigne(VAR_T.MAG.courants_tuyau[i].courant_recalcu_xyz);
		    }else{
			VAR_T.ELEC.charges_tuyau[i].sigma_laterale.assigne(VAR_T.ELEC.charges_tuyau[i].sigma_reca_tuyau);
		    }
		}
	    }
	}
	public void elimine(){
	    if(charges_et_courants!=null){
		charges_et_courants.dispose();
		charges_et_courants=null;
	    }	    
	}
	boolean est_dedans_ellipse(point dist){
	    if(spherique)
		return (dist.longueur_carre()<rayon*rayon);
	    else
		return (dist.x*dist.x/aa2+dist.y*dist.y/bb2<1);
	}
	boolean est_dedans(point dist,int d){
	    if(!tuyau_central){
		if(spherique)
		    return (dist.longueur_carre()<(rayon+d)*(rayon+d));
		else
		    return (dist.x*dist.x/((aa+d)*(aa+d))+dist.y*dist.y/((bb+d)*(bb+d))<1);
	    }else{
		if(Math.abs(dist.y)>bb+d)
		    return false;
		else{
		    if(Math.abs(dist.x)<largeur/2)
			return true;
		    else{
			cucu=Math.abs(dist.x)-largeur/2;
			return (cucu*cucu/((aa+d)*(aa+d))+dist.y*dist.y/((bb+d)*(bb+d))<1);
		    }
		}
	    }
	}
	    /*
	      if(Math.abs(dist.x)<largeur/2){
	      return (Math.abs(dist.y)<rayon);
	      }else{
	      if(dist.x>0)
	      tata.assigne(dist.x-largeur,dist.y);
	      else
	      tata.assigne(dist.x+largeur,dist.y);
	      return est_dedans_ellipse(tata);
	      }
	      }
	    */
	boolean far_from_boarders(point dist){
	    if(Math.abs(dist.x)<largeur/2){
		return (Math.abs(Math.abs(dist.y)-rayon)>distance_minimum_au_bord);
	    }else{
		if(dist.x>0)
		    tata.assigne(dist.x-largeur/2,dist.y);
		else
		    tata.assigne(dist.x+largeur/2,dist.y);
		if(spherique)
		    return (Math.abs(tata.longueur()-rayon)>distance_minimum_au_bord);
		else{
		    if(tata.longueur_carre()>1){
			coco=tata.y*tata.y/tata.longueur_carre();
			cucu=tata.x*tata.x/tata.longueur_carre();
			cece=(float)Math.sqrt(flun/(coco/bb2+cucu/aa2));
			return ((float)Math.abs(tata.longueur()-cece)>distance_minimum_au_bord);
		    }else{
			return true;
		    }
		}
	    }
	}
	void paint(boolean avec_champs){
	    subject.eraserect(graph,0,0,1000,1200,Color.white);
	    peint_le_cadre(graph);
	    if(avec_champs)
		chp_clc.dessiner_les_champs((float)0.5);
	    po_xyz.assigne(centre,zed);
	    //ecrire_champ=true; 
	    champ_au_centre.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());

	    //ecrire_champ=false; 
	    champ_au_centre.print("  champ_au_centre. ");
	    ecrit_champ_au_centre_sur_ecran();
	    if(graph==gTampon_ell){
		try {
		    tracker_ell.waitForID(1); 
		}
		catch (InterruptedException e){
		    System.out.println(" image pas arrivee?");
		}
		peinture(image_ell);
	    }
	}
	void peint_le_cadre(Graphics gr){
	    gr.setColor(Color.black);
	    for (int moitie=1;moitie>-2;moitie-=2)
		gr.drawArc((int)(centre.x+moitie*largeur/2-aa),(int)(centre.y-bb),(int)(2*aa),(int)(2*bb),90,-moitie*180);
	    tete.assigne(zed,rayon);
	    tata.assigne_additionne(centre,tete);
	    toto_int=(int)largeur/2;
	    gr.drawLine((int)tata.x-toto_int,(int)tata.y,(int)tata.x+toto_int,(int)tata.y);
	    tata.assigne_soustrait(centre,tete);
	    gr.drawLine((int)tata.x-toto_int,(int)tata.y,(int)tata.x+toto_int,(int)tata.y);
	}
	/*
	int ittet(float phi_part_centrale,int ihbb,int iavv){
	    float dfi=1000,dfifi=0;
	    int itetta=0;
	    for (int iteta=0;iteta<32;iteta++){
		dfifi=Math.abs(phi_part_centrale-VAR_B.phi_partie_centrale[ihbb][iteta][iavv]);
		if(dfifi<dfi){
		    dfi=dfifi;
		    itetta=iteta;
		}
	    }
	    return itetta;
	}
	*/
	point_xyz champ_total_en_un_point(point_xyz pt_aux_xyz,boolean inclue_chp_ext){
	    bi_fieldd.assigne(champ_en_un_point_ellipse(pt_aux_xyz,-10,-10,-10,-10,false));
	    champ_en_un_point.assigne(bi_fieldd.somme());
	    if(inclue_chp_ext)
		champ_en_un_point.additionne(champ_exterieur_xyz);
	    if(ecrire_champ)
		champ_en_un_point.print(" fieldd ");
	    return champ_en_un_point;
	}
	point sigma_B_avec_symetries(point sigma,int itet,int ifi,int ihb,int iav){
	    ssg.assigne(sigma);
	    if(ifi>=VAR_B.geom.nombre_x_z[itet])
		ssg.x=-ssg.x;
	    if(iav==1)
		ssg.x=-ssg.x;
	    if(ihb==0)
		ssg.y=-ssg.y;
	    return ssg;
	}
	point sigma_T_avec_symetries(point sigma,int il,int ihb,int iav){
	    ssg.assigne(sigma);
	    if(il>=32)
		ssg.x=-ssg.x;
	    if(iav==1)
		ssg.x=-ssg.x;
	    if(ihb==0)
		ssg.y=-ssg.y;
	    return (ssg);
	}
	bi_point_xyz I_B_xyz_avec_symetries(int itet,int ifi,int ifj,int ihb,int iav){
	    ccxy=VAR_B.MAG.deux_indices_mag[itet][ifj].I_x_constant;
	    u_cr_x_constant.assigne(VAR_B.MAG.deux_indices_mag[itet][ifj].u_courant_x_constant);
	    if(iav==1)
		u_cr_x_constant.y=-u_cr_x_constant.y;
	    if(ihb==0)
		u_cr_x_constant.z=-u_cr_x_constant.z;

	    u_cr_x_constant.multiplie_cst(-ccxy);
	    
	    ccxy=VAR_B.MAG.deux_indices_mag[itet][ifj].I_y_constant;

	    u_cr_y_constant.assigne(VAR_B.geom.deux_indices[itet][ifj].u2_xyz);
	    if(ifi>=VAR_B.geom.nombre_x_z[itet])
		u_cr_y_constant.z=-u_cr_y_constant.z;
	    if(iav==1)
		u_cr_y_constant.assigne_oppose();

	    u_cr_y_constant.multiplie_cst(ccxy);
	    fluss.assigne(u_cr_x_constant,u_cr_y_constant);
	    
	    return (fluss);
	}
	bi_point_xyz I_T_xyz_avec_symetries(int ii,int ihb,int iav){
	    u_cr_x_constant.assigne(VAR_T.MAG.courants_tuyau[ii].courant_lateral.p_x);
	    u_cr_x_constant.x=0;
	    if(iav==1)
		u_cr_x_constant.y=-u_cr_x_constant.y;
	    if(ihb==0)
		u_cr_x_constant.z=-u_cr_x_constant.z;
	    
	    u_cr_y_constant.assigne(VAR_T.MAG.courants_tuyau[ii].courant_lateral.p_y);
	    if(iav==1)
		u_cr_y_constant.assigne_oppose();
	    fluss.assigne(u_cr_x_constant,u_cr_y_constant);	    
	    return (fluss);
	}
	bi_point_xyz champ_en_un_point_ellipse(point_xyz posi_xyz,int ihaut_bas_rej,int itet_rej,int iavar_rej,int ifi_ou_iL_rej,boolean dans_tuyau){
	    point_xyz pos_xyz=new point_xyz(posi_xyz);
	    if(ecrire_champ)
		posi_xyz.print_sl(" pppppppppp posi_xyz ");
	    if(ihaut_bas_rej==1&&itet_rej==0&&iavar_rej==-1&&ifi_ou_iL_rej==31)
		posi_xyz.print_sl(" pppppppppp posi_xyz ");
	    bi_feld_xyz.assigne(zer_xyz,zer_xyz); 
	    if(calculer_centres_charges)
		center_charges.reset();
	    int ihb=0,iav=0;float y=0;float S=0;
	    float dds=0,ccxy=0;
	    int ii=0,ifj=0;
	    //if(1==0)
	    for (int ihaut_bas=-1;ihaut_bas<2;ihaut_bas+=2){
		ihb=0;
		if(ihaut_bas==1)
		    ihb=1;
		for (int iavar=-1;iavar<2;iavar+=2){
		    iav=0;
		    if(iavar==1)
			iav=1;
		    if(ecrire_champ)
		       bi_feld_xyz.print(" ihb "+ihb+" iav "+iav+" bi_feld_xyz ");
		    for (int itet=0;itet<32;itet++){
			y=ihaut_bas*VAR_B.geom.x_y_ellipse[itet].p1.y;
			for (int ifi=0;ifi<2*VAR_B.geom.nombre_x_z[itet];ifi++){
			    ifj=ifi;
			    if(ifi>=VAR_B.geom.nombre_x_z[itet])
				ifj=2*VAR_B.geom.nombre_x_z[itet]-1-ifi;
			    totologic=!(ihaut_bas_rej==ihaut_bas&&itet_rej==itet&&iavar_rej==iavar&&ifi_ou_iL_rej==ifi);
			    /*
			    if(!totologic){
				toto_xyz.assigne(-iavar*VAR_B.geom.x_z_ellipse[itet][ifj].p1.x,y,-iavar*VAR_B.geom.x_z_ellipse[itet][ifj].p1.z);
				toto_xyz.additionne(centre,zed);
				toto_xyz.print_sl("qqqqqqqqqqqqqqqqqqq ihb "+ihb+" iav "+iav+"itet "+itet+" ifi "+ifi+" toto_xyz ");
			    }
			    */
			    //if(ecrire_champ&&(itet==0||itet==15||itet==31)&&(ifi==0||ifi==VAR_B.geom.nombre_x_z[itet]/2||ifi==VAR_B.geom.nombre_x_z[itet]||ifi==3*VAR_B.geom.nombre_x_z[itet]/2||ifi==2*VAR_B.geom.nombre_x_z[itet]-1))
			    //	System.out.println(" ihb "+ihb+" iav "+iav+" VAR_B.geom.nombre_x_z[itet] "+VAR_B.geom.nombre_x_z[itet]+" ifi "+ifi+" ifj "+ifj+" totologic "+totologic);

			    if(totologic||calculer_centres_charges){
				toto_xyz.assigne(-iavar*VAR_B.geom.x_z_ellipse[itet][ifj].p1.x,y,-iavar*VAR_B.geom.x_z_ellipse[itet][ifj].p1.z);
				if(ifi>=VAR_B.geom.nombre_x_z[itet])
				    toto_xyz.x=-toto_xyz.x;
				tutu.assigne(centre);
				if(tuyau_central)
				    if(ifi<VAR_B.geom.nombre_x_z[itet]&&iav==0||ifi>=VAR_B.geom.nombre_x_z[itet]&&iav==1){
					tutu.x+=largeur/2;
				    }else
					tutu.x-=largeur/2;
				toto_xyz.additionne(tutu,zed);
				tata_xyz.assigne_soustrait(toto_xyz,pos_xyz);
				caca=tata_xyz.longueur();
				caca=caca*caca*caca;
				dds=Math.abs(VAR_B.geom.d_surface[itet][ifj]);
				S+=dds;
				if(electrique){
				    sisig.assigne(sigma_B_avec_symetries(VAR_B.ELEC.deux_indices_elec[itet][ifj].sigma,itet,ifi,ihb,iav));
				    if(ecrire_champ&&(itet==0||itet==15||itet==31)&&(ifi==0||ifi==VAR_B.geom.nombre_x_z[itet]/2||ifi==VAR_B.geom.nombre_x_z[itet]||ifi==3*VAR_B.geom.nombre_x_z[itet]/2||ifi==2*VAR_B.geom.nombre_x_z[itet]-1)){
					System.out.println(" ihb "+ihb+" iav "+iav+" nombre_x_z[itet] "+VAR_B.geom.nombre_x_z[itet]+" itet "+itet+" ifi "+ifi+" ifj "+ifj);
					toto.assigne_facteur(sisig,mu0_ou_un_sur_eps0);
					toto.print(" dds "+dds+" caca "+caca+" sisig/eps0 ");
					VAR_B.ELEC.deux_indices_elec[itet][ifj].sigma.print(" sigma ");
				    }

				    if(calculer_centres_charges)
					center_charges.incrementer(toto_xyz.extrait_point(),sisig.somme(),dds);
				    bi_feld_xyz.p_x.soustrait_facteur(tata_xyz,sisig.x*dds/caca);
				    bi_feld_xyz.p_y.soustrait_facteur(tata_xyz,sisig.y*dds/caca);
				    //if(d_c<(float)10.&&itet==32&&(ifi==0||ifi==15)){
				    
				    
				}else{
				    I_B_xyz.assigne(I_B_xyz_avec_symetries(itet,ifi,ifj,ihb,iav));
				    //if(ecrire_champ&&itet==7&&(ifi==7||ifi==2*VAR_B.geom.nombre_x_z[itet]-1-7))
				    //I_B_xyz.print(" ihb "+ihb+" iav "+iav+" itet "+itet+" ifi "+ifi+" I_B_xyz ");

				    pt_xyz.assigne_facteur((I_B_xyz.p_x.vectoriel(tata_xyz)),dds/caca);						   
				    bi_feld_xyz.p_x.additionne(pt_xyz);
				    pt_xyz.assigne_facteur((I_B_xyz.p_y.vectoriel(tata_xyz)),dds/caca);
				    bi_feld_xyz.p_y.additionne(pt_xyz);
				    //if(ecrire_champ&&ihb==1&&iav==0&&(itet==0||itet==7||itet==15||itet==24||itet==30||itet==31)&&(ifi==0||ifi==VAR_B.geom.nombre_x_z[itet]/2||ifi==VAR_B.geom.nombre_x_z[itet]||ifi==3*VAR_B.geom.nombre_x_z[itet]/2||ifi==2*VAR_B.geom.nombre_x_z[itet]-1)){
if(ecrire_champ&&ihb==1&&iav==0&&(itet==0||itet==7||itet==15||itet==24||itet==30||itet==31)&&(ifi==0||ifi==VAR_B.geom.nombre_x_z[itet]/2||ifi==VAR_B.geom.nombre_x_z[itet]-1)){
					System.out.println(" ihb "+ihb+" iav "+iav+" itet "+itet+" ifi "+ifi+" dds "+dds);
					I_B_xyz.print(" I_B_xyz ");
					System.out.println(" I_x_constant "+VAR_B.MAG.deux_indices_mag[itet][ifj].I_x_constant+" I_y_constant "+VAR_B.MAG.deux_indices_mag[itet][ifj].I_y_constant);
				    }
				    //if(tata_xyz.longueur_carre()>1){
				}
			    }
			    //}
			}
		    }
		}
	    }
	    if(ecrire_champ){
		bi_feld_xyz.print(" mi_parcours bi_feld_xyz ");
		titi_xyz.assigne_facteur(bi_feld_xyz.p_x,facteur_fondamental);
		titi_xyz.print(" mi_parcours bi_feld_xyz.p_x *f.f. ");
		titi_xyz.assigne_facteur(bi_feld_xyz.p_y,facteur_fondamental);
		titi_xyz.print(" mi_parcours bi_feld_xyz.p_y *f.f. ");
	    }
	    //if(1==0)
	    float dpphh=0;
	    if(tuyau_central){
		point_xyz tatata_xyz=new point_xyz(zer_xyz);
		int itetafi=0,iLL=0;
		float dix=0,d_phi=0,rdfi=0,S_laterale=0;
		for (int ihaut_bas=-1;ihaut_bas<2;ihaut_bas+=2){
		    ihb=0;
		    if(ihaut_bas==1)
			ihb=1;
		    for (int iavar=-1;iavar<2;iavar+=2){
			iav=0;
			if(iavar==1)
			    iav=1;
			if(ecrire_champ)
			    bi_feld_xyz.print("ihb "+ihb+" iav "+iav+" bi_feld_xyz");
			for (int itet=0;itet<32;itet++){
			    y=ihaut_bas*VAR_B.geom.x_y_ellipse[itet].p1.y;
			    //if(!extremites_seules){
			    itetafi=itet;
			    dpphh+=VAR_B.geom.d_phi_partie_centrale[itetafi];
			    rdfi=rayon*VAR_B.geom.d_phi_partie_centrale[itetafi];
			    S_laterale+=(rdfi*largeur);
			    dix=centre.x-largeur/fldeux-(float)largeur/(float)128.;
			    for (int iL=0;iL<64;iL++){
				dix+=largeur/(float)64.;
				totologic=true;
				if(dans_tuyau){
				    totologic=!(ihaut_bas_rej==ihaut_bas&&itet_rej==itetafi&&iavar_rej==iavar&&ifi_ou_iL_rej==iL);
				    /*
				    if(!totologic&&tetelogic){
				    toto_y_z.assigne_facteur(VAR_B.proj_du_ray[ihb][itetafi][iav],(float)rayon);
				    toto_y_z.y+=centre.y;
				    toto_xyz.assigne(dix,toto_y_z);
				    toto_xyz.print(" ttttttttttoto_xyz. ");
				    }
				    */
				}
				if(totologic||calculer_centres_charges){
				    //if(!(itetafi==itetafi_rejete&&iL==iL_rejete)){

				    toto_y_z.assigne_facteur(VAR_B.geom.proj_du_ray[itetafi],(float)rayon);
				    if(ihb==0)
					if(iav==0){
					    toto_y_z.y=-toto_y_z.y;
					}else{
					    toto_y_z.assigne_oppose();
					}
				    else
					if(iav==1)
					    toto_y_z.z=-toto_y_z.z;
				    toto_y_z.y+=centre.y;
				    toto_xyz.assigne(dix,toto_y_z);
				    tata_xyz.assigne_soustrait(toto_xyz,pos_xyz);
				    coco=tata_xyz.longueur();
				    tete_xyz.assigne(zed,zed,zed);
				    iLL=iL;
				    if(iLL>=32)
					iLL=63-iL;
				    ii=VAR_T.index[itetafi][iLL];
				    if(electrique){
					titi.assigne(sigma_T_avec_symetries(VAR_T.ELEC.charges_tuyau[ii].sigma_laterale,iL,ihb,iav));//en fait sigma_laterale_x_constantet  sigma_laterale_y_constant
					if(ecrire_champ&&(itet==0||itet==15||itet==31)&&(iL==0||iL==47||iL==63)){
					    titi.print(" ihb "+ihb+" iav "+iav+" itet "+itet+" iL "+iL+" sigma_T_avec_symetries");
					}
					cici=rdfi*largeur/(float)64.;
					if(calculer_centres_charges)
					    center_charges.incrementer(toto_xyz.extrait_point(),titi.somme(),cici);
					tete_xyz.additionne_facteur(tata_xyz,cici*titi.x/(coco*coco*coco));
					bi_feld_xyz.p_x.soustrait(tete_xyz);
					tete_xyz.additionne_facteur(tata_xyz,cici*titi.y/(coco*coco*coco));
					bi_feld_xyz.p_y.soustrait(tete_xyz);
				    }else{
					I_T_xyz.assigne(I_T_xyz_avec_symetries(ii,ihb,iav));
					titi_xyz.assigne_facteur((tata_xyz.vectoriel(I_T_xyz.p_x)),rdfi*largeur/(float)64./(coco*coco*coco));
					bi_feld_xyz.p_x.additionne(titi_xyz);
					titi_xyz.assigne_facteur((tata_xyz.vectoriel(I_T_xyz.p_y)),rdfi*largeur/(float)64./(coco*coco*coco));
					bi_feld_xyz.p_y.additionne(titi_xyz);
					/*
					if(ecrire_champ&&(itet==0||itet==7||itet==15||itet==24||itet==31)&&iL==31){
					    I_T_xyz.print(" ihb "+ihb+" iav "+iav+" itet "+itet+" iL "+iL+" I_T_xyz ");
					    VAR_T.MAG.courants_tuyau[ii].courant_lateral.print(" courant_lateral ");
					    titi_xyz.print_sl(" titi_xyz ");
					}
					*/
				    }
				}
			    }
			}
		    }
		}
		if(ecrire_champ)
		    System.out.println(" S_laterale "+S_laterale+" attendue "+2*pi*rayon*largeur+" dpphh "+dpphh);
	    }
	    if(calculer_centres_charges)
		center_charges.calcule();
	    if(ecrire_champ)
		bi_feld_xyz.print(" final bi_feld_xyz");

	    bi_feld_xyz.p_x.multiplie_cst(facteur_fondamental);
	    bi_feld_xyz.p_y.multiplie_cst(facteur_fondamental);
	    if(ecrire_champ)
		bi_feld_xyz.print(" S++++ "+S+" 4*pi*rayon*rayon "+4*pi*rayon*rayon+"bi_ feld ");
		//VAR_B.d_surface[1000][1000]=0;	
	    return bi_feld_xyz;
	}
	
	//point champ_en_un_point_vertical(point posit,float z_bidon){
	
	class courants_et_charges extends Frame{
	    point gauche_ext_plus=new point(zer); 
	    point droite_ext_plus=new point(zer);
	    Image image_densite; 
	    Graphics gTampon_densite;
	    MediaTracker tracker_densite;
	    couleur couleur_lum=new couleur(0,0,0);
	    point_xyz pt_F=new point_xyz(zer_xyz);
	    boolean partie_centrale=false,depart_ellipse=true,prrint=false;
	    vecteur v_dessin=new vecteur(zer,zer);
	    float angle_de_cote,cos_angle_de_cote,sin_angle_de_cote;
	    int iLLL=0;
	    point pt_D=new point(zer),pt_E=new point(zer),pt_C=new point(zer);
	    point pt_diff2=new point(zer),pt_diff=new point(zer);
	    public courants_et_charges(String s){
		super(s);	    
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    //while(occupied){
			    //}
			    if(charges_et_courants!=null){
				charges_et_courants.dispose();
				charges_et_courants=null;
			    }
			    dispose();
			}
		    });		
		setVisible(true);
		setSize(right_ens-left_ens,bottom_cote-top_cote);
		setLocation(left_ens+i_ens*600,top_cote);
		gr_de_cote=getGraphics();
		gr_de_cote.setFont(subject.times_gras_14);
		angle_de_cote=pi/(float)3.;
		cos_angle_de_cote=(float)Math.cos(angle_de_cote);
		sin_angle_de_cote=(float)Math.sin(angle_de_cote);
		paalette=new palette();
		image_densite=createImage(right_ens-left_ens,bottom_ens-top_ens);
		gTampon_densite=image_densite.getGraphics();
		tracker_densite=new MediaTracker(this);
		tracker_densite.addImage(image_densite,1);
		if(i_demarre<3||demo)
		    graph_de_cote=gr_de_cote;
		else
		    graph_de_cote=gTampon_densite;
	    }
	    void dessine_de_cote(){
		//setVisible(true);
		if(derniere_commande=="montrer les champs totaux"||derniere_commande=="montrer les champs induits"||derniere_commande=="montrer les lignes de champ")
		    return;
		subject.eraserect(graph_de_cote,0,0,1000,1000,Color.white);
		graph_de_cote.setColor(Color.black);
		float Q=zed;
		paalette.initie();
		int iav=0,ii=0,ifj=0;
		int ihb_max=0,ifi_max=0,itet_max=0;
		for (int ihb=0;ihb<2;ihb++)
		    for (int itet=0;itet<32;itet++){
			//for (int iav=0;iav<2;iav++)
			for (int ifi=0;ifi<2*VAR_B.geom.nombre_x_z[itet];ifi++){
			    ifj=ifi;
			    if(ifi>=VAR_B.geom.nombre_x_z[itet])
				ifj=2*VAR_B.geom.nombre_x_z[itet]-1-ifi;
			    if(electrique){
				sisig.assigne(sigma_B_avec_symetries(VAR_B.ELEC.deux_indices_elec[itet][ifj].sigma,itet,ifi,ihb,0));
				paalette.met_a_jour(sisig.somme());
			    }else{
				I_B_xyz.assigne(I_B_xyz_avec_symetries(itet,ifi,ifj,ihb,0));
				caca=paalette.I_ou_Q_max;
				paalette.met_a_jour((I_B_xyz.somme()).longueur());
				if(paalette.I_ou_Q_max>caca){
				    ihb_max=ihb;
				    ifi_max=ifi;
				    itet_max=itet;
				}

			    }
			}
		    }
		System.out.println(" maximum bouchons ihb_max"+ihb_max+" itet_max "+itet_max+" ifi_max "+ifi_max+" ifj "+ifj+" I_ou_Q_max "+paalette.I_ou_Q_max);
		float I_ou_Q_max_T=0;
		int iL_max=0,iLL=0;
		if(tuyau_central){
		    for (int ihb=0;ihb<2;ihb++)
			for (int itet=0;itet<32;itet++){						    for (int iL =0;iL <64;iL ++){
				iLL=iL;
				if(iLL>=32)
				    iLL=63-iL;
				ii=VAR_T.index[itet][iLL];
				if(electrique){
				    toto.assigne(sigma_T_avec_symetries(VAR_T.ELEC.charges_tuyau[ii].sigma_laterale,iL,ihb,0));//en fait sigma_laterale_x_constant et sigma_laterale_y_constant
				    coco=toto.somme();
				    paalette.met_a_jour(coco);
				    paalette.met_a_jour(-coco);
				}else{
				    I_T_xyz.assigne(I_T_xyz_avec_symetries(ii,ihb,0));
				    coco=(I_T_xyz.somme().longueur());
				    paalette.met_a_jour(coco);
				    //paalette.met_a_jour(-coco);
				    caca=paalette.I_ou_Q_max;
				    if(I_ou_Q_max_T<coco){
					ihb_max=ihb;
					iL_max=iL;
					itet_max=itet;
					I_ou_Q_max_T=coco;
				    }
				}
			    }		
			}
		}
			System.out.println(" maximum  tuyau ihb_max"+ihb_max+" itet_max "+itet_max+" iL_max "+iL_max+" I_ou_Q_max_T "+I_ou_Q_max_T); 

		if(electrique)
		    paalette.I_ou_Q_min=-paalette.I_ou_Q_max;
		if(prrint)  
		    System.out.println("ùùùùùù I_ou_Q_min "+paalette.I_ou_Q_min+" I_ou_Q_max "+paalette.I_ou_Q_max);
		float courant=zed;
		po_xyz.assigne(centre,zed);
		if(electrique)
		    calculer_centres_charges=true;
		champ_au_centre.assigne(champ_total_en_un_point(po_xyz,inclue_champ_exterieur).extrait_point());
		calculer_centres_charges=false;
		toto.assigne_normalise(champ_au_centre);
		if(prrint)
		    champ_au_centre.print_sl(" normalise x "+toto.x+" y "+toto.y+" champ_au_centre ");
		if(electrique)
		    toto.assigne_oppose();
		float phiphi=0;
		float teta=(float)Math.atan2(toto.y,toto.x),r=0;
		coco=(float)Math.cos(teta);
		cucu=(float)Math.sin(teta);
		float tgteta=cucu/coco;
		point pt1=new point(zer);
		point tangente=new point(zer);
		if(!tuyau_central){
		    r=(float)Math.sqrt(flun/(cucu*cucu/bb2+coco*coco/aa2));
		    tangente.assigne(-cucu/bb2,coco/aa2);
		    pt1.assigne(r*coco,r*cucu);
		}else{
		    //if(index_direction_champ==4){tututu
		    if(index_direction_champ==4||tuyau_central&&index_direction_champ!=0){
			tangente.assigne(-flun,zed);
			pt1.assigne(zed,bb);
		    }else if(index_direction_champ==0){
			pt1.assigne(aa+largeur/2,zed);
			tangente.assigne(zed,flun);
		    }else if(index_direction_champ!=4){
			cece=tgteta*tgteta;
			a_prime=flun/aa2+cece/bb2;
			b_prime=largeur/2*cece/bb2;
			c_prime=cece*largeur*largeur/(4*bb2)-1;
			delta_prime=b_prime*b_prime-a_prime*c_prime;
			caca=(float)Math.sqrt(delta_prime);
			System.out.println(" cece "+cece+" a_prime "+a_prime+" delta_prime "+delta_prime);
			pt1.x=(-b_prime+caca)/a_prime;
			pt1.y=tgteta*(pt1.x+largeur/2);
			r=pt1.longueur();
			coco=pt1.x/r;
			cucu=pt1.y/r;
			tangente.assigne(-cucu/bb2,coco/aa2);
			pt1.x+=largeur/2;
		    }
		}
		tangente.normalise();
		point norma=new point(coco/aa2,cucu/bb2);
		norma.normalise();
		if(Math.abs(pt1.x)<0.01)
		    pt1.x=0;
		if(prrint){
		    tangente.print(" tangente ");
		    norma.print_sl(" teta "+teta+" norma ");
		    pt1.print(" pt1 ");
		}
		point pt2=new point(-pt1.x,-pt1.y);
		pt_diff.assigne_soustrait(pt2,pt1);
		int nb=(int)pt1.longueur()/6;
		pt_diff.divise_cst((float)nb);
		float y_E=0,x_E=0;
		pt_C.assigne(pt1);
		pt_C.additionne_diviseur(pt_diff,fldeux);
		while (pt_C.x>=-Math.abs(pt_diff.x)/2-0.1){
		    //pt_C.print_sl("ùùù  pt_C ");
		    //if(index_direction_champ==4){tututu
		    if(index_direction_champ==4||tuyau_central&&index_direction_champ!=0){
			y_E=pt_C.y;
			x_E=largeur/2+aa*(float)Math.sqrt(flun-y_E*y_E/bb2);
			if(prrint)
			    pt_C.print_sl("ùùù  x_E "+x_E+" y_E "+y_E+" pt_C ");
			if(pt_C.y<-1)
			    break;
		    }else{
			//if(tgteta*largeur/2<bb){
			y_E=-bb;
			x_E=(y_E-pt_C.y)*tangente.x/tangente.y+pt_C.x;
			//
			if(prrint)
			    pt_C.print_sl("mmmmmmmmm x_E "+x_E+" y_E "+y_E+" totologic "+totologic+" tgteta "+tgteta+" pt_C "); 
			/*
			if(tuyau_central)
			    totologic=x_E>largeur/2;
			else
			    totologic=x_E>bb*tangente.x/tangente.y;
			//if(prrint)
			//			    totologic=(-bb-pt_C.y)*tangente.x/tangente.y+pt_C.x>Math.abs(y_E*tangente.x/tangente.y);

			if(!totologic&&!tuyau_central)
			    break;
			*/
			if(x_E>largeur/2){
			    depart_ellipse=true;
			    coco=aa2*norma.x*norma.x;    
			    a_prime=norma.y*norma.y/coco+flun/bb2;
			    cici=norma.x*pt_C.x+norma.y*pt_C.y-largeur/2*norma.x;
			    b_prime=-norma.y*cici/coco;
			    c_prime=cici*cici/coco-flun;
			    delta_prime=b_prime*b_prime-a_prime*c_prime;
			    cucu=(float)Math.sqrt(delta_prime);
			    y_E=(-b_prime-cucu)/a_prime;
			    //float y_p_E=(-b_prime+cucu)/a_prime;
			    if(index_direction_champ==0)
				x_E=pt_C.x;
			    else{
				x_E=aa*(float)Math.sqrt(1-y_E*y_E/bb2);
				//    float x_p_E=aa*(float)Math.sqrt(1-y_p_E*y_p_E/bb2);
				float ass=y_E*norma.y+(x_E+largeur/2)*norma.x;
				//float assp=y_p_E*norma.y+(x_p_E+largeur/2)*norma.x;
				float bss=pt_C.x*norma.x+pt_C.y*norma.y;
				if(prrint){
				    System.out.println(" ass "+ass+" bss "+bss);
				    pt_C.print(" x_E "+x_E+" y_E "+y_E+" pt_C ");
				}
				x_E+=largeur/2;
			    }
			}else{
			    y_E=-bb;
			    x_E=(y_E-pt_C.y)*tangente.x/tangente.y+pt_C.x;
			    if(prrint)
				pt_C.print("xxxxxxx x_E "+x_E+" y_E "+y_E+" pt_C ");
			    depart_ellipse=false;

			    //if(tuyau_central&&x_E<bb*tangente.x/tangente.y)
			    //break;
			}
		    }
		    pt_D.assigne(x_E,y_E);
		    pt_diff2.assigne_facteur(tangente,(float)10);//%%%%
		    //			    if(prrint)
		    pt_D.additionne_facteur(pt_diff2,(float)0.5);
		    int j=0;
		    partie_centrale=false;
		    int moitie=1;
		    while(pt_D.y<bb){
			if(tuyau_central)
			    if(moitie!=-1)
				if(!partie_centrale){
				    partie_centrale=pt_D.x<largeur/2;
				}else{
				    partie_centrale=pt_D.x>-largeur/2;
				    if(!partie_centrale)
					moitie=-1;
				}
			titilogic=appelle_dessin_point(moitie);
			if(!titilogic)
			    break;
			j++;
			pt_D.additionne(pt_diff2);
		    }
		    pt_C.additionne(pt_diff);
		}				

		peint_le_cadre(graph_de_cote);
		paalette.dessine(graph_de_cote);
		dessine_champ_exterieur(graph_de_cote);
		if(electrique)
		    center_charges.dessine(graph_de_cote);
		System.out.println("ùùùùùù I_ou_Q_min "+paalette.I_ou_Q_min+" I_ou_Q_max "+paalette.I_ou_Q_max);
		if(graph_de_cote==gTampon_densite){
		    System.out.println(" graph_de_cote=gTampon_densite! ");
			try {
			    tracker_densite.waitForAll(); 
			}
			catch (InterruptedException e){
			    System.out.println(" image pas arrivee?");
			}
			peinture_densite(image_densite);
		}
	    }
	    void peinture_densite(Image imag_d){
		System.out.println(" debut peinture_densite grande_taille_densite "+grande_taille_densite);
		if(grande_taille_densite){
		    System.out.println(" i_ens "+i_ens+" left_ens "+left_ens+" top_ens "+top_ens);
		    if(i_ens==1)
			subject.ens_de_dipoles[i_ens].setLocation(left_ens,top_ens);
		    subject.ens_de_dipoles[i_ens].setSize((right_ens-left_ens)*2,2*(bottom_ens-top_ens));
		    Point lo_dc=new Point(subject.ens_de_dipoles[i_ens].getLocation());
		    System.out.println(" lo_dc.x "+lo_dc.x+" lo_dc.y "+lo_dc.y);
		    Dimension dim_d=new Dimension(subject.ens_de_dipoles[i_ens].getSize());
		    System.out.println(" dim_d "+dim_d);
		    grp_c.drawImage(imag_d,left_ens,top_ens,2*(right_ens-left_ens),2*(bottom_ens-top_ens),subject.ens_de_dipoles[i_ens]);
		}else{
		    //for(int i=0;i<100;i++){
		    gr_de_cote.drawImage(imag_d,left_ens,top_ens,subject.ens_de_dipoles[i_ens]);
		    System.out.println(" left_cote "+left_cote+" top_cote "+top_cote);
		    //}
		}
	    }
	    boolean appelle_dessin_point(int moitie){
		int ihaut_bas=-1,ihb=0,itet_ou_fi=0;
		tetelogic=true;
		if(prrint)
		    pt_D.print(" partie_centrale "+partie_centrale+" pt_D ");
		if(!partie_centrale){
		    cici=pt_D.x-moitie*largeur/2;
		    coco=flun-cici*cici/aa2-pt_D.y*pt_D.y/bb2;
		    if(coco>0){
			coco=bb*(float)Math.sqrt(coco);
			pt_F.assigne(pt_D,coco);
			if(pt_F.x*pt_F.x+pt_F.y*pt_F.y<0.1){
			    itet_ou_fi=0;
			    ihaut_bas=-1;
			    ihb=0;
			}else{
			    for (int ite=0;ite<32;ite++){
				if(Math.abs(pt_D.y)<VAR_B.geom.x_y_ellipse[ite].p1.y-VAR_B.geom.x_y_ellipse[ite].p2.y/2){
				    if(ite/8*8==ite&&prrint)
					System.out.println(" ite "+ite+"x_y_ellipse[ite].p1.y "+VAR_B.geom.x_y_ellipse[ite].p1.y+" x_y_ellipse[ite].p2.y "+VAR_B.geom.x_y_ellipse[ite].p2.y);
				    itet_ou_fi=ite;
				    break;  
				}
			    }
			}
		    }else
			return false;
		}else{
		    pt_F.assigne(pt_D,(float)Math.sqrt(bb2-pt_D.y*pt_D.y));
		    float phi=(float)Math.atan2(pt_F.z,pt_F.y);

		    for (int i=0;i<32;i++){
			if(phi>VAR_B.geom.phi_partie_centrale[i]-VAR_B.geom.d_phi_partie_centrale[i]){
			    if(i/8*8==i&&prrint)
				System.out.println(" i "+i+" VAR_B.geom.phi_partie_centrale[i] "+VAR_B.geom.phi_partie_centrale[i]+" VAR_B.geom.d_phi_partie_centrale[i] "+VAR_B.geom.d_phi_partie_centrale[i]);
			    itet_ou_fi=i;
			    break;  
			}
			
		    }
		    if(prrint)
			System.out.println(" phi "+phi+" itet_ou_fi "+itet_ou_fi+" VAR_B.geom.phi_partie_centrale[itet_ou_fi] "+VAR_B.geom.phi_partie_centrale[itet_ou_fi]);
		}
		if(pt_F.y>0){
		    ihaut_bas=1;
		    ihb=1;
		}else{
		    ihaut_bas=-1;
		    ihb=0;
		}
		titi.assigne_additionne(centre,pt_D);
		dessin_point(ihb,itet_ou_fi,titi);
		//titi.print(" centre x "+centre.x+" y "+centre.y+" toto ");
		return true;
	    }
	    void calcule_geom(int iteth,int ifih,int ihaut_bas){
		if(iteth<0||iteth>31||ifih<0||ifih>63)
		    return;
		ihaut_bas=1;
		float xx=0,yy=0,zz=0,rr=0;
		yy=ihaut_bas*VAR_B.geom.x_y_ellipse[iteth].p1.y;
		rr=(float)Math.sqrt(flun-yy*yy/(bb2));
		xx=VAR_B.geom.x_z_ellipse[iteth][ifih].p1.x*rr;
		zz=VAR_B.geom.x_z_ellipse[iteth][ifih].p1.z*rr;
		cucu=xx*xx/aa2+yy*yy/bb2+zz*zz/bb2;
		if(prrint)
		    System.out.println(" iteth "+iteth+" ifih "+ifih+" zz "+zz+"  xx   "+xx+" yy  "+yy+" cucu "+cucu);
	    }
	    void dessin_point(int ihb,int ite_fi,point pptt){
		//System.out.println(" partie_centrale "+partie_centrale+" ihb "+ihb+" ite_fi "+ite_fi);
		int ifii=0,ifj=0;
		if(!partie_centrale){
		    int num=0;vecteur_x_z vec=new vecteur_x_z(zer_x_z,zer_x_z);
		    if(prrint)
			System.out.println(" ite_fi "+ite_fi+" pt_F.x "+pt_F.x+" VAR_B.geom.nombre_x_z[ite_fi] "+VAR_B.geom.nombre_x_z[ite_fi]); 
		    for (int iph=0;iph<VAR_B.geom.nombre_x_z[ite_fi];iph++){
			vec.assigne(VAR_B.geom.x_z_ellipse[ite_fi][iph]);
			if(Math.abs(pt_F.x)>0){
			    if(prrint)
				vec.print(" iph "+iph+" vec ");
			    if(Math.abs(pt_F.x)>vec.p1.x-Math.abs(vec.p2.x/2)){
				if(pt_F.x>0)
				    ifii=iph;
				else
				    ifii=2*VAR_B.geom.nombre_x_z[ite_fi]-1-iph;
				ifj=iph;
				break;
			    }  
			} 
		    }
		    if(electrique){
			sisig.assigne(sigma_B_avec_symetries(VAR_B.ELEC.deux_indices_elec[ite_fi][ifj].sigma,ite_fi,ifii,ihb,0));
			couleur_lum=paalette.determine_la_couleur(sisig.somme());
			vas_y_dessine(pptt);
			if(pt_C.longueur_carre()>pt_diff.longueur_carre()/4){
			    sisig.assigne(sigma_B_avec_symetries(VAR_B.ELEC.deux_indices_elec[ite_fi][ifj].sigma,ite_fi,2*VAR_B.geom.nombre_x_z[ite_fi]-1-ifii,1-ihb,0));
			    couleur_lum=paalette.determine_la_couleur(sisig.somme());
			    titi.assigne_soustrait(pptt,centre);
			    toto.assigne_soustrait(centre,titi);
			    vas_y_dessine(toto);
			    if(prrint&&ihb==1){
				System.out.println("$ ihb "+ihb+" ite_fi "+ite_fi+" ifii "+ifii);
				System.out.println(" nombre_x_z[ite_fi] "+VAR_B.geom.nombre_x_z[ite_fi]);
				System.out.println(" sigma.l "+VAR_B.ELEC.deux_indices_elec[ite_fi][ifj].sigma.longueur());

				couleur_lum.print("$ ihb "+ihb+" ite_fi "+ite_fi+" ifii "+ifii+" nombre_x_z[ite_fi] "+VAR_B.geom.nombre_x_z[ite_fi]+" sigma.l "+VAR_B.ELEC.deux_indices_elec[ite_fi][ifj].sigma.longueur()+" couleur_lum ");
				VAR_B.geom.x_z_ellipse[ite_fi][ifj].p1.print_sl(" pt_F.x "+pt_F.x+" x_z_ellipse.p1.print_sl ");
			    }
			}
		    }else{
			I_B_xyz.assigne(I_B_xyz_avec_symetries(ite_fi,ifii,ifj,ihb,0));
			cucu=(I_B_xyz.somme()).longueur();
			if(cucu>paalette.I_ou_Q_max)
			    cucu=paalette.I_ou_Q_max*(float)0.999;
			if(prrint)
			    System.out.println(" ite_fi "+ite_fi+" ifj "+ifj+" ifii "+ifii+" cucu "+cucu);
			couleur_lum=paalette.determine_la_couleur(cucu);
			tete.assigne_normalise((I_B_xyz.somme()).extrait_point());	
			vas_y_dessine(pptt);
			if(prrint){
			    couleur_lum.print("*ite_fi "+ite_fi+" num "+num+" nb "+VAR_B.geom.nombre_x_z[ite_fi]+" ifij "+ifj+" cucu "+cucu+" couleur ");
			}
			if(pt_C.longueur_carre()>pt_diff.longueur_carre()/4){
			    I_B_xyz.assigne(I_B_xyz_avec_symetries(ite_fi,2*VAR_B.geom.nombre_x_z[ite_fi]-1-ifii,ifj,1-ihb,0));
			    cucu=(I_B_xyz.somme()).longueur();
			    if(cucu>paalette.I_ou_Q_max)
				cucu=paalette.I_ou_Q_max*(float)0.999;
			    couleur_lum=paalette.determine_la_couleur(cucu);
			    tete.assigne_normalise((I_B_xyz.somme()).extrait_point());
			    titi.assigne_soustrait(pptt,centre);
			    toto.assigne_soustrait(centre,titi);
			    vas_y_dessine(toto);
			}
		    }
		}else{
		    iLLL=(int)(32*(pt_F.x+largeur)/(float)largeur);
		    int illl=iLLL;
		    if(iLLL>31)
			iLLL=63-iLLL;
		    int j=VAR_T.index[ite_fi][iLLL];
		    if(electrique){
		  	toto.assigne(sigma_T_avec_symetries(VAR_T.ELEC.charges_tuyau[j].sigma_laterale,illl,ihb,0));//en fait sigma_laterale_x_constant et sigma_laterale_y_constant
			couleur_lum=paalette.determine_la_couleur(toto.somme());
			vas_y_dessine(pptt);
			if(prrint)
			    couleur_lum.print("$ ihb "+ihb+" ite_fi "+ite_fi+" iL "+iLLL+" pptt x "+pptt.x+" y "+pptt.y+" couleur_lum ");
			if(pt_C.longueur_carre()>pt_diff.longueur_carre()/4){
			    toto.assigne(sigma_T_avec_symetries(VAR_T.ELEC.charges_tuyau[j].sigma_laterale,illl,1-ihb,0));//en fait sigma_laterale_x_constant et  sigma_laterale_x_constant
			    couleur_lum=paalette.determine_la_couleur(toto.somme());
			    titi.assigne_soustrait(pptt,centre);
			    toto.assigne_soustrait(centre,titi);
			    vas_y_dessine(toto);
			}
		    }else{
			tete.assigne_normalise((I_T_xyz_avec_symetries(j,ihb,0).somme()).extrait_point());
			cucu=(I_T_xyz_avec_symetries(j,ihb,0).somme()).longueur();
			if(prrint)
			    System.out.println(" ite_fi "+ite_fi+" iLLL "+iLLL+" illl "+illl+" cucu "+cucu);
			couleur_lum=paalette.determine_la_couleur(cucu);
			vas_y_dessine(pptt);
			if(prrint){
			    couleur_lum.print("$ ihb "+ihb+" ite_fi "+ite_fi+" iL "+iLLL+" couleur_lum ");
			    VAR_T.MAG.courants_tuyau[j].courant_lateral.print(" courant_lateral ");  
			}
			if(pt_C.longueur_carre()>pt_diff.longueur_carre()/4){
			    tete.assigne_normalise((I_T_xyz_avec_symetries(j,1-ihb,0).somme()).extrait_point());
			    cucu=(I_T_xyz_avec_symetries(j,1-ihb,0).somme()).longueur();
			    //cucu=tete.longueur();
			    couleur_lum=paalette.determine_la_couleur(cucu);
			    titi.assigne_soustrait(pptt,centre);
			    toto.assigne_soustrait(centre,titi);
			    vas_y_dessine(toto);
			}
		    }
		}
	    }
	    void vas_y_dessine(point pptt){
		if(electrique){
		    subject.paintrect_couleur(graph_de_cote,(int)pptt.y-1,(int)pptt.x-1,(int)pptt.y+1,(int)pptt.x+1,couleur_lum.col);
		}else{
		    if(partie_centrale)
			// if(index_direction_champ==0)
			tete.assigne_oppose();
		    v_dessin.p1.assigne(tete);
		    v_dessin.p2.assigne(pptt);
		    if(prrint)
		    	v_dessin.print(" v_dessin ");
		    //if(!(index_direction_champ==4&&v_dessin.p1.x>0.01)) 
		    v_dessin.dessine((float)5.,flun,graph_de_cote,couleur_lum.col);
		}
	    }
	}	
    }
    class barreau_cylindrique_infini extends bille_ou_barreau_cylindrique{
	courants_et_charges courants_et_charges;
	int iteration=0;
	float surf_extrem_elem=0;
	float surf_laterale_elem=0;
	float dI_axial_lateral_sur_rdphi[]=new float [64];
	float dI_ortho_radial_sur_dx;
	float rdphi;
	barreau_cylindrique_infini(point ctr1, float rayon1, float longueur1,float largeur1){
	    super();
	    susceptibilite=80;
	    centre=new point(ctr1);
	    centre_multipole.assigne(centre);
	    rayon=(int)rayon1;longueur=(int)longueur1;
	    facteur_fondamental=mu0_ou_un_sur_eps0/(4*pi);            ;
	    surf_extrem_elem=pi*rayon*rayon/(16*16);
	    fais_les_calculs(true);	    
	}
	public void ecrit_aide(){
	}
	void appelle_VAR_B(boolean f){
	}
	public void determine_la_direction_ext(int i){
	}
	point pt_de_depart_surf(float tt){
	    return toto;
	}
	void fais_les_calculs(boolean initi){
	    electrique=electrique0;
	    rdphi=2*pi*rayon/(float)64.;
	    float phi=-2*pi/(float)64.,sinphi=0;
	    for (int ifi=0;ifi<64;ifi++){	
		phi+=2*pi/(float)64.;
		dI_axial_lateral_sur_rdphi[ifi]=champ_exterieur.y*susceptibilite*(float)Math.sin(phi)/mu0_ou_un_sur_eps0;
		//dI_axial_lateral_sur_rdphi[ifi]=zed;
	    }
	    dI_ortho_radial_sur_dx=champ_exterieur.x*susceptibilite/mu0_ou_un_sur_eps0;
	    surf_laterale_elem=rayon*longueur/(float)64.*pi/16;
	    if(!electrique)
		courants_et_charges=new courants_et_charges(" Densites de courant ");	    	
	    else
		courants_et_charges=new courants_et_charges("  Densites de charges ");     
	    chp_clc.remplis();
	    paint(true);
	    fais_lignes_de_champ_barreaux();
	    scale=(float)champ_exterieur.longueur();//pour la vue de face
	    courants_et_charges.centre_deface.print(" centre_deface ");
	}
	public void elimine(){
	    if(courants_et_charges!=null){
		courants_et_charges.dispose();
		courants_et_charges=null;
	    }	    
	}
	public void change_la_demie_(int demie_h,boolean v_pas_h,boolean tt){
	    if(v_pas_h)
		rayon=demie_h;
	    else{
	    }
	    fais_les_calculs(false);
	}
	boolean far_from_boarders(point dist){
	    return (float)Math.abs((float)Math.abs(dist.y)-rayon)>distance_minimum_au_bord;
	}
	boolean est_dedans(point dist,int d){
	    return true;	    
	}
	public void paint(boolean avec_champs){
	    System.out.println("mmmmmmmmmmmmmm entree paint iter "+iter+" i_ens "+i_ens);
	    //if(deja_venu)
	    //	projection_du_rayon_sur_y_z[1000]=null;
	    //deja_venu=true;
	    graph.setColor(Color.black);
	    graph.drawLine((int)(centre.x-500 ),(int)(centre.y-rayon),(int)(centre.x+500 ),(int)(centre.y-rayon));
	    graph.drawLine((int)(centre.x-500 ),(int)(centre.y+rayon),(int)(centre.x+500 ),(int)(centre.y+rayon));
       	    vecteur v=new vecteur(zer,zer);
	    courants_et_charges.dessine_de_face();
	    if(avec_champs)chp_clc.dessiner_les_champs((float)0.7);
	}
	point_xyz champ_total_en_un_point(point_xyz pt_xyz,boolean inclu_chp_ext){
	    tutu.assigne_soustrait(pt_xyz.extrait_point(),centre);
	    toto_xyz.assigne(champ_partiel_en_un_point(pt_xyz));
	    if(tutu.longueur_carre()<20)
		toto_xyz.print(" toto_xyz truc ");
	    if(inclu_chp_ext)
		toto_xyz.additionne(champ_exterieur_xyz);
	    return toto_xyz;
	}
	point_xyz champ_partiel_en_un_point(point_xyz posi_xyz){
	    point_xyz pos_xyz=new point_xyz(posi_xyz);
	    tutu_xyz.assigne(zer_xyz);
	    titi_y_z.assigne(pos_xyz.y,pos_xyz.z);
	    for (int ifi=0;ifi<64;ifi++){
		toto_y_z.assigne(centre.y,zed);	
		toto_y_z.additionne_facteur(projections_du_rayon[ifi],(float)rayon);
		tata_y_z.assigne_soustrait(titi_y_z,toto_y_z);
		if(tata_y_z.longueur_carre()>1){
		    coco=tata_y_z.longueur_carre();
		    tutu_xyz.x-=(dI_ortho_radial_sur_dx*tata_y_z.scalaire(projections_du_rayon[ifi])/coco);//on devrait multiplier par , mais le fact.fondam. est mu0/2pi
		    tata_y_z.multiplie_cst(dI_axial_lateral_sur_rdphi[ifi]*rdphi/coco);
		    tata_y_z.rotation(zed,flun); 


		    tutu_xyz.additionne(zed,tata_y_z.y,tata_y_z.z);
		    //tata_y_z.print(" coco "+coco+" tata_y_z ");
		    //System.out.println(" ifi "+ifi+" dI_axial_lateral_sur_rdphi[ifi] "+dI_axial_lateral_sur_rdphi[ifi]+" dI_ortho_radial_sur_dx "+dI_ortho_radial_sur_dx);
		}
	    }
	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    tutu_xyz.additionne(champ_exterieur_xyz);
	    return tutu_xyz;
	}
	class courants_et_charges extends Frame{
	    Graphics gr_deface;
	     point_y_z centre_deface;
	    int right_face=300+left_ens+i_ens*600,left_face=0+left_ens+i_ens*600,bottom_face=600+top_ens,top_face=300+top_ens;
	    public courants_et_charges(String s){
		super(s);	    
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    //while(occupied){
			    //}
			    if(courants_et_charges!=null){
				courants_et_charges.dispose();
				courants_et_charges=null;
			    }
			    dispose();
			};
		    });		
		setVisible(true);
		setSize(right_face-left_face,bottom_face-top_face);
		setLocation(left_face,top_face);
		gr_deface=getGraphics();
		centre_deface=new point_y_z((float)(bottom_face-top_face)/2,(float)(right_face-left_face)/2);
		
	    }
	    void dessine_de_face(){
		gr_deface.setColor(Color.black);
		gr_deface.drawOval((int)centre_deface.z-rayon,(int)centre_deface.y-rayon,2*rayon,2*rayon);
		vecteur_y_z v_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		for (int ifi=0;ifi<64;ifi+=4){
		    toto_y_z.assigne_facteur(projections_du_rayon[ifi],(float)rayon);
		    toto_y_z.additionne(centre_deface);
		    toto_int=(int)Math.round(10*(float)Math.abs(dI_axial_lateral_sur_rdphi[ifi]/dI_axial_lateral_sur_rdphi[16]));
		    if(dI_axial_lateral_sur_rdphi[ifi]>0)
			gr_deface.setColor(Color.red);
		    else
			gr_deface.setColor(Color.blue);
		    gr_deface.drawOval((int)toto_y_z.z-toto_int,(int)toto_y_z.y-toto_int,2*toto_int,2*toto_int);
		    titi_y_z.assigne_facteur(projections_perp_au_rayon[ifi],-flun);
		    v_y_z.assigne(titi_y_z,toto_y_z);
		    v_y_z.dessine(flun,flun,gr_deface,Color.orange);
		    v_y_z.print(" ifi "+ifi+" v_y_z ");
		}
		vecteur_y_z vecteur_chp_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		//position_y_z.assigne((float)top_face,(float)(left_face));
		position_y_z.assigne(zed,zed);
		point_xyz vvv=new point_xyz(zer_xyz); 
		for (int kk=0;kk<2000;kk++){
		    tata_y_z.assigne_soustrait(position_y_z,centre_deface);
		    if(Math.abs(tata_y_z.longueur()-rayon)>distance_minimum_au_bord){
			vecteur_chp_y_z.pnt.assigne(position_y_z);
			toto_y_z.assigne(position_y_z.y-centre_deface.y+centre.y,position_y_z.z-centre_deface.z);
			//toto.assigne(zed,toto_y_z.y);
			pt_xyz.assigne(zed,toto_y_z);
			//vvv.assigne(champ_partiel_en_un_point(toto,toto_y_z.z));
			vvv.assigne(champ_partiel_en_un_point(pt_xyz));
			vecteur_chp_y_z.chp=vvv.extrait_point_y_z();
			vecteur_chp_y_z.dessine(scale,fct_zm_sspl,gr_deface,Color.green);
			if(kk/10*10==kk)
			    vecteur_chp_y_z.print(" kkùùùù "+kk+" vecteur_chp_y_z ");
			if(kk/100*100==kk)
			    System.out.println(" centre_deface.y "+centre_deface.y+" centre.y "+centre.y+" centre_deface.z "+centre_deface.z); 
			toto_y_z.assigne_soustrait(centre_deface,position_y_z);
			if(toto_y_z.longueur_carre()<40*40)	
			    System.out.println("***************** position_y_z.y-centre_deface.y+centre.y "+(position_y_z.y-centre_deface.y+centre.y));	
			}
		    position_y_z.z+=20;
		    if(position_y_z.z>right_face-left_face){
			position_y_z.z=zed;
			position_y_z.y+=20;
			if(position_y_z.y>bottom_face-top_face)
			    break;
		    }
		}
		/*
		if(!electrique){
		  System.out.println(" bottom_face "+bottom_face+" top_face "+top_face+" left_face "+left_face+" right_face "+right_face);
		  projection_du_rayon_sur_y_z[1000]=null;
		}
		*/	    
	    }
	}	
    }
    abstract class dipole extends multipl_ou_barreau{
	vecteur dip;
	float q_ou_i=zed;point pt_dipole;
	point pos_plus,pos_moins;
	float sin_angle_mean[]=new float[8];
	float cos_angle_mean[]=new float[8];
	dipole(point centre1,float q_ou_i1,point pt_dipole1){
	    super();
	    centre=new point(centre1);
	    q_ou_i=(float)q_ou_i1; 
	    pt_dipole=new point(pt_dipole1);
	    raccourci_symetrique=false;
	    calcul_cosinus();
	    if(electrique){
		pos_plus=new point(centre);
		pos_plus.additionne_facteur(pt_dipole,(float)0.5);
		pos_moins=new point(pos_plus);
		pos_moins.soustrait(pt_dipole);
	    }else{
		rayon=(int)Math.round((float)Math.sqrt((float)Math.abs(pt_dipole.x)/pi));
		System.out.println(" i_ens "+i_ens+" rayon "+rayon+" pt_dipole.x "+pt_dipole.x);
	    }
	    dip=new vecteur(centre,pt_dipole);
	    if(n_dipoles_ou_barreaux==1)
		montre_champs_et_paint(true);
	}
	dipole(point centre1,point dipol1){
	    super();
	    centre=new point(centre1);
	    dip=new vecteur(zer,zer);
	    pt_dipole=new point(dipol1);
	    raccourci_symetrique=false;
	    dip.assigne(centre,pt_dipole);
	    calcul_cosinus();
	    if(dipol1.x>0)
		cucu=4;
	    else
		cucu=-4;
	    if(electrique){
		pos_plus=new point(centre);
		pos_plus.additionne(cucu,zed);
		pos_moins=new point(centre);
		pos_moins.soustrait(cucu,zed);
		q_ou_i=(float)pt_dipole.longueur()/(2*cucu);; 
	    }else{
		rayon=8;
		float s=pi*rayon*rayon;
		q_ou_i=pt_dipole.longueur()/s;
	    }
	    if(n_dipoles_ou_barreaux==1)
		montre_champs_et_paint(true);
	}
	void fais_les_calculs(boolean initi){
	    electrique=electrique0;
	}
	public void ecrit_aide(){
	}
	public boolean gere_menus_souris(){
	    return true;
	}
	void montre_champs_et_paint(boolean avec_champs){
		centre_multipole.assigne(centre);
		centre_multipole.print_sl(" cccccccccccccccccentre_multipole ");
		chp_clc.remplis();
		if(avec_champs){
		    chp_clc.dessiner_les_champs(fldeux);
		    fais_lignes_de_champ_dipole();
		}
		paint(true);
	}
	void calcul_cosinus(){
	    cucu=-pi/16;
	    for (int i=0;i<8;i++){
		cucu+=pi/8;
		sin_angle_mean[i]=(float)Math.sin(cucu);
		cos_angle_mean[i]=(float)Math.cos(cucu);
	    }
	}
	void paint(boolean avec_champs){
	    centre.print(" q_ou_i" +q_ou_i+ " centre ");
	    if(electrique){
		/*
		subject.paintcircle_couleur(graph,(int)Math.round(centre.x+pt_dipole.x/2),(int)Math.round(centre.y),4,Color.red);
		subject.paintcircle_couleur(graph,(int)Math.round(centre.x-pt_dipole.x/2),(int)Math.round(centre.y),4,Color.blue);
		*/
		subject.paintcircle_couleur(graph,(int)Math.round(pos_plus.x),(int)Math.round(pos_plus.y),4,Color.red);
		subject.paintcircle_couleur(graph,(int)Math.round(pos_moins.x),(int)Math.round(pos_moins.y),4,Color.blue);
		subject.paintcircle_couleur(graph,(int)Math.round(centre.x),(int)Math.round(centre.y),4,Color.black);
	    }else{
		graph.setColor(Color.blue);
		graph.fillOval((int)(float)Math.round(centre.x-(float)(rayon+1)/2),(int)centre.y-(rayon+1),rayon+1,2*(rayon+1));
		graph.setColor(Color.white);
		graph.fillOval((int)(float)Math.round(centre.x-(float)(rayon-1)/2),(int)centre.y-(rayon-1),rayon-1,2*(rayon-1));
		vecteur v1=new vecteur(zer,zer);
		toto_int=1;
		if(pt_dipole.x<0)
		   toto_int=-1;
		v1.chp.assigne(zed,(float)0.1*toto_int);
		v1.pnt.assigne(centre.x+(rayon)/2,centre.y);
		v1.dessine((float)40.,flun,graph,Color.blue);
		v1.pnt.assigne(centre.x+(rayon)/2,centre.y+1);
		v1.dessine((float)40.,flun,graph,Color.blue);

	    }
	    //if(!demo)
	    //sin_angle_mean[1000]=zed;
	}
	void fais_lignes_de_champ_dipole(){
	    if(electrique){
		//point_de_depart.assigne_additionne(centre,pos_moins);
		point_de_depart.assigne(pos_plus);
		pos_plus.print_sl(" n_dipoles_ou_barreaux "+n_dipoles_ou_barreaux+" pos_plus ");
	    }else{
		point_de_depart.assigne(centre);
	    }
	    point_de_depart.print(" point_de_depart ");	
	    int x=0,y=0,x1=0,y1=0;
	    int nbl_min=0,nbl_max=15;
	    if(electrique)
		nbl_max=27;
	    float phi_depart=-pi/((float)(nbl_max-nbl_min)*2);
	    float ecart=20;
	    if(!electrique)
		ecart=rayon/(float)(nbl_max-nbl_min);

	    for (int i=nbl_min;i<=nbl_max;i++){
		System.out.println(" i  $$$$ "+i+" nbl_min "+nbl_min+" nbl_max "+nbl_max);
		if(!electrique)
		    point_sur_la_ligne.assigne(point_de_depart.x,point_de_depart.y+ecart*(Math.abs(i)+(float)0.5));
		else{
		    phi_depart+=pi/(float)(nbl_max-nbl_min);
		    toto.assigne((float)Math.cos(phi_depart),(float)Math.sin(phi_depart));
		    point_sur_la_ligne.assigne_additionne(point_de_depart,toto);		
		}		       
		totologic=true;
		/*
		if(!electrique){
		    toto.assigne_soustrait(point_sur_la_ligne,centre);
		    totologic=toto.longueur_carre()>rayon;
		}
		*/
		if(totologic){
		    point_sur_la_ligne.print_sl(" $$i "+i+"point_sur_la_ligne ");
		    graph.setColor(Color.green);
		    int k_fin=0;fin_ligne=false;
		    for (int kk=0;kk<1500;kk++){
			/*
			  if(i==0&&(kk-1)/100*100==kk-1){
			  point_sur_la_ligne.print("kk "+kk+" x "+x+" y "+y+" point_sur_la_ligne ");
			  unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");				
			  }
			*/
			inside=est_dans_la_fenetre(point_sur_la_ligne);
			if(kk==0)
			    inside_prec=inside;
			if(inside_prec&&!inside)
			    break;
			inside_prec=inside;
			x=(int)Math.round(point_sur_la_ligne.x);
			y=(int)Math.round(point_sur_la_ligne.y);

			graph.drawLine(x,y,x,y);
			//if(n_dipoles_ou_barreaux==2){
			coco=centre_multipole.x-(point_sur_la_ligne.x-centre_multipole.x);
			x1=(int)Math.round(coco);
			coco=centre_multipole.y-(point_sur_la_ligne.y-centre_multipole.y);
			y1=(int)Math.round(coco);
			if(n_dipoles_ou_barreaux!=2||symetrie_centre_suffisante){
			    graph.drawLine(x1,y,x1,y);
			    graph.drawLine(x,y1,x,y1);
			}
			graph.drawLine(x1,y1,x1,y1);
			po_xyz.assigne(point_sur_la_ligne,zed);
			chpt.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
			unitaire_suivant_champ.assigne_diviseur(chpt,chpt.longueur());
			if(ecrire_faute){
			    unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");
			    chpt.print_sl(" chpt ");
			}
			/*
			  if(i==0&&(kk/100*100==kk||((kk-1)/100*100==kk-1))){
			  point_sur_la_ligne.print("kk "+kk+" x "+x+" y "+y+" point_sur_la_ligne ");
			  unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");
			  chpt.print_sl(" chpt ");
			  }
			*/
			point_sur_la_ligne.additionne(unitaire_suivant_champ);
			//if(i==0&&(kk/100*100==kk||((kk-1)/100*100==kk-1)))
			//point_sur_la_ligne.print(" point_sur_la_ligne ");

			diff_point_de_depart.assigne_soustrait(point_sur_la_ligne,centre);
			//if(kk!=0&&n_dipoles_ou_barreaux==1&&diff_point_de_depart.x*diff_point_de_depart_prec.x<=0)
			if(kk!=0)
			    if(diff_point_de_depart.x*diff_point_de_depart_prec.x<0){
				fin_ligne=true;
			    }
			if(fin_ligne){
			    //System.out.println(" fin_ligne "+fin_ligne+" diff_point_de_depart.x "+diff_point_de_depart.x+" diff_point_de_depart_prec.x "+diff_point_de_depart_prec.x); 
			    break;
			}
			diff_point_de_depart_prec.assigne(diff_point_de_depart);
			//if(i==0&&kk/100*100==kk)
			//	point_sur_la_ligne.print("kk$$$ "+kk+" x "+x+" y "+y+" point_sur_la_ligne ");

			k_fin=kk;
		    }
		    point_sur_la_ligne.print_sl(" k_fin "+k_fin+"point_sur_la_ligne ");
		}			    
	    }
	    /*
	      if(!electrique&&!demo)
	      D_prec[1000]=null;
	    */
	}
	boolean pas_trop_proche_des_bords(point dist){
	    return true;
	}
      	boolean est_dedans(point dist, int d){
	    return true;	    
	}
	point_xyz champ_total_en_un_point(point_xyz posi_xyz,boolean inc){
	    point_xyz pos_xyz=new point_xyz(posi_xyz);
	    if(ecrire_faute)
		System.out.println(" champ_en_un_point dipole ");
	    float rrr=0;titi.assigne(zer);
	    toto.assigne_soustrait(posit,centre);
	    if(ecrire_faute){
		pos_xyz.print(" pos_xyz ");
		centre.print_sl(" centre ");
		toto.print_sl(" toto ");
	    }
	    point posit=new point(pos_xyz.extrait_point());
	    //if(!(i_demarre==2&&toto.longueur_carre()<5*5))
	    if(electrique){
		//q_dist_plus.print(" q_dist_plus ");
		//posit.print(" posit ");	
		//pos_plus.print(" pos_plus ");	
		q_dist_plus.assigne_soustrait(posit,pos_plus);
		aaa=(float)q_dist_plus.longueur();
		q_dist_plus.divise_cst(aaa*aaa*aaa);
		q_dist_moins.assigne_soustrait(posit,pos_moins);
		aaa=(float)q_dist_moins.longueur();
		q_dist_moins.assigne_oppose();
		q_dist_moins.divise_cst(aaa*aaa*aaa);
		titi.assigne_additionne(q_dist_plus,q_dist_moins);
		if(ecrire_faute){
		    q_dist_plus.print_sl(" q_dist_plus ");
		    q_dist_moins.print_sl(" q_dist_moins ");
		}
	    }else{
		titi.assigne(zer);
		for (int j=0;j<8;j++){
		    distance_minimum_a_l_arc=0;
		    point_central_arc.assigne(centre.y+rayon*cos_angle_mean[j],rayon*sin_angle_mean[j]);
		    courant.assigne(sin_angle_mean[j],-cos_angle_mean[j]);
		    distance_a_point_central_arc.assigne(posit.y-point_central_arc.y,-point_central_arc.z);
		    coco=centre.x-posit.x;
		    coco=(float)Math.sqrt(coco*coco+distance_a_point_central_arc.longueur_carre());
		    toto.assigne(distance_a_point_central_arc.vectoriel(courant),-(posit.x-centre.x)*courant.z);
		    toto.divise_cst(coco*coco*coco);
		    if(pt_dipole.x>0)
			titi.additionne(toto);
		    else
			titi.soustrait(toto);
		}
	    }
	    titi_xyz.assigne(titi,zed);
	    return titi_xyz;
	}
    }
    class dipole_de_pres extends dipole {
	public dipole_de_pres(point centre1,float q_ou_i1,point pt_dipole1){
	    super(centre1,q_ou_i1,pt_dipole1);
	    de_loin=false;
	    pt_dipole.print(" dipole_de_pres "+" q_ou_i1 "+q_ou_i1+" pt_dipole ");

	}
    }
    class dipole_de_loin extends dipole {
	dipole_de_loin(point centre1,point pt_dipole1){
	    super(centre1,pt_dipole1);
	    de_loin=true;
	    System.out.println(" dipole_de_loin "+" pt_dipole.x "+pt_dipole.x);
	}
    }

    class quadripole extends multipl_ou_barreau{
	float q_lam=0;boolean aus_fern=false;boolean symetrie=true;
	point d_pt_souris=new point(zer);
	quadripole(boolean vu_de_loin){
	    super();
	    aus_fern=vu_de_loin;
	    float distance_dipoles=100;
	    q_lam=180;
	    raccourci_symetrique=false;
	    if(vu_de_loin)
		distance_dipoles=10;
	    if(i_demarre==1&&electrique){
		distance_dipoles=200;
		q_lam=90;
	    }
	    centre_multipole.assigne((float)(left_ens+right_ens)/2,(float)(top_ens+bottom_ens)/2);
	    centre_multipole.print(" centre_multipole ");
	    centre=new point(centre_multipole);
	    if(!electrique)
		q_lam=3000;
	    coco=(float)distance_dipoles/2;
	    point ctr=new point(centre_multipole);
	    ctr.soustrait(coco,coco);
	    subject.eraserect(graph,0, 0, 1000, 1000,Color.white);
	    tutu.assigne(q_lam,zed);
	    n_dipoles_ou_barreaux=2;
	    if(vu_de_loin)
		bipole[0]=new dipole_de_loin(ctr,tutu);
	    else
		bipole[0]=new dipole_de_pres(ctr,q_lam,tutu);
	    point pt1=new point(ctr);
	    ctr.x+=distance_dipoles;
	    ctr.y+=distance_dipoles;
	    symetrie_centre_suffisante=(pt1.x==ctr.x||pt1.y==ctr.y);
	    tutu.assigne(-q_lam,zed);
	    if(vu_de_loin)
		bipole[1]=new dipole_de_loin(ctr,tutu);
	    else
		bipole[1]=new dipole_de_pres(ctr,q_lam,tutu);
	    fais_les_calculs(true);
	}
	void fais_les_calculs(boolean initi){
	    electrique=electrique0;
	    bipole[0].centre.print("bipole[0].centre ");
	    bipole[1].centre.print("bipole[1].centre ");
	    chp_clc.remplis();
	    chp_clc.dessiner_les_champs(fldeux);
	    if(i_demarre==1)
		fais_lignes_de_champ_quad_de_pres();
	    if(i_demarre==2)
		fais_lignes_de_champ_quad_de_loin();
	    //bipole[1000]=null;
	    paint(true);
	}
	public void ecrit_aide(){
	}
	boolean pas_trop_proche_des_bords(point dist){
	    tata.assigne_additionne(dist,centre_multipole);
	    titilogic=true;
	    if(!electrique){
		if(i_demarre==1)
		    cucu=15;
		else if(i_demarre==2)
		    cucu=Math.abs(bipole[1].centre.x-bipole[0].centre.x)/2;
		for (int i=0;i<2;i++){
		    if(Math.abs(tata.x-bipole[i].centre.x)<cucu&&Math.abs(tata.y-bipole[i].centre.y)<bipole[i].rayon*(float)1.5)
			titilogic=false;
		}
	    }else{
		for (int i=0;i<2;i++){
		    if(Math.abs(tata.y-bipole[i].centre.y)<10)
			if(Math.abs(tata.x-bipole[i].pos_plus.x)<10||(tata.x-bipole[i].pos_plus.x)*(tata.x-bipole[i].pos_moins.x)<0)
			    titilogic=false;
		}
	    }
	    return titilogic;
	}
	point_xyz champ_total_en_un_point(point_xyz posi_xyz,boolean inc ){
	    point_xyz pos_xyz=new point_xyz(posi_xyz);
	    tata.assigne(bipole[0].champ_total_en_un_point(pos_xyz,false).extrait_point());
	    if(ecrire_faute)
		tata.print_sl(" champ_en_un_point quadripole  tata");
	    tata.additionne(bipole[1].champ_total_en_un_point(pos_xyz,false).extrait_point());
	    /*
	    if(Math.abs(toto.y)<10&&Math.abs(toto.x)<200){
		posit.print_sl("posit ");
		bipole[0].champ_total_en_un_point(posit,posit_z).print_sl("bipole[0].champ_total_en_un_point(posit,posit_z) ");
		bipole[1].champ_total_en_un_point(posit,posit_z).print_sl("bipole[1].champ_total_en_un_point(posit,posit_z) ");
		tata.print(" tata ");
	    }
	    */						    

	    //tata.assigne(bipole[1].champ_total_en_un_point(posit,posit_z));
	    tata_xyz.assigne(tata,zed);
	    return tata_xyz;
	}
	public void paint(boolean avec_champs){
	    System.out.println("paint trouve_deplacement "+trouve_deplacement);
	    if(avec_champs)chp_clc.dessiner_les_champs(fldeux);
	    for(int iq=0;iq<n_dipoles_ou_barreaux;iq++)
		bipole[iq].paint(true);
	    //if(!electrique&&!demo)
	    //	bipole[1000]=null;
	}
	void fais_lignes_de_champ_quad_de_pres(){
	    int nb_pts_de_depart=0;
	    if(symetrie)
		nb_pts_de_depart=1;
	    else
		nb_pts_de_depart=2;
	    for (int jj=0;jj<nb_pts_de_depart;jj++){
		if(electrique){
		    point_de_depart.assigne(bipole[jj].pos_plus);
		    bipole[jj].pos_plus.print_sl(" n_dipoles_ou_barreaux "+n_dipoles_ou_barreaux+" bipole[jj].pos_plus ");
		}else{
		    point_de_depart.assigne(bipole[jj].centre);
		    point_de_depart.y-=(float)bipole[jj].rayon;
		}
		point_de_depart.print(" point_de_depart ");	
		int x=0,y=0,x1=0,y1=0;
		int nbl_min=0,nbl_max=63;
		float ecart=0;
		if(!electrique){
		    ecart=2*bipole[jj].rayon/(float)(nbl_max-nbl_min);
		    if(i_demarre==2)
			ecart*=3;
		}
		for (int i=0;i<64;i+=2){
		    System.out.println(" i  $$$$ "+i+" nbl_min "+nbl_min+" nbl_max "+nbl_max);
		    if(!electrique)
			point_sur_la_ligne.assigne(point_de_depart.x,point_de_depart.y+ecart*(Math.abs(i)+(float)0.5));
		    else{
			if(i_demarre==1){
			    if(i<32)
				toto.assigne(costet[2*i+1],sintet[2*i+1]);
			    else{
				toto.assigne(costet[2*(i-32)+1],sintet[2*(i-32)+1]);
				toto.y=-toto.y;
			    }
			}else{
			    if(i<32)
				toto.assigne(costet[i],sintet[i]);
			    else{
				toto.assigne(costet[i-32],sintet[(i-32)]);
				toto.y=-toto.y;
			    }
			}
			point_sur_la_ligne.assigne_additionne(point_de_depart,toto);
		    }		       
		    point_sur_la_ligne.print_sl(" $$i "+i+"point_sur_la_ligne ");
		    graph.setColor(Color.green);
		    int k_fin=0;fin_ligne=false;
		    for (int kk=0;kk<2000;kk++){
			x=(int)Math.round(point_sur_la_ligne.x);
			y=(int)Math.round(point_sur_la_ligne.y);

			graph.drawLine(x,y,x,y);
			//if(n_dipoles_ou_barreaux==2){
			if(symetrie){

			    coco=centre_multipole.x-(point_sur_la_ligne.x-centre_multipole.x);
			    x1=(int)Math.round(coco);
			    coco=centre_multipole.y-(point_sur_la_ligne.y-centre_multipole.y);
			    y1=(int)Math.round(coco);
			    graph.drawLine(x1,y1,x1,y1);
			}
			for (int n=0;n<2;n++){
			    //for (int n=0;n<1;n++){
			    if(electrique){
				if(kk==0)
				    D_prec[n].assigne(zer);
				D[n].assigne_soustrait(point_sur_la_ligne,bipole[n].pos_plus);
				if(D[n].longueur_carre()<3*3&&D[n].longueur_carre()<D_prec[n].longueur_carre()){
				    fin_ligne=true;
				    System.out.println("D[n].longueur_carre() "+D[n].longueur_carre()+"D_prec[n].longueur_carre() "+D_prec[n].longueur_carre());
				    
				}
			    }else{
				if(kk==0)
				    D_prec[n].assigne_soustrait(point_sur_la_ligne,bipole[n].centre);
				D[n].assigne_soustrait(point_sur_la_ligne,bipole[n].centre);
				if(kk/10*10==kk)
				    System.out.println(" kk "+kk+" D[n].x "+D[n].x+" D_prec[n].x)"+D_prec[n].x);
				if(D[n].x*D_prec[n].x<0)
				    fin_ligne=Math.abs(D[n].y)<bipole[n].rayon;
			    }
			    D_prec[n].assigne(D[n]);
			}
			ecrire_faute=kk==0;
			po_xyz.assigne(point_sur_la_ligne,zed);
			chpt.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
			unitaire_suivant_champ.assigne_diviseur(chpt,chpt.longueur());
			if(ecrire_faute){
			    unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");
			    chpt.print_sl(" chpt ");
			}
			/*
			  if(i==0&&(kk/100*100==kk||((kk-1)/100*100==kk-1))){
			  point_sur_la_ligne.print("kk "+kk+" x "+x+" y "+y+" point_sur_la_ligne ");
			  unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");
			  chpt.print_sl(" chpt ");
			  }
			*/
			point_sur_la_ligne.additionne(unitaire_suivant_champ);
			//if(i==0&&(kk/100*100==kk||((kk-1)/100*100==kk-1)))
			//point_sur_la_ligne.print(" point_sur_la_ligne ");

			diff_point_de_depart.assigne_soustrait(point_sur_la_ligne,point_de_depart);
			//if(kk!=0&&n_dipoles_ou_barreaux==1&&diff_point_de_depart.x*diff_point_de_depart_prec.x<=0)
			if(fin_ligne){
			    //System.out.println(" fin_ligne "+fin_ligne+" diff_point_de_depart.x "+diff_point_de_depart.x+" diff_point_de_depart_prec.x "+diff_point_de_depart_prec.x); 
			    break;
			}
			pt_prec.assigne(point_sur_la_ligne);
			diff_point_de_depart_prec.assigne(diff_point_de_depart);
			//if(i==0&&kk/100*100==kk)
			//	point_sur_la_ligne.print("kk$$$ "+kk+" x "+x+" y "+y+" point_sur_la_ligne ");

			k_fin=kk;
		    }
		    point_sur_la_ligne.print_sl(" k_fin "+k_fin+"point_sur_la_ligne ");
		}
	    }
	    /*
	      if(!electrique&&!demo)
	      D_prec[1000]=null;
	    */
	}
	void fais_lignes_de_champ_quad_de_loin(){
	    int i_sens=1;int x=0,y=0,x1=0,y1=0;
	    toto.assigne_soustrait(bipole[0].centre,centre_multipole);
	    float rayon_cercle_virtuel=8*toto.longueur();
	    point direction_initiale=new point(zer);
	    point dist_centre=new point(zer);
	    int i_max=64;
	    int j_max=1;
	    if(!symetrie){
		i_max=64;
		j_max=2;
	    }
	    for (int j=0;j<j_max;j++){
		for (int i=0;i<i_max;i++){
		    if(i<32)
			direction_initiale.assigne(costet[i],sintet[i]);
		    else{
			direction_initiale.assigne(costet[i-32],sintet[(i-32)]);
			direction_initiale.y=-direction_initiale.y;
		    }
		    if(j==1)
			direction_initiale.x=-direction_initiale.x;
		    point_sur_la_ligne.assigne_facteur(direction_initiale,rayon_cercle_virtuel);
		    point_sur_la_ligne.additionne(centre_multipole);
		    graph.setColor(Color.green);
		    int k_fin=0;fin_ligne=false;
		    po_xyz.assigne(point_sur_la_ligne,zed);
		    chpt.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
		    i_sens=1;
		    tete.assigne_soustrait(point_sur_la_ligne,centre_multipole);
		    if(tete.scalaire(chpt)<0)
			i_sens=-1;
		    if(i_sens==-1){
			for (int kk=0;kk<3000;kk++){
			    ecrire_faute=kk==-1;
			    po_xyz.assigne(point_sur_la_ligne,zed);
			    chpt.assigne(champ_total_en_un_point(po_xyz,false).extrait_point());
			    unitaire_suivant_champ.assigne_diviseur(chpt,i_sens*chpt.longueur());
			    if(ecrire_faute){
				unitaire_suivant_champ.print_sl(" unitaire_suivant_champ");
				chpt.print_sl(" chpt ");
			    }
			    if(!est_dans_la_fenetre(point_sur_la_ligne)){
			       unitaire_suivant_champ.multiplie_cst((float)4.0);
			       if(dist_centre.longueur_carre()>1000*1000)
				   unitaire_suivant_champ.multiplie_cst((float)10.0);
			       if(dist_centre.longueur_carre()>2000*2000)
				   unitaire_suivant_champ.multiplie_cst((float)10.0);
			}
			    point_sur_la_ligne.additionne(unitaire_suivant_champ);
			    //if(!est_dans_la_fenetre(point_sur_la_ligne))
			    //	break;
			    dist_centre.assigne_soustrait(point_sur_la_ligne,centre_multipole);
			    if(kk!=0&&dist_centre.longueur_carre()<rayon_cercle_virtuel*rayon_cercle_virtuel){
				
				break;
			    }
			    x=(int)Math.round(point_sur_la_ligne.x);
			    y=(int)Math.round(point_sur_la_ligne.y);
			    graph.drawLine(x,y,x,y);
			    //if(n_dipoles_ou_barreaux==2){
			    if(symetrie){
				coco=centre_multipole.x-(point_sur_la_ligne.x-centre_multipole.x);
				x1=(int)Math.round(coco);
				coco=centre_multipole.y-(point_sur_la_ligne.y-centre_multipole.y);
				y1=(int)Math.round(coco);
				graph.drawLine(x1,y1,x1,y1);
			    }

			    k_fin=kk;
			}
			direction_initiale.print_sl(" i "+i+" k_fin "+k_fin+" direction_initiale ");
		    }
		}	
	    }
	}
	boolean est_dedans(point dist,int d){
	    return true;	    
	}
	public boolean gere_menus_souris (){
	    boolean ret=false;
	    System.out.println(" pressee "+pressee+"trouve_deplacement"+trouve_deplacement);
	    float ax=0,ay=0;point dd=new point(zer);int idip=0,iq=0;
	    if (pressee){
		if(!trouve_deplacement){
		    for(idip=0;idip<2;idip++){
			System.out.println("idip cherche "+idip);
			iq=0;
			if(command=="deplacer un dipole par son centre"){
			    dd.assigne((float)ppmouseh,(float)ppmousev);
			    dd.soustrait(bipole[idip].centre);
			}else if(command=="deplacer une charge d'un dipole"){
			    for(iq=-1;iq<2;iq+=2){
				dd.assigne((float)ppmouseh,(float)ppmousev);
				if(iq==-1)
				    toto.assigne(bipole[idip].pos_moins);
				else
				    toto.assigne(bipole[idip].pos_plus);
				dd.soustrait(toto);
				if(dd.longueur_carre()<=3*3)
				    break;
			    }
			}
			if(dd.longueur_carre()<=3*3)
			    break;
		    }
		    if(dd.longueur_carre()<=3*3){
			comment="idip trouve "+idip;
			idip_dep=idip;
			iq_dep=iq;
			d_pt_souris.assigne((float)ppmouseh,(float)ppmousev);
			if(iq!=0){
			    comment+="charge trouvee "+iq;
			    if(iq==-1)
				d_pt_souris.soustrait(bipole[idip].pos_moins);
			    else
				d_pt_souris.soustrait(bipole[idip].pos_plus);
			}else{
			    d_pt_souris.soustrait(bipole[idip].centre);
			}
			trouve_deplacement=true;
			System.out.println(" *****************deplacement initial, idip "+idip+" pressee "+pressee);
			System.out.println("trouve_deplacement "+trouve_deplacement+" relachee "+relachee+" idip_dep "+idip_dep);
		    }
		}
	    }
	    if(trouve_deplacement){
		toto.assigne((float)ppmouseh,(float)ppmousev);
		toto.soustrait(d_pt_souris);
		/*
		  if(iq_dep==0){
		  bipole[idip_dep]=null;;
		  if(idip_dep==0)
		  tutu.assigne(q_lam,zed);
		  else
		  tutu.assigne(-q_lam,zed);
		  if(aus_fern)
		  bipole[idip_dep]=new dipole_de_loin(totog4498
,tutu);
		  else
		  bipole[idip_dep]=new dipole_de_pres(toto,q_lam,tutu);
		*/
		if(electrique)
		titi.assigne_soustrait(bipole[idip_dep].pos_plus,bipole[idip_dep].centre);
		if(iq_dep==0){
		    bipole[idip_dep].centre.assigne(toto);
		    if(electrique){
		    bipole[idip_dep].pos_plus.assigne_additionne(bipole[idip_dep].centre,titi);
		    bipole[idip_dep].pos_moins.assigne_soustrait(bipole[idip_dep].centre,titi);
		    }
		}else if(electrique){
		    symetrie=false;
		    if(iq_dep==-1)
			bipole[idip_dep].pos_moins.assigne(toto);
		    else
			bipole[idip_dep].pos_plus.assigne(toto);
		    bipole[idip_dep].centre.assigne_additionne(bipole[idip_dep].pos_moins,bipole[idip_dep].pos_plus);
		    bipole[idip_dep].centre.multiplie_cst((float)0.5);
		    bipole[idip_dep].q_ou_i=bipole[idip_dep].pt_dipole.longueur()/titi.longueur();
		    bipole[idip_dep].pt_dipole.assigne_soustrait(bipole[idip_dep].pos_plus,bipole[idip_dep].centre);
		    bipole[idip_dep].pt_dipole.multiplie_cst(bipole[idip_dep].q_ou_i);
		}
		    
		centre_multipole.assigne_additionne(bipole[0].centre,bipole[1].centre);
		centre_multipole.multiplie_cst((float)0.5);
		//if(electrique){
		//bipole[idip_dep].pos_plus.print_sl(" idip_dep "+idip_dep+" iq_dep "+iq_dep+" bipole[idip_dep].pos_plus ");
		//bipole[idip_dep].pos_moins.print_sl(" bipole[idip_dep].pos_moins ");
		//}
		if(!pressee){
		    //bipole[1000]=null;
		    return true;
		}
	    }
	    return false;
	}
    }

    public void cree_dipoles_ou_barreau (){

	//float q_lam=(float)1.e-19*(float)1.e-10;
	point ctr=new point(320,320);
	if(demo)
	    ctr.multiplie_cst((float)0.5);
	    ctr=new point(300,300);
	    if(i_demarre<=2){
		if(demo)
		    ctr.multiplie_cst((float)0.5);
		float q_lam=180;
		if(!electrique)
		    q_lam=3000;
		float distance_dipoles=100;
		if(i_demarre==1&&electrique){
		    distance_dipoles=200;
		    q_lam=90;
		}
		subject.eraserect(graph,0, 0, 1000, 1000,Color.white);
		toto=new point(q_lam,(float)0.);
		if(i_demarre==0){
		    n_dipoles_ou_barreaux=1;
		    if(i_ens<=1)
			multipole_ou_barreau=new dipole_de_pres(ctr,q_lam,toto);
		    else
			multipole_ou_barreau=new dipole_de_loin(ctr,toto);
		}else if(i_demarre==1){
		    n_dipoles_ou_barreaux=2;
		    multipole_ou_barreau=new quadripole(false);
		}else if(i_demarre==2){
		    if(demo){
			n_dipoles_ou_barreaux=1;
			multipole_ou_barreau=new dipole_de_loin(ctr,toto);
		    }else{
			n_dipoles_ou_barreaux=2;
			multipole_ou_barreau=new quadripole(true);
		    }
		}
		multipol=multipole_ou_barreau;
	    }else{
		n_dipoles_ou_barreaux=1;
		if (i_demarre==3){
		    ctr.assigne((float)(left_ens+right_ens)/2,(float)(top_ens+bottom_ens)/2);
		    //multipole_ou_barreau=new barreau_cylindrique(ctr,360,120,0);
		    ellipsoid=new ellipsoide(ctr,80,80,180);
		    bill_ou_barreau=ellipsoid;
		}else if (i_demarre==4){
		    //ctr.assigne(320,220);
		    ctr.assigne((float)(left_ens+right_ens)/2,(float)(top_ens+bottom_ens)/2);
		    ellipsoid=new ellipsoide(ctr,80,80,0);
		    multipole_ou_barreau=ellipsoid;
		    bill_ou_barreau=ellipsoid;
		}else if (i_demarre==5){
		    //ctr.assigne(320,220);
		    ctr.assigne((float)(left_ens+right_ens)/2,(float)(top_ens+bottom_ens)/2);
		    ellipsoid=new ellipsoide(ctr,80,80,0);
		    multipole_ou_barreau=ellipsoid;
		    bill_ou_barreau=ellipsoid;
		}else if(i_demarre==6){
		    ctr.y+=100;
		    multipole_ou_barreau=new barreau_cylindrique_infini(ctr,80,200,0);
		    //bill_ou_barreau=multipole_ou_barreau;
		}
		
		multipol=bill_ou_barreau;
	    }	
    }

}

class solution_eq_lineaires{
    float matrix[][]=new float[4][4];
    float inverse_matrix[][]=new float[4][4];
    float secmem[]=new float[4];
    float coef[]=new float[4];
    int dim;
    public solution_eq_lineaires(int dim1,float[][] matrix1,float[] secmem1){
	dim=dim1;
	for (int i=0;i<dim;i++){
	    secmem[i]=secmem1[i];
	    for (int j=0;j<dim;j++)
		matrix[i][j]=matrix1[i][j]; 
	}

    }
   void assigne_secmem(float[] secm){
	    for (int j=0;j<dim;j++)
		    secmem[j]=secm[j];
    }
    public float[] resout(){
	float pivot, souspiv;
	float essai[][]=new float[4][4];
	for (int i=0;i<dim;i++)
	    for (int j=0;j<dim;j++){
		essai[i][j]=matrix[i][j]; 
		if (i==j)
		    inverse_matrix[i][j]=(float)1.;
		else
		    inverse_matrix[i][j]=(float)0.;
	    }
	for (int i=0;i<dim;i++){
	    pivot=essai[i][i];
	    for (int j=i;j<dim;j++)
		essai[i][j]=essai[i][j]/pivot;
	    for (int j=0;j<=i;j++)
		inverse_matrix[i][j]=inverse_matrix[i][j]/pivot;
	    //System.out.println("i "+i+" pivot "+pivot+" essai[i][i] "+essai[i][i]);
	    for (int ii=0;ii<dim;ii++){
		if (ii!=i) {
		    souspiv=essai[ii][i];
		    for (int j=i;j<dim;j++)
			essai[ii][j]-=essai[i][j]*souspiv;
		    for (int j=0;j<=i;j++)
			inverse_matrix[ii][j]-=inverse_matrix[i][j]*souspiv;
		}
	    }
	}
	/*
	for (int i=0;i<dim;i++)
	    System.out.println(" essai i "+i+" "+(float)essai[i][0]+" "+(float)essai[i][1]+" "+(float)essai[i][2]+" "+(float)essai[i][3]);
	*/
	for (int i=0;i<dim;i++){
	    for (int j=0;j<dim;j++){
		essai[i][j]=(float)0.;	
		for (int k=0;k<4;k++)
		    essai[i][j]+=matrix[i][k]*inverse_matrix[k][j];
	    }
	    // System.out.println("$$ essai i "+i+" "+(float)essai[i][0]+" "+(float)essai[i][1]+" "+(float)essai[i][2]+" "+(float)essai[i][3]);
	}
	/*
	for (int i=0;i<dim;i++)
	    System.out.println(" matrix  i "+i+" "+(float)matrix[i][0]+" "+(float)matrix[i][1]+" "+(float)matrix[i][2]+" "+(float)matrix[i][3]);
	for (int i=0;i<dim;i++){
	    System.out.println(" inv_mat_in_x  i "+i+" "+(float)inverse_matrix[i][0]+" "+(float)inverse_matrix[i][1]+" "+(float)inverse_matrix[i][2]+" "+(float)inverse_matrix[i][3]);
	}
	System.out.println(" secmem "+(float)secmem[0]+" "+(float)secmem[1]+" "+(float)secmem[2]+" "+(float)secmem[3]);
	*/
	for (int i=0;i<dim;i++){
	    coef[i]=(float)0.;
	    for (int j=0;j<dim;j++)
		coef[i]+=inverse_matrix[i][j]*secmem[j];
	}
	return coef;
    }

}
class vecteur{
    static final float pi=(float)3.141592652;
    point chp,pnt,p1,p2;
    public vecteur (vecteur v){
	chp=new point(v.chp);
	pnt=new point(v.pnt);
	p1=chp;
	p2=pnt;
    }
    public vecteur (point v, point p){
	chp=new point(v);
	pnt=new point(p);
	p1=chp;
	p2=pnt;
    }
    public vecteur (float x,float y,float dx,float dy){
	chp=new point(x,y);
	pnt=new point(dx,dy);
	p1=chp;
	p2=pnt;
    }
    public void assigne(float x,float y,float dx,float dy){
	chp=new point(x,y);
	pnt=new point(dx,dy);
	p1=chp;
	p2=pnt;
    }
    public void assigne ( vecteur v){
	chp.assigne(v.chp);
	pnt.assigne(v.pnt);
	p1=chp;
	p2=pnt;
    }
    public void assigne(point v, point p){
	chp.assigne(v);
	pnt.assigne(p);
	p1=chp;
	p2=pnt;
    }
    public void assigne_soustrait ( vecteur v,vecteur www){
	chp.assigne_soustrait(v.chp,www.chp);
	pnt.assigne_soustrait(v.pnt,www.pnt);
	p1=chp;
	p2=pnt;
    }
    public void dessine( float fzoom,float fct_zm_sspl,Graphics g,Color col){
	
	int x_ini=(int)Math.round(pnt.x); int y_ini=(int)Math.round(pnt.y);
	int x_fin=x_ini+(int)(chp.x*40/fzoom*fct_zm_sspl);
	int y_fin=y_ini+(int)(chp.y*40/fzoom*fct_zm_sspl);
	g.setColor(col);
	//System.out.println(" x_ini "+x_ini+" y_ini "+y_ini+" x_fin "+x_fin+" y_fin "+y_fin);	    
	g.drawLine(x_ini, y_ini, x_fin, y_fin);
	float direct=chp.direction()*pi/(float)180.;
	float dir=direct+3*pi/(float)4.;
	int xf1=x_fin+(int)(7.0*(float)Math.cos(dir));int yf1=y_fin+(int)(7.0*(float)Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
	dir=direct-3*pi/(float)4.;
	xf1=x_fin+(int)(7.0*(float)Math.cos(dir));yf1=y_fin+(int)(7.0*(float)Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
    }
    public void print(String st){
	System.out.println(st+ " chp x "+(float)chp.x+" y "+(float)chp.y+" pnt x "+(float)pnt.x+" y "+(float)pnt.y);
    }
    public void print_bis(String st){
	System.out.println(st+ " p1 x "+(float)p1.x+" y "+(float)p1.y+" p2 x "+(float)p2.x+" y "+(float)p2.y);
    }
}
class vecteur_y_z{
    static final float pi=(float)3.141592652;
    point_y_z chp;point_y_z pnt;
    public vecteur_y_z (vecteur_y_z v){
	chp=new point_y_z(v.chp);
	pnt=new point_y_z(v.pnt);
    }
    public vecteur_y_z (point_y_z v, point_y_z p){
	chp=new point_y_z(v);
	pnt=new point_y_z(p);
    }
    public void assigne ( vecteur_y_z v){
	chp.assigne(v.chp);
	pnt.assigne(v.pnt);
    }
    public void assigne(point_y_z v, point_y_z p){
	chp.assigne(v);
	pnt.assigne(p);
    }
    public void assigne_soustrait ( vecteur_y_z v,vecteur_y_z www){
	chp.assigne_soustrait(v.chp,www.chp);
	pnt.assigne_soustrait(v.pnt,www.pnt);
    }
    public void print(String st){
	System.out.println(st+ " chp.y "+(float)chp.y+" chp.z "+(float)chp.z+" pnt.y "+(float)pnt.y+" pnt.z "+(float)pnt.z);
    }

    public void dessine( float fzoom,float fct_zm_sspl,Graphics g,Color col){
	
	int x_ini=(int)Math.round(pnt.z); int y_ini=(int)Math.round(pnt.y);
	int x_fin=x_ini+(int)(chp.z*40/fzoom*fct_zm_sspl);
	int y_fin=y_ini+(int)(chp.y*40/fzoom*fct_zm_sspl);
	g.setColor(col);
	g.drawLine(x_ini, y_ini, x_fin, y_fin);
	float direct=chp.direction()*pi/(float)180.;
	float dir=direct+3*pi/(float)4.;
	int xf1=x_fin+(int)((float)7.0*(float)Math.cos(dir));int yf1=y_fin+(int)((float)7.0*(float)Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
	dir=direct-3*pi/(float)4.;
	xf1=x_fin+(int)((float)7.0*(float)Math.cos(dir));yf1=y_fin+(int)((float)7.0*(float)Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
    }
}

class vecteur_x_z{
    point_x_z p1;point_x_z p2;
    public vecteur_x_z (vecteur_x_z v){
	p1=new point_x_z(v.p1);
	p2=new point_x_z(v.p2);
    }
    public vecteur_x_z (point_x_z v, point_x_z p){
	p1=new point_x_z(v);
	p2=new point_x_z(p);
    }
    public void assigne ( vecteur_x_z v){
	p1.assigne(v.p1);
	p2.assigne(v.p2);
    }
    public void assigne(point_x_z v, point_x_z p){
	p1.assigne(v);
	p2.assigne(p);
    }
    public void print(String st){
	System.out.println(st+ " p1.x "+p1.x+" p1.z "+p1.z+" p2.x "+p2.x+" p2.z "+p2.z);
    }
}
class bi_point_xyz{
    point_xyz p_x,p_y,s;
    bi_point_xyz(){
	p_x=new point_xyz((float)0.,(float)0.,(float)0.);
	p_y=new point_xyz((float)0.,(float)0.,(float)0.);
	s=new point_xyz((float)0.,(float)0.,(float)0.);
   }
    public void assigne(point_xyz p,point_xyz q){
	p_x.assigne(p);
	p_y.assigne(q);
	s=new point_xyz((float)0.,(float)0.,(float)0.);
    }
    public void assigne(bi_point_xyz p){
	p_x.assigne(p.p_x);
	p_y.assigne(p.p_y);
    }
    public void multiplie_cst(float f){
	p_x.multiplie_cst(f);
	p_y.multiplie_cst(f);
    }
    public point_xyz somme(){
	s.assigne_additionne(p_x,p_y);
	return s;
    }
    public void print(String st){
	System.out.println(st+ " p_x x "+(float)p_x.x+" y "+(float)p_x.y+" z "+(float)p_x.z+" p_y x "+(float)p_y.x+" y "+(float)p_y.y+" z "+(float)p_y.z);
    }

}

class point_x_z{
    float x=0,z=0;
    point_x_z(float x1,float z1){
	x=x1;z=z1;
    }
    point_x_z(point_x_z p){
	x=p.x;z=p.z;
    }
    void assigne(float x1,float z1){
	x=x1;z=z1;
    }
    void assigne(point_x_z pt){
	x=pt.x;z=pt.z;
    }
    void assigne_normalise(point_x_z pt){	
	x=pt.x/pt.longueur();z=pt.z/pt.longueur();
	
    }
    public float longueur(){
	return((float)Math.sqrt(x*x+z*z));
    }
    public void print(String st){
	float xx=(float)x;float zz=(float)z;float l=(float)longueur();
	System.out.println(st+ " x "+xx+" z "+zz+" longueur() "+l);
    }
    public void print_sl(String st){
	float xx=(float)x;float zz=(float)z;
	System.out.println(st+ " x "+xx+" z "+zz);
    }

}
class point_y_z{
    float y=(float)0.,z=(float)0.;
    static final float pi=(float)3.141592652;
    point_y_z(float y1,float z1){
	y=y1;z=z1;
    }
    point_y_z(point_y_z p){
	y=p.y;z=p.z;
    }
    void assigne(float y1,float z1){
	y=y1;z=z1;
    }
    void assigne(point_y_z pt){
	y=pt.y;z=pt.z;
    }
    void assigne_normalise(point_y_z pt){	
	y=pt.y/pt.longueur();z=pt.z/pt.longueur();
	
    }
    public void assigne_oppose(){
	y=-y;z=-z;
    }
     public void normalise(){
	float l=longueur();
	y/=l;z/=l;	
    }
    public void assigne_facteur(point_y_z a,float b){
	y=a.y*b;z=a.z*b;
    }
    public void assigne_facteur(point_xyz a,float b){
	y=a.y*b;z=a.z*b;
    }

    void assigne_additionne(point_y_z pt,point_y_z qt){
	y=pt.y+qt.y;z=pt.z+qt.z;
    }
    void assigne_soustrait(point_y_z pt,point_y_z qt){
	y=pt.y-qt.y;z=pt.z-qt.z;
    }
    void additionne(point_y_z pt){
	y+=pt.y;z+=pt.z;
    }
    void soustrait(point_y_z pt){
	y-=pt.y;z-=pt.z;
    }
    public void additionne_facteur(point_y_z a,float b){
	y+=b*a.y;
	z+=b*a.z;
    }
    public float longueur(){
	return((float)Math.sqrt(y*y+z*z));
    }
    public float longueur_carre(){
	return (y*y+z*z);
    }
    public void additionne_point_facteur(point_y_z a,float c){
	y+=c*a.y;
	z+=c*a.z;
    }
    public float scalaire(point_y_z a){
	return y*a.y+z*a.z;
    }
     public  float vectoriel(point_y_z b){
	 return y*b.z-z*b.y;
     }
    public void rotation(float c_ang,float s_ang){
	float y_p=y;float z_p=z;
	y=c_ang*y_p-s_ang*z_p;
	z=s_ang*y_p+c_ang*z_p;
    }

    public void multiplie_cst(float c){
	y*=c;
	z*=c;
    }
    public void divise_cst(float c){
	y/=c;
	z/=c;
    }
    public float direction(){
	float angle=0;
	if((float)Math.abs(z)>(float)Math.abs(y)){
	    angle=(float)180./pi*(float)Math.asin(y/longueur());
	    if(z<(float)0.)
		if(y>0)
		    angle=(float)180.-angle;
		else
		    angle=-(float)180.-angle;
	}else{
	    angle=(float)180./pi*(float)Math.acos(z/longueur());
	    if(y<(float)0.)angle=-angle;
	}
	return angle;
    }

    public void print_sl(String st){
	System.out.println(st+ " y "+(float)y+" z "+(float)z);
    }
    public void print(String st){
	System.out.println(st+ " y "+(float)y+" z "+(float)z+" l "+longueur());
    }
}
class point implements Cloneable{
    static final float pi=(float)3.141592652;
    float x,y;    
    point(float xi, float yi){
	x=xi;y=yi;
    }
    point(point a){
	x=a.x;y=a.y;
    }
    point(point a,float b){
	x=a.x*b;y=a.y*b;
    }
    public Object clone(){
	try{
	    //point e=(point)super.clone();
	    //return e;
	    return super.clone();
	}
	catch (CloneNotSupportedException e){
	    return null;
	}
	
    }
    public void zero(){
	x=(float)0.;y=(float)0.;
    }
    public float direction(){
	float angle=0;
	if((float)Math.abs(x)>(float)Math.abs(y)){
	    angle=(float)180./pi*(float)Math.asin(y/longueur());
	    if(x<(float)0.)
		if(y>0)
		    angle=(float)180.-angle;
		else
		    angle=-(float)180.-angle;
	}else{
	    angle=(float)180./pi*(float)Math.acos(x/longueur());
	    if(y<(float)0.)angle=-angle;
	}
	return angle;
    }
    public void projections(float cosinus,float sinus)
    {
        float x_p=x;float y_p=y;
        x=-sinus*x_p+cosinus*y_p;
        y=cosinus*x_p+sinus*y_p;
    }
     public void assigne(float xi, float yi){
	x=xi;y=yi;
    }
    public void assigne(point a){
	x=a.x;y=a.y;
    }
    public void assigne_oppose(){
	x=-x;y=-y;
    }
    public void assigne_normalise(point a){
	x=a.x/a.longueur();y=a.y/a.longueur();
    }
    public void normalise(){
	float l=longueur();
	x/=l;y/=l;
    }
    public void assigne_oppose(point a){
	x=-a.x;y=-a.y;
    }
    public void assigne_additionne(point a,point b){
	x=a.x+b.x;y=a.y+b.y;
    }
    public void assigne_soustrait(point a,point b){
	x=a.x-b.x;y=a.y-b.y;
    }
    public void assigne_facteur(point a,float b){
	x=a.x*b;y=a.y*b;
    }
    public void assigne_diviseur(point a,float b){
	x=a.x/b;y=a.y/b;
    }
    public float distance_carre(point pt){
	float d;
	d=(float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2);
	return d;
    }
    public void multiplie_cst(float a){
	x*=a;
	y*=a;
    }
    public void divise_cst(float a){
	x/=a;
	y/=a;
    }
    public void additionne(float xx,float yy){
	x+=xx;
	y+=yy;
    }
    public void additionne(point a){
	x+=a.x;
	y+=a.y;
    }
    public void soustrait(point a){
	x-=a.x;
	y-=a.y;
    }
    public void soustrait(float xx,float yy){
	x-=xx;
	y-=yy;
    }
    public void additionne_facteur(point a,float b){
	x+=b*a.x;
	y+=b*a.y;
    }
    public void additionne_diviseur(point a,float b){
	x+=a.x/b;
	y+=a.y/b;
    }
    public void soustrait_facteur(point a,float b){
	x-=b*a.x;
	y-=b*a.y;
    }
    public void soustrait_diviseur(point a,float b){
	x-=a.x/b;
	y-=a.y/b;
    }
    public float distance(point pt){
	float d;
	d=(float)Math.sqrt((float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2));
	return d;
    }
    public float longueur(){
	return ((float)Math.sqrt(x*x+y*y));
    }
    public float somme(){
	return (x+y);
    }
    public float longueur_carre(){
	return (x*x+y*y);
    }
    public float scalaire(point a){
	return x*a.x+y*a.y;
    }
    public float produit_vectoriel(point a){
	return x*a.y-y*a.x;
    }
    public void rotation(float angle){
	float cos=(float)Math.cos(angle);float sin=(float)Math.sin(angle);
	float x_p=x;float y_p=y;
	x=cos*x_p-sin*y_p;
	y=sin*x_p+cos*y_p;
    }
    public void rotation(float c_ang,float s_ang){
	float x_p=x;float y_p=y;
	x=c_ang*x_p-s_ang*y_p;
	y=s_ang*x_p+c_ang*y_p;
    }
    public void print(String st){
	float xx=(float)x;float yy=(float)y;float l=(float)longueur();
	System.out.println(st+ " x "+xx+" y "+yy+" longueur() "+l);
    }
    public void print_sl(String st){
	float xx=(float)x;float yy=(float)y;
	System.out.println(st+ " x "+xx+" y "+yy);
    }
}
class point_xyz implements Cloneable{
    static final float pi=(float)3.141592652;
    float x,y,z;    
    point_xyz(float xi, float yi, float zi){
	x=xi;y=yi;z=zi;
    }
    point_xyz(point_xyz a){
	x=a.x;y=a.y;z=a.z;
    }
    point_xyz(point_xyz a,float b){
	x=a.x*b;y=a.y*b;z=a.z*b;
    }
    point_xyz(float xx,point_y_z b){
	x=xx;y=b.y;z=b.z;
    }
    point_xyz(point a, float zz){
	x=a.x;y=a.y;z=zz;
    }
    public Object clone(){
	try{
	    //point e=(point)super.clone();
	    //return e;
	    return super.clone();
	}
	catch (CloneNotSupportedException e){
	    return null;
	}
	
    }    public void zero(){
	x=(float)0.;y=(float)0.;z=(float)0.;
    }
     public void assigne(float xi, float yi, float zi){
	x=xi;y=yi;z=zi;
    }
    public void assigne(float xx,point_y_z b){
	x=xx;y=b.y;z=b.z;
    }
    public void assigne_normalise(float xx,point_y_z b){
	x=xx;y=b.y;z=b.z;
	float l=(float)Math.sqrt(x*x+y*y+z*z);
	x/=l;y/=l;z/=l;
	//x/=longueur();y/=longueur();z/=longueur();
    }
    public void assigne_normalise(point_y_z b){
	x=0;y=b.y;z=b.z;
	float l=(float)Math.sqrt(y*y+z*z);
	y/=l;z/=l;
	//x/=longueur();y/=longueur();z/=longueur();
    }
    public void assigne(point a, float zz){
	x=a.x;y=a.y;z=zz;
    }
    public void assigne_normalise(point a, float zz){
	x=a.x;y=a.y;z=zz;
	float l=(float)Math.sqrt(x*x+y*y+z*z);
	x/=l;y/=l;z/=l;

	//x/=longueur();y/=longueur();z/=longueur();
    }
    public void assigne_oppose(){
	x=-x;y=-y;z=-z;
    }
    public void assigne_oppose(point_xyz a){
	x=-a.x;y=-a.y;z=-a.z;
    } 
    public void assigne(point_xyz a){
	x=a.x;y=a.y;z=a.z;
    }
    public void assigne_normalise(point_xyz a){
	x=a.x;y=a.y;z=a.z;
	float l=(float)Math.sqrt(x*x+y*y+z*z);
	x/=l;y/=l;z/=l;

    }
    public void normalise(){
	float l=(float)Math.sqrt(x*x+y*y+z*z);
	x/=l;y/=l;z/=l;

	//x/=longueur();y/=longueur();z/=longueur();	
    }

    public void assigne_normalise(float a,float b,float c){
	float coco=(float)Math.sqrt(a*a+b*b+c*c);
	x=a/coco;y=b/coco;z=c/coco;
    }
    public void assigne_additionne(point_xyz a,point_xyz b){
	x=a.x+b.x;y=a.y+b.y;z=a.z+b.z;
    }
    public void assigne_soustrait(point_xyz a,point_xyz b){
	x=a.x-b.x;y=a.y-b.y;z=a.z-b.z;
    }
    public void assigne_facteur(point_xyz a,float b){
	x=a.x*b;y=a.y*b;z=a.z*b;
    }
    public void assigne_diviseur(point_xyz a,float b){
	x=a.x/b;y=a.y/b;z=a.z/b;
    }
    point extrait_point(){
	return new point(x,y);
    }
    point_y_z extrait_point_y_z(){
	return new point_y_z(y,z);
    }
    public float distance_carre(point_xyz pt){
	float d;
	d=(float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2)+(float)Math.pow(z-pt.z,2);
	return d;
    }
    public void multiplie_cst(float a){
	x*=a;y*=a;z*=a;
    }
    public void divise_cst(float a){
	x/=a;y/=a;z/=a;
    }
    public void additionne(float xx,float yy,float zz){
	x+=xx;y+=yy;z+=zz;
    }
    public void additionne(point b,float zz){
	x+=b.x;y+=b.y;z+=zz;
    }
    public void additionne(float xx,point_y_z b){
	x+=xx;y+=b.y;z+=b.z;
    }
    public void additionne(point_xyz a){
	x+=a.x;y+=a.y;z+=a.z;
    }
    public void soustrait(point_xyz a){
	x-=a.x;y-=a.y;z-=a.z;
    }
    public void soustrait(float xx,float yy,float zz){
	x-=xx;y-=yy;z-=zz;
    }
    public void additionne_facteur(point_xyz a,float b){
	x+=b*a.x;y+=b*a.y;z+=b*a.z;
    }
    public void soustrait_facteur(point_xyz a,float b){
	x-=b*a.x;y-=b*a.y;z-=b*a.z;
    }
    public float distance(point_xyz pt){
	float d;
	d=(float)Math.sqrt((float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2)+(float)Math.pow(z-pt.z,2));
	return d;
    }
    public float longueur(){
	return ((float)Math.sqrt(x*x+y*y+z*z));
    }
    public float longueur_carre(){
	return (x*x+y*y+z*z);
    }
    public float scalaire(point_xyz a){
	return x*a.x+y*a.y+z*a.z;
    }
    public float scalaire(point_y_z a){
	return y*a.y+z*a.z;
    }
    public point_xyz vectoriel(point_xyz a){
	return new point_xyz(y*a.z-z*a.y,z*a.x-x*a.z,x*a.y-y*a.x);
    }
     public void print(String st){
	float xx=(float)x;float yy=(float)y;float zz=(float)z;float l=(float)longueur();
	//System.out.println(st+ " x "+xx+" y "+yy+" z "+zz+" longueur() "+l);
	System.out.println(st+ " x "+(float)xx+" y "+(float)yy+" z "+(float)zz+" lg "+longueur());
    }
     public void print_sl(String st){
	float xx=(float)x;float yy=(float)y;float zz=(float)z;float l=(float)longueur();
	//System.out.println(st+ " x "+xx+" y "+yy+" z "+zz+" longueur() "+l);
	System.out.println(st+ " x "+(float)xx+" y "+(float)yy+" z "+(float)zz);
    }
}
abstract class triple_entier{
    int r,v,b;
    public triple_entier(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public triple_entier(couleur c1){
	r=c1.r;v=c1.v;b=c1.b;
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne_facteur(triple_entier c,float d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
    public void remise_a_zero(){
	r=0;v=0;b=0;
    }
    public void multiplie(float f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void divise(float f){
	r=(int)(r/f);v=(int)(v/f);b=(int)(b/f);
    }
    public boolean egale(couleur a){
	return ((r==a.r)&&(v==a.v)&&(b==a.b));
    }
    abstract public void assigne(int r1,int v1,int b1);
    public void print(String st){
	System.out.println(st+ " r "+r+" v "+v+" b "+b);
    }
}
class triple_int extends triple_entier{
    public triple_int(int r1,int v1,int b1){
	super(r1,v1,b1);
    }
    public triple_int(couleur c1){
	super(c1);
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public void assigne_facteur(triple_int c,float d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
}
class couleur extends triple_entier{
    Color col;
    public couleur(int r1,int v1,int b1){
	super(r1,v1,b1);
	col=new Color(r,v,b);
	//	    if(col==rouge) marche pas!!!!!
    }
    public couleur(couleur c1){
	super(c1);
	col=new Color(c1.r,c1.v,c1.b);
    }
    public void assigne(couleur c){
	col=c.col;
	r=c.r;v=c.v;b=c.b;
    }
    /*
    public void assigne(triple_float c){
	r=(int)c.r;v=(int)c.v;b=(int)c.b;
	col=new Color(r,v,b);

    }
    */
    public void multiplie(float f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
	col=new Color(r,v,b);
    }
}


