package aggiornamento;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.time.SUTime.TimeUnit;
import pipeline.istancepipeline;

public class ControlloreAggiornamenti {

	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static void controlloaggiornamenti() throws Exception{
		StanfordCoreNLP pipeline = istancepipeline.istancePipeline();
		System.out.println("Fine istanziamento pipeline");
		// controllo quali sono i file da annotare
		final File folder = new File("Pages");
		//VARIABILE SETTATA A SI QUANDO IL CRAWLER INZIIALE SMETTE DI SALVARE PAGINE (IN UN CONTESTO DI QUANTITa LIMITATE DI PAGINE)
		File[] listaFile = folder.listFiles();
		
		while(true) {
	
			for (int i = 0; i < listaFile.length; i++) {
				File file = listaFile[i];
				if (file.isFile() && file.getName().endsWith(".json")) {
					
					JSONParser parser = new JSONParser();
				    String nomefile = file.getName();
				    int flagAggiornamento = 0;
			        try {     
			        	
			            Object obj = parser.parse(new FileReader("Pages/"+nomefile));
			            JSONObject jsonObject =  (JSONObject) obj;
			            flagAggiornamento= ((Number) jsonObject.get("flag")).intValue();

			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (ParseException e) {
			            e.printStackTrace();
			        }
					if (flagAggiornamento==1) { //il file � stato aggiornato dal crawler aggiornatore..aggiornare le triple di quel file sula KB
						//System.out.println("il flag è a 1");
						AggiornamentoTriple.updator(nomefile,pipeline);
						System.out.println("Le triple riguardanti "+nomefile+ " sono state aggiornate");
					}else {
					System.out.println("Il file "+nomefile+" non contiene aggiornamenti");
					}
					
					//TripleExtractionJson.estrai("Pages/" + file.getName(),pipeline );
	                 
					// TripleExtraction.estrai(file.getName());
				}
			}
			//Spleep X minuti .. modificabile per magari UN MESE
			System.out.println("Riposo 15 m");

	        Thread.sleep(15 * 60 * 1000);

		}//chiusura while
		
		
		
		
	}
	
	
	
	
	
	
}
