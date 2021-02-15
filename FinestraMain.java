package Autonoleggio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.*;
import javax.swing.*;

/**Classe che contiene il nucleo della GUI. In questo JFrame
 * sono raccolto i 7 pulsanti che permettono all'utente di navigare
 * tra le funzioni dell'autonoleggio, sostituendosi al ciclo do-while
 * della versione testuale. */
public class FinestraMain extends JFrame {

	//ID di default per la serializzazione
	private static final long serialVersionUID = 1L;
	
	//Variabili di tipo bottone, da sottoporre al Listener interno
	private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7;
	//Variabile contenente il riferimento all'autonoleggio, che gestisce
	//i cambiamenti nell'ArrayList di Veicoli immatricolati, da passare a tutte
	//le finestre che verranno aperte da questa schermata
	protected Autonoleggio autonoleggio;

	//Costruttore che gestisce la disposizione degli elementi sul Frame
	public FinestraMain() {
		super("Autonoleggio CarPisa");
		try {
			autonoleggio = new Autonoleggio("CarPisa");
		} catch (ClassNotFoundException | IOException e) {
			FinestraDialogo nuova = new FinestraDialogo("Problemi di lettura sul file.");
		}
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel primo = new JPanel();
		primo.setLayout(new BorderLayout());
		JPanel secondo = new JPanel();
		secondo.setLayout(new GridLayout(3,2));
		JPanel terzo = new JPanel();
		terzo.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel greet = new JLabel("Cosa vuoi fare oggi?");
		Listener ascoltatore = new Listener(this);
		btn1 = new JButton("Immatricola un veicolo");
		btn2 = new JButton("Rimuovi un veicolo");
		btn3 = new JButton("Visualizza parco mezzi");
		btn4 = new JButton("Inserisci prenotazione");
		btn5 = new JButton("Rimuovi prenotazione");
		btn6 = new JButton("Disponibilità' per data");
		btn7 = new JButton("Chiudi gestore");
		btn1.addActionListener(ascoltatore);
		btn2.addActionListener(ascoltatore);
		btn3.addActionListener(ascoltatore);
		btn4.addActionListener(ascoltatore);
		btn5.addActionListener(ascoltatore);
		btn6.addActionListener(ascoltatore);
		btn7.addActionListener(ascoltatore);
		secondo.add(btn1);
		secondo.add(btn2);
		secondo.add(btn3);
		secondo.add(btn4);
		secondo.add(btn5);
		secondo.add(btn6);
		terzo.add(greet);
		primo.add(btn7, BorderLayout.SOUTH);
		primo.add(terzo, BorderLayout.NORTH);
		primo.add(secondo, BorderLayout.CENTER);
		this.add(primo);
		pack();
		this.setVisible(true);
	} 
	
	//Metodo main() per avviare il frame
	public static void main(String[] args) {
		
		FinestraMain main = new FinestraMain();
	}

	/**Listener interno alla classe che gestisce la i pulsanti cliccati
	 * attivando la creazione del corrispondente JFrame. All'uscita, salva
	 * automaticamente il contenuto del parco mezzi dell'Autonoleggio e stampa
	 * su console un messaggio con il successo dell'operazione.*/
	public class Listener implements ActionListener {
		
		FinestraMain main;
	
		public Listener(FinestraMain m) {
			main = m;
		}

		public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == main.btn1)
			immatricola();
		else if (src == main.btn2)
			rimuovi();
		else if (src == main.btn3)
			visualizza();
		else if (src == main.btn4)
			inserisci();
		else if (src == main.btn5)
			rimuoviP();
		else if (src == main.btn6)
			disponibilita();
		else if (src == main.btn7)
			salvaChiudi();
		}
	
		private void immatricola() {
			FinestraImmatricola immatricola = new FinestraImmatricola(main, autonoleggio);
			main.setVisible(false);
		}
		
		private void rimuovi() {
			FinestraRimuovi rimuovi = new FinestraRimuovi(main, autonoleggio);
			main.setVisible(false);
		}
		
		private void visualizza() {
			FinestraVisualizza visualizza = new FinestraVisualizza(main, autonoleggio);
			main.setVisible(false);
		}
		
		private void inserisci() {
			FinestraInserisci visualizza = new FinestraInserisci(main, autonoleggio);
			main.setVisible(false);
		}		
		
		private void rimuoviP() {
			FinestraRimuoviP rimuovi_p = new FinestraRimuoviP(main, autonoleggio);
			main.setVisible(false);
		}	
		
		private void disponibilita() {
			FinestraDisponibilita disponibilita = new FinestraDisponibilita(main, autonoleggio);
			main.setVisible(false);
		}	
		
		private void salvaChiudi() {
			try {
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(autonoleggio.nome_percorso)));
				file_output.writeObject(autonoleggio.parco_mezzi);
				file_output.close();
				System.out.println("File salvato.");
			} catch (IOException e) {
				System.out.println("ERRORE di I/O");
				System.out.println(e);
			} catch (NullPointerException e) {
				System.out.println(e);
			}
		    main.setVisible(false);
		    main.dispose();
		    System.exit(0); 
		}
	}
}
