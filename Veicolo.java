package Autonoleggio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Scanner;

/**Questa classe rappresenta la classe da cui ereditano le due classi impiegate per l'esercitazione,
 * ovvero Furgone e Autovettura. Ogni Veicolo e' identificato da una targa, usato anche nel metodo
 * equals per stabilire identita' tra Veicoli (dato che e' unica), un modello e un autonoleggio di 
 * appartenenza, dato che viene aggiunto tramite il metodo di immatricolazione dell'autonoleggio.
 * Un booleano verifica che il mezzo non abbia prenotazioni in corso mentre un ArrayList, di
 * default ordinato per ordine di data crescente, contiene le prenotazioni inserite dall'autonoleggio.
 * Completa la classe uno scanner per gestire i dati in input.*/
public class Veicolo implements Serializable {
	//ID di default per classe Serializzabile
	static final long serialVersionUID = 2;
	//La targa del Veicolo
	protected String targa;
	//Il modello del Veicolo
	protected String modello;
	//L'Autonoleggio presso cui e' registrato il Veicolo
	protected Autonoleggio autonoleggio;
	//Flag per stabilire se un Veicolo ha una Prenotazione in corso o meno
	protected boolean prenotato;
	//ArrayList che contiene una serie di oggetti Prenotazione
	protected ArrayList<Prenotazione> lista_prenotazioni = new ArrayList<Prenotazione>();
	//Scanner per gestire gli input dell'utente
	protected transient Scanner input_ve;
	
	//Costruttore unico della classe: ammette la creazione di un oggetto Veicolo solo
	//a partire da una targa (valida), un modello (valido) attraverso dei metodi che
	//vengono chiamati ricorsivamente finche' l'input non e' corretto, e infine un Autonoleggio presso cui
	//e' registrato il veicolo. Di default il boolean prenotato e' impostato a false
	public Veicolo(String tar, String mod, Autonoleggio autonol) {
		if (validaTarga(tar))
			targa = tar;
		else {
			System.out.println("Inserire una targa valida per questo mezzo.");
			input_ve = new Scanner(System.in);
			String nuova_targa = input_ve.nextLine();
			setTarga(nuova_targa);
		}
		if (validaModello(mod))
			modello = mod;
		else {
			System.out.println("Inserire un modello valido per questo mezzo.");
			input_ve = new Scanner(System.in);
			String nuovo_modello = input_ve.nextLine();
			setModello(nuovo_modello);
		}
		prenotato = false;
		autonoleggio = autonol;
	}
	
	//Metodo per impostare una targa (valida). Si effettuan chiamate ricorsive
	//finche' la targa non e' scritta in un formato valido
	public void setTarga(String targa_n) {
		input_ve = new Scanner(System.in);
		if (validaTarga(targa_n)) {
			if (targa.equalsIgnoreCase(targa_n))
				System.out.println("La targa e' uguale alla precedente.");
			else {
				String targa_min = targa_n;
				targa = targa_min.toUpperCase();
				System.out.println("Targa modificata con successo.");
			}
		} else {
			System.out.println("Inserisci una targa valida (es. AZ123ZA)");
			String targa_nuova = input_ve.nextLine();
			setTarga(targa_nuova);
		}
	}
	
	//Metodo per recuperare la targa di un Veicolo
	public String getTarga() {
		return targa;
	}
	
	//Metodo per impostare un nuovo modello per il veicolo. Chiamata ricorsiva
	//finche' non viene impostato un modello valido
	public void setModello(String modello_n) {
		input_ve = new Scanner(System.in);
		if (validaModello(modello_n)) {
			modello = modello_n;
			System.out.println("Modello modificato con successo.");
		} else {
			System.out.println("Inserisci un modello valido.");
			String modello_nuovo = input_ve.nextLine();
			setModello(modello_nuovo);
		}
	}

	//Metodo per recuperare il modello
	public String getModello() {
		return modello;
	}
	
	//Metodo per impostare l'Autonoleggio a cui appartiene il Veicolo
	public void setAutonoleggio(Autonoleggio autonol) {
		autonoleggio = autonol;
	}
	
	//Metodo per recuperare l'Autonoleggio a cui appartiene il Veicolo
	public Autonoleggio getAutonoleggio() {
		return autonoleggio;
	}
	
	//Metodo per impostare il flag di prenotazione del Veicolo
	public void setPrenotato(boolean pren) {
		prenotato = pren;
	}
	
	//Metodo per recuperare il flag di prenotazione del Veicolo
	public boolean getPrenotato() {
		return prenotato;
	}
	
	//Metodo per assegnare al Veicolo un ArrayList contenente oggetti Prenotazione
	public void setListaPrenotazioni(ArrayList<Prenotazione> lista_p) {
		lista_prenotazioni = lista_p;
	}
	
	//Metodo per recuperare l'ArrayList contenente oggetti Prenotazione per un Veicolo
	public ArrayList<Prenotazione> getListaPrenotazioni() {
		return lista_prenotazioni;
	}
	
	//Metodo per stampare gli oggetti Prenotazione con un indice
	public void visualizzaPrenotazioni() {
		if (!this.getPrenotato())
			System.out.println("Il veicolo non ha prenotazioni.");	
		else {
				System.out.println("Per il veicolo targato " + this.getTarga() + 
					" ci sono le seguenti prenotazioni:");
			int i = 1;
			for (Prenotazione x : lista_prenotazioni) {
				System.out.print(i + ": ");
				System.out.println(x.toString());
				i++;
			}
		}
	}
	
	//Metodo che utilizza un'espressione regolare per verificare che la targa sia in un formato valido in Italia,
	//statico perche' lo utilizza anche Autonoleggio. Implementato sia in costruttore che metodo set
	public static boolean validaTarga(String tar) {
		return Pattern.matches("[A-Z]{2}\\s*[0-9]{3}\\s*[A-Z]{2}", tar);
	}
	
	//Metodo per validare il modello. Banalmente controlla che la Stringa sia maggiore di 3 caratteri
	//e che non sia costituita da spazi bianchi. Statico perche' usato anche in controlli da altri metodi
	public static boolean validaModello(String mod) {
		return mod.length() > 3 && !mod.isBlank();
	}
	
	//Metodo che fa override di toString()
	public String toString() {
		return "Targa:\t\t" + targa + "\nModello:\t" + modello + "\nAutonoleggio:\t" +
			autonoleggio.getNome() + ".";
	}
	
	//Metodo duplicato a uso della GUI, per formattare meglio l'output
	public String toString2() {
		return "Targa:\t" + targa + "\nModello:\t" + modello + "\nAutonoleggio:\t" +
			autonoleggio.getNome() + ".";
	}
	
	//Metodo per restituire un'equivalenza tra 2 oggetti veicolo. Si prende in considerazione la targa
	public boolean equalsTo(Veicolo y) {
		return this.targa.equals(y.targa);
	}
}
