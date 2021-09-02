package com.assignment01;

import java.util.Arrays;
import java.util.List;

public class Token {
    public enum Type { KEYWORD, DELIMITER, WORD }

    private static final List<String> KEYWORD_LIST = Arrays.asList("if", "while");
    private static final List<String> DELIMITER_LIST = Arrays.asList("(", ")", "{", "}");

    private String data;
    private Type type;

    public Token() { }
    public Token(String data) {
        this.data = data;

        if (DELIMITER_LIST.contains(data)) this.type = Type.DELIMITER;
        else if (KEYWORD_LIST.contains(data)) this.type = Type.KEYWORD;
        else this.type = Type.WORD;
    }
    public Token(String data, Type type) {
        this.data = data;
        this.type = type;
    }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}
