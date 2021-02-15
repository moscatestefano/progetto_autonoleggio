package Autonoleggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

/**Questa classe rappresenta una finestra che permette all'utente di
 * visualizzare tutti i veicoli aggiunti all'autonoleggio di una JTextArea apposita.*/
public class FinestraVisualizza extends JFrame {

		//ID di defaul per la serializzazione
		private static final long serialVersionUID = 1L;
		
		//Variabile contenente il riferimento al JFrame principale della GUI
		private FinestraMain main;
		//Variabile contenente il riferimento all'autonoleggio, che gestisce
		//i cambiamenti nell'ArrayList di Veicoli immatricolati
		protected Autonoleggio autonoleggio;
		//Variabile di tipo bottone, da sottoporre al Listener interno
		private JButton esci;
	
		//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio
		//e dispone tutti gli elementi della finestra. Automaticamente popola la
		//JTextArea con le invocazioni ripetute dei metodi toString() dei Veicoli
		//presenti in Autonoleggio
		public FinestraVisualizza(FinestraMain m, Autonoleggio aut) {
			main = m;
			autonoleggio = aut;
			this.setSize(500,300);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			JPanel primo = new JPanel();
			primo.setLayout(new BorderLayout());
			JLabel sommario = new JLabel("Questi sono i mezzi dell'autonoleggio:");
			JTextArea area_testo = new JTextArea(15, 30);
			area_testo.setEditable(false);
			JScrollPane scroll = new JScrollPane(area_testo);
			if (autonoleggio.parco_mezzi.size() == 0)
				area_testo.append("Nessun mezzo immatricolato");
			else {
				for (Veicolo v : autonoleggio.parco_mezzi) {
					area_testo.append(v.toString2());
					area_testo.append("\n\n");
				}
			}
			JButton esci = new JButton("Chiudi");
			Listener ascoltatore = new Listener(this);
			esci.addActionListener(ascoltatore);
			primo.add(esci, BorderLayout.SOUTH);
			primo.add(scroll, BorderLayout.CENTER);
			primo.add(sommario, BorderLayout.NORTH);
			this.add(primo);
		}
		
		/**Listener interno alla classe che gestisce unicamente la
		 * chiusura della finestra.*/
		class Listener implements ActionListener {
			
			private FinestraVisualizza visualizza;
			
			public Listener(FinestraVisualizza f_v) {
				visualizza = f_v;
			}
			
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == visualizza.esci)
					visualizza.setVisible(false);
					visualizza.dispose();
					main.setVisible(true);
			}
		}
	}
