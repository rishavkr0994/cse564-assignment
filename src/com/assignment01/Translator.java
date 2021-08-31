package com.assignment01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The program implements a translator. It takes a text file as input, reads the text and then performs tokenization. It
 * then parses the tokens to generate the symbolic notation. In this process, it throws a RuntimeException object in
 * case the syntax is invalid.
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-08-30
 */
public class Translator {
    /**
     * Reads a text file (path is the first command line argument) and translates the text into the symbolic notation
     * @param args  array of command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            throw new IllegalArgumentException("file path is not provided");
        String text = readTextFile(args[0]);
        ArrayList<String> tokenList = splitWords(text);
        translate(tokenList);
    }

    /**
     * Reads the content of a text file into a string and returns it
     * @param path  location of the text file which is to be read
     * @return      content of the text file as a string
     * @throws IOException
     */
    private static String readTextFile(String path) throws IOException {
        String fileText = ""; String lineText;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null) {
            fileText = fileText + lineText + "\n";
        }
        return fileText;
    }

    /**
     * Performs tokenization on the text and returns the list of tokens
     * @param text  text to be tokenized
     * @return      list of tokens
     */
    private static ArrayList<String> splitWords(String text) {
        ArrayList<String> wordList = new ArrayList<>();
        String[] lineList = text.split("\n");
        for (String line: Arrays.stream(lineList).filter(x -> !x.isEmpty()).toList()) {
            ArrayList<String> wordListForLine = splitLine(line);
            wordList.addAll(wordListForLine);
        }
        return wordList;
    }

    /**
     * Tokenizes a line of text and returns the list of tokens
     * @param line  line of text to be tokenized
     * @return      list of tokens generated from tokenization
     */
    private static ArrayList<String> splitLine(String line) {
        ArrayList<String> tokenList = new ArrayList<>();

        String currentToken = "";
        for (int index = 0; index < line.length(); index++) {
            char ch = line.charAt(index);
            if (Character.isLetterOrDigit(ch)) currentToken += ch;
            else if (ch == '(' || ch == ')' || ch == '{' || ch == '}') {
                if (!currentToken.isEmpty()) {
                    tokenList.add(currentToken);
                    currentToken = "";
                }
                tokenList.add(Character.toString(ch));
            }
        }

        if (!currentToken.isEmpty())
            tokenList.add(currentToken);
        return tokenList;
    }

    /**
     * Translate the list of tokens into the symbolic notation. It also performs syntactic validation of the tokens and
     * throws as RuntimeException in case of invalid syntax
     * @param tokens    list of tokens to be translated
     */
    private static void translate(ArrayList<String> tokens) {
        int currentToken = 0;
        while (currentToken < tokens.size()) {
            currentToken = checkMethod(tokens, currentToken);
            System.out.println();
        }
    }

    /**
     * Translates and syntactically validates a method construct
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkMethod(ArrayList<String> tokens, int currentWord) {
        if (tokens.get(currentWord++).matches("[A-za-z0-9]+") && tokens.get(currentWord++).equals("(")
                && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
            System.out.print("[");
            while (currentWord < tokens.size()) {
                switch (tokens.get(currentWord)) {
                    case "if":
                        currentWord = checkIf(tokens, currentWord);
                        break;
                    case "while":
                        currentWord = checkWhile(tokens, currentWord);
                        break;
                    case "}":
                        System.out.print("]");
                        return ++currentWord;
                    default:
                        currentWord = checkInstruction(tokens, currentWord);
                        break;
                }
            }
        }
        throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates an if construct
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkIf(ArrayList<String> tokens, int currentWord) {
        if (tokens.get(currentWord++).matches("if") && tokens.get(currentWord++).equals("(")
                && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
            System.out.print("<");
            while (currentWord < tokens.size()) {
                switch (tokens.get(currentWord)) {
                    case "if":
                        currentWord = checkIf(tokens, currentWord);
                        break;
                    case "while":
                        currentWord = checkWhile(tokens, currentWord);
                        break;
                    case "}":
                        System.out.print(">");
                        return ++currentWord;
                    default:
                        currentWord = checkInstruction(tokens, currentWord);
                        break;
                }
            }
        }
        throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates a while construct
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkWhile(ArrayList<String> tokens , int currentWord) {
        if (tokens.get(currentWord++).matches("while") && tokens.get(currentWord++).equals("(")
                && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
            System.out.print("(");
            while (currentWord < tokens.size()) {
                switch (tokens.get(currentWord)) {
                    case "if":
                        currentWord = checkIf(tokens, currentWord);
                        break;
                    case "while":
                        currentWord = checkWhile(tokens, currentWord);
                        break;
                    case "}":
                        System.out.print(")");
                        return ++currentWord;
                    default:
                        currentWord = checkInstruction(tokens, currentWord);
                        break;
                }
            }
        }
        throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates an instruction
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkInstruction(ArrayList<String> tokens, int currentWord) {
        if (tokens.get(currentWord).matches("[A-za-z0-9]+")) {
            System.out.print("-");
            return ++currentWord;
        } else throw new RuntimeException("Syntax Error");
    }
}
