package com.assignment02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description Text
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
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

    /**
     * Description Text
     * @param file
     * @throws Exception
     */
    public abstract void parseTextFile(File file) throws Exception;

    /**
     * Description Text
     * @return
     */
    public abstract ArrayList<Route> calculateDummyRoute();

    /**
     * Description Text
     * @return
     */
    public abstract ArrayList<Route> calculateShortestRoute();

    /**
     * Description Text
     * @return
     */
    public ArrayList<City> getCityList() { return cityList; }
}
