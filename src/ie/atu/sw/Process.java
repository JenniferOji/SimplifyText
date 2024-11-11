package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Process {
    ArrayList<String> paragraph = new ArrayList<>();
    private ConcurrentHashMap<String, double[]> text = new ConcurrentHashMap<>();

    public void load(String filePath, WordEmbeddings embeddings, GoogleEmbeddings googleEmb) throws Exception {
		try (var bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
			//each line of the file stored in the lines variable
			String line;
			String[] words = null;
			//reads each line in the file until readLine returns null
			while ((line = bReader.readLine()) != null ) {
				//https://stackoverflow.com/questions/20226641/split-lines-into-two-strings-using-bufferedreader
				words = line.split("\\s+");
				
				//https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
				paragraph.addAll(Arrays.asList(words));
				
			}	
			for(String word : words) {
				//finding the embedding value for each word in the array 
				googleEmb.find(word);
			}
		}
		//System.out.println(paragraph);
	}
    
    /*
	For each word in the target text file, compute similarity with the embeddings of each Google-1000 word.
	Use cosine similarity (or a similar metric) to measure how close the embeddings are and find the Google-1000 word with the highest similarity.
	Replace Words:*/
}
