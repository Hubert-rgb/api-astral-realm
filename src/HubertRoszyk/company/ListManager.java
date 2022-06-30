package HubertRoszyk.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListManager {
    private static  ListManager instance;
    public static ListManager getInstance() {
        if (instance == null) {
            instance = new ListManager();
        }
        return instance;
    }
    public List<List<Planet>> galaxies = new ArrayList<>();
    public List<User> users = new ArrayList<>();
    public List<Planet> planets = new ArrayList<>();
    HashMap<Integer, List<Integer>> usersPlanetsHashMap = new HashMap<>();
    HashMap<Integer, Integer> userGalaxy = new HashMap<>();
}
