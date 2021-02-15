package Autonoleggio;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**Questa classe rappresenta una finestra che permette all'utente di
 * rimuovere una Prenotazione inserita nel relativo ArrayList di un Veicolo.
 * Il Listener, dopo aver verificato la correttezza dei dati inseriti, genera una
 * finestra di dialogo con il successo dell'operazione.*/
public class FinestraRimuoviP extends JFrame {
	
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
		//Variabile di tipo JTextArea, da sottoporre al Listener interno
		private JTextArea area_testo;
		//Variabili di tipo JTextField, da sottoporre al Listener interno
		private JTextField targa, prenotazione;
		//Variabili di tipo bottone, da sottoporre al Listener interno
		private JButton esci, visualizza, rimuovere;
	
		//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
		//e dispone tutti gli elementi della finestra
		public FinestraRimuoviP(FinestraMain m, Autonoleggio aut) {
			main = m;
			autonoleggio = aut;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			JPanel primo = new JPanel();
			primo.setLayout(new BorderLayout());
			sommario = new JLabel("Inserisci la targa per visualizzare le prenotazioni di un mezzo:");
			area_testo = new JTextArea(15, 30);
			area_testo.setEditable(false);
			JScrollPane scroll = new JScrollPane(area_testo);
			esci = new JButton("Chiudi");
			visualizza = new JButton("Visualizza");
			rimuovere = new JButton("Cancella");
			targa = new JTextField("Targa", 7);
			prenotazione = new JTextField("Numero pr.", 7);
			JPanel secondo = new JPanel();
			secondo.setLayout(new BorderLayout());
			Listener ascoltatore = new Listener(this);
			esci.addActionListener(ascoltatore);
			visualizza.addActionListener(ascoltatore);
			rimuovere.addActionListener(ascoltatore);
			JPanel secondo_flow = new JPanel();
			secondo_flow.setLayout(new FlowLayout(FlowLayout.CENTER));
			secondo_flow.add(targa);
			secondo_flow.add(visualizza);
			secondo.add(secondo_flow, BorderLayout.WEST);
			JPanel terzo = new JPanel();
			terzo.setLayout(new BorderLayout());
			JPanel terzo_flow = new JPanel();
			terzo_flow.setLayout(new FlowLayout(FlowLayout.CENTER));
			terzo_flow.add(prenotazione);
			terzo_flow.add(rimuovere);
			terzo.add(terzo_flow, BorderLayout.EAST);
			secondo.add(terzo, BorderLayout.SOUTH);
			primo.add(esci, BorderLayout.SOUTH);
			primo.add(scroll, BorderLayout.CENTER);
			primo.add(sommario, BorderLayout.NORTH);
			primo.add(terzo, BorderLayout.EAST);
			primo.add(secondo, BorderLayout.WEST);
			this.add(primo);
			pack();
		}
		
		/**Listener interno alla classe che gestisce la rimozione
		 * di una Prenotazione o la chiusura del JFrame, riportando la visibilita'
		 * al frame Main. Si procede in due tempi: prima si chiede all'utente di visualizzare 
		 * le prenotazioni di un veicolo inserendone la targa, dopodiche', una volta salvato il Veicolo,
		 *  si procede alla rimozione della Prenotazione inserendo l'indice relativo. Se il mezzo
		 *  non ha piu' oggetti Prenotazione al suo interno, il Veicolo viene flaggato come rimuovibile (prenotato = false).
		 * Il JLabel notifica eventuali anomalie e una finestra di dialogo notifica l'avvenuta rimozione.*/
		class Listener implements ActionListener {
			
			private FinestraRimuoviP rimuovi_p;
			private Veicolo target;
			private boolean targa_ok;
			
			public Listener(FinestraRimuoviP f_rp) {
				rimuovi_p = f_rp;
				targa_ok = false;
				target = null;
			}
			
			public void actionPerformed(ActionEvent e) {

				String btn = e.getActionCommand();
				if (btn.equals("Chiudi")) {
					rimuovi_p.setVisible(false);
					rimuovi_p.dispose();
					main.setVisible(true);
				} else if (btn.equals("Visualizza")) {
					targa_ok = false;
					if (Veicolo.validaTarga(targa.getText())) {
						for (int vei = 0; vei < autonoleggio.parco_mezzi.size(); vei++)
							if (autonoleggio.parco_mezzi.get(vei).getTarga().equals(targa.getText())) {
								target = autonoleggio.parco_mezzi.get(vei);
								if (target.lista_prenotazioni.size() == 0)
									area_testo.append("Nessuna prenotazione per questo mezzo");
								else 
									for (int i = 0; i < target.lista_prenotazioni.size(); i++) {
										Prenotazione p = target.lista_prenotazioni.get(i);
										area_testo.append(i + 1 + ". " + p.toString());
										area_testo.append("\n");
										targa_ok = true;
										sommario.setText("Ora scrivi a destra il numero della prenotazione da rimuovere e clicca su cancella.");
								}
							}
					} else {
						sommario.setText("Assicurati che la targa sia corretta e che il veicolo sia dell'autonoleggio.");
					}
				} else if (btn.equals("Cancella")) {
					if (targa_ok) {
						Integer index = Integer.valueOf(prenotazione.getText());
						target.lista_prenotazioni.remove(index - 1);
						targa_ok = false;
						if (target.lista_prenotazioni.size() == 0)
							target.setPrenotato(false);
						main.setVisible(true);
						rimuovi_p.setVisible(false);
						rimuovi_p.dispose();
						FinestraDialogo ok = new FinestraDialogo("Prenotazione rimossa.");
					}	
				}
		}
	}
}
