package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception{
        if (args.length == 0)
            throw new IllegalArgumentException("file path is not provided");
        String text = readTextFile(args[0]); // "/Users/lizhuoran/Desktop/File 1.txt";
        ArrayList<String> tokens = splitWords(text);
        System.out.println(tokens);
        translate(tokens);
    }

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

    private static ArrayList<String> splitWords(String text) {
        ArrayList<String> wordList = new ArrayList<>();
        String[] lineList = text.split("\n");
        for (String line: Arrays.stream(lineList).filter(x -> x.isEmpty() == false).toList()) {
            ArrayList<String> wordListForLine = splitLine(line);
            wordList.addAll(wordListForLine);
        }
        return wordList;
    }

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

    private static void translate(ArrayList<String> tokens)
    {
        // int i = 0;
        // while (i <= tokens.size())
        // {
        //     i = checkMethod(tokens, i);
        // }
    }
    public static int checkMethod(String[] tokens, int currentWord)
    {
        if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
            currentWord++;
        else {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "("))
            currentWord++;
        else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], ")"))
        {
            currentWord ++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "{"))
        {
            currentWord ++;
            System.out.print("[ ");
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "if"))
        {
            checkIf(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "while"))
        {
            checkWhile(tokens, currentWord);
        }else if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
        {
            checkInstruction(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "}"))
        {
            System.out.print(" ]");
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        return currentWord;
    }
    public static int checkIf(String[] tokens, int currentWord)
    {
        if (Objects.equals(tokens[currentWord], "if"))
        {
            currentWord ++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "("))
        {
            currentWord ++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], ")"))
        {
            currentWord ++;
        }else {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "{"))
        {
            System.out.print("< ");
            currentWord++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "if"))
        {
            currentWord = checkIf(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "while"))
        {
            currentWord = checkWhile(tokens, currentWord);
        }else if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
        {
            currentWord = checkInstruction(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "}"))
        {
            System.out.print(" >");
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        return currentWord;
    }
    public static int checkWhile(String[] tokens, int currentWord)
    {
        if (Objects.equals(tokens[currentWord], "while"))
        {
            currentWord ++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "("))
        {
            currentWord ++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], ")"))
        {
            currentWord ++;
        }else {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "{"))
        {
            System.out.print("( ");
            currentWord++;
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        if (Objects.equals(tokens[currentWord], "if"))
        {
            checkIf(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "while"))
        {
            checkWhile(tokens, currentWord);
        }else if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
        {
            currentWord = checkInstruction(tokens, currentWord);
            System.out.print(" )");
        }else if (Objects.equals(tokens[currentWord], "}"))
        {
            System.out.print(" )");
            return currentWord;
        } else
        {
            throw new RuntimeException("Syntax error");
        }
        return currentWord;
    }
    public static int checkInstruction(String[] tokens, int currentWord)
    {
        if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
        {
            System.out.print("- ");
            currentWord ++;
        }
        if (Objects.equals(tokens[currentWord], "if"))
        {
            checkIf(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "while"))
        {
            checkWhile(tokens, currentWord);
        }else if (!Objects.equals(tokens[currentWord], "if") && !Objects.equals(tokens[currentWord], "while") && !Objects.equals(tokens[currentWord], "(") && !Objects.equals(tokens[currentWord], ")") && !Objects.equals(tokens[currentWord], "{") && !Objects.equals(tokens[currentWord], "}"))
        {
            currentWord = checkInstruction(tokens, currentWord);
        }else if (Objects.equals(tokens[currentWord], "}"))
        {
        }else
        {
            throw new RuntimeException("Syntax error");
        }
        return currentWord;
    }
}

