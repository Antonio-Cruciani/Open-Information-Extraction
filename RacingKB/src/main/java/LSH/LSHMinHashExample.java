package LSH;

import info.debatty.java.lsh.LSHMinHash;
import info.debatty.java.lsh.MinHash;
import java.util.Random;

public class LSHMinHashExample {

    public static void main(String[] args) {
        // Number of sets
        int count = 2000;
        
        // Size of dictionary
        int n = 100;
        
        // Number of buckets
        // Attention: to get relevant results, the number of elements per bucket
        // should be at least 100
        int buckets = 10;
        
        // Let's generate some random sets
        boolean[][] vectors = new boolean[count][];
        Random r = new Random();
        
        // To get some interesting measures, we first generate a single
        // sparse random vector
        vectors[0] = new boolean[n];    
        for (int j = 0; j < n; j++) {
            vectors[0][j] = (r.nextInt(10) == 0);
        }
        
        // Then we generate the other vectors, which have a reasonable chance 
        // to look like the first one...
        for (int i = 1; i < count; i++) {
            vectors[i] = new boolean[n];
            
            for (int j = 0; j < n; j++) {
                vectors[i][j] = (r.nextDouble() <= 0.7 ? vectors[0][j] : (r.nextInt(10) == 0));
            }
        }
        
        // Now we can proceed to LSH binning
        // We will test multiple stages
        for (int stages = 1; stages <= 10; stages++) {
            
            // Compute the LSH hash of each vector
            LSHMinHash lsh = new LSHMinHash(stages, buckets, n);
            int[][] hashes = new int[count][];
            for (int i = 0; i < count; i++) {
                boolean[] vector = vectors[i];
                hashes[i] = lsh.hash(vector);
            }

            // We now have the LSH hash for each input set
            // Let's have a look at how similar sets (according to Jaccard 
            // index) were binned...
            int[][] results = new int[11][2];
            for (int i = 0; i < vectors.length; i++) {
                boolean[] vector1 = vectors[i];
                int[] hash1 = hashes[i];

                for (int j = 0; j < i; j++) {
                    boolean[] vector2 = vectors[j];
                    int[] hash2 = hashes[j];
                    
                    // We compute the similarity between each pair of sets
                    double similarity = MinHash.jaccardIndex(vector1, vector2);

                    // We count the number of pairs with similarity 0.1, 0.2, 
                    // 0.3, etc.
                    results[(int) (10 * similarity)][0]++;

                    // Do they fall in the same bucket for one of the stages?                
                    for (int stage = 0; stage < stages; stage++) {
                        if (hash1[stage] == hash2[stage]) {
                            results[(int) (10 * similarity)][1]++;
                            break;
                        }
                    }
                }
            }

            // Now we can display (and plot in Gnuplot) the result:
            // For pairs that have a similarity x, the probability of falling
            // in the same bucket for at least one of the stages is y
            for (int i = 0; i < results.length; i++) {
                double similarity = (double) i / 10;
                
                double probability = 0;
                if (results[i][0] != 0) {
                    probability = (double) results[i][1] / results[i][0];
                }
                System.out.println("" + similarity + "\t" + probability + "\t" + stages);
            }
            
            // Separate the series for Gnuplot...
            System.out.print("\n");
        }
    }
}