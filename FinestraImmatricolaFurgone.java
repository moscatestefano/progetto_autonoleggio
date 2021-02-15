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
import Autonoleggio.Furgone.*;

/**Questa classe rappresenta una finestra che permette all'utente di
 * immatricolare un Furgone, chiedendo di compilare dei campi targa
 * e modello e di selezionare da 2 JComboBox il valore dei 2 enum corrispondenti.
 * I valori saranno passati al costruttore di Furgone al clic del bottone di
 * conferma. */
public class FinestraImmatricolaFurgone extends JFrame {

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
	private JComboBox patente, grandezza;
	//Variabile di classe JLabel che permette una comunicazione basilare con
	//l'utente
	private JLabel sommario;

	//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
	//e dispone tutti gli elementi della finestra
	public FinestraImmatricolaFurgone(FinestraMain m, Autonoleggio aut) {
		main = m;
		autonoleggio = aut;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		sommario = new JLabel("Vuoi immatricolare un furgone. Inserisci i dati:");
		primo = new JPanel();
		primo.setLayout(new BorderLayout());
		JPanel bottoni = new JPanel();
		bottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		esci = new JButton("Chiudi");
		conferma = new JButton("Conferma");
		bottoni.add(esci);
		bottoni.add(conferma);
		Listener ascoltatore = new Listener(this);
		esci.addActionListener(ascoltatore);
		conferma.addActionListener(ascoltatore);
		patente = new JComboBox(Patente.values());
		grandezza = new JComboBox(TipologiaFurgone.values());
		JPanel slider = new JPanel();
		slider.setLayout(new FlowLayout(FlowLayout.CENTER));
		slider.add(patente);
		slider.add(grandezza);
		targa = new JTextField("Targa", 7);
		modello = new JTextField("Modello", 10);
		JPanel campi_testo = new JPanel();
		campi_testo.setLayout(new FlowLayout(FlowLayout.CENTER));
		campi_testo.add(targa);
		campi_testo.add(modello);
		primo.add(campi_testo, BorderLayout.WEST);
		primo.add(sommario, BorderLayout.NORTH);
		primo.add(slider, BorderLayout.CENTER);
		primo.add(bottoni, BorderLayout.SOUTH);
		this.add(primo);
		pack();
	}
	
	/**Listener interno alla classe che gestisce la creazione
	 * di un Furgone o la chiusura del JFrame, riportando la visibilita'
	 * al frame Main. Alla conferma si verifica che il mezzo non esiste gia'
	 * nell'autonoleggio si procede al suo inserimento nell'ArrayList. Il JLabel notifica
	 * eventuali anomalie e una finestra di dialogo notifica l'avvenuta immatricolazione.*/
	class Listener implements ActionListener {
		
		private FinestraImmatricolaFurgone immatricola_fur;
		
		public Listener(FinestraImmatricolaFurgone f_if) {
			immatricola_fur = f_if;
		}
		
		public void actionPerformed(ActionEvent e) {

			String btn = e.getActionCommand();
			
			if (btn.equals("Chiudi")) {
				immatricola_fur.setVisible(false);
				immatricola_fur.dispose();
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
					Veicolo tmp = new Furgone(targa.getText(), modello.getText(), autonoleggio, (Patente)patente.getSelectedItem(),
							(TipologiaFurgone)grandezza.getSelectedItem());
						autonoleggio.parco_mezzi.add(tmp);
						main.setVisible(true);
						immatricola_fur.setVisible(false);
						immatricola_fur.dispose();
						FinestraDialogo ok = new FinestraDialogo("Mezzo aggiunto correttamente.");
					} 
			} else {
				sommario.setText("Controlla targa e modello.");
			}
		}
		}
	}
}
