package com.assignment02;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TSPAsymmetric extends TSP {
    private static final String CITY_COUNT_TAG = "DIMENSION";
    private static final String DATA_SECTION_START_TAG = "EDGE_WEIGHT_SECTION";
    private static final String DATA_SECTION_END_TAG = "EOF";

    private ArrayList<Route> routeList = new ArrayList<>();

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

        int srcCityIdx = 0;
        while (lineIdx < lineList.length && !lineList[lineIdx].equals(DATA_SECTION_END_TAG)) {
            String[] distanceList = lineList[lineIdx].trim().split("\\s+");
            for (int destCityIdx = 0; destCityIdx < distanceList.length; destCityIdx++) {
                if (srcCityIdx == destCityIdx) continue;

                int distance = Integer.parseInt(distanceList[destCityIdx]);
                routeList.add(new Route(cityList.get(srcCityIdx), cityList.get(destCityIdx), distance));
            }
            lineIdx++; srcCityIdx++;
        }
    }

    @Override
    public ArrayList<Route> calculateShortestPath() {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);
            double x1 = city.getX();
            double y1 = city.getY();
            Route route = new Route();
            route.setSrc(city);
            if (cityList.size() == i + 1)
                city = cityList.get(0);
            else city = cityList.get(++i);
            double x2 = city.getX();
            double y2 = city.getY();
            route.setDest(city);

            double distance = Math.sqrt((x1 + x2) * Math.abs(x2 - x1) + (y1 + y2) * Math.abs(y2-y1));
            route.setDist(distance);
            result.add(route);
        }
        return result;
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
