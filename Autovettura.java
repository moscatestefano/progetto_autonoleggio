package Autonoleggio;

/**Questa classe rappresenta un Autovettura e deriva dalla superclasse
 * Veicolo. Di questa eredita modello, targa, autonoleggio, il flag di
 * prenotazione e la lista delle prenotazioni. Aggiunge tre enum per i
 * valori invariabili di Posti, Alimentazione e Tipologia. Non implementa
 * nuovi metodi se non quelli necessari a recuperare o settare informazioni
 * sulle classi enum.*/
public class Autovettura extends Veicolo {
	//ID di default per la serializzazione
	static final long serialVersionUID = 1L;
	//La variabile contenente uno dei 3 valori di Posti
	protected Posti posti;
	//La variabile contenente uno dei 2 valori di AlimentazioneAuto
	protected AlimentazioneAuto alimentazione;
	//La variabile contenente uno dei 3 valori di TipologiaAuto
	protected TipologiaAuto tipologia;
	
	//Costruttore che inizializza tutte le variabili invocando il costruttore della superclasse
	public Autovettura(String tar, String mod, Autonoleggio autonol, Posti p, AlimentazioneAuto a, TipologiaAuto t) {
		super(tar, mod, autonol);
		posti = p;
		alimentazione = a;
		tipologia = t;
	}
	
	//Metodo per impostare la variabile Posti
	public void setPosti(Posti p) {
		posti = p;
	}
	
	//Metodo per recuperare la variabile Posti
	public Posti getPosti() {
		return posti;
	}
	
	//Metodo per impostare la variabile AlimentazioneAuto
	public void setAlimentazione(AlimentazioneAuto a) {
		alimentazione = a;
	}
	
	//Metodo per recuperare la variabile AlimentazioneAuto
	public AlimentazioneAuto getAlimentazione() {
		return alimentazione;
	}
	
	//Metodo per impostare la variabile TipologiaAuto
	public void setTipologia(TipologiaAuto t) {
		tipologia = t;
	}
	
	//Metodo per recuperare la variabile TipologiaAuto
	public TipologiaAuto getTipologia() {
		return tipologia;
	}
	
	//Metodo per visualizzare le informazioni sull'Autovettura
	public String toString() {
		return "[Autovettura]\nTarga:\t\t" + targa + "\nModello:\t" + modello + "\nTipologia:\t" + tipologia +
				"\nAlimentazione:\t" + alimentazione + "\nPosti:\t\t" + this.getPosti() + "\nAutonoleggio:\t" +
				autonoleggio.nome;
	}
	
	//Metodo duplicato a uso della GUI, per formattare meglio l'output
	public String toString2() {
		return "[Autovettura]\nTarga:\t" + targa + "\nModello:\t" + modello + "\nTipologia:\t" + tipologia +
				"\nAlimentazione:\t" + alimentazione + "\nPosti:\t" + this.getPosti() + "\nAutonoleggio:\t" +
				autonoleggio.nome;
	}
	
	//enum dei Posti, valori chiusi
	enum Posti {
		DUE, QUATTRO, CINQUE;
	}
	
	//enum di TipologiaAuto, valori chiusi
	enum TipologiaAuto {
		UTILITARIA, MEDIA, BERLINA
	}
	
	//enum di AlimentazioneAuto, valori chiusi
	enum AlimentazioneAuto {
		BENZINA, DIESEL
	}
	

}
