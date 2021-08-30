package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws Exception{
        if (args.length == 0)
        {
            throw new IllegalArgumentException("file not exist");
        }
        String path = args[0]; //"/Users/lizhuoran/Desktop/File 1.txt";
        String text = readTextFile(path);
        String[] Token = splitWords(text);
        translate(Token);
    }
    public static String readTextFile(String path) throws IOException {
        String text = "";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        while ((str = br.readLine()) != null)
        {
            text = text + str;
        }
        return text;
    }
    public static String[] splitWords(String text)
    {
        text.split("\n");
        return text;
    }
    public static void translate(String[] tokens)
    {
        int i = 0;
        while (i <= tokens.length)
        {
            i = checkMethod(tokens, i);
        }
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

