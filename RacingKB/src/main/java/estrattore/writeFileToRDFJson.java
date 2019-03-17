package estrattore;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import edu.stanford.nlp.ie.util.RelationTriple;

public class writeFileToRDFJson{

	
	 static String kbfile = "KGracing";
	// metodo che controlla se l'rdf è gia esistente
	public static boolean checkRDFexist() throws RDFParseException, UnsupportedRDFormatException, IOException {

		
		File file = new File(kbfile);
		if (file.exists()) {
			
			System.out.println("exist old RDF ritorno true");

			return true;
		}
		return false;
	}

	
	// metodo che si occupa di gestire la creazione / modifica del file RDF
	// contenente le triple
	public static void creaRDF(Collection<RelationTriple> triples,String contesto) throws Exception {

		// SE ESISTE GIA RDF Legge e crea il model a cui aggiunge altre triple e
		// riscrive il file
		if (checkRDFexist()) {
			InputStream input = new FileInputStream(kbfile);
			Model existentmodel = Rio.parse(input, "", RDFFormat.TRIG);
			System.out.println("SCRIVO LE NUOVE TRIPLE");
			System.out.println("il contesto è "+contesto);

			ModelBuilder builder = new ModelBuilder();
			builder.setNamespace("ex", "http://example.org/");
						for (RelationTriple triple : triples) {

				builder.namedGraph("http://example.org/"+contesto);

				builder.subject("ex:" + URLEncoder.encode(triple.subjectLemmaGloss(), "UTF-8"));
				builder.add("ex:" + URLEncoder.encode(triple.relationLemmaGloss(), "UTF-8"),
						"ex:" + URLEncoder.encode(triple.objectLemmaGloss(), "UTF-8"));
			}
			Model model = builder.build();
			System.out.println("HO LETTO RDF esistente... ECCO LE TRIPLE ESISTENTI");
			//for (Statement statement : existentmodel) {
				//System.out.println(statement);}
			
			model.addAll(existentmodel);

			try {
				FileOutputStream out = new FileOutputStream(kbfile, false);
				System.out.println("Sovrascrivo rdf");

				Rio.write(model, out, RDFFormat.TRIG);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			// Se non esiste un file RDF, vine creato la prima volta con le nuove triple
			// Creazione del ModelBuilder previsto da RDF4j e successiva aggiunta delle
			// triple ad esso
			System.out.println("il contesto è "+contesto);

			System.out.println("NON ESITE RDF\n");
			ModelBuilder builder = new ModelBuilder();
			builder.setNamespace("ex", "http://example.org/");
			for (RelationTriple triple : triples) {
				builder.namedGraph("http://example.org/"+contesto);
			
				builder.subject("ex:" + URLEncoder.encode(triple.subjectLemmaGloss(), "UTF-8"));
				builder.add("ex:" + URLEncoder.encode(triple.relationLemmaGloss(), "UTF-8"),
						"ex:" + URLEncoder.encode(triple.objectLemmaGloss(), "UTF-8"));
			}
			Model model = builder.build();

			for (RelationTriple triple : triples) {
				System.out.println("TRIPLAAAAA");
				System.out.println(triple.confidence + "\t" + triple.subjectGloss() + "\t" + triple.relationLemmaGloss()
						+ "\t" + triple.objectLemmaGloss());

			}

			// Scrittura Modello su file
			try {
				FileOutputStream out = new FileOutputStream(kbfile, false);
				Rio.write(model, out, RDFFormat.TRIG);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// FA VEDERE IN CONSOLE IL FILE RDF
			System.out.println("MI VEDI PERCHè SCRIVO LA PRIMA VOLTA RDF");
			//Rio.write(model, System.out, RDFFormat.TRIG);
			//
		}
	}
}