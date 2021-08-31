package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This program implements a translator
 * @author  Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since   2021-08-30
 */
public class Main {
    /**
     * Reads a file and translates it to the expected output form
     * @param args  command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            throw new IllegalArgumentException("file path is not provided");
        String text = readTextFile(args[0]); // "/Users/lizhuoran/Desktop/File 1.txt";
        ArrayList<String> tokens = splitWords(text);
        System.out.println(tokens);
        translate(tokens);
    }

    /**
     * Reads the content of a file as a string
     * @param path  the location of the file which is to be read
     * @return      the content of the file as a string
     * @throws IOException
     */
    private static String readTextFile(String path) throws IOException {
        String text = "";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        while ((str = br.readLine()) != null) {
            text = text + str + "\n";
        }
        return text;
    }

    /**
     * Tokenizes the source code as text to a list of words / tokens
     * @param text  text comprising the source code to be parsed
     * @return      list of tokens
     */
    private static ArrayList<String> splitWords(String text) {
        ArrayList<String> wordList = new ArrayList<>();
        String[] lineList = text.split("\n");
        for (String line: Arrays.stream(lineList).filter(x -> x.isEmpty() == false).toList()) {
            ArrayList<String> wordListForLine = splitLine(line);
            wordList.addAll(wordListForLine);
        }
        return wordList;
    }

    /**
     * Tokenizes a line from source code to a list of words / tokens
     * @param line  a line from the source code to be parsed
     * @return      list of tokens
     */
    private static ArrayList<String> splitLine(String line) {
        ArrayList<String> tokenList = new ArrayList<>();

        int index = 0; String token = "";
        while (index < line.length()) {
            char ch = line.charAt(index);
            if (Character.isLetterOrDigit(ch)) {
                token += ch;
            }
            else if (ch == '(' || ch == ')' || ch == '{' || ch == '}') {
                if (token.length() > 0) {
                    tokenList.add(token);
                    token = "";
                }
                tokenList.add(Character.toString(ch));
            }
            index++;
        }

        if (token.length() > 0)
            tokenList.add(token);
        return tokenList;
    }

    /**
     * Translate the list of tokens to the expected output
     * @param tokens    list of words / tokens to be translated
     */
    private static void translate(ArrayList<String> tokens)
    {
        int currentToken = 0;
        while (currentToken < tokens.size()) {
            currentToken = checkMethod(tokens, currentToken);
            System.out.println();
        }
    }

    /**
     * Syntactic validation of a method construct
     * @param tokens        list of tokens for the source code
     * @param currentWord   current token pointer in the <code>tokens</code>
     * @return              next token pointer
     */
    public static int checkMethod(ArrayList<String> tokens, int currentWord)
    {
        if (tokens.get(currentWord++).matches("[A-za-z0-9]+")) {
            if (tokens.get(currentWord++).equals("(") && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
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
            } else throw new RuntimeException("Syntax Error");
        }
        throw new RuntimeException("Syntax Error");
    }

    /**
     * Syntactic validation of a method construct
     * @param tokens        list of tokens for the source code
     * @param currentWord   current token pointer in the <code>tokens</code>
     * @return              next token pointer
     */
    public static int checkIf(ArrayList<String> tokens, int currentWord)
    {
        if (tokens.get(currentWord++).matches("if")) {
            if (tokens.get(currentWord++).equals("(") && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
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
            } else throw new RuntimeException("Syntax Error");
        }
        throw new RuntimeException("Syntax Error");
    }

    /**
     * Syntactic validation of a 'while' construct
     * @param tokens        list of tokens for the source code
     * @param currentWord   current token pointer in the <code>tokens</code>
     * @return              next token pointer
     */
    public static int checkWhile(ArrayList<String> tokens , int currentWord)
    {
        if (tokens.get(currentWord++).matches("while")) {
            if (tokens.get(currentWord++).equals("(") && tokens.get(currentWord++).equals(")") && tokens.get(currentWord++).equals("{")) {
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
            } else throw new RuntimeException("Syntax Error");
        }
        throw new RuntimeException("Syntax Error");
    }

    private static List<String> reservedKeyword = Arrays.asList("if", "while", "{", "}", "(", ")");

    /**
     * Syntactic validation of a instruction
     * @param tokens        list of tokens for the source code
     * @param currentWord   current token pointer in the <code>tokens</code>
     * @return              next token pointer
     */
    public static int checkInstruction(ArrayList<String> tokens, int currentWord)
    {
        if (!reservedKeyword.contains(tokens.get(currentWord)))  {
            System.out.print("-");
            return ++currentWord;
        } else throw new RuntimeException("Syntax Error: Invalid Instruction");
    }
}
