package com.assignment02;

import java.io.File;
import java.util.ArrayList;

/**
 * https://blog.csdn.net/wangqiuyun/article/details/38680151
 */
public class TSPSymmetric extends TSP {
    private static final String DATA_SECTION_START_TAG = "NODE_COORD_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    private double[][] distanceMatrix;
    private int cityNum;
    private int[] colFlag;
    private int[] rowFlag;

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

    @Override
    public ArrayList<Route> calculateDummyRoute() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City src = cityList.get(i);
            City dest = cityList.get(i == cityList.size() - 1 ? 0 : i + 1);
            double distance = getEuclideanDistance(src, dest);
            Route route = new Route(src, dest, distance);
            result.add(route);
        }
        return result;
    }

    @Override
    public ArrayList<Route> calculateShortestRoute() {
        initDistanceMatrix();
        ArrayList<Route> result = new ArrayList<>();
        double[] distance = new double[cityNum];
        int i = 0, j = 0;
        while(rowFlag[i] == 1)
        {
            for (int k = 0; k < cityNum; k++)
            {
                distance[k] = distanceMatrix[i][k];
            }
            j = selectMin(distance);
            rowFlag[i] = 0;//row set 0，already pass
            colFlag[j] = 0;//col set 0，already pass
            result.add(new Route(cityList.get(i), cityList.get(j), distanceMatrix[i][j]));
            i = j;//current point pointer to next pointer
        }
        return result;
    }

    private void initDistanceMatrix() {
        cityNum = cityList.size();
        distanceMatrix = new double[cityNum][cityNum];
        double[] x = new double[cityNum];
        double[] y = new double[cityNum];
        for (int i = 0; i < cityNum; i++)
        {
            City city = cityList.get(i);
            x[i] = city.getX();
            y[i] = city.getY();
        }
        for (int i = 0; i < cityNum - 1; i++)
        {
            distanceMatrix[i][i] = 0; //city1 to city1
            for (int j = i + 1; j < cityNum; j++)
            {
                double ds = Math.sqrt((x[i] + x[j]) * Math.abs(x[i] - x[j]) + (y[i] + y[j]) * Math.abs(y[i] -y[j]));
                distanceMatrix[i][j] = distanceMatrix[j][i] = ds;
            }
        }
        distanceMatrix[cityNum - 1][cityNum - 1] = 0; //?
        colFlag = new int[cityNum];
        colFlag[0] = 0;// first point?
        for (int i = 1; i < cityNum; i++)
        {
            colFlag[i] = 1; //init, not pass
        }
        rowFlag = new int[cityNum];
        for (int i = 0; i < cityNum; i++)
        {
            rowFlag[i] = 1; //init, not pass
        }
    }

    private int selectMin(double[] distance) // p is every distance
    {
        int j = 0;
        double m = distance[0]; //from 0
        int k = 0; //store the next city and return
        while (colFlag[j] == 0) { // start from point no pass
            j++;
            if(j>=cityNum){
                m = distance[0];
                break;
            }
            else{
                m = distance[j];
            }
        }
        for (; j < cityNum; j++) { //scan j, find short path
            if (colFlag[j] == 1) {
                if (m >= distance[j]) {
                    m = distance[j];
                    k = j;
                }
            }
        }
        return k;
    }

    private double getEuclideanDistance(City src, City dest) {
        double x1 = src.getX(), y1 = src.getY(), x2 = dest.getX(), y2 = dest.getY();
        return Math.sqrt((x1 + x2) * Math.abs(x2 - x1) + (y1 + y2) * Math.abs(y2-y1));
    }
}
