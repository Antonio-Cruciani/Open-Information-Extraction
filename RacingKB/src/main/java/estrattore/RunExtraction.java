package estrattore;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import pipeline.istancepipeline;

public class RunExtraction {

	
	
	public static void AvvioEstrazione() throws Exception {
		//instanzio una sola volta la pipeline
				StanfordCoreNLP pipeline = istancepipeline.istancePipeline();
				// controllo quali sono i file da annotare
				final File folder = new File("Pages");
				//VARIABILE SETTATA A SI QUANDO IL CRAWLER INZIIALE SMETTE DI SALVARE PAGINE (IN UN CONTESTO DI QUANTITa LIMITATE DI PAGINE)
				//File[] listaFile = folder.listFiles();
		int c =-1;
		int appoggiofor=0;
			while (true) {
				File[] listaFile = folder.listFiles();

			     // Arrays.sort(listaFile, (f1, f2) -> f1.compareTo(f2));
				Arrays.sort(listaFile, (f1, f2) -> {
			         return new Date(f1.lastModified()).compareTo(new Date(f2.lastModified()));
			      });
					for (int i =c+1; i < listaFile.length; i++) {
					File file = listaFile[i];
					if (file.isFile() && file.getName().endsWith(".json")) {
						String nomeFileSenzaExt = FilenameUtils.removeExtension(file.getName());

						System.out.println(nomeFileSenzaExt);

						TripleExtractionJson.estrai("Pages/" + file.getName(),pipeline );
		                 
						// TripleExtraction.estrai(file.getName());
					}
					appoggiofor=i;
				}//chiude for
					System.out.println("*");
					System.out.println("*");
					System.out.println("*");
					System.out.println("*");
					System.out.println("*");
					System.out.println("*");
		c=appoggiofor;
		System.out.println(c);
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		System.out.println("*");
		TimeUnit.SECONDS.sleep(3);

	}//chiude while
	
	}//chiude metodo
	
}//chiude classe

