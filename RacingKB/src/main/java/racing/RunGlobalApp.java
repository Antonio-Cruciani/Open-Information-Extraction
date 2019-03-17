package racing;

import java.io.File;
import java.util.Scanner;

import aggiornamento.ThreadAggkb;
import estrattore.ThreadExtraction;
import spidy.ThreadSpiderAvv;
import spidy.TreadaAggFile;

public class RunGlobalApp {

	public static void main(String[] args) throws Exception {
		// Scanner per leggere l'input
		Scanner scanner = new Scanner(System.in);
		

		boolean rispostaattesa = false;
		while (rispostaattesa != true) {
			System.out.print("Vuoi avviare in modalità 'prima volta'?(si/no): ");

			// Prendiamo l'input
			String checkcrawler = scanner.next();
			if (checkcrawler.equals("si")) {

				System.out.print("Avvio modalità Crawling & Extraction... ");
				//Creaiamo la cartella Pages dove verrano scaricate le pagine 
				readFile.creaCartella();
					//Partono i trhead del Crawler e il thread che estrae triple
				ThreadSpiderAvv ThreadAvvioSpider = new ThreadSpiderAvv();
				ThreadExtraction ThreadEstrazione = new ThreadExtraction();
				Thread nuovoThread = new Thread(ThreadAvvioSpider);
				Thread nuovoThread2 = new Thread(ThreadEstrazione);

				nuovoThread.start();
				nuovoThread2.start();
				scanner.close();
				rispostaattesa = true;

			} else if (checkcrawler.equals("no")) {
			
				System.out.print("Avvio in modalità Aggiornamento... ");
				// Partono i Thread per argiornare i documenti e per aggiornare la Kb
				ThreadAggkb treadAggiornaKb = new ThreadAggkb();
				TreadaAggFile treadAggiornaFile = new TreadaAggFile();
				Thread nuovoThread = new Thread(treadAggiornaKb);
				Thread nuovoThread2 = new Thread(treadAggiornaFile);

				nuovoThread.start();
				nuovoThread2.start();

				rispostaattesa = true;

				scanner.close();

			} else {
				System.out.println("Input non atteso...riprova (si/no) ");

			}

		}

	}
}