package Autonoleggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;

import javax.swing.*;

/**Questa classe rappresenta una finestra che permette all'utente di
 * prenotare un Veicolo, chiedendo di compilare dei campi giorno, mese e
 * anno e, dopo aver verificato la correttezza dei dati inseriti, passa
 * i dati al costruttore di Prenotazione.*/
public class FinestraInserisci extends JFrame {
		
		//ID di default per la serializzazione
		private static final long serialVersionUID = 1L;
		
		//Variabile contenente il riferimento al JFrame principale della GUI
		private FinestraMain main;
		//Variabile contenente il riferimento all'autonoleggio, che gestisce
		//i cambiamenti nell'ArrayList di Veicoli immatricolati
		protected Autonoleggio autonoleggio;
		//Variabili di tipo JTextField, da sottoporre al Listener interno
		private JTextField giorno, mese, anno, richiedente, targa;
		//Variabile di classe JLabel che permette una comunicazione basilare con
		//l'utente
		private JLabel sommario;
		//Variabili di tipo bottone, da sottoporre al Listener interno
		private JButton esci, conferma;

		//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
		//e dispone tutti gli elementi della finestra
		public FinestraInserisci(FinestraMain m, Autonoleggio aut) {
			main = m;
			autonoleggio = aut;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			JPanel primo = new JPanel();
			primo.setLayout(new BorderLayout());
			sommario = new JLabel("Compila i campi per prenotare il mezzo");
			esci = new JButton("Chiudi");
			conferma = new JButton("Conferma");
			giorno = new JTextField("GG", 3);
			mese = new JTextField("MM", 3);
			anno = new JTextField("AAAA", 5);
			JPanel data = new JPanel();
			data.setLayout(new FlowLayout(FlowLayout.CENTER));
			data.add(giorno);
			data.add(mese);
			data.add(anno);
			JPanel generalita = new JPanel();
			generalita.setLayout(new FlowLayout(FlowLayout.CENTER));
			richiedente = new JTextField("Richiedente", 15);
			targa = new JTextField("Targa", 7);
			generalita.add(richiedente);
			generalita.add(targa);
			Listener ascoltatore = new Listener(this);
			primo.add(conferma);
			esci.addActionListener(ascoltatore);
			conferma.addActionListener(ascoltatore);
			primo.add(esci, BorderLayout.SOUTH);
			primo.add(data, BorderLayout.WEST);
			primo.add(generalita, BorderLayout.CENTER);
			primo.add(conferma, BorderLayout.EAST);
			primo.add(sommario, BorderLayout.NORTH);
			this.add(primo);
			pack();
		}
		
		/**Listener interno alla classe che gestisce la creazione
		 * di una Prenotazione o la chiusura del JFrame, riportando la visibilita'
		 * al frame Main. Alla conferma si verifica che il richiedente e la data siano validi
		 * e si procede alla creazione della Prenotazione, se non esiste gia' per il giorno scelto
		 * Il JLabel notifica eventuali anomalie e una finestra di dialogo notifica l'avvenuto inserimento.*/
		class Listener implements ActionListener {
			
			private FinestraInserisci inserisci;
			
			public Listener(FinestraInserisci f_in) {
				inserisci = f_in;
			}
			
			public void actionPerformed(ActionEvent e) {
				
				String btn = e.getActionCommand();
				
				if (btn.equals("Chiudi")) {
					inserisci.setVisible(false);
					inserisci.dispose();
					main.setVisible(true);
				} else if (btn.equals("Conferma")) {
					boolean data_ok = false;
					boolean veicolo_ok = false;
					boolean richiedente_ok = true;
					try {
					Veicolo prova = null;
					if (richiedente.getText().equals("Richiedente") || richiedente.getText().isBlank()) {
						richiedente_ok = false;
						sommario.setText("Inserire un richiedente valido");
					}
					for (Veicolo v : autonoleggio.parco_mezzi)
						if (v.getTarga().equals(targa.getText())) {
							prova = v;
							veicolo_ok = true;
						} else {
						 sommario.setText("La targa non ha riscontri.");
						}
					LocalDate tmp = LocalDate.of(Integer.valueOf(anno.getText()), Integer.valueOf(mese.getText()), Integer.valueOf(giorno.getText()));
					LocalDate oggi = LocalDate.now();
					if (tmp.compareTo(oggi) < 0) {
						throw new ErroreFormatoData();
					}
					if (veicolo_ok && richiedente_ok) {
						Prenotazione nuova = new Prenotazione(tmp, richiedente.getText());
						if (!Autonoleggio.esistePrenotazione(prova, nuova))
								data_ok = true;
						if (data_ok) {
						prova.lista_prenotazioni.add(nuova);
						SortData ordina = new SortData();
						Collections.sort(prova.lista_prenotazioni, ordina);
						prova.setPrenotato(true);
						inserisci.setVisible(false);
						inserisci.dispose();
						main.setVisible(true);
						FinestraDialogo ok = new FinestraDialogo("Prenotazione registrata.");
						} else {
							FinestraDialogo err_data = new FinestraDialogo("Il mezzo e' gia' prenotato per questo giorno");
						}
					}
					} catch (ErroreFormatoData a) {
						FinestraDialogo data = new FinestraDialogo(a.toString());
						data_ok = false;
					} catch (DateTimeException f) {
						FinestraDialogo data = new FinestraDialogo(f.toString());
						data_ok = false;
					} catch (NumberFormatException n) {
						FinestraDialogo data = new FinestraDialogo(n.toString());
					}
				}
			}
		}
	}
