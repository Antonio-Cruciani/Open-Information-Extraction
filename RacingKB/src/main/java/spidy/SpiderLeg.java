package spidy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//import javax.swing.text.Document;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.String;

public class SpiderLeg {
	/*
	 * public void crawl(String nextURL); // Dato un URL fa una HTTP Request per una
	 * pagina web public boolean searchForWord(String word); // Cerca una specifica
	 * parola nel testo public List<String> getLinks(); // Ritorna una lista di
	 * tutti gli URL nella pagina private List<String> links = new
	 * LinkedList<String>(); // Just a list of URLs private Document htmlDocument;
	 * // This is our web page, or in other words, our document
	 */

	// USER_AGENT è un agente virtuale che viene interpretato dal web server come un
	// browser normale.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	Integer i = 0;

	/**
	 * @param url - URL Da visitare
	 * @return risultato del crawling
	 */
	public boolean crawl(String url) {

		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			Elements linksOnPage = new Elements();
			this.htmlDocument = htmlDocument;
			if (connection.response().statusCode() == 200) // 200 è lo status code dell'HTTP
															// che indica che tutto va bene.
			{
				System.out.println("\n**Visita** Pagina ricevuta a " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Fallimento** Catturato qualcosa non in HTML ");
				return false;
			}
			// System.out.println(url);

			// if (url.equals("https://en.wikipedia.org/wiki/Category:Formula_One")) {
			// linksOnPage.select("div.mw-content-ltr").select("a[href]");
			// }else {
			linksOnPage = htmlDocument.select("div.mw-content-ltr").select("a[href]");
			// }

			System.out.println("Trovato (" + linksOnPage.size() + ") links");

			for (Element link : linksOnPage) {

				String lino = link.absUrl("href").toString();
				// System.out.println(link.absUrl("href").toString());

				// System.out.println(link);

				String img = "jpg";
				String imgpeg = "png";
				// non funziona per capire se ci sono o meno jpg nei links
				if (lino.indexOf('#') < 0 || (!(lino.toLowerCase().contains(img.toLowerCase())))
						|| !(lino.toLowerCase().contains(imgpeg.toLowerCase()))) {
					this.links.add(link.absUrl("href"));
				}

			}

			

			return true;
		} catch (IOException ioe) {
			// HTTP request fallita
			return false;
		}
	}

	/**
	 * Effettua una ricerca nel body del documento HTML catturato.
	 * 
	 * @param searchWord - Parola da cercare nel testo
	 * @return risultato della ricerca
	 */
	public boolean searchForWord(String searchWord) {
		// Defensive coding. This method should only be used after a successful crawl.
		if (this.htmlDocument == null) {
			System.out.println("ERRORE! richiama crawl() prima di effettuare l'analisi sul documento!!");
			return false;
		}
		System.out.println("Sto cercando la parola  " + searchWord + "...");
		String bodyText = this.htmlDocument.body().text();
		// System.out.println(bodyText);
		return bodyText.toLowerCase().contains(searchWord.toLowerCase());
	}

	public List<String> getLinks() {

		return this.links;
	}

}
