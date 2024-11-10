package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WordEmbeddings {
	//mapping
	//takes in a string and an array of doubles and calling each collection wordEmbeddings 
	ConcurrentHashMap<String, double[]> wordEmbeddings = new ConcurrentHashMap<>();
	
    /**
     * //A method that when called reads the content of the embedding file until there's no lines left 
     * @throws Exception
     * running time of this method is O(n) as it is just iterating through a loop and reading each line where n is the number of lines 
     */
	public void load(String filePath) throws Exception {
		try (var bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
			//each line of the file stored in the lines variable
			String lines;
			//reads each line in the file until readLine returns null
			while ((lines = bReader.readLine()) != null ) {
				//putting the words into a word array by splitting breaks at each comma 
				String[] sections = lines.split(","); 
                String word = sections[0];
				
                //splitting the doubles associated with each word into an array of doubles 
                //the loop iterates through each value in sections(each word) and parses it as a double
                double[] embeddingValues = new double[sections.length - 1];
                for (int i = 1; i < sections.length; i++) {
                	//embeddingValues holds all the numeric values associated with each word -> [i - 1] to skip the first word in the array 
                    embeddingValues[i - 1] = Double.parseDouble(sections[i]);
                }
                wordEmbeddings.put(word, embeddingValues);
                //printing out the values in the embeddings file 
                //System.out.println(word + " " + Arrays.toString(embeddingValues));

                	
				}
			System.out.println("Embeddings file loaded");

		    }

		catch (Exception e) {
            throw new Exception("Encountered a problem reading the embeddings file. " + e.getMessage());
        }		
	}
	
	

	 public boolean finding(String word) {
	        if (wordEmbeddings.containsKey(word)) {
	            double[] embeddings = wordEmbeddings.get(word);
	            System.out.println(word + " " + Arrays.toString(embeddings));
	        } else {
	            System.out.println("Word \"" + word + "\" not found in embeddings.");
	        }
			return false;
	}
	 
	 public void find(String word, ConcurrentHashMap<String, double[]> googleWordEmbeddings){
	        if (wordEmbeddings.containsKey(word)) {
	            double[] gEmbeddings = wordEmbeddings.get(word);
	            googleWordEmbeddings.put(word, gEmbeddings);
	           // System.out.println(word + " " + Arrays.toString(gEmbeddings));
	        }

	}



	/*public boolean containsWord(String word, ArrayList<String> googleWords, ConcurrentHashMap<String, double[]> googleWordEmbeddings) {
		
	}*/
	 
	 
	
	
}
