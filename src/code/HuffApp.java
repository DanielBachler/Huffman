package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class HuffApp {
	private int[]freqTable;
	private final static int ASCII_TABLE_SIZE = 128;
	private String originalMessage = "";
	private PriorityQ theQueue;
	private HuffTree huffTree;
	private String encodedMessage = "";
	private String[] codeTable;
	private String decodedMessage = "";
	

	public static void main(String[] args) {
		new HuffApp();	
	}
		
	
	public HuffApp() {
		codeTable = new String[ASCII_TABLE_SIZE];
		freqTable = new int[ASCII_TABLE_SIZE];
		readInput();
		displayOriginalMessage();
		makeFrequencyTable(originalMessage);
		displayFrequencyTable();
		addToQueue();
		buildTree(theQueue);
		//when the following method is implemented, remove the "//", so it executes
		//makeCodeTable(huffTree.root, "");  						
		encode();
		displayEncodedMessage();
		displayCodeTable();
		decode();
		displayDecodedMessage();		
	}
	
	private void readInput() {
		//read input in from the input.txt file and save to originalMessage	field
        try {
            Scanner textInput = new Scanner(new File("input.txt"));
            while(textInput.hasNextLine()) {
                originalMessage += textInput.nextLine();
            }
        } catch(FileNotFoundException e) {
            System.out.println("Unable to open input file.");
        }
	}
	
	private void displayOriginalMessage() {
		System.out.println("Original message: " +  originalMessage);
	}
	
	private void makeFrequencyTable(String inputString)
	{	
		//populate the frequency table using inputString. results are saved to the 
		//freqTable field
        for(int i = 0; i < inputString.length(); i++) {
            freqTable[(int)inputString.charAt(i)]++;
        }
	}
	
	private void displayFrequencyTable()
	{	
		//print the frequency table. skipping any elements that are not represented
        System.out.println("Frequency Table\nchar | val");
        for(int i = 0; i < ASCII_TABLE_SIZE; i++) {
            if (freqTable[i] != 0) {
                System.out.printf("%c\t | %d\n", (char)i, freqTable[i]);
            }
        }
        System.out.println();
	}
	
	private void addToQueue() 
	{
		//add the values in the frequency table to the PriorityQueue. Hint use the 
		//PriorityQ class. save the results to theQueue field
        for(int i = 0; i < freqTable.length; i++) {
            if(freqTable[i] != 0) {
                theQueue.insert(new HuffTree((char)i, freqTable[i]));
            }
        }
	}
	
	private void buildTree(PriorityQ hufflist) 
	{
		//pull items from the priority queue and combine them to form 
		//a HuffTree. Save the results to the huffTree field
        HuffTree temp1 = hufflist.remove();
        HuffTree temp2 = hufflist.remove();
        HuffTree start = new HuffTree(temp1.getWeight() + temp2.getWeight(), temp1, temp2);
        hufflist.insert(start);
        while(hufflist.getSize() < 2) {
            temp1 = hufflist.remove();
            temp2 = hufflist.remove();
            HuffTree temp3 = new HuffTree(temp1.getWeight() + temp2.getWeight(), temp1, temp2);
            hufflist.insert(temp3);
        }
        huffTree = hufflist.remove();
	}
	
	private void makeCodeTable(HuffNode huffNode, String bc)
	{		
		//hint, this will be a recursive method

		if(huffNode.leftChild != null) {

        } else if(huffNode.rightChild != null){

        }
	}
	
	private void displayCodeTable()
	{	
		//print code table, skipping any empty elements
	}


	//If you could do the rest of these that would be great
	private void encode()                   
	{		
		//use the code table to encode originalMessage. Save result in the encodedMessage field
	}

	private void displayEncodedMessage() {
		System.out.println("\nEncoded message: " + encodedMessage);		
	}

	private void decode()
	{
		//decode the message and store the result in the decodedMessage field
	}
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
