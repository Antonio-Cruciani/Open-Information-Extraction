package aggiornamento;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class AggiornamentoTriple {

	
	 @SuppressWarnings("unchecked")
	public static void updator(String nomefile, StanfordCoreNLP pipeline) throws RDFParseException, UnsupportedRDFormatException, IOException, ParseException {
		
		 // deve calcolare le nuove triple dal file pages_qualcosa che � stato aggiornato,   poi le passa all aggiornatore
		 //della classe AggiornamentoRDF

			JSONParser parser = new JSONParser();
			 String dati = new String();
			 String id_file = new String();
			 String contesto= new String();
	        try {     
	        	
	        	System.out.println(nomefile);
	            Object obj = parser.parse(new FileReader("Pages/"+nomefile));
	            JSONObject jsonObject =  (JSONObject) obj;
	           dati = (String) jsonObject.get("testo");
	           //System.out.println("dati"+dati);
	           id_file = String.valueOf( jsonObject.get("id"));
	           contesto= "pages_"+id_file+".json";
	            //System.out.println(dati);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			Annotation doc = new Annotation(dati);
			pipeline.annotate(doc);

			int sentNo = 0;
			// Lista che conterr� tutte le triple di un doc. filtrate secondo Name Entity
			Collection<RelationTriple> AlltriplesNE = new ArrayList<RelationTriple>();
			//Collection<RelationTriple> AlltripleSporche = new ArrayList<RelationTriple>();
			// per ogni sentence recuperata nel documento
			for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
				//System.out.println("Sentence #" + ++sentNo + ": " + sentence.get(CoreAnnotations.TextAnnotation.class));
				// recupero triple relazionali da una sentence
				Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

				List<RelationTriple> tripleswithNE = triples.stream()
						// mcontrollo su soggetto
					
						.filter(triple -> triple.subject.stream().noneMatch(token -> "O".equals(token.ner()))).filter(triple ->triple.confidence>0.7)
						// controllo su oggetto
						//.filter(triple -> triple.object.stream().noneMatch(token -> "O".equals(token.ner())))
						// conversione in lista
						.collect(Collectors.toList());
				// Aggiunta delle triple filtrate di una sentence alla lista con tute le triple
				// per questo doc.

				AlltriplesNE.addAll(tripleswithNE);
				//AlltripleSporche.addAll(triples);
			}

			// chiamata al metodo creaRDF passando le triple trovate nel doc. il metodo si
			// occupa secondo opportuni controlli di aggiungerle al RDF
			AggiornamentoRDF.updateFileRDF(AlltriplesNE,contesto);
			
			
			 Object obj = parser.parse(new FileReader("Pages/"+nomefile));
	           JSONObject jsonObjectreplace =  (JSONObject) obj;
			jsonObjectreplace.remove("flag"); 
			jsonObjectreplace.put("flag", 0); 
			try (FileWriter filo = new FileWriter("Pages/"+nomefile)) {
    			filo.write(jsonObjectreplace.toJSONString());
    			System.out.println("Successfully Copied JSON Object to File...");
    			System.out.println("\nJSON Object: " + obj);
    		}
			
	 }
	
}
