package estrattore;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import pipeline.istancepipeline;

public class Run {
	//static boolean finito = false;

	public static void main(String[] args) throws Exception {
		//instanzio una sola volta la pipeline
		StanfordCoreNLP pipeline = istancepipeline.istancePipeline();
		// controllo quali sono i file da annotare
		final File folder = new File("Pages");
		//VARIABILE SETTATA A SI QUANDO IL CRAWLER INZIIALE SMETTE DI SALVARE PAGINE (IN UN CONTESTO DI QUANTITa LIMITATE DI PAGINE)
		File[] listaFile = folder.listFiles();
//int c =0;
// int appoggiofor=0;
	//	while (true) {
			for (int i =0/*0c */; i < listaFile.length; i++) {
			File file = listaFile[i];
			if (file.isFile() && file.getName().endsWith(".json")) {
				String nomeFileSenzaExt = FilenameUtils.removeExtension(file.getName());

				System.out.println(nomeFileSenzaExt);

				TripleExtractionJson.estrai("Pages/" + file.getName(),pipeline );
                 
				// TripleExtraction.estrai(file.getName());
			}
			//appoggiofor=i;
		}
//c=appoggiofor;
		
	}
		
	//}//chiusura while
}
