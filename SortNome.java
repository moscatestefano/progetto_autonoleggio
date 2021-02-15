package Autonoleggio;
 
import java.util.Comparator;
/**Semplice Comparator per ordinare le prenotazion in base al nome*/
public class SortNome implements Comparator<Prenotazione> {

	//Sfrutta la comparazione tra due stringhe per ordinare gli oggetti Prenotazione
	public int compare(Prenotazione p1, Prenotazione p2) {
		if (p1.richiedente.compareToIgnoreCase(p2.richiedente) < 0)
			return -1;
		else if (p1.richiedente.compareToIgnoreCase(p2.richiedente) > 0)
			return 1;
		else
			return 0;
	}

}
