package com.assignment02;

import java.io.*;
import java.util.ArrayList;

public class TSPAsymmetric extends TSP {
    private static final String DATA_SECTION_START_TAG = "EDGE_WEIGHT_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    @Override
    public void parseTextFile(File file) throws Exception {
        String fileText = readTextFile(file);
        String[] lineList = fileText.split("\n");

        int lineIdx = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_START_TAG))
            lineIdx++;
        lineIdx++;

        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_END_TAG)) {
            String[] tokens = lineList[lineIdx].split(" ");
            // City city = new City();
            // city.setNum(Integer.parseInt(tokens[0]));
            // city.setX(Double.valueOf(tokens[1]));
            // city.setY(Double.valueOf(tokens[2]));
            // cityList.add(city);
            lineIdx++;
        }
        throw new Exception("Not Implemented");
    }

    @Override
    public ArrayList<Route> calculateShortestPath() {
        return null;
    }
}
