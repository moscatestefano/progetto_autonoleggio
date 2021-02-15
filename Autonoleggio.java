package Autonoleggio;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import Autonoleggio.Autovettura.*;
import Autonoleggio.Furgone.*;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;

/**Questa e' la classe che rappresenta l'autonoleggio. L'autonoleggio e' concepito per essere
 * gestito da un operatore che opera tramite gestore, ha un proprio nome identificativo, dispone
 * di un parco mezzi (concepito come un ArrayList di oggetti Veicolo) e una variabile "veicolo_di_stato"
 * di tipo Veicolo che, se implementata tramite comando del gestore, permette di passare automaticamente
 * un veicolo gia' registrato presso l'autonoleggio come argomento dei metodi, saltando la parte di input.
 * Completa la classe uno Scanner non serializzabile per le operazioni di input da tastiera.*/

public class Autonoleggio implements Serializable {
	//ID default per l'interfaccia Serializable
	static final long serialVersionUID = 1L;
	//Il nome del file su cui operare per la persistenza dei dati del parco mezzi
	protected final String nome_percorso = "carpisa.dat";
	
	//Il nome del'autonoleggio.
	protected String nome;
	//Il nome dell'operatore che lavora al gestore
	protected String operatore;
	//L'ArrayList che contiene tutti i mezzi immatricolati presso l'autonoleggio
	protected ArrayList<Veicolo> parco_mezzi = new ArrayList<Veicolo>();
	//Il veicolo di stato serve a passare un veicolo gia' immatricolato come argomento delle operazioni di rimozione, visualizzazione e prenotazione
	// senza dover passare per la ricerca del mezzo ogni volta. L'oggetto e' null a ogni avvio del gestore
	protected Veicolo veicolo_di_stato; 
	//Scannero per le operazioni di input
	protected transient Scanner input_aut;
	
	//Costruttore di default impiegato per i test e non utilizzato ai fini del gestore. Gia' caricato con qualche veicolo.
	public Autonoleggio() {
			parco_mezzi = new ArrayList<Veicolo>();
			Veicolo uno = new Autovettura("EZ458JP", "Nissan Daily", this, Posti.QUATTRO, AlimentazioneAuto.BENZINA, TipologiaAuto.BERLINA);
			Veicolo due = new Autovettura("MN753LO", "Almera Tino", this, Posti.CINQUE, AlimentazioneAuto.DIESEL, TipologiaAuto.MEDIA);
			Veicolo tre = new Furgone("PO425MV", "Iveco Horner", this, Patente.C, TipologiaFurgone.GRANDE);
			Veicolo quattro = new Furgone("LM435GH", "Fiat Fiorino", this, Patente.B, TipologiaFurgone.PICCOLO);
			parco_mezzi.add(uno);
			parco_mezzi.add(due);
			parco_mezzi.add(tre);
			parco_mezzi.add(quattro);
			operatore = "Nessun operatore definito";
			nome = "";
	}
	
