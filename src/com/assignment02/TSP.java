package com.assignment02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class TSP {
    protected ArrayList<City> cityList = new ArrayList<>();

    protected String readTextFile(File file) throws IOException {
        String lineText;
        StringBuilder fileTextStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null) {
            fileTextStringBuilder.append(lineText).append("\n");
        }
        return fileTextStringBuilder.toString();
    }

    public abstract void parseTextFile(File file) throws Exception;
    public abstract ArrayList<Route> calculateShortestPath();

    public ArrayList<City> getCityList() { return cityList; }
}
