package Autonoleggio;

/**Eccezione implementata per far fronte a tutte le eccezioni
 * non incluse in java.time.DateTimeFormatException. Nello specifico,
 * le prenotazioni per date anteriori rispetto al giorno in cui viene
 * utilizzato il gestore*/
public class ErroreFormatoData extends Exception {
	
	private static final long serialVersionUID = 5765703280574986174L;

	public ErroreFormatoData() {
		super("C'e' un errore nella data inserita.");
	}
			
	public ErroreFormatoData(String e) {
			super(e);
	}
}
