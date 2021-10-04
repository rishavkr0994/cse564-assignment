import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WorkSpace extends Observable {
    private static final int DEFAULT_CITY_HEIGHT = 10;
    private static final int DEFAULT_CITY_WIDTH = 10;
    private final List<City> cityList = new ArrayList<>();
    private ArrayList<Route> routeList = new ArrayList<>();

    private WorkSpace() { }

    private static WorkSpace _instance;
    public static WorkSpace getInstance() {
        if (_instance == null)
            _instance = new WorkSpace();
        return _instance;
    }

    public void addNewCity(City city) {
        cityList.add(city);
        setChanged();
        notifyObservers();
    }
    public void moveExistingCity(City city, int x, int y) {
        city.move(x, y);
        setChanged();
        notifyObservers();
    }
    public void clearAllCities() {
        cityList.clear();
        setChanged();
        notifyObservers();
    }

    public void load(File file) throws IOException {
        cityList.clear();
        String lineText;
        StringBuilder fileTextStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null) {
            fileTextStringBuilder.append(lineText).append("\n");
        }
        String[] lineList = fileTextStringBuilder.toString().split("\n");
        int lineIdx = 0;
        while(lineIdx < lineList.length) {
            String[] tokens = lineList[lineIdx].split(" ");
            City city = new City(tokens[0],Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
                    DEFAULT_CITY_WIDTH, DEFAULT_CITY_HEIGHT);
            cityList.add(city);
            lineIdx++;
        }
        setChanged();
        notifyObservers();
    }

    public void save(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        for(City city : cityList)
            writer.write(String.format("%s %d %d\n", city.getLabel(), city.getX(), city.getY()));
        writer.close();
    }

    public List<City> getCityList() { return cityList; }

    public List<Route> getRouteList() { return routeList; }
    public void setRouteList(ArrayList<Route> routeList ) {
        this.routeList = routeList;
    }
}
