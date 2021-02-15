package Autonoleggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**Questa classe rappresenta una finestra che permette all'utente di
 * rimuovere un Veicolo dall'autonoleggio, chiedendo di compilare un campo targa.
 * Il Listener, dopo aver verificato la correttezza dei dati inseriti, genera una
 * finestra di dialogo con il successo dell'operazione.*/
public class FinestraRimuovi extends JFrame {
	
		//ID di default per la serializzazione
		private static final long serialVersionUID = 1L;
		
		//Variabile contenente il riferimento al JFrame principale della GUI
		private FinestraMain main;
		//Variabile contenente il riferimento all'autonoleggio, che gestisce
		//i cambiamenti nell'ArrayList di Veicoli immatricolati
		private Autonoleggio autonoleggio;
		//Variabile di classe JLabel che permette una comunicazione basilare con
		//l'utente
		private JLabel sommario;
		//Variabile di tipo JTextField, da sottoporre al Listener interno
		private JTextField targa;
		//Variabile di tipo bottone, da sottoporre al Listener interno
		private JButton esci;
	
		//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
		//e dispone tutti gli elementi della finestra
		public FinestraRimuovi(FinestraMain m, Autonoleggio aut) {
			main = m;
			autonoleggio = aut;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			JPanel primo = new JPanel();
			primo.setLayout(new BorderLayout());
			sommario = new JLabel("Inserisci la targa del mezzo da rimuovere");
			targa = new JTextField("Targa", 7);
			JButton conferma = new JButton("Conferma");
			JPanel centro = new JPanel();
			centro.setLayout(new FlowLayout(FlowLayout.CENTER));
			centro.add(targa);
			centro.add(conferma);
			JButton esci = new JButton("Chiudi");
			Listener ascoltatore = new Listener(this);
			esci.addActionListener(ascoltatore);
			conferma.addActionListener(ascoltatore);
			primo.add(centro, BorderLayout.CENTER);
			primo.add(esci, BorderLayout.SOUTH);
			primo.add(sommario, BorderLayout.NORTH);
			this.add(primo);
			pack();
		}
		
		/**Listener interno alla classe che gestisce la rimozione
		 * un Veicolo dall'ArrayList parco_mezzi, a patto che esistano oggetti di Prenotazione
		 * all'interno del suo ArrayList riservato alle prenotazioni.
		 * Il JLabel notifica eventuali anomalie e una finestra di dialogo notifica l'avvenuto inserimento.*/
		class Listener implements ActionListener {
			
			private FinestraRimuovi rimuovi;
			
			public Listener(FinestraRimuovi f_r) {
				rimuovi = f_r;
			}
			
			public void actionPerformed(ActionEvent e) {
				
				String btn = e.getActionCommand();
				if (btn.equals("Chiudi")) {
					rimuovi.setVisible(false);
					rimuovi.dispose();
					main.setVisible(true);
				} else if (btn.equals("Conferma"))
					for (int i = 0; i < autonoleggio.parco_mezzi.size(); i++)
						if (autonoleggio.parco_mezzi.get(i).getTarga().equalsIgnoreCase(targa.getText())) {
							if (!autonoleggio.parco_mezzi.get(i).getPrenotato()) {
								autonoleggio.parco_mezzi.remove(i);
								FinestraDialogo ok = new FinestraDialogo("Mezzo rimosso");
							} else 
								sommario.setText("Attenzione: mezzo prenotato.");
						} else {
							sommario.setText("Ricontrolla la targa.");
						}
			}
		}
	}
