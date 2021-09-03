package com.assignment01;

import java.util.Arrays;
import java.util.List;

/**
 * This class is the data structure for storing token information
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-08-30
 */
public class Token {
    /**
     * Types of token
     */
    public enum Type { KEYWORD, DELIMITER, WORD }

    private static final List<String> KEYWORD_LIST = Arrays.asList("if", "while");
    private static final List<String> DELIMITER_LIST = Arrays.asList("(", ")", "{", "}");

    private String data;
    private Type type;

    /**
     * Default constructor
     */
    public Token() { }

    /**
     * This constructor can be used to create a new object with the supplied <code>data</code> parameter. The type
     * field is automatically assigned based on the <code>data</code> parameter
     * @param data  token data
     */
    public Token(String data) {
        this.data = data;

        if (DELIMITER_LIST.contains(data)) this.type = Type.DELIMITER;
        else if (KEYWORD_LIST.contains(data)) this.type = Type.KEYWORD;
        else this.type = Type.WORD;
    }

    /**
     * This constructor can be used to create a new object with the supplied <code>data</code> and <code>type</code>
     * parameters
     * @param data  token data
     * @param type  token type
     */
    public Token(String data, Type type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Returns the token data
     * @return token data
     */
    public String getData() { return data; }

    /**
     * Sets the token data
     * @param data token data
     */
    public void setData(String data) { this.data = data; }

    /**
     * Returns the token type
     * @return token type
     */
    public Type getType() { return type; }

    /**
     * Sets the token type
     * @param type token type
     */
    public void setType(Type type) { this.type = type; }
}
