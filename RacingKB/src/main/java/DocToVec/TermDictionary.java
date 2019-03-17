package DocToVec;

import java.util.HashMap;
import java.util.Map;

public class TermDictionary implements Dictionary {
	private final Map<String, Integer> indexedTerms;
    private int counter;

    public TermDictionary() {
        indexedTerms = new HashMap<>();
        counter = 0;
    }
    
    public void addTerm(String term) {
    	
        if(!indexedTerms.containsKey(term)) {
            indexedTerms.put(term, counter++);
        }       
    }

    public void addTerms(String[] terms) {
    	
        for (String term : terms) {
        	
            addTerm(term);
        }
    }
    
    @Override
    public Integer getTermIndex(String term) {
        return indexedTerms.get(term);
    }

    @Override
    public int getNumTerms() {
        return indexedTerms.size();
    }
}


