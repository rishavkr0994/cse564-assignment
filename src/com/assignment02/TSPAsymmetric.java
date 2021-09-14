package com.assignment02;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Reads and parses an asymmetric data file to extract distance information between the cities and provides algorithms
 * to plot a dummy route and the shortest route between the cities (using a greedy traveling salesman problem algorithm).
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class TSPAsymmetric extends TSP {
    private static final String CITY_COUNT_TAG = "DIMENSION";
    private static final String DATA_SECTION_START_TAG = "EDGE_WEIGHT_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    private final ArrayList<Route> routeList = new ArrayList<>();

    private int cityNum;
    private double[][] distanceMatrix;
    private int[] colFlag;
    private int[] rowFlag;

    /**
     * Read and parse the file to extract distance information between the cities
     * @param file file containing asymmetric data
     * @throws Exception failure to read file or invalid file format encountered while parsing the file
     */
    @Override
    public void parseTextFile(File file) throws Exception {
        String fileText = readTextFile(file);
        String[] lineList = fileText.split("\n");

        int lineIdx = 0, cityCount = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_START_TAG)) {
            List<String> tokens = Arrays.stream(lineList[lineIdx].split(":"))
                    .map(String::trim).collect(Collectors.toList());

            if (tokens.size() == 2 && tokens.get(0).equals(CITY_COUNT_TAG))
                cityCount = Integer.parseInt(tokens.get(1));
            lineIdx++;
        }
        lineIdx++;

        cityList = generateRandomCityList(cityCount);

        int srcCityIdx = 0; int destCityIdx = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_END_TAG)) {
            if (!lineList[lineIdx].isBlank()) {
                String[] distanceList = lineList[lineIdx].trim().split("\\s+");
                for (int i = 0; i < distanceList.length; i++) {
                    int distance = Integer.parseInt(distanceList[i]);
                    Route route = new Route();
                    route.setSrc(cityList.get(srcCityIdx));
                    route.setDest(cityList.get(destCityIdx));
                    route.setDist(distance);
                    routeList.add(route);

                    if (destCityIdx == (cityCount - 1)) {
                        destCityIdx = 0; srcCityIdx++;
                    } else destCityIdx++;
                }
            }
            lineIdx++;
        }
    }

    /**
     * Evaluate a random path for travelling from one city to another and returning to the starting city, by travelling
     * from one city to another in order of the city number
     *
     * @return dummy route list
     */
    @Override
    public ArrayList<Route> calculateDummyRoute() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            int colOffset = (i == cityList.size() - 1 ? 0 : i + 1);
            Route route = routeList.get(i * cityList.size() + colOffset);
            result.add(route);
        }
        return result;
    }

    /**
     * Evaluate the shortest path for travelling from one city to another and returning to the starting city using a
     * greedy travelling salesman problem algorithm
     *
     * @return shortest route list
     */
    @Override
    public ArrayList<Route> calculateShortestRoute() {
        initDistanceMatrix();
        ArrayList<Route> result = new ArrayList<>();

        int i = 0;
        while (rowFlag[i] == 1) {
            int j = getNearestCityIndex(distanceMatrix[i]);
            if (j == -1) j = 0;

            rowFlag[i] = 0; colFlag[j] = 0;

            Route route = new Route();
            route.setSrc(cityList.get(i));
            route.setDest(cityList.get(j));
            route.setDist(distanceMatrix[i][j]);
            result.add(route);

            i = j;
        }
        return result;
    }

    private void initDistanceMatrix() {
        cityNum = cityList.size();

        distanceMatrix = new double[cityNum][cityNum];
        for (int i = 0; i < cityNum; i++) {
            for (int j = 0; j < cityNum; j++) {
                if (i != j)
                    distanceMatrix[i][j] = routeList.get(i * cityNum + j).getDist();
                else distanceMatrix[i][j] = 0;
            }
        }

        colFlag = new int[cityNum];
        colFlag[0] = 0;
        for (int i = 1; i < cityNum; i++)
            colFlag[i] = 1;

        rowFlag = new int[cityNum];
        for (int i = 0; i < cityNum; i++)
            rowFlag[i] = 1;
    }

    private int getNearestCityIndex(double[] distance) {
        int nearestCityIndex = -1; double minDistance = 0;
        for (int j = 0; j < cityNum; j++) {
            if (colFlag[j] == 1) {
                if (nearestCityIndex == -1 || minDistance >= distance[j]) {
                    minDistance = distance[j];
                    nearestCityIndex = j;
                }
            }
        }
        return nearestCityIndex;
    }

    private ArrayList<City> generateRandomCityList(int count) {
        ArrayList<City> randomCityList = new ArrayList<>();

        int randMin = 100, randMax = randMin + count * 10;
        Random random = new Random();

        for (int cityNum = 1; cityNum <= count; cityNum++) {
            City city = new City();
            city.setNum(cityNum);
            do {
                city.setX((double) random.nextInt(randMax - randMin + 1) + randMin);
                city.setY((double) random.nextInt(randMax - randMin + 1) + randMin);
            } while (randomCityList.contains(city));
            randomCityList.add(city);
        }
        return randomCityList;
    }
}
