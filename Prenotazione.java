package Autonoleggio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**Questa classe rappresenta un oggetto Prenotazione, che serve a fissare
 * una richiesta per un Veicolo in una precisa data. Si compone di una LocalDate
 * che contiene il riferimento alla data della Prenotazione, di un richiedente che
 * effettua la Prenotazione e di uno Scanner per gestire gli input dell'utente.*/
public class Prenotazione implements Serializable {
	
	//ID di default per la serializzazione
	static final long serialVersionUID = 3L;
	
	//Variabile che contiene la data di prenotazione
	protected LocalDate data_prenotazione;
	//Variabile che contiene il nome del richiedente
	protected String richiedente;
	//Variabile Scanner non serializzabile per gestire gli input
	protected transient Scanner input_pre;
	
	//Costruttore che accetta come argomento un Veicolo, nella cui lista_prenotazioni la
	//prenotazione andra' inserita (operazione gestita dall'Autonoleggio). Si opera un
	//controllo con ciclo do-while per garantire che venga inserito almeno una stringa
	//non vuota per il richiedente. Dopo aver creato un LocalDate tramite utente e uno con la data
	//di invocazione del metodo, si verifica che l'utente non abbia inserito date nel
	//passato: in caso contrario si lancia un'eccezione, terminando la procedura
	public Prenotazione(Veicolo v) throws ErroreFormatoData, NullPointerException {
		input_pre = new Scanner(System.in);
		System.out.println("Stai prenotando l'auto targata " + v.getTarga() + " .");
		do {
			System.out.println("Inserisci il nome del cliente: ");
			richiedente = input_pre.nextLine();
		} while ((richiedente.length() < 3) || (richiedente.isBlank()));
		input_pre = new Scanner(System.in);
		try {
		System.out.println("Scrivi giorno, mese e anno della prenotazione" +
		" separati da spazi e premi Invio.");
		int gg = input_pre.nextInt();
		int mm = input_pre.nextInt();
		int aa = input_pre.nextInt();
		LocalDate tmp = LocalDate.of(aa, mm, gg);
		LocalDate oggi = LocalDate.now();
		if (tmp.compareTo(oggi) < 0)
			throw new ErroreFormatoData("Stai cercando di prenotare nel passato.");
		data_prenotazione = tmp;
		} catch (ErroreFormatoData e) {
			System.out.println("Ci sono problemi con la data inserita.");
		} catch (NullPointerException f) {
			System.out.println("Ricontrolla gli indici.");
		}
	}
	
	//Costruttore impiegato dalla GUI che lavora su oggetti LocalDate gia' validati, mentre
	//il veicolo a cui aggiungere la prenotazione e' salvato in una variabile locale
	public Prenotazione(LocalDate d, String rich) {
		data_prenotazione = d;
		richiedente = rich;
	}
	
	//Questo metodo fa uso di un DateTimerFormatter per scrivere la data in un formato
	//accettabile in Italia e la restituisce come String
	public String formattaData(LocalDate data) {
		DateTimeFormatter slash = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data_prenotazione.format(slash);
	}
	
	//Metodo per descrivere la prenotazione
	public String toString() {
		return "Prenotato da: " + richiedente + " il giorno: " + formattaData(data_prenotazione) + ".";
	}
	
}
