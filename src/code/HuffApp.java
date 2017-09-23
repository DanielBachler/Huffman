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
    private int[] freqTable;
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
        for (int i = 0; i < ASCII_TABLE_SIZE; i++) {
            codeTable[i] = "";
        }
        freqTable = new int[ASCII_TABLE_SIZE];
        readInput();
        displayOriginalMessage();
        makeFrequencyTable(originalMessage);
        displayFrequencyTable();
        addToQueue();
        buildTree(theQueue);
        makeCodeTable(huffTree.root, "");
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
            while (textInput.hasNextLine()) {
                originalMessage += textInput.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open input file.");
        }
    }

    private void displayOriginalMessage() {
        System.out.println("Original message: " + originalMessage);
    }

    private void makeFrequencyTable(String inputString) {
        //populate the frequency table using inputString. results are saved to the
        //freqTable field
        for (int i = 0; i < inputString.length(); i++) {
            int temp = inputString.charAt(i);
            freqTable[temp]++;
        }
    }

    private void displayFrequencyTable() {
        //print the frequency table. skipping any elements that are not represented
        System.out.println("Frequency Table\nchar | val");
        for (int i = 0; i < ASCII_TABLE_SIZE; i++) {
            if (freqTable[i] != 0) {
                System.out.printf("%c\t | %d\n", (char) i, freqTable[i]);
            }
        }
        System.out.println();
    }

    private void addToQueue() {
        //add the values in the frequency table to the PriorityQueue. Hint use the
        //PriorityQ class. save the results to theQueue field
        theQueue = new PriorityQ(originalMessage.length() * 4);
        for (int i = 0; i < ASCII_TABLE_SIZE; i++) {
            if (freqTable[i] != 0) {
                HuffTree temp = new HuffTree((char) i, freqTable[i]);
                theQueue.insert(temp);
            }
        }
    }

    private void buildTree(PriorityQ hufflist) {
        //pull items from the priority queue and combine them to form
        //a HuffTree. Save the results to the huffTree field
        HuffTree temp1 = hufflist.remove();
        HuffTree temp2 = hufflist.remove();
        HuffTree start = new HuffTree(temp1.getWeight() + temp2.getWeight(), temp1, temp2);
        hufflist.insert(start);
        while (hufflist.getSize() > 1) {
            temp1 = hufflist.remove();
            temp2 = hufflist.remove();
            HuffTree temp3 = new HuffTree(temp1.getWeight() + temp2.getWeight(), temp1, temp2);
            hufflist.insert(temp3);
        }
        huffTree = hufflist.remove();
    }

    private void makeCodeTable(HuffNode huffNode, String bc) {
        /**
         * Reads down the tree from left to right, counting its value's until it hits nodes
         * with no children.  Then it reads the char and records the path to the code table
         */
        //Checking if there is a left child for traversal
        if (huffNode.leftChild != null) {
            makeCodeTable(huffNode.leftChild, bc + "0");
        }
        //Checks if there is a right child for traversal
        if (huffNode.rightChild != null) {
            makeCodeTable(huffNode.rightChild, bc + "1");
        }
        //If no children, then it is a character
        if (huffNode.rightChild == null && huffNode.rightChild == null) {
            //Get the character of the node
            char letter = huffNode.character;
            //Find what spot in the array that is
            int spot = (int) letter;
            //Put the byte code into the array, at the proper location
            codeTable[spot] = bc;
        }
    }

    private void displayCodeTable() {
        //print code table, skipping any empty elements
        System.out.println("Code Table\nchar | val");

        for (int i = 0; i < ASCII_TABLE_SIZE; i++) {
            if (!codeTable[i].equals("")) {
                System.out.printf("%c\t | %s\n", (char) i, codeTable[i]);
            }
        }
        System.out.println();
    }


    //If you could do the rest of these that would be great
    private void encode() {
        for (int i = 0; i < originalMessage.length(); i++) { //iterates through entire original message
            System.out.print(codeTable[(int) originalMessage.charAt(i)]); // find the ascii value of the letter and goes to it in the code table then prints binary value
        }

        //use the code table to encode originalMessage. Save result in the encodedMessage field
    }

    private void displayEncodedMessage() {
        System.out.println("\nEncoded message: " + encodedMessage);
    }

    private void decode() {
        HuffNode current = huffTree.root; //keeps track of the current node

        for (int i = 0; i < encodedMessage.length(); i++) { //iterates through the entire encoded message

            if (current.leftChild == null && current.rightChild == null) { // if the current node is a leaf it adds it to string decodedmessage
                decodedMessage = decodedMessage + current.character; // adds to string
                i--; // if this wasnt here we would skip a number
                current = huffTree.root; //goes back to root
            } else if (encodedMessage.charAt(i) == 0) { // if its zero go left
                current = current.leftChild; // left
            } else { // if its not a leaf and isnt zeero it must be a one
                current = current.rightChild; // go right
            }

        }
        //decode the message and store the result in the decodedMessage field
    }

    public void displayDecodedMessage() {
        System.out.println("Decoded message: " + decodedMessage);
    }
}
