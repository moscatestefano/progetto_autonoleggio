package Autonoleggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**Classe che rappresenta una semplice finestra di dialogo
 * per notificare degli alert. */
public class FinestraDialogo extends JFrame {
	//ID di default per la serializzazione
	private static final long serialVersionUID = 1L;
	
	//Variabile che conserva il JFrame a cui ritornare una volta chiuso
	private JFrame origine;
	//Variabile bottone di uscita
	private JButton esci;
	
	//Costruttore per una finestra che visualizzi un messaggio passato
	//come argomento
	public FinestraDialogo(String s) {
		origine = null;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		JPanel primo = new JPanel();
		primo.setLayout(new BorderLayout());
		JPanel bottoni = new JPanel();
		bottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton esci = new JButton("OK");
		bottoni.add(esci);
		JLabel testo = new JLabel(s);
		Listener ascoltatore = new Listener(this);
		esci.addActionListener(ascoltatore);
		primo.add(bottoni, BorderLayout.SOUTH);
		primo.add(testo, BorderLayout.CENTER);
		this.add(primo);
		pack();
	}
	
	/**Listener interno alla classe che gestisce la chiusura
	 * della finestra di dialogo */
class Listener implements ActionListener {
		
		private FinestraDialogo dialogo;
		
		public Listener(FinestraDialogo f_d) {
			dialogo = f_d;
		}
		
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src == dialogo.esci) 
				dialogo.setVisible(false);
				dialogo.dispose();
		}
	}

}
