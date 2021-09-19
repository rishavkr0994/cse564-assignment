package com.assignment02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads and parses a file to extract data and implements algorithms to find a dummy route and the shortest route
 * (traveling salesman greedy algorithm) between the cities.
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
     * Read and parse the file to extract data.
     *
     * @param file file containing data
     * @throws Exception failure to read file or invalid file format encountered while parsing the file
     */
    public abstract void parseTextFile(File file) throws Exception;

    /**
     * Evaluate a random path for traveling from one city to another and returning to the starting city, by traveling
     * from one city to another in order of the city number.
     *
     * @return dummy route list
     */
    public abstract ArrayList<Route> calculateDummyRoute();

    /**
     * Evaluate the shortest path (traveling salesman greedy algorithm) for traveling from one city to another and
     * returning to the starting city.
     *
     * @return shortest route list
     */
    public abstract ArrayList<Route> calculateShortestRoute();

    /**
     * Get the city list.
     * @return city list
     */
    public ArrayList<City> getCityList() {
        return cityList;
    }
}
