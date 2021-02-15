package Autonoleggio;

import java.util.Comparator;
/**Semplice Comparator per ordinare le prenotazioni per data*/
public class SortData implements Comparator<Prenotazione> {
	
	//Sfrutta il parametro LocalDate di Prenotazione per ordinare due oggetti
	public int compare(Prenotazione p1, Prenotazione p2) {
		if (p1.data_prenotazione.compareTo(p2.data_prenotazione) < 0)
			return -1;
		else if (p1.data_prenotazione.compareTo(p2.data_prenotazione) > 0)
			return 1;
		else
			return 0;
	}

}
