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

public class SpiderLegJournal {

	

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
			System.out.println(url);
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
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
			Elements linksOnPage;
			if (url.equals("https://www.motorsport.com/f1/news/")) {
				// System.out.println("PIPPO");
				linksOnPage = htmlDocument.select("div.ms-page-content").select("a[href]");
				// System.out.println(linksOnPage.text());
			} else if (url.equals("https://www.bbc.com/sport/formula1")) {
				linksOnPage = htmlDocument.select("div.spacing-v2").select("a[href]");
			} else {
				linksOnPage = htmlDocument.select("a[href]");
			}

			System.out.println("Trovato (" + linksOnPage.size() + ") links");
			for (Element link : linksOnPage) {
				String lino = link.absUrl("href").toString();
				// System.out.println(link.absUrl("href").toString());

				if (lino.indexOf('#') < 0) {
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
