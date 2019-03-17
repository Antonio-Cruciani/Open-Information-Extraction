package spidy;

import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.instrument.Instrumentation;

public class ScriviJson {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	/*
	 * Metodo statico che viene utilizzato per scrivere il file JSON
	 * 
	 */

	public static void scrivi(int id, String fonte, String URL, String testo) throws IOException {

		JSONObject obj = new JSONObject();

		String a;
		LocalDate localDate;
		DateTimeFormatter formatter;
		String FormattedString;
		// Creo l'oggetto inserendo i dati :
		/*
		 * id -- rappresenta l'id dell'oggetto fonte -- rappresenta la fonte dalla quale
		 * prendiamo il documento essa può avere due valori: Wikipedia o Giornale
		 * Sportivo url -- rappresenta l'url della pagina dalla quale prendiamo il
		 * documento flag -- rappresenta un flag che indica se il file è stato
		 * aggiornato o meno data -- indica la data dell'ultimo download testo --
		 * contiene il corpo del documento
		 */
		long stringSizeInBytes = testo.getBytes().length;
		long stringSizeInKB = stringSizeInBytes/1024;
		if(stringSizeInKB>1) {
		obj.put("id", id);
		obj.put("fonte", fonte);
		a = URLEncoder.encode(URL, "UTF-8");
		obj.put("url", a);
		obj.put("flag", 0);
		localDate = LocalDate.now();
		formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy");
		FormattedString = localDate.format(formatter);
		obj.put("data", FormattedString);
		obj.put("testo", testo);

		// Scrivo il JSON nella cartella
		try (FileWriter filo = new FileWriter("Pages/page_" + id + ".json")) {
			filo.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
		/*
		 * Se il file pesa poco lo cancello in quanto (motivato da test empirici) non
		 * contiene informazioni utili
		 */

		//File fcheck = new File("/Pages/page_" + id + ".json");
		//long fileSizeInBytes = fcheck.length();
		//long fileSizeInKB = fileSizeInBytes / 1024;

		//System.out.println(fileSizeInKB);
		//if (fileSizeInKB < 1.1) {
		//	fcheck.delete();
		//}
		}
	}

	/*
	 * Metodo statico che viene utilizzato per effettuare l'aggiornamento dei file
	 * JSON confronto la data dell'ultimo aggiornamento della pagina web con la data
	 * presente nel file JSON (data del download), se la data dell'ultima modifica è
	 * successiva alla data del download allora aggiorno il documento nella
	 * directory
	 */
	public static void aggiorna(String path) throws InterruptedException {

		String a;
		LocalDate localDate;
		DateTimeFormatter formatter;
		String FormattedString;
		JSONArray Ja = new JSONArray();
		JSONParser parser = new JSONParser();
		File dir = new File(path);
	while(true) {

		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File file : directoryListing) {
				if (file.isFile() && file.getName().endsWith(".json")) {
					JSONObject objapp = new JSONObject();
					try {
					
						String finale = "";
						String namefile = file.getName();
						System.out.println("Sto analizzando:" + namefile);
						Object obj = parser.parse(new FileReader("Pages/" + namefile));

						JSONObject jsonObject = (JSONObject) obj;
						if (((Long) jsonObject.get("flag") == 0) || ((Long) jsonObject.get("flag") == 1)) {

							String data_modifica_file = (String) jsonObject.get("data");
							Date date_download = new SimpleDateFormat("dd-LLL-yyyy").parse(data_modifica_file);
							//formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy");
							System.out.println("Data download " + date_download);
							String url_downloaded = (String) jsonObject.get("url");
							url_downloaded = URLDecoder.decode(url_downloaded, "UTF-8");
							URL url2 = new URL(url_downloaded);
							URLConnection connection_date = url2.openConnection();
							String data_modifica_pagina = connection_date.getHeaderField("Last-Modified");
							SimpleDateFormat format = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
							// LocalDate.parse(appo, formatter);
							Date data_modifica_pag_parsato;
							//String localD;
							//Controllo sul responde HTTP - last modified, se non c'� risposta si assume che ci sia aggiornamento da fare.
							if (data_modifica_pagina == null) {
								System.out.println("Nessuna risposta su HTTP_Last_Modified..aggiorniamo il file");
								localDate = LocalDate.now();
								formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy");
								FormattedString = localDate.format(formatter);
								//assumiamo che la pagina sia stata moficata in questo momento
								data_modifica_pag_parsato = new SimpleDateFormat("dd-LLL-yyyy").parse(FormattedString);
								// System.out.println(d);

							} else {
								System.out.println("******************************************************************");

								data_modifica_pag_parsato = format.parse(data_modifica_pagina);
							}

							if (data_modifica_pag_parsato.after(date_download)) {

								try {

									Connection connection = Jsoup.connect(url_downloaded).userAgent(USER_AGENT);
									Document doc = Jsoup.connect(url_downloaded).get();
									
									if (((String) jsonObject.get("fonte")).equals("Wikipedia")) {
										String inputLine;

										Elements paragraphs = doc.body().select("p");
										for (Element p : paragraphs) {
											String result = "";
											inputLine = p.text();
											int f = 0;
											int nparenthesis = 0;
											// da correggere
											for (int i = 0; i < inputLine.length(); i++) {
												if (inputLine.charAt(i) != '[' && nparenthesis == 0) { 
													result += inputLine.charAt(i);
												} else if (inputLine.charAt(i) == '[') {
													// f=1;
													nparenthesis += 1;
												} else if (inputLine.charAt(i) == ']') {
													// f=0;
													nparenthesis -= 1;
												}
											}

											finale = finale + " " + result;

										}

									}

								} catch (MalformedURLException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}

								// Modifico il file JSON rimuovendo i vecchi dati ed aggiungendo quelli
								// aggiornati
								jsonObject.remove("testo");
								jsonObject.remove("flag");
								jsonObject.put("testo", finale);
								jsonObject.put("flag", 1);
								localDate = LocalDate.now();
								formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy");
								FormattedString = localDate.format(formatter);
								jsonObject.remove("data");
								jsonObject.put("data", FormattedString);

								try (FileWriter file1 = new FileWriter(
										"Pages/page_" + ((Long) jsonObject.get("id")) + ".json")) {
									file1.write(jsonObject.toJSONString());
									System.out.println("Successfully updated json object to file...!!");

								}

							}
						}
					} catch (Exception e) {
						System.out.println(file.getName());
						e.printStackTrace();

					}
				}//chiusura if_file.isFILE
				
				
			}
		}
		
		System.out.println("Riposo 15 m del Crawler di aggiornamento pagine");

        Thread.sleep(15 * 60 * 1000);
	} //chiusura WHILE INFITINO
	}

}
