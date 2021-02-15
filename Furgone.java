package Autonoleggio;

/**Questa classe rappresenta un Furgone e deriva dalla superclasse
 * Veicolo. Di questa eredita modello, targa, autonoleggio, il flag di
 * prenotazione e la lista delle prenotazioni. Aggiunge due enum per i
 * valori invariabili di Patente e TipologiaFurgone. Non implementa
 * nuovi metodi se non quelli necessari a recuperare o settare informazioni
 * sulle classi enum.*/
public class Furgone extends Veicolo {
	
	//ID di defaul per la serializzazione
	static final long serialVersionUID = 1L;
	
	//La variabile contenente uno dei due valori di Patente
	protected Patente patente;
	//La variabile contentente uno dei due valori di TipologiaFurgone
	protected TipologiaFurgone tipologia;
	
	//Costruttore che inizializza tutte le variabili invocando il costruttore della superclasse
	public Furgone(String tar, String mod, Autonoleggio autonol, Patente p, TipologiaFurgone t) {
		super(tar, mod, autonol);
		patente = p;
		tipologia = t;
	}
	//Metodo per impostare la variabile Patente
	public void setPatente(Patente p) {
		patente = p;
	}
	
	//Metodo per recuperare la variabile Patente
	public Patente getPatente() {
		return patente;
	}
	
	//Metodo per impostare la variabile TipologiaFurgone
	public void setTipologiaFurgone(TipologiaFurgone t) {
		tipologia = t;
	}
	
	//Metodo per recuperare la variabile TipologiaFurgone
	public TipologiaFurgone getTipologiaFurgone() {
		return tipologia;
	}
	
	//Metodo per visualizzare le informazioni sull'autovettura
	public String toString() {
		return "[Furgone]\nTarga:\t\t" + targa + "\nModello:\t" + modello + "\nTipologia:\t" + tipologia +
				"\nPatente:\t" + patente + "\nAutonoleggio:\t" + autonoleggio.nome;
	}
	
	//Metodo duplicato a uso della GUI, per formattare meglio l'output
	public String toString2() {
		return "[Furgone]\nTarga:\t" + targa + "\nModello:\t" + modello + "\nTipologia:\t" + tipologia +
				"\nPatente:\t" + patente + "\nAutonoleggio:\t" + autonoleggio.nome;
	}
	
	//enum di Patente, valori chiusi
	enum Patente {
		B, C;
	}
	
	//enum di TipologiaFurgone, valori chiusi
	enum TipologiaFurgone {
		PICCOLO, GRANDE;
	}
}
