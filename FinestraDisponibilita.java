package Autonoleggio;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**Questa e' la classe che rappresenta un JFrame predisposto a visualizzare
 * quali mezzi dell'Autonoleggio sono disponibili in una data inserita dall'utente,
 * che scrive in campi appositi giorno, mese e anno*/
public class FinestraDisponibilita extends JFrame {

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
		//Variabili di tipo JTextField, da sottoporre al Listener interno
		private JTextField giorno, mese, anno;
		//Variabile di tipo classe JTextArea per visualizzare i risultati
		private JTextArea disponibili;
		//Variabili di tipo bottone, da sottoporre al Listener interno
		private JButton esci, cerca;
	
		//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
		//e dispone tutti gli elementi della finestra, tra cui uno scroller
		public FinestraDisponibilita(FinestraMain m, Autonoleggio aut) {
			super("Autonoleggio CarPisa");
			main = m;
			autonoleggio = aut;
			this.setSize(500,300);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			JPanel primo = new JPanel();
			primo.setLayout(new BorderLayout());
			disponibili = new JTextArea(15, 30);
			disponibili.setEditable(false);
			JScrollPane scroll = new JScrollPane(disponibili);
			sommario = new JLabel("Inserisci una data per verificare quali mezzi sono disponibili");
			esci = new JButton("Chiudi");
			cerca = new JButton("Cerca");
			giorno = new JTextField("GG", 5);
			mese = new JTextField("MM", 5);
			anno = new JTextField("AAAA", 7);
			JPanel data = new JPanel();
			data.setLayout(new FlowLayout(FlowLayout.CENTER));
			data.add(giorno);
			data.add(mese);
			data.add(anno);
			JPanel targa_bot = new JPanel();
			targa_bot.setLayout(new BorderLayout());
			targa_bot.add(cerca, BorderLayout.SOUTH);
			targa_bot.add(data, BorderLayout.CENTER);
			Listener ascoltatore = new Listener(this);
			cerca.addActionListener(ascoltatore);
			esci.addActionListener(ascoltatore);
			primo.add(sommario, BorderLayout.NORTH);
			primo.add(targa_bot, BorderLayout.WEST);
			primo.add(disponibili, BorderLayout.CENTER);
			primo.add(esci, BorderLayout.SOUTH);
			this.add(primo);
			pack();
		}
		
		/**Listener interno alla classe che gestisce la visione delle disponibilita'
		 * o la chiusura del JFrame, riportando la visibilita'
		 * al frame Main. All'avvio di ricerca si verifica che la data sia corretta
		 * e che il Veicolo non sia gia' prenotato per quella data. Il JLabel si modifica
		 * e nella JTextArea vengono stampate tutte le informazioni
		 * relative alle prenotazioni diverse dalla data inserita.*/
		class Listener implements ActionListener {
			
			private FinestraDisponibilita disponibilita;
			
			public Listener(FinestraDisponibilita f_d) {
				disponibilita = f_d;
			}
			
			public void actionPerformed(ActionEvent e) {

				String btn = e.getActionCommand();
				
				if (btn.equals("Chiudi")) {
					disponibilita.setVisible(false);
					disponibilita.dispose();
					main.setVisible(true);
				} else if (btn.equals("Cerca")) {
					try {
						boolean prenotato = false;
						boolean data_ok = true;
						LocalDate tmp = LocalDate.of(Integer.valueOf(anno.getText()), Integer.valueOf(mese.getText()), Integer.valueOf(giorno.getText()));
						LocalDate oggi = LocalDate.now();
						if (tmp.compareTo(oggi) < 0) {
							data_ok = false;
							throw new ErroreFormatoData();
						}
						sommario.setText("I mezzi disponibili per la data inserita sono i seguenti:");
						for (Veicolo v : autonoleggio.parco_mezzi) {
							for (Prenotazione p : v.lista_prenotazioni)
								if (p.data_prenotazione.equals(tmp))
									prenotato = true;
						if (!prenotato && data_ok) {
							disponibili.setText("");
							disponibili.append(v.toString2());
							disponibili.append("\n\n");
							prenotato = false;
							}
						}
					} catch (ErroreFormatoData a) {
						FinestraDialogo data = new FinestraDialogo(a.toString());
					} catch (DateTimeException f) {
						FinestraDialogo data = new FinestraDialogo(f.toString());
					} 
			}
		}
	}
}
