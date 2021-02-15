package Autonoleggio;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Autonoleggio.Autovettura.*;

/**Questa classe rappresenta una finestra che permette all'utente di
 * immatricolare un Autovettura, chiedendo di compilare dei campi targa
 * e modello e di selezionare da 3 JComboBox il valore dei 3 enum corrispondenti.
 * I valori saranno passati al costruttore di Autovettura al clic del bottone di
 * conferma. */
public class FinestraImmatricolaVeicolo extends JFrame {
	
	//ID di default per la serializzazione
	private static final long serialVersionUID = 1L;
	
	//Variabile contenente il riferimento al JFrame principale della GUI
	private FinestraMain main;
	//Variabile contenente il riferimento all'autonoleggio, che gestisce
	//i cambiamenti nell'ArrayList di Veicoli immatricolati
	private Autonoleggio autonoleggio;
	//Variabili di tipo bottone, da sottoporre al Listener interno
	private JButton esci, conferma;
	//Variabili di tipo JTextField, da sottoporre al Listener interno
	private JTextField targa, modello;
	//Variabile di classe JPanel che contiene tutti gli elementi del Frame
	private JPanel primo;
	//Variabili di classe JComboBox per permettere all'utente di selezionare
	//uno dei valori degli enum senza immissione da tastiera
	private JComboBox posti, alimentazione, tipologia;
	//Variabile di classe JLabel che permette una comunicazione basilare con
	//l'utente
	private JLabel sommario;
	
	//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
	//e dispone tutti gli elementi della finestra
	public FinestraImmatricolaVeicolo(FinestraMain m, Autonoleggio a) {
		main = m;
		autonoleggio = a;
		this.setLocationRelativeTo(null);
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		primo = new JPanel();
		primo.setLayout(new BorderLayout());
		sommario = new JLabel("Vuoi immatricolare un'autovettura. Inserisci i dati:");
		primo.add(sommario, BorderLayout.NORTH);
		JPanel bottoni = new JPanel();
		bottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		esci = new JButton("Chiudi");
		conferma = new JButton("Conferma");
		bottoni.add(esci);
		bottoni.add(conferma);
		primo.add(bottoni, BorderLayout.SOUTH);
		Listener ascoltatore = new Listener(this);
		esci.addActionListener(ascoltatore);
		conferma.addActionListener(ascoltatore);
		posti = new JComboBox(Posti.values());
		alimentazione = new JComboBox(AlimentazioneAuto.values());
		tipologia = new JComboBox(TipologiaAuto.values());
		JPanel slider = new JPanel();
		slider.setLayout(new FlowLayout(FlowLayout.CENTER));
		slider.add(posti);
		slider.add(alimentazione);
		slider.add(tipologia);
		targa = new JTextField("Targa", 7);
		modello = new JTextField("Modello", 10);
		JPanel campi_testo = new JPanel();
		campi_testo.setLayout(new FlowLayout(FlowLayout.CENTER));
		campi_testo.add(targa);
		campi_testo.add(modello);
		primo.add(campi_testo, BorderLayout.WEST);
		primo.add(slider);
		this.add(primo);
		pack();
	}
	
	/**Listener interno alla classe che gestisce la creazione
	 * di una Autovettura o la chiusura del JFrame, riportando la visibilita'
	 * al frame Main. Alla conferma si verifica che il mezzo non esiste gia'
	 * nell'autonoleggio si procede al suo inserimento nell'ArrayList. Il JLabel notifica
	 * eventuali anomalie e una finestra di dialogo notifica l'avvenuta immatricolazione.*/
	class Listener implements ActionListener {
		
		private FinestraImmatricolaVeicolo immatricola_ve;
		
		public Listener(FinestraImmatricolaVeicolo f_iv) {
			immatricola_ve = f_iv;
		}
		
		public void actionPerformed(ActionEvent e) {

			String btn = e.getActionCommand();
			
			if (btn.equals("Chiudi")) {
				immatricola_ve.setVisible(false);
				immatricola_ve.dispose();
				main.setVisible(true);
			} else if (btn.equals("Conferma")) {
				if (Veicolo.validaTarga(targa.getText()) && Veicolo.validaModello(modello.getText())) {
					boolean trovato = false;
					for (Veicolo v : autonoleggio.parco_mezzi) 
						if (v.getTarga().equals(targa.getText())) {
							sommario.setText("Il mezzo e' gia' presente");
							trovato = true;
						} 
					if (!trovato) {
					Veicolo tmp = new Autovettura(targa.getText(), modello.getText(), autonoleggio, (Posti)posti.getSelectedItem(),
							(AlimentazioneAuto)alimentazione.getSelectedItem(), (TipologiaAuto)tipologia.getSelectedItem());
						autonoleggio.parco_mezzi.add(tmp);
						main.setVisible(true);
						immatricola_ve.setVisible(false);
						immatricola_ve.dispose();
						FinestraDialogo ok = new FinestraDialogo("Mezzo aggiunto correttamente.");
					} 
			} else {
				sommario.setText("Controlla targa e modello.");
			}
		}
		}
	}
}
