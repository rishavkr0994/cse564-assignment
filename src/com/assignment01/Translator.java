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
        ArrayList<Token> tokenList = splitWords(text);
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
    private static ArrayList<Token> splitWords(String text) {
        ArrayList<Token> tokenList = new ArrayList<>();
        String[] lineList = text.split("\n");
        for (String line: Arrays.stream(lineList).filter(x -> !x.isEmpty()).toList()) {
            ArrayList<Token> tokenListForLine = splitLine(line);
            tokenList.addAll(tokenListForLine);
        }
        return tokenList;
    }

    /**
     * Tokenizes a line of text and returns the list of tokens
     * @param line  line of text to be tokenized
     * @return      list of tokens generated from tokenization
     */
    private static ArrayList<Token> splitLine(String line) {
        ArrayList<Token> tokenList = new ArrayList<>();
        String currentToken = "";
        for (int index = 0; index < line.length(); index++) {
            char ch = line.charAt(index);
            if (Character.isLetterOrDigit(ch)) currentToken += ch;
            else if (ch == '(' || ch == ')' || ch == '{' || ch == '}') {
                if (!currentToken.isEmpty()) {
                    tokenList.add(new Token(currentToken));
                    currentToken = "";
                }
                tokenList.add(new Token(Character.toString(ch)));
            }
        }

        if (!currentToken.isEmpty())
            tokenList.add(new Token(currentToken));
        return tokenList;
    }

    /**
     * Translate the list of tokens into the symbolic notation. It also performs syntactic validation of the tokens and
     * throws as RuntimeException in case of invalid syntax
     * @param tokens    list of tokens to be translated
     */
    private static void translate(ArrayList<Token> tokens) {
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
    private static int checkMethod(ArrayList<Token> tokens, int currentWord) {
        if (tokens.get(currentWord).getType() == Token.Type.WORD &&
                tokens.get(++currentWord).getData().equals("(") &&
                tokens.get(++currentWord).getData().equals(")") &&
                tokens.get(++currentWord).getData().equals("{")) {
            System.out.print("[");
            currentWord = checkBody(tokens, ++currentWord);
            if (tokens.get(currentWord).getData().equals("}")) {
                System.out.print("]");
                return ++currentWord;
            } else throw new RuntimeException("Syntax Error");
        } else throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates an if construct
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkIf(ArrayList<Token> tokens, int currentWord) {
        if (tokens.get(currentWord).getType() == Token.Type.KEYWORD && tokens.get(currentWord).getData().equals("if") &&
                tokens.get(++currentWord).getData().equals("(") &&
                tokens.get(++currentWord).getData().equals(")") &&
                tokens.get(++currentWord).getData().equals("{")) {
            System.out.print("<");
            currentWord = checkBody(tokens, ++currentWord);
            if (tokens.get(currentWord).getData().equals("}")) {
                System.out.print(">");
                return ++currentWord;
            } else throw new RuntimeException("Syntax Error");
        } else throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates a while construct
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkWhile(ArrayList<Token> tokens , int currentWord) {
        if (tokens.get(currentWord).getType() == Token.Type.KEYWORD && tokens.get(currentWord).getData().equals("while") &&
                tokens.get(++currentWord).getData().equals("(") &&
                tokens.get(++currentWord).getData().equals(")") &&
                tokens.get(++currentWord).getData().equals("{")) {
            System.out.print("(");
            currentWord = checkBody(tokens, ++currentWord);
            if (tokens.get(currentWord).getData().equals("}")) {
                System.out.print(")");
                return ++currentWord;
            } else throw new RuntimeException("Syntax Error");
        } else throw new RuntimeException("Syntax Error");
    }

    /**
     * Translates and syntactically validates an instruction
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkInstruction(ArrayList<Token> tokens, int currentWord) {
        if (tokens.get(currentWord).getType() == Token.Type.WORD) {
            System.out.print("-");
            return ++currentWord;
        } else throw new RuntimeException("Syntax Error");
    }

    /**
     * Syntactically validates a body (between the opening and closing braces)
     * @param tokens        list of tokens to be translated
     * @param currentWord   current token pointer in the list of tokens
     * @return              next token pointer for further translation
     */
    private static int checkBody(ArrayList<Token> tokens, int currentWord) {
        while (currentWord < tokens.size()) {
            Token token = tokens.get(currentWord);
            switch (token.getType()) {
                case KEYWORD:
                    if (token.getData().equals("if"))
                        currentWord = checkIf(tokens, currentWord);
                    else if (token.getData().equals("while"))
                        currentWord = checkWhile(tokens, currentWord);
                    break;
                case WORD:
                    currentWord = checkInstruction(tokens, currentWord);
                    break;
                case DELIMITER:
                    return currentWord;
            }
        }
        return currentWord;
    }
}
