package pipeline;

import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.PropertiesUtils;

public class istancepipeline {

	public static StanfordCoreNLP istancePipeline() {
		Properties props = PropertiesUtils.asProperties("annotators",
				"tokenize,ssplit,pos,lemma,ner,depparse,coref,natlog,openie");
		props.setProperty("parse.maxlen", "100");
	
		// USARE IL PROSSIMO
		props.setProperty("openie.resolve_coref", "true");
		//VALUTARE I PROSSIMI
		props.setProperty("ner.combinationMode", "HIGH_RECALL");
		props.setProperty("sutime.markTimeRanges", "true");
		props.setProperty("openie.affinity_probability_cap", "0.9");
		
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		return pipeline;
	}

}
