package aggiornamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;

import edu.stanford.nlp.ie.util.RelationTriple;

public  class AggiornamentoRDF {
	
	 static String kbfile = "KGracing";

	public static void updateFileRDF(Collection<RelationTriple> triples, String contesto) throws RDFParseException, UnsupportedRDFormatException, IOException {
		
		System.out.println("Sono nel metodo updateFIleRDF");
		// deve andare a leggere il file della base di conoscenza, eliminare tutte le triple coo contesto uguale a contesto PASSATO IN INPUT
				// aggiungere le nuove.
		
		InputStream input = new FileInputStream(kbfile);
		
		// QUESTE SONO LE MIE TRIPLE
		
		Model existentmodel = Rio.parse(input, "", RDFFormat.TRIG);
     
        ValueFactory vf = SimpleValueFactory.getInstance();

		IRI contestoIRI = vf.createIRI("http://example.org/"+contesto);
		existentmodel.remove(null, null, null,contestoIRI);
	
		
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
	//	for (Statement statement : existentmodel) {
		//	System.out.println(statement);}
		model.addAll(existentmodel);

		try {
			FileOutputStream out = new FileOutputStream(kbfile, false);
			System.out.println("MI VEDI PERCHè SOVRAAAAASCRIVO rdf");

			Rio.write(model, out, RDFFormat.TRIG);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
		
		
		
	}

}
}
