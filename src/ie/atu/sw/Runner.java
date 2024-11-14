package ie.atu.sw;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;


public class Runner{

	public static void main(String[] args) throws Exception{
		
		//Variables 
		Scanner input = new Scanner(System.in);
		int choice;
	
		String file1 = "../word-embeddings.txt";
		String file2 = "../google-1000.txt";
		
		//default file to print info to if user does not enter their own
        String outputFile = "output.txt";
        
        //Initializing the word embedding and google words class 
        WordEmbeddings embeddings = new WordEmbeddings();
        GoogleEmbeddings googleEmb = new GoogleEmbeddings();
        Process process = new Process();

		//You should put the following code into a menu or Menu class
		System.out.println(ConsoleColour.BLACK);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Virtual Threaded Text Simplifier             *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embeddings File");
		System.out.println("(2) Specify Google 1000 File");
		System.out.println("(3) Specify an Output File (default: ./output.txt)");
		System.out.println("(4) Execute, Analyse and Report");
		System.out.println("(5) Optional Extras...");
		System.out.println("(6) Quit");
		
		//Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK);
		//looping until the user enters a valid option 
		System.out.println();
		do {
			System.out.print("Select Option [1-6] : ");
			choice = input.nextInt();
		}while(choice <1 || choice >6);
		
		//looping until the user wants to choses to close the program if the number entered is anything but 1 - 6 
		do 
		{
			if(choice == 1) {
				System.out.print("Please specify the path to the embeddings file: ");
				//String embeddingFilePath = input.next();
				System.out.println();
				String embeddingFilePath = file1;

				embeddings.load(embeddingFilePath);
				
				do 
			    { 
					System.out.print("Select Option [1-6] : ");
				    choice = input.nextInt(); 
					    
			    }while(choice < 1 || choice > 6);
			}
			
			if(choice == 2) {
				System.out.print("Please specify the path to the Google 1000 file: ");
				//String googleFilePath = input.next();
				String googleFilePath = file2;
				System.out.println();
				googleEmb.load(googleFilePath, embeddings);
				do 
			    { 
				    System.out.print("Select Option [1-6] : ");
				    choice = input.nextInt(); 
					    
			     }while(choice < 1 || choice > 6);
			}
			
			if(choice == 3) {
				System.out.print("Please specify an output file name(./output.txt): ");
				outputFile  = input.next();
				outputFile = outputFile + ".txt";
				System.out.println(outputFile);
				
				//content already been written will be output to the file  
			    //open_file = true;
			    
				do 
				{ 
					System.out.print("Select Option [1-6] : ");
					choice = input.nextInt(); 
					    
			    }while(choice < 1 || choice > 6);
			}
			
			if(choice == 4) {
				System.out.print("Please specify the path to the text file: ");
				String textFilePath = input.next();
				System.out.println();

				process.load(textFilePath, embeddings, googleEmb, outputFile);
				
				 do 
				 { 
					 System.out.print("Select Option [1-6] : ");
					 choice = input.nextInt(); 
					    
			     }while(choice < 1 || choice > 6);
			}
			
			if(choice == 5) {
				
				do 
			    { 
					System.out.print("Select Option [1-6] : ");
					choice = input.nextInt(); 
					    
			    }while(choice < 1 || choice > 6);
			}
			
			if(choice == 6) {
				
				 do 
				 { 
					 System.out.print("Select Option [1-6] : ");
					 choice = input.nextInt(); 
					    
			     }while(choice < 1 || choice > 6);
			}
			
			//break;
			
		}while(choice != 6);
		/*
		//You may want to include a progress meter in you assignment!
		System.out.print(ConsoleColour.YELLOW);	//Change the colour of the console text
		int size = 100;							//The size of the meter. 100 equates to 100%
		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
			printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
			Thread.sleep(10);					//Slows things down so the animation is visible 
		}
		*/
	}
	
	
	/*
	 *  Terminal Progress Meter
	 *  -----------------------
	 *  You might find the progress meter below useful. The progress effect 
	 *  works best if you call this method from inside a loop and do not call
	 *  System.out.println(....) until the progress meter is finished.
	 *  
	 *  Please note the following carefully:
	 *  
	 *  1) The progress meter will NOT work in the Eclipse console, but will
	 *     work on Windows (DOS), Mac and Linux terminals.
	 *     
	 *  2) The meter works by using the line feed character "\r" to return to
	 *     the start of the current line and writes out the updated progress
	 *     over the existing information. If you output any text between 
	 *     calling this method, i.e. System.out.println(....), then the next
	 *     call to the progress meter will output the status to the next line.
	 *      
	 *  3) If the variable size is greater than the terminal width, a new line
	 *     escape character "\n" will be automatically added and the meter won't
	 *     work properly.  
	 *  
	 * 
	 */
	public static void printProgress(int index, int total) {
		if (index > total) return;	//Out of range
        int size = 50; 				//Must be less than console width
	    char done = '█';			//Change to whatever you like.
	    char todo = '░';			//Change to whatever you like.
	    
	    //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;
        
        /*
         * A StringBuilder should be used for string concatenation inside a 
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.  
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
        	sb.append((i < completeLen) ? done : todo);
        }
        
        /*
         * The line feed escape character "\r" returns the cursor to the 
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");
        
        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }




}