package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Process {
    ArrayList<String> paragraph = new ArrayList<>();//array list for storing google words 
    private ConcurrentHashMap<String, double[]> text = new ConcurrentHashMap<>();//hashMap for storing the users word and embedding
    private ConcurrentHashMap<String, String> updatedText = new ConcurrentHashMap<>();//hashMap for storing the users updated text 

    public void load(String filePath, WordEmbeddings embeddings, GoogleEmbeddings googleEmb, String fileName) throws Exception {
		try (var bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
			//each line of the file stored in the lines variable
			String line;
		    paragraph.clear();
	        text.clear();
	        updatedText.clear();
	        
			//reads each line in the file until readLine returns null
			while ((line = bReader.readLine()) != null ) {
				//https://stackoverflow.com/questions/20226641/split-lines-into-two-strings-using-bufferedreader
				String[] words = line.split("\\s+");
				
				//https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
				paragraph.addAll(Arrays.asList(words));				
			}	
			for(String word : paragraph) {
				//finding the embedding value for each word in the array 
				getEmbedding(word, embeddings);
			}
			
			//calling the best match after all the words are loaded 
			bestMatch(text, googleEmb.googleWordEmbeddings, fileName);
		}
	}
    
    //getting the embedding values of the words in the users texts
	public void getEmbedding(String word, WordEmbeddings embeddings) {
	    double[] gEmbeddings = embeddings.getEmbedding(word);
	    if (gEmbeddings != null) {
	        text.put(word, gEmbeddings);
	    }  
	    else {
	       //System.out.println("Embedding for \"" + word + "\" not found.");
	    }
	}
	
	//https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/CosineSimilarity.java.html
	//https://technomaster.medium.com/cosine-similarity-3ba642e74e58
	public Double cosineSimilarity( double[]userVector,  double[]googleVector) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < googleVector.length; i++) {
	      dotProduct += userVector[i] * googleVector[i];
	      normA += Math.pow(userVector[i], 2);
          normB += Math.pow(googleVector[i], 2);
        }  
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	//https://sentry.io/answers/iterate-hashmap-java/
	public void bestMatch(Map <String, double[]> userMap, Map <String, double[]> googleMap, String fileName) {
		//looping for every entry (word) in the users paragraph
		for(Map.Entry<String, double[]> entry : userMap.entrySet()) {
			String userWord = entry.getKey(); //key
			double[] userEmbedding = entry.getValue(); //value
			
			//variable need to be inside loop to ensure it updates for each word 
			String wordMatch = null;
			double[] embeddingMatch = null;
			double highestMatch = -1.0; //the lowest cosine match 
			
			//for every word in the user text file i'm also looping through every word in the google embedding 
			for(Map.Entry<String, double[]> entry2 : googleMap.entrySet()) {
				String googleWord = entry2.getKey(); //key
				double[] googleEmbedding = entry2.getValue(); //value
				
				//calling cosine function to check the similarity 
				double similarity = cosineSimilarity(userEmbedding, googleEmbedding);
				
				//swapping the words if it find one with a higher similarity 
				if (highestMatch < similarity) {
					 highestMatch = similarity;
		             wordMatch = googleWord;
		             embeddingMatch = googleEmbedding;
				}
			}
			
			//if there is a match
			if (wordMatch != null) {
		          updatedText.put(userWord, wordMatch); 
		    }
		}
		
		//a new array to store the new words for the paragraph
		ArrayList<String> newParagraph = new ArrayList<>();
        
        //https://www.geeksforgeeks.org/hashmap-getordefaultkey-defaultvalue-method-in-java-with-examples/
        for (String word : paragraph) {
            //replacing each word with its best match if it has one 
            String replacement = updatedText.getOrDefault(word, word);
            newParagraph.add(replacement);
        }
        
        System.out.println(String.join(" ", newParagraph));
        
        //https://www.w3schools.com/java/java_files_create.asp
		File file = new File(fileName);
		boolean fileExist = file.exists();

        try {
        	FileWriter writer;
        	if(fileExist) {
        		writer = new FileWriter(fileName, true);//true for appending data
        		writer.write("\n" + String.join(" ", newParagraph));
                System.out.println("Successfully wrote parsed text to the specified file " + fileName);
        	}
        	
        	else {
        		writer = new FileWriter(fileName);
        		writer.write(String.join(" ", newParagraph));
                System.out.println("Successfully wrote parsed text to the specified file " + fileName);
        	}
        	writer.close();
          } 
        
        catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
          }

	}
}
