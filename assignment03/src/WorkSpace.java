import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WorkSpace extends Observable {
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

    public List<City> getCityList() { return cityList; }

    public List<Route> getRouteList() { return routeList; }
    public void setRouteList(ArrayList<Route> routeList ) {
        this.routeList = routeList;
    }
}
