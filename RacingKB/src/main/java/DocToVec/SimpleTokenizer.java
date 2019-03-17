package DocToVec;

import java.util.ArrayList;
import java.util.List;

public class SimpleTokenizer implements Tokenizer {
	private final int minTokenSize;

    public SimpleTokenizer(int minTokenSize) {
        this.minTokenSize = minTokenSize;
    }

    public SimpleTokenizer() {
        this(0);
    }
    
    @Override
    public String[] getTokens(String document) {
        String[] tokens = document.trim().split("\\s+");
       
        List<String> cleanTokens = new ArrayList<>();
        for (String token : tokens) {
            String cleanToken = token.trim().toLowerCase()
              .replaceAll("[^A-Za-z\']+", "");
            if(cleanToken.length() > minTokenSize) {
                cleanTokens.add(cleanToken);
            }
        }
        return cleanTokens.toArray(new String[0]);
    }

}
