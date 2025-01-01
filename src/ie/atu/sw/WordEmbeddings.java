package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordEmbeddings {
	//takes in a string and an array of doubles and calling the collection wordEmbeddings 
	ConcurrentHashMap<String, double[]> wordEmbeddings = new ConcurrentHashMap<>();
	
    /**
     * //A method that when called reads the content of the embedding file until there's no lines left 
     * @throws Exception
     * running time of this method is O(n) as it is just iterating through a loop and reading each line where n is the number of lines 
     */
	
	//////////////////////////////////////////////////////////////////
	//VIRTUAL THREADS 
	public void load(String filePath) throws Exception {
	    try (var pool = Executors.newVirtualThreadPerTaskExecutor()){
	    	Files.lines(Paths.get(filePath))
	    	.forEach(line -> pool.execute(() -> process(line)));
	          	
	   	};

		System.out.println("Embeddings file loaded");
	     
	}

	public void process(String line) {
			String[] sections = line.split(","); 
            String word = sections[0];
			
            //splitting the doubles associated with each word into an array of doubles 
            //the loop iterates through each value in sections(each word) and parses it as a double
            double[] embeddingValues = new double[sections.length - 1];
            for (int i = 1; i < sections.length; i++) {
            	//embeddingValues holds all the numeric values associated with each word -> [i - 1] to skip the first word in the array 
                embeddingValues[i - 1] = Double.parseDouble(sections[i]);
            }
            wordEmbeddings.put(word, embeddingValues);
 
	}
	//////////////////////////////////////////////////////////////////
	
	
	
	
	 //method to find a word and its embedding 
	 public boolean find(String word) {
	     if (wordEmbeddings.containsKey(word)) {
	    	 double[] embeddings = wordEmbeddings.get(word);
	         System.out.println(word + " " + Arrays.toString(embeddings));
	         return true;
	     } 
	     else {
	    	 System.out.println("Word \"" + word + "\" not found in embeddings.");
	     }
		 return false;
	}

	 
	 //method returning the embedding of specified word for the google1000 words  
	 public double[] getEmbedding(String word) {
	     return wordEmbeddings.get(word);
	 }


	/*public boolean containsWord(String word, ArrayList<String> googleWords, ConcurrentHashMap<String, double[]> googleWordEmbeddings) {
		
	}*/
	 
	 
	
	
}
