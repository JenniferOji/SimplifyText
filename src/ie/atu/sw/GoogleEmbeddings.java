package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GoogleEmbeddings {	

    ArrayList<String> googleWords = new ArrayList<>();
    ConcurrentHashMap<String, double[]> googleWordEmbeddings = new ConcurrentHashMap<>();

	 /**
     * //A method that when called reads the content of the embedding file until there's no lines left 
     * @throws Exception
     * running time of this method is O(n) as it is just iterating through a loop and reading each line where n is the number of lines 
     */
	
	
	
	//VIRTUAL THREADS 
	public void load(String filePath, WordEmbeddings embeddings) throws Exception {
	    try (var pool = Executors.newVirtualThreadPerTaskExecutor()){
	    	Files.lines(Paths.get(filePath))
	    	.forEach(word -> pool.execute(() -> 
	    	{
	    	    googleWords.add(word.trim());
	    	    getEmbedding(word, embeddings);
	    	}));
	          	
	   	};

        System.out.println("Google1000 file loaded");
	     
	}


		//////////////////////////////////////////////////////////////////
		
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
