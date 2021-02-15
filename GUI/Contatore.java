package Autonoleggio.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Contatore extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final String percorso = "contatore.dat";
	private JButton btn1, btn2, btn3;
	private int total = 900;
	private JTextField totale;

	public Contatore() {
		super("Firestone Alchemy Counter");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
				ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(percorso)));
				total = (int)file_input.readObject();
				file_input.close();
		} catch (FileNotFoundException e) {
				System.out.println("e' la prima volta che lavori sul file.");
				System.out.println("Le modifiche saranno registrate alla chiusura del gestore;");
				System.out.println();
		} catch (ClassNotFoundException e) {
				System.out.println("Errore di lettura");
		} catch (IOException e) {
				System.out.println("Errore di I/O");
		}
		JPanel primo = new JPanel();
		primo.setLayout(new BorderLayout());
		JPanel secondo = new JPanel();
		secondo.setLayout(new FlowLayout(FlowLayout.CENTER));
		Listener ascoltatore = new Listener(this);
		btn1 = new JButton("-1");
		btn1.addActionListener(ascoltatore);
		btn2 = new JButton("-2");
		btn2.addActionListener(ascoltatore);
		btn3 = new JButton("-3");
		btn3.addActionListener(ascoltatore);
		totale = new JTextField();
		totale.setEditable(false);
		totale.setText(String.valueOf(total));
		JPanel tot = new JPanel();
		tot.setLayout(new FlowLayout(FlowLayout.CENTER));
		tot.add(totale);
		secondo.add(btn1);
		secondo.add(btn2);
		secondo.add(btn3);
		primo.add(secondo, BorderLayout.SOUTH);
		primo.add(tot, BorderLayout.NORTH);
		this.add(primo);
		this.setVisible(true);
		pack();
	}
	
	public static void main(String[] args) {
		
		Contatore nuovo = new Contatore();
	}

	public class Listener implements ActionListener {
		
		private Contatore cont;
		
		public Listener(Contatore c) {
			cont = c;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			String btn = e.getActionCommand();
			
			if (btn.equals("-1")) {
			int totale_da_stringa = Integer.valueOf(cont.totale.getText());
			totale_da_stringa -= 1;
			total = totale_da_stringa;
			String totale_in_stringa = String.valueOf(totale_da_stringa);
			totale.setText(totale_in_stringa);
			try {
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(percorso)));
				file_output.writeObject(total);
				file_output.close();
			} catch (IOException g) {
				System.out.println(g);
			} catch (NullPointerException f) {
				System.out.println(f);
			}
			} else if (btn.equals("-2")) {
				int totale_da_stringa = Integer.valueOf(cont.totale.getText());
				totale_da_stringa -= 2;
				total = totale_da_stringa;
				String totale_in_stringa = String.valueOf(totale_da_stringa);
				totale.setText(totale_in_stringa);
				try {
					ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(percorso)));
					file_output.writeObject(total);
					file_output.close();
				} catch (IOException g) {
					System.out.println(g);
				} catch (NullPointerException f) {
					System.out.println(f);
				}
		} else if (btn.equals("-3")) {
			int totale_da_stringa = Integer.valueOf(cont.totale.getText());
			totale_da_stringa -= 3;
			total = totale_da_stringa;
			String totale_in_stringa = String.valueOf(totale_da_stringa);
			totale.setText(totale_in_stringa);
			try {
				ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(percorso)));
				file_output.writeObject(total);
				file_output.close();
			} catch (IOException g) {
				System.out.println(g);
			} catch (NullPointerException f) {
				System.out.println(f);
			}
		}
	}
	}
}
