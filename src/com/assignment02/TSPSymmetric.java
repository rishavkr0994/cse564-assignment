package com.assignment02;

import java.io.File;
import java.util.ArrayList;

/**
 * Reads and parses a symmetric data file to extract city information and provides algorithms to plot a dummy route and
 * the shortest route between the cities (using a greedy travelling salesman problem algorithm).
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class TSPSymmetric extends TSP {
    private static final String DATA_SECTION_START_TAG = "NODE_COORD_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    private int cityNum;
    private double[][] distanceMatrix;
    private int[] colFlag;
    private int[] rowFlag;

    /**
     * Read and parse the file to extract city data
     * @param file file containing symmetric data
     * @throws Exception failure to read file or invalid file format encountered while parsing the file
     */
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
            City city = new City();
            city.setNum(Integer.parseInt(tokens[0]));
            city.setX(Double.parseDouble(tokens[1]));
            city.setY(Double.parseDouble(tokens[2]));
            cityList.add(city);
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
            City src = cityList.get(i);
            City dest = null;
            if (i == cityList.size() - 1)
                dest = cityList.get(0);
            else dest = cityList.get(i + 1);

            double distance = getEuclideanDistance(src, dest);
            Route route = new Route();
            route.setSrc(src);
            route.setDest(dest);
            route.setDist(distance);
            result.add(route);
        }
        return result;
    }

    /**
     * Evaluate the shortest path for travelling from one city to another and returning to the starting city using a
     * greedy travelling salesman problem algorithm
     * </p>
     * Source: https://blog.csdn.net/wangqiuyun/article/details/38680151
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
            distanceMatrix[i][i] = 0;
            for (int j = i + 1; j < cityNum; j++) {
                double distance = getEuclideanDistance(cityList.get(i), cityList.get(j));
                distanceMatrix[i][j] = distanceMatrix[j][i] = distance;
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

    private double getEuclideanDistance(City src, City dest) {
        double x1 = src.getX(), y1 = src.getY(), x2 = dest.getX(), y2 = dest.getY();
        return Math.sqrt((x1 + x2) * Math.abs(x1 - x2) + (y1 + y2) * Math.abs(y1 - y2));
    }
}
