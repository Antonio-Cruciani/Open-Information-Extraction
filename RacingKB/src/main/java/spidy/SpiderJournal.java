
package spidy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.*;

import javax.swing.text.html.parser.DTD;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Strutture.TupleTable;

import java.io.FilePermission;

import java.security.AccessController;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Connection connection = Jsoup.connect("http://www.example.com")
Document htmlDocument = connection.get();
Elements linksOnPage = htmlDocument.select("a[href]");
*/
public class SpiderJournal {

	int i = 0;

	// Fields
	private static final int MAX_PAGES_TO_SEARCH = 50;
	// pagesVisited è di tipo Set perché, per definizione, in un set
	// non abbiamo duplicati
	private Set<String> pagesVisited = new HashSet<String>();
	// pageToVisit è di tipo lista perché ogni volta che il crawler visita
	// una pagina vogliamo anche collezionare tutti gli url contenuti nella pagina
	// e appenderli a fine lista. Con questo approccio possiamo utilizzare un
	// approccio
	// BFS anzichè DFS, il quale è più consistente.
	private List<String> pagesToVisit = new LinkedList<String>();

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private Document htmlDocument;
	/**
	 * @param url        - Punto di inizio dello spider
	 * @param searchWord - Parola che stiamo cercando
	 */
	ArrayList<String> Table = new ArrayList();
	// String fileName = "/Users/antonio/Pages/table.txt";
	String TableLine = null;

	public void search(String url, String[] WordList, int WordListLenght) {
		Table table = new Table();

		String searchWord;
		SpiderLegJournal leg = new SpiderLegJournal();
		int j, k, c;
		String path = "Pages/";
		TupleTable tupla = new TupleTable(pagesVisited, 0);
		j = 0;
		c = 0;
		tupla = table.LoadTable(path);
		this.pagesVisited = tupla.getPagesVisited();
		i = tupla.getI();
		for (String page : tupla.getPagesVisited()) {
			Table.add(page);
		}

		while (j <= MAX_PAGES_TO_SEARCH) {
			k = 0;
			while (k < (WordListLenght - 1)) {
				searchWord = WordList[k];
				String currentUrl;
				// SpiderLeg leg = new SpiderLeg();
				// URL url_1;
				if (this.pagesToVisit.isEmpty()) {
					currentUrl = url;
					this.pagesVisited.add(url);
				} else {
					currentUrl = this.nextUrl();
				}
				leg.crawl(currentUrl); // Bel fritto misto. Guardare il metodo crawl in
										// SpiderLeg
				boolean success = leg.searchForWord(searchWord);
				if (success) {
					try {
						Connection connection = Jsoup.connect(currentUrl).userAgent(USER_AGENT);
						Document doc = Jsoup.connect(currentUrl).get();
						Table.add(currentUrl);
						Table.add("\n");
						String inputLine;

						i += 1;
						if (!(currentUrl.equals("https://www.motorsport.com/f1/news/")
								&& currentUrl.equals("https://www.bbc.com/sport/formula1"))) {

							Elements paragraphs = doc.body().select("p");

							String finale = "";
							for (Element p : paragraphs) {

								inputLine = p.text();
								finale = finale + " " + inputLine;

							}

							ScriviJson.scrivi(i, "Giornale Sportivo", currentUrl, finale);

							System.out.println("Done");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(String.format("**Successo** Parola %s trovata in %s", searchWord, currentUrl));
					// break; te fermi
				}

				this.pagesToVisit.addAll(leg.getLinks());
				k += 1;
			}
			j += 1;
			System.out.println(String.format("**Fatto** Visitato %s web page(s)", this.pagesVisited.size()));

		}
		// k+=1;

		// }
		// Scrivo la tabella
		table.WriteTable(Table, i, path);

		System.out.println("Fine Ricerca");

	}

	// Metodo per determinare quale pagina visitare evitando ripetizioni
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}

}
