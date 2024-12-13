package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import implementations.*;
import utilities.Iterator;

class WordInfo implements Comparable<WordInfo> {
    private String word;
    private Map<String, List<Integer>> fileLineNumbers;

    public WordInfo(String word) {
        this.word = word.toLowerCase();
        this.fileLineNumbers = new HashMap<>();
    }

    public void addOccurrence(String filename, int lineNumber) {
        fileLineNumbers.computeIfAbsent(filename, k -> new ArrayList<>()).add(lineNumber);
    }

    public String getWord() {
        return word;
    }

    public Map<String, List<Integer>> getFileLineNumbers() {
        return fileLineNumbers;
    }

    @Override
    public int compareTo(WordInfo other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(":\n");
        for (Map.Entry<String, List<Integer>> entry : fileLineNumbers.entrySet()) {
            sb.append("  File: ").append(entry.getKey()).append("\n");
            sb.append("  Lines: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}

public class WordTracker {
    //main loop
    public static void main(String[] args) {
        String filename = args[0];

        String option = args[1];

        if (args.length <= 3){
            String outputFilename = args[2];
        }

        processFile(filename);

    }

    private static BSTree<WordInfo> wordTree;
    
    public WordTracker() {
        wordTree = new BSTree<>();
    }

    public static void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                // Split line into words, removing punctuation and converting to lowercase
                String[] words = line.toLowerCase().split("[\\s\\p{Punct}]+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        // Create a temporary WordInfo for searching
                        WordInfo searchInfo = new WordInfo(word);
                        BSTreeNode<WordInfo> existingNode = wordTree.search(searchInfo);

                    if (existingNode == null) {
                        // Word doesn't exist in tree, add it
                        searchInfo.addOccurrence(filename, lineNumber);
                        wordTree.add(searchInfo);
                    } else {
                        // Word exists, update its occurrences
                        existingNode.getElement().addOccurrence(filename, lineNumber);
                    }
                }
            }
            lineNumber++;
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + filename);
        e.printStackTrace();
    }
    }

    public void printWordOccurrences() {
        System.out.println("Word Occurrences (in alphabetical order):");
        Iterator<WordInfo> iterator = wordTree.inorderIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
