package Autonoleggio;

import java.io.*;
import java.time.DateTimeException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**Questa e' la classe che ospita il metodo main della versione testuale del gestore
 * dell'autonoleggio. Consiste di 3 parti: un avvio, un ciclo do-while principale e
 * una chiusura. Nell'avvio viene creata un'istanza di Autonoleggio, che eventualmente
 * carica un file con un parco mezzi esistente, e definisce l'operatore che sta lavorando
 * al gestore. Il ciclo do-while permette di interagire con tutte le funzioni disponibili,
 * richieste dal progetto o meno, chiedendo all'utente l'input di un solo carattere che attivera'
 * la funzione corrispondente in uno switch. All'uscita il gestore saluta l'operatore e sovrascrive
 * automaticamente il parco mezzi dell'autonoleggio */
public class AutonoleggioTestConsole {
	
	public static void main(String[] args) throws Exception, InputMismatchException, NullPointerException, DateTimeException, ErroreFormatoData, FileNotFoundException, ClassNotFoundException, IOException {
		//Scanner del gestore
		Scanner input_main = new Scanner(System.in);
		
		System.out.println("Avvio...");
		//Costruttore dell'autonoleggio, inizializzato con il nome CarPisa
		Autonoleggio autonol_avvio = new Autonoleggio("CarPisa");
		
		char risposta = 'f';
		//Il counter serve per salutare l'operatore solo al primo avvio del gestore o al cambio
		int counter = 0; 
		//Il veicolo di stato (o veicolo salvato in memoria) viene settato null a ogni avvio
		autonol_avvio.veicolo_di_stato = null;
		System.out.println("Benvenuto al gestore dell'autonoleggio " + autonol_avvio.nome + ".");
		if (autonol_avvio.operatore.equals("Nessun operatore definito")) {
			System.out.println("Chi sta utilizzando il gestore? Scrivi il tuo nome:");
			String risposta_string = input_main.nextLine();
			autonol_avvio.operatore = risposta_string;
		}
		do {
			if (counter == 0)
				System.out.print("\nCiao " + autonol_avvio.getNomeOperatore() + ". ");
			System.out.print("Cosa vuoi fare?");
			if (autonol_avvio.veicolo_di_stato != null)
				System.out.println("\t\t\t\tVEICOLO DI STATO PRESENTE {" + autonol_avvio.veicolo_di_stato.getTarga() + "}");
			else
				System.out.println();
			System.out.println();
			//Richiama la funzione per immatricolare un veicolo, Furgone o Autovettura
			System.out.print("  [I] mmatricola una vettura");
			//Inserisce una prenotazione per un determinato veicolo
			System.out.println("\t  [P] renota un veicolo");
			//Rimuove un veicolo dal parco mezzi se non prenotato
			System.out.print("  [R] imuovi una vettura");
			//Cancella una determinata Prenotazione da un determinato veicolo
			System.out.println("\t  [C] ancella prenotazione");
			//Visualizza la lista dei mezzi nell'autonoleggio con un minimo grado di granularita'
			System.out.print("  [L] ista mezzi disponibili");
			//Visualizza le prenotazioni di un veicolo o la disponibilita' per data di tutto il parco mezzi
			System.out.println("\t  [V] isualizza prenotazioni veicolo/data");
			System.out.println();
			//Permette di invocare la funzione che aggiunge un veicolo di stato
			System.out.print("  [A] aggiungi veicolo di stato");
			//Permette di impostare un altro operatore
			System.out.println("\t  [S] etta operatore");
			//Elimina il veicolo di stato salvato in memoria
			System.out.print("  [E] limina veicolo di stato");
			//Modifica targa o modello del veicolo
			System.out.println("\t  [M] odifica dati veicolo");
			System.out.println();
			//Esce dal programma e salva
			System.out.println("  [T] ermina programma");
			System.out.println();
			System.out.print("Digita una scelta:");
			
			try {
			risposta = input_main.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Il valore inserito non e' corretto");
			}
			//Il counter aumenta cosi' da non salutare di nuovo l'operatore
			counter++;
			//Ogni funzione dello switch verifica che non vi sia un veicolo di stato.
			//Se esiste, bypassa la funzione per la ricerca del veicolo e lo passa come
			//argomento diretto del metodo scelto
			switch (Character.toUpperCase(risposta)) {
			case 'I': autonol_avvio.immatricolaVeicolo(); break; 
			case 'P': if (autonol_avvio.veicolo_di_stato == null) 
						autonol_avvio.aggiungiPrenotazioneCerca();
					else
						autonol_avvio.aggiungiPrenotazione(autonol_avvio.veicolo_di_stato); break;
			case 'R': if (autonol_avvio.veicolo_di_stato == null) 
						autonol_avvio.rimuoviVeicoloCerca();
					  else
						  autonol_avvio.rimuoviVeicolo(autonol_avvio.veicolo_di_stato); break;  
		    case 'C': if (autonol_avvio.veicolo_di_stato == null) 
		    			autonol_avvio.rimuoviPrenotazioniCerca();
		    		  else
		    			  autonol_avvio.rimuoviPrenotazioni(autonol_avvio.veicolo_di_stato); break; 						  
			case 'L': autonol_avvio.visualizza(); break; 
			case 'V': if (autonol_avvio.veicolo_di_stato == null) 
						  autonol_avvio.visualizzaPrenotazioniCerca();
					  else
						  autonol_avvio.visualizzaPrenotazioni(autonol_avvio.veicolo_di_stato); break;
			case 'A': autonol_avvio.ricercaVeicolo(); break; 
			case 'S': { System.out.println("Stai per cambiare operatore. Scrivi il nuovo utente:");
				String tmp_nome = input_main.nextLine();
				autonol_avvio.setNomeOperatore(tmp_nome);
				//Il counter si resetta per salutare il nuovo operatore
				counter = 0;
				}; break;
			case 'E': autonol_avvio.veicolo_di_stato = null; System.out.println("Veicolo di stato rimosso."); break;
			case 'M': if (autonol_avvio.veicolo_di_stato == null)
						autonol_avvio.modificaDatiCerca();
					  else
						autonol_avvio.modificaDatiVeicolo(autonol_avvio.veicolo_di_stato); break;
			}
			//Una riga per separare visivamente il gestore dalla funzione
			System.out.println("\n-------------------");

		} while (Character.toUpperCase(risposta) != 'T');
		//Si chiude l'input principale e si sovrascrive il salvataggio
		input_main.close();
		try {
			ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(autonol_avvio.nome_percorso)));
			file_output.writeObject(autonol_avvio.parco_mezzi);
			file_output.close();
			System.out.println("File salvato correttamente.");
		} catch (IOException e) {
			System.out.println("ERRORE di I/O");
			System.out.println(e);
		} catch (NullPointerException e) {
			System.out.println(e);
		}
		System.out.println("Grazie per aver utilizzato il gestore, " + autonol_avvio.getNomeOperatore() + ". Buona giornata!");
	}
}