	//Costruttore impiegato nel main, che inizializza alcune variabili e carica dei dati precedentemente salvati,
	//se esistono. La parte relativa alla gestione delle eccezioni e' stata presa dal materiale delle lezioni.
	//L'operatore non e' definito in modo da poterlo scrivere nel gestore all'avvio
	@SuppressWarnings("unchecked")
	public Autonoleggio(String n) throws FileNotFoundException, ClassNotFoundException, IOException {
			operatore = "Nessun operatore definito";
			nome = n;
			try {
				ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nome_percorso)));
				parco_mezzi = (ArrayList<Veicolo>)file_input.readObject();
				file_input.close();
			} catch (FileNotFoundException e) {
				System.out.println("E' la prima volta che lavori sul file.");
				System.out.println("Le modifiche saranno registrate alla chiusura del gestore;");
				System.out.println();
			} catch (ClassNotFoundException e) {
				System.out.println("Errore di lettura");
			} catch (IOException e) {
				System.out.println("Errore di I/O");
			}
	}
	
	
	//Metodo set per il nome dell'autonoleggio
	public void setNome(String nome_x) {
		nome = nome_x;
	}
	
	//Metodo get per il nome dell'autonoleggio
	public String getNome() {
		return nome;
	}
	
	//Metodo set per l'array dei veicoli
	public void setParcoMezzi(ArrayList<Veicolo> nuovo_parco) {
		parco_mezzi = nuovo_parco;
	}
	
	//Metodo get per l'array dei veicoli
	public ArrayList<Veicolo> getParcoMezzi() {
		return parco_mezzi;
	}
	
	//Metodo set per il nome dell'operatore
	public void setNomeOperatore(String nome_o) {
		operatore = nome_o;
		System.out.println("Utenza cambiata.");
	}
	
	//Metodo get per il nome dell'operatore
	public String getNomeOperatore() {
		return operatore;
	}
	
	//Questo metodo aggiunge un veicolo al parco mezzi. Tramite input e' possibile decidere
	//la tipologia di mezzo e, tramite uno switch, quali sono i parametri immutabili che lo caratterizzano
	public void immatricolaVeicolo() throws IllegalArgumentException, StringIndexOutOfBoundsException, NullPointerException {
		try {
		System.out.println("Vuoi immatricolare un nuovo veicolo e aggiungerlo al parco mezzi.");
		System.out.println("Si tratta di un furgone o di un'autovettura? [F/A]");
		input_aut = new Scanner(System.in);
		char fOa;
		do {
			fOa = input_aut.nextLine().charAt(0);
		} while (Character.toUpperCase(fOa) != 'F' && Character.toUpperCase(fOa) != 'A');
		String tar, mod;
		switch (Character.toUpperCase(fOa)) {
		//caso Furgone
		case 'F': {
			String pat, pat2, grand, grand2;
			System.out.println("Hai selezionato la classe Furgone. Scrivi targa e modello su due righe diverse.");
			tar = input_aut.nextLine();
			mod = input_aut.nextLine();
			System.out.println("Ora indica che patente serve per guidarla. [B/C]");
			do {
				pat = input_aut.nextLine();
				pat2 = pat.toUpperCase();
			} while (!pat2.equals("B") && !pat2.equals("C"));
			System.out.println("e' un modello piccolo o grande? [PICCOLO/GRANDE]");
			do {
				grand = input_aut.nextLine();
				grand2 = grand.toUpperCase();
			} while (!grand2.equals("PICCOLO") && !grand2.equals("GRANDE"));
			Veicolo furgone = new Furgone(tar, mod, this, Patente.valueOf(pat2), TipologiaFurgone.valueOf(grand2));
			//Si controlla che il mezzo non esista gia'
			boolean esistente = false;
			for (Veicolo x : parco_mezzi)
				if (x.equalsTo((Veicolo)furgone)) {
					esistente = true;
					System.out.println("Questo mezzo gia' appartiene all'autonoleggio " + this.nome + ".");
				}
			if (!esistente) {
				parco_mezzi.add(furgone);
				System.out.println("Mezzo aggiunto al parco macchine.");
			}
			break;
		}
		//caso Autovettura
		case 'A': {
			String pos, pos2, tipo, tipo2, alim, alim2;
			System.out.println("Hai selezionato la classe Autovettura. Scrivi targa e modello su due righe diverse.");
			tar = input_aut.nextLine();
			mod = input_aut.nextLine();
			System.out.println("Ora indica quanti posti ha. [DUE/QUATTRO/CINQUE]");
			do {
				pos = input_aut.nextLine();
				pos2 = pos.toUpperCase();
			} while (!pos2.equals("DUE") && !pos2.equals("QUATTRO") && !pos2.equals("CINQUE"));
			System.out.println("Indica la tipologia. [UTILITARIA/MEDIA/BERLINA]");
			do {
				tipo = input_aut.nextLine();
				tipo2  = tipo.toUpperCase();
			} while (!tipo2.equals("UTILITARIA") && !tipo2.equals("MEDIA") && !tipo2.equals("BERLINA"));
			System.out.println("Infine indica il tipo di alimentazione. [BENZINA/DIESEL]");
			do {
				alim = input_aut.nextLine();
				alim2 = alim.toUpperCase();
			} while (!alim2.equals("BENZINA") && !alim2.equals("DIESEL"));
			Veicolo autovettura = new Autovettura(tar, mod, this, Posti.valueOf(pos2), AlimentazioneAuto.valueOf(alim2), TipologiaAuto.valueOf(tipo2));
			//Si controlla che il mezzo non esista gia'
			boolean esistente = false;
			for (Veicolo x : parco_mezzi)
				if (x.equalsTo((Veicolo)autovettura)) {
					esistente = true;
					System.out.println("Questo mezzo gia' appartiene all'autonoleggio " + nome + ".");
				}
			if (!esistente) {
				parco_mezzi.add(autovettura);
				System.out.println("Mezzo aggiunto al parco macchine.");
			}
			break;
		}
		default : System.out.println("Qualcosa e' andato storto."); ; break;
		}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
			System.out.println("Controlla che i valori inseriti siano corretti.");
		} catch (NullPointerException f) {
			System.out.println(f);
			System.out.println("Verifica che i parametri inseriti siano validi.");
		} catch (StringIndexOutOfBoundsException g) {
			System.out.println("Verifica che i dati contengano caratteri validi.");
		}
	}
	
	//Metodo per cercare un veicolo immatricolato e salvarlo nel veicolo di stato
	public void ricercaVeicolo() {
		System.out.println("Stai per cercare un veicolo per salvarlo in memoria.");
		System.out.println("Digita la targa del veicolo:");
		input_aut = new Scanner(System.in);
		String risp = input_aut.nextLine();
		boolean trovato = false;
		if ((veicolo_di_stato != null) && (risp.equalsIgnoreCase(veicolo_di_stato.getTarga()))) {
			System.out.println("Questo veicolo e' gia' salvato in memoria.");
			return;
		}
		if (Veicolo.validaTarga(risp))
			for (Veicolo x : parco_mezzi)
				if (x.getTarga().equalsIgnoreCase(risp)) {
					veicolo_di_stato = x;
					trovato = true;
					System.out.println("Il veicolo targato " + x.getTarga() + " e' stato aggiunto allo stato.");
				}
		if (!trovato)
			System.out.println("Spiacente, la targa non e' corretta o il mezzo non e' di proprieta' dell'autonoleggio " + this.getNome() + ".");
	}
	
	//Metodo per cercare un veicolo nel parco mezzi e passarlo come argomento per la sua rimozione dal parco mezzi
	public void rimuoviVeicoloCerca() {
		if (parco_mezzi.size() == 0)
			System.out.println("Ancora nessun veicolo nel parco mezzi.");
		else {
		System.out.println("Vuoi rimuovere un veicolo dal parco mezzi. Qual e' la sua targa?");
		input_aut = new Scanner(System.in);
		String risp = input_aut.nextLine();
		boolean trovato = false;
		for (Veicolo x : parco_mezzi)
			if (risp.equals(x.getTarga())) {
				trovato = true;
				rimuoviVeicolo(x);
				break;
			}
		//Nel caso il mezzo non appartenza all'autonoleggio
		if (!trovato)
			System.out.println("Il mezzo non e' di questo autonoleggio o la targa e' errata.");
		}
	}
	
	//Metodo per la rimozione dei veicoli passati come argomento, che controlla se esistono prenotazioni e lo cancella
	//Viene richiamata anche la funzione trimToSize(), in caso di database molto popolato
	public void rimuoviVeicolo(Veicolo vecchio_veicolo) {
		if ((parco_mezzi.contains(vecchio_veicolo)) && (!vecchio_veicolo.getPrenotato())) {
			parco_mezzi.remove(vecchio_veicolo);
			vecchio_veicolo.setAutonoleggio(null);
			parco_mezzi.trimToSize();
			if (veicolo_di_stato != null)
				veicolo_di_stato = null;
			System.out.println("Mezzo rimosso dal parco macchine.");
		} else {
			System.out.println("Controlla che il mezzo non abbia prenotazioni in corso.");
		}
	}
	
	//Metodo per visualizzare o la disponibilita' dei mezzi in una precisa data o le prenotazioni di uno specifico
	//mezzo tramite uno switch. Si riuniscono qui per affinita' due punti distinti della traccia. Nel caso Data, 
	//si chiedono all'utente in input tre interi e crea un oggetto LocalDate. Una serie di catch gestiscono eventuali
	//eccezioni. Se non riscontrate, si richiama la funzione specifica. Nel caso Veicolo, si chiede la targa e si richiama
	//la funzione corrispondente. Questa funzione termina subito se il parco mezzi e' vuoto
	public void visualizzaPrenotazioniCerca() throws DateTimeException, ErroreFormatoData, InputMismatchException {
		if (parco_mezzi.size() == 0)
			System.out.println("Ancora nessun veicolo nel parco mezzi.");
		else {
			String risp;
			boolean trovato = false;
			do {
		System.out.println("Vuoi visualizzare le disponibilita' del parco mezzi per data o le \n\t prenotazioni di un veicolo? [D/V]");
		input_aut = new Scanner(System.in);
		risp = input_aut.nextLine();
			} while (risp.toUpperCase().charAt(0) != 'D' && risp.toUpperCase().charAt(0) != 'V');
			switch (risp.toUpperCase().charAt(0)) {
			//caso Data
			case 'D': System.out.println("Stai cercando le disponibilita' in base a una data." + 
					"\nInserisci giorno, mese e anno separati da spazi.");
				try {	
					int gg = input_aut.nextInt();
					int mm = input_aut.nextInt();
					int aa = input_aut.nextInt();
					input_aut.nextLine();
					LocalDate tmp = LocalDate.of(aa, mm, gg);
					LocalDate oggi = LocalDate.now();
					if (tmp.compareTo(oggi) < 0)
						throw new ErroreFormatoData("Stai cercando una data nel passato.");
					cercaPerData(tmp);
					trovato = true; break;
				} catch (ErroreFormatoData e) {
					System.out.println(e);
				} catch (DateTimeException e) {
					System.out.println(e);
				} catch (InputMismatchException e) {
					System.out.println(e);
				}; break;
			//caso Veicolo
			case 'V': System.out.println("Vuoi vedere le prenotazioni di un solo veicolo.\nInserisci la sua targa.");
					  String tar = input_aut.nextLine();
						for (Veicolo x : parco_mezzi)
						if (tar.equals(x.getTarga())) {
							trovato = true;
							visualizzaPrenotazioni(x);
							break;
						}; break;
			default: System.out.println("C'e' un errore con la richiesta.");
			}
		//Nel caso il veicolo non appartenga all'autonoleggio
		if (!trovato)
			System.out.println("La data inserita non e' corretta o non esistono veicoli corrispondenti.");
		}
	}
	
	//Questo metodo sviluppa tramite uno switch il motivo della disponibilita' per data, richiesta dalla traccia.
	//Un ulteriore switch chiede all'utente se vuole cercare le disponibilita' di furgoni o autovetture, dopodiche',
	//se la lista delle prenotazioni dei veicoli idonei non e' vuota, viene scandita con un ciclo for each per verificare
	//che non esistano prenotazioni con la data inserita. Un counter aumenta per ogni veicolo disponibile in quella data
	//e viene stampato in calce all'elenco dei mezzi ancora liberi al termine della ricerca
	public void cercaPerData(LocalDate data) {
		System.out.println("Vuoi cercare disponibilita' per furgoni o per autovetture? [F/A]");
		String risp2;
		boolean prenotato_giorno = false;
		int counter = 0;
		do {
			risp2 = input_aut.nextLine();
		} while (risp2.toUpperCase().charAt(0) != 'F' && risp2.toUpperCase().charAt(0) != 'A');
			switch (risp2.toUpperCase().charAt(0)) {
			//caso Furgone
			case 'F': for (Veicolo x : parco_mezzi) {
						if (x instanceof Furgone) {
							if (x.lista_prenotazioni.size() == 0) {
								System.out.println((Furgone)x);
								System.out.println();
								prenotato_giorno = true;
								counter++;
							} else
								for (Prenotazione p : x.lista_prenotazioni) {
								if (p.data_prenotazione.compareTo(data) == 0) {
								prenotato_giorno = true;
								//il ciclo si interrompe non appena la lista delle prenotazioni incontra un match
								break;
							}
						} if (!prenotato_giorno) {
						System.out.println((Furgone)x);
						System.out.println();
						counter++;
						}
						prenotato_giorno = false;
					}
			}; break;
			//caso Autovettura
			case 'A': for (Veicolo x : parco_mezzi) {
						if (x instanceof Autovettura) {
							if (x.lista_prenotazioni.size() == 0) {
								System.out.println((Autovettura)x);
								System.out.println();
								prenotato_giorno = true;
								counter++;
							} else
								for (Prenotazione p : x.lista_prenotazioni) {
								if (p.data_prenotazione.compareTo(data) == 0) {
								prenotato_giorno = true;
								//il ciclo si interrompe non appena la lista delle prenotazioni incontra un match
								break;
							}
						} if (!prenotato_giorno) {
					System.out.println((Autovettura)x);
					System.out.println();
					counter++;
					}
					prenotato_giorno = false;
				}
			}; break;
			default: System.out.println("Si e' verificato un errore."); 
			}
		System.out.print("Per il giorno inserito sono disponibili " + counter);
		if (counter != 1)
			System.out.println(" veicoli.");
		else
			System.out.println(" veicolo.");
	}
	
	//Metodo che stampa una lista delle prenotazioni inserite per il mezzo in argomento.
	//Uno switch gestito da input utente permette di ordinare le liste per nome o data.
	public void visualizzaPrenotazioni(Veicolo mezzo) {
		if (!mezzo.getPrenotato())
			System.out.println("Il veicolo non ha prenotazioni.");
		else if (mezzo.lista_prenotazioni.size() == 1) {
			for (int i = 0; i < mezzo.lista_prenotazioni.size(); i++) {
			Prenotazione p = mezzo.lista_prenotazioni.get(i);
			System.out.println(i + 1 + ": " + p.toString());
			}
		} else {
			System.out.println("Desideri ordinare le prenotazioni per nome utente o data?");
			System.out.println("[N] ome\t[D] ata");
			String risp;
			do {
				risp = input_aut.nextLine();
			} while (risp.toUpperCase().charAt(0) != 'N' && risp.toUpperCase().charAt(0) != 'D');
			switch (risp.toUpperCase().charAt(0)) {
			case 'N': SortNome ordina_nome = new SortNome();
			  		  Collections.sort(mezzo.lista_prenotazioni, ordina_nome); break;
			case 'D': SortData ordina_data = new SortData();
			  		  Collections.sort(mezzo.lista_prenotazioni, ordina_data); break;
			default: System.out.println("Input non corretto.");
			}
			for (int i = 0; i < mezzo.lista_prenotazioni.size(); i++) {
			Prenotazione p = mezzo.lista_prenotazioni.get(i);
			System.out.println(i + 1 + ": " + p.toString());
			}
		}
	}
	
	//Metodo che cerca un veicolo nel parco mezzi e lo passa come argomento della prenotazione vera e propria
	public void aggiungiPrenotazioneCerca() throws ErroreFormatoData {
		if (parco_mezzi.size() == 0)
			System.out.println("Ancora nessun veicolo nel parco mezzi.");
		else {
		System.out.println("Vuoi prenotare un veicolo. Qual e' la sua targa?");
		input_aut = new Scanner(System.in);
		String risp = input_aut.nextLine();
		boolean trovato = false;
		for (Veicolo x : parco_mezzi) {
			if (risp.equals(x.getTarga())) {
				trovato = true;
				aggiungiPrenotazione(x);
				SortData ordina_data = new SortData();
				Collections.sort(x.lista_prenotazioni, ordina_data);
				break;
			}
		}
		//Nel caso il veicolo non appartenga all'autonoleggio
		if (!trovato)
			System.out.println("Il mezzo non e' di questo autonoleggio o la targa e' errata.");
		}
	}
	
	//Metodo che aggiunge una prenotazione per il veicolo in argomento, verificando che effettivamente sia nel parco mezzi
	//(passaggio necessario perche' potrebbe essere invocata con un veicolo di stato come argomento) e invoca il costruttore
	//di default di un oggetto Prenotazione. Se la Prenotazione non esiste gia', verifica che si ottiene tramite una funzione
	//apposita, la Prenotazione viene inserita nella lista delle prenotazioni in dotazione a ogni Veicolo e questo viene
	//flaggato come prenotato per evitare operazioni di rimozione accidentali.
	public void aggiungiPrenotazione(Veicolo mezzo) throws NullPointerException, ErroreFormatoData, DateTimeException, InputMismatchException {
		try {
			if (parco_mezzi.contains(mezzo)) {
				Prenotazione tmp = new Prenotazione(mezzo);
				if (tmp.data_prenotazione == null)
					throw new ErroreFormatoData("Prenotazione non valida.");
				if (!esistePrenotazione(mezzo, tmp)) {
					mezzo.lista_prenotazioni.add(tmp);
					mezzo.setPrenotato(true);
					System.out.println("Prenotazione per il mezzo targato " + mezzo.getTarga() +
							" aggiunta correttamente.");
			} else
				System.out.println("Per questo giorno il mezzo targato " + mezzo.getTarga() +
						" e' gia' prenotato.");
			}
		} catch (NullPointerException e) {
			System.out.println("C'e' un errore con le date di prenotazione.");
		} catch (ErroreFormatoData e) {
			System.out.println(e);
		} catch (DateTimeException e) {
			System.out.println("Dati inseriti non validi.");
		} catch (InputMismatchException e) {
			System.out.println("Verifica che i dati inseriti siano numeri.");
		}
	}
		
	//Metodo che verifica l'esistenza di un oggetto prenotazione tramite il metodo compareTo.
	//Se la lista e' vuota la funzione termina immediatamente. Statico perche' usato anche in GUI
	public static boolean esistePrenotazione(Veicolo v, Prenotazione p_1) {
		if (v.lista_prenotazioni.size() == 0)
			return false;
		else 
			for (Prenotazione p_2 : v.lista_prenotazioni)
				if (p_2.data_prenotazione.compareTo(p_1.data_prenotazione) == 0)
					return true;
		return false;
	}
	
	//Metodo per cercare un veicolo dal parco mezzi per passarlo come argomento alla funzione che
	//rimuovera' una prenotazione dalla lista integrata al veicolo
	public void rimuoviPrenotazioniCerca() {
		System.out.println("Vuoi rimuovere una prenotazione da un veicolo.\nQual e' la sua targa?");
		input_aut = new Scanner(System.in);
		String risp = input_aut.nextLine();
		boolean trovato = false;
		for (Veicolo x : parco_mezzi) {
			if (risp.equals(x.getTarga())) {
				trovato = true;
				rimuoviPrenotazioni(x);
				SortData ordina_data = new SortData();
				Collections.sort(x.lista_prenotazioni, ordina_data);
				break;
			}
		}
		if (!trovato)
			System.out.println("Il mezzo non e' di questo autonoleggio o la targa e' errata.");
	}
	
	//Metodo che, preso, un veicolo come argomento, ne stampa la lista delle prenotazioni (di
	//default ordinate per data). In input l'utente potra' scrivere il numero della prenotazione
	//da rimuovere, che verra' pescata direttamente dall'indice dell'ArrayList (opportunamente diminuita)
	public void rimuoviPrenotazioni(Veicolo v) {
		if (v.lista_prenotazioni.size() > 0) {
			for (int i = 0; i < v.lista_prenotazioni.size(); i++) {
				System.out.println((i + 1) + ". " + v.lista_prenotazioni.get(i).toString());
			}
			System.out.println("Scrivi il numero della prenotazione da rimuovere:");
			input_aut = new Scanner(System.in);
			int rimuovere = input_aut.nextInt();
			try {
			v.lista_prenotazioni.remove(rimuovere - 1);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
			} System.out.println("Prenotazione rimossa.");
			if (v.lista_prenotazioni.size() == 0)
				v.setPrenotato(false);
		} else {
			System.out.println("Non esistono prenotazioni per questo veicolo.");
		}
	}
	
	//Metodo per cercare un veicolo dal parco mezzi per passarlo come argomento alla funzione che
	//modifichera' i parametri di un veicolo
	public void modificaDatiCerca() {
		if (parco_mezzi.size() == 0)
			System.out.println("Ancora nessun veicolo nel parco mezzi.");
		else {
		System.out.println("Vuoi modificare i dati di un veicolo. Qual e' la sua targa?");
		input_aut = new Scanner(System.in);
		String risp = input_aut.nextLine();
		boolean trovato = false;
		for (Veicolo x : parco_mezzi)
			if (risp.equals(x.getTarga())) {
				trovato = true;
				modificaDatiVeicolo(x);
				break;
			}
		if (!trovato)
			System.out.println("Il mezzo non e' di questo autonoleggio o la targa e' errata.");
		}
	}
	
	//Metodo che chiede all'utente quale variabile del veicolo modificare e gestisce la
	//risposta con uno switch. Il metodo si potrebbe agilmente estendere anche alle 
	//variabili delle sottoclassi di Veicolo (ovvero Autovettura e Furgone,
	//vd. metodo this.visualizza()), ma per semplicita' si e' scelto di mantenere le modifiche
	//per la sola superclasse Veicolo
	public void modificaDatiVeicolo(Veicolo v) {
		System.out.println("\nCosa vuoi modificare di questo veicolo?"
				+ "\n[T] arga\t[M] odello\t");
		input_aut = new Scanner(System.in);
		char risposta = input_aut.nextLine().charAt(0);
		switch (Character.toUpperCase(risposta)) {
		//caso targa
		case 'T': {System.out.println("Scrivi la nuova targa.");
				String tar = input_aut.nextLine();
				v.setTarga(tar);
				}; break;
		//caso modello
		case 'M': {System.out.println("Scrivi il nuovo modello.");
				String mod = input_aut.nextLine();
				v.setModello(mod);
				}; break;
		default: System.out.println("Input non valido.");
		}
	}

	//Overriding del metodo toString()
	public String toString() { 
		if (this.parco_mezzi.size() == 1)
			return "Questo autonoleggio si chiama \"" + nome + "\" e ha 1 vettura a disposizione.";
		else
			return "Questo autonoleggio si chiama \"" + nome + "\" e ha " + this.parco_mezzi.size() + " vetture a disposizione.";
	}
	
	//Metodo che permette di visualizzare un sommario dei veicoli (per intero o registrati per tipologia)
	//e ne invoca il corretto metodo toString(). Se si scelgono entrambe le tipologie viene stampato
	//anche un riepilogo dell'autonoleggio
	public void visualizza() {
		if (parco_mezzi.size() == 0)
			System.out.println("\nNon ci sono ancora vetture registrate.");
		else {
		System.out.println(this.toString());
		System.out.println("\nVuoi visualizzare tutto il parco mezzi o solo una tipologia di veicolo?"
				+ "\n[T] utto\t[F] urgoni\t[A] utovetture");
		input_aut = new Scanner(System.in);
		char risposta = input_aut.nextLine().charAt(0);
		int i = 0;
		int j = 1;
		switch (Character.toUpperCase(risposta)) {
		//caso Tutto
		case 'T': for (Veicolo x : parco_mezzi) {
						System.out.println("\n" + j + ".");
						System.out.println(x);
						i++;
						j++;
				}; System.out.println("\n" + this.toString()); break;
		//caso Autovetture
		case 'A': for (Veicolo x : parco_mezzi) {
					if ((x instanceof Autovettura)) {
						System.out.println("\n" + j + ".");
						System.out.println((Autovettura)x);
						i++;
						j++;
						}
				if (i == 0)
					System.out.println("L'autonoleggio non ha Autovetture al momento.");
				} break;
		//caso Furgoni
		case 'F': for (Veicolo x : parco_mezzi) {
					if ((x instanceof Furgone)) {
						System.out.println("\n" + j + ".");
						System.out.println((Furgone)x);
						i++;
						j++;
					}
					if (i == 0)
						System.out.println("L'autonoleggio non ha Furgoni al momento.");
				} break;
		default: System.out.println("Input non corretto."); this.visualizza();
		}
	}
	}
}
