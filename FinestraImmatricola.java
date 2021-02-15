package Autonoleggio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

/**Questa classe rappresenta una finestra che permette all'utente di
 * scegliere se immatricolare un Furgone o un Autovettura, smistandolo
 * verso il frame corrispondente */
public class FinestraImmatricola extends JFrame {
	
	//ID di defaul per la serializzazione
	private static final long serialVersionUID = 1L;
	
	//Variabile contenente il riferimento al JFrame principale della GUI
	private FinestraMain main;
	//Variabile contenente il riferimento all'autonoleggio, che gestisce
	//i cambiamenti nell'ArrayList di Veicoli immatricolati
	private Autonoleggio autonoleggio;
	//Variabili di tipo bottone, da sottoporre al Listener interno
	private JButton autovettura, furgone;

	//Costruttore che accetta come argomenti una FinestraMain e un Autonoleggio e
	//dispone tutti gli elementi della finestra
	public FinestraImmatricola(FinestraMain m, Autonoleggio aut) {
		main = m;
		autonoleggio = aut;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		JPanel primo = new JPanel();
		primo.setLayout(new BorderLayout());
		JLabel sommario = new JLabel("Che veicolo vuoi immatricolare?");
		primo.add(sommario);
		autovettura = new JButton("Autovettura");
		furgone = new JButton("Furgone");
		JPanel bottoni = new JPanel();
		bottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottoni.add(autovettura);
		bottoni.add(furgone);
		Listener ascoltatore = new Listener(this);
		autovettura.addActionListener(ascoltatore);
		furgone.addActionListener(ascoltatore);
		primo.add(sommario, BorderLayout.NORTH);
		primo.add(bottoni, BorderLayout.CENTER);
		this.add(primo);
		pack();
	}
	
	/**Listener interno alla classe che gestisce la creazione
	 * del JFrame corrispondente alla scelta cliccata tra Autovettura
	 * e Veicolo*/
	class Listener implements ActionListener {
		
		private FinestraImmatricola immatricola;
		
		public Listener(FinestraImmatricola f_i) {
			immatricola = f_i;
		}
		
		public void actionPerformed(ActionEvent e) {

			String btn = e.getActionCommand();
			if (btn.equals("Autovettura")) {
				FinestraImmatricolaVeicolo vei = new FinestraImmatricolaVeicolo(main, autonoleggio);
				immatricola.setVisible(false);
				immatricola.dispose();
			} else if (btn.equals("Furgone")) {
				FinestraImmatricolaFurgone frg = new FinestraImmatricolaFurgone(main, autonoleggio);
				immatricola.setVisible(false);
				immatricola.dispose();
			}
		}
	}
}	
