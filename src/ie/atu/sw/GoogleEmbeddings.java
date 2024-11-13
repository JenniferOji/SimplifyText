package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GoogleEmbeddings {	

    ArrayList<String> googleWords = new ArrayList<>();
    ConcurrentHashMap<String, double[]> googleWordEmbeddings = new ConcurrentHashMap<>();


	 /**
     * //A method that when called reads the content of the embedding file until there's no lines left 
     * @throws Exception
     * running time of this method is O(n) as it is just iterating through a loop and reading each line where n is the number of lines 
     */
	public void load(String filePath, WordEmbeddings embeddings) throws Exception {
		try (var bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
			//each line of the file stored in the lines variable
			String word;
			//reads each line in the file until readLine returns null
			while ((word = bReader.readLine()) != null ) {
				//putting the words into a word array  - trim to remove any extra spaces or newlines
                googleWords.add(word.trim()); 
                
              //splitting the doubles associated with each word into an array of doubles 
                //the loop iterates through each value in sections(each word) and parses it as a double
                
                //printing out the values in the google1000 file 
                /*for (int i = 0; i < googleWords.size(); i++) {
                    System.out.println(googleWords.get(i));
                }*/
                
               // embeddings.find(word, googleWordEmbeddings);
                getEmbedding(word, embeddings);

			}
            System.out.println("Google1000 file loaded");

		}
		
		catch (Exception e) {
            throw new Exception("Encountered a problem reading the google file. " + e.getMessage());
        }
	}
	
	//method to get the embedding values 
	public void getEmbedding(String word, WordEmbeddings embeddings) {
	    double[] gEmbeddings = embeddings.getEmbedding(word);
	    if (gEmbeddings != null) {
	        googleWordEmbeddings.put(word, gEmbeddings);
	        //System.out.println(word + " " + Arrays.toString(gEmbeddings)); // Print or remove as needed
	    }
	    
	    else {
	       // System.out.println("Embedding for \"" + word + "\" not found.");
	    }
	}
	

	//method to find a specified word 
	 public boolean find(String word) {
	        if (googleWordEmbeddings.containsKey(word)) {
	            double[] embeddings = googleWordEmbeddings.get(word);
	            System.out.println(word + " " + Arrays.toString(embeddings));
	        } 
	        else {
	            System.out.println("Word \"" + word + "\" not found in embeddings.");
	        }
			return false;
	}
	 
	
	
	
}
