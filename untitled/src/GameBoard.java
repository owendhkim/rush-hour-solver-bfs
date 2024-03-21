import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameBoard {
    int vehicleCount;
    ArrayList<State> wonStates = new ArrayList<>();
    ArrayList<State> wonStates2 = new ArrayList<>();
    ArrayList<State> shortestWonStates2 = new ArrayList<>();



    public ArrayList<State> getShortestWonStates() {
        return shortestWonStates;
    }
    public void setShortestWonStates(ArrayList<State> shortestWonStates) {
        this.shortestWonStates = shortestWonStates;
    }
    ArrayList<State> shortestWonStates = new ArrayList<>();
    ArrayList<ArrayList<Integer>> initialLocations = new ArrayList<>();
    public State getInitialState() {
        return initialState;
    }
    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }
    State initialState;
    int [] grid = new int[36];
    HashMap<Integer, State> hm = new HashMap<>();
    HashMap<Integer, State> hm2 = new HashMap<>();

    public ArrayList<ArrayList<Integer>> getInitialLocations() {
        return initialLocations;
    }

    public void setInitialLocations(ArrayList<ArrayList<Integer>> initialLocations) {
        this.initialLocations = initialLocations;
    }
    public ArrayList<Pair> bfs(State state){
        ArrayList<Pair> pairs = new ArrayList<>();
        Queue<State> queue = new LinkedList<>();
        state.setLayer(0);
        hm.put(Arrays.hashCode(arrlistTointarr(state.getStateLocations())), state);
        queue.add(state);

        while(!queue.isEmpty()){
            State oldState = new State(queue.poll());
            if(oldState.goalReached()){
                while(oldState.getParent() != null){
                    pairs.add(calcPair(oldState));
                    oldState = oldState.getParent();
                }
                return pairs;
            }
            for (ArrayList<ArrayList<Integer>> arr : allPossibleNextStates(oldState.getStateLocations())){
                if (!(hm.containsKey(Arrays.hashCode(arrlistTointarr(arr))))){
                    State newState = new State();
                    newState.setStateLocations(arr);
                    newState.setLayer(oldState.getLayer()+1);
                    newState.setParent(oldState);
                    hm.put(Arrays.hashCode(arrlistTointarr(arr)), newState);
                    queue.add(newState);
                }
            }
        }
        return null;
    }
    public int getNumOfPaths(){
        Queue<State> queue = new LinkedList<>();
        int numOfPaths = 0;
        this.initialState.setLayer(0);
        this.initialState.setNumOfPaths(1);
        this.initialState.setVisited(true);
        hm2.put(Arrays.hashCode(arrlistTointarr(this.initialState.getStateLocations())), this.initialState);
        queue.add(this.initialState);

        while(!queue.isEmpty()){
            State oldState = new State(queue.poll());
            if(oldState.goalReached()){
                wonStates2.add(oldState);
                continue;
            }
            for (State arr : allPossibleNextStates2(oldState)){
                if (!(hm2.containsKey(Arrays.hashCode(arrlistTointarr(arr.getStateLocations()))))){
                    arr.setVisited(true);
                    arr.setParent(oldState);
                    arr.setNumOfPaths(oldState.getNumOfPaths());
                    arr.setLayer(oldState.getLayer()+1);
                    hm2.put(Arrays.hashCode(arrlistTointarr(arr.getStateLocations())), arr);
                    queue.add(arr);
                }
                else{
                    if(hm2.get(Arrays.hashCode(arrlistTointarr(arr.getStateLocations()))).getLayer() == oldState.getLayer()+1){
                        hm2.get(Arrays.hashCode(arrlistTointarr(arr.getStateLocations()))).setNumOfPaths(oldState.getNumOfPaths() + hm2.get(Arrays.hashCode(arrlistTointarr(arr.getStateLocations()))).getNumOfPaths());
                    }
                }
            }
        }
        int min = wonStates2.get(0).getLayer();
        for (State s1 : wonStates2) {
            if(s1.getLayer()<min){
                min = s1.getLayer();
            }
        }
        for (State s2 : wonStates2) {
            if(s2.getLayer()==min){
                shortestWonStates2.add(s2);
            }
        }
        for (State s4 : shortestWonStates2) {
            numOfPaths = s4.getNumOfPaths() + numOfPaths;

        }
        return numOfPaths;
    }
    public Pair calcPair(State s) {
        int index = 0;
        ArrayList<ArrayList<Integer>> l1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> l2 = new ArrayList<>();
        l1.addAll(s.getStateLocations());
        l2.addAll(s.getParent().getStateLocations());
        l1.removeAll(l2);
        ArrayList<Integer> v1 = new ArrayList<>(l1.get(0));

        ArrayList<ArrayList<Integer>> l3 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> l4 = new ArrayList<>();
        l3.addAll(s.getStateLocations());
        l4.addAll(s.getParent().getStateLocations());
        l4.removeAll(l3);
        ArrayList<Integer> v2 = new ArrayList<>(l4.get(0));
        for (int i = 0; i < s.getStateLocations().size(); i++) {
            if (s.getStateLocations().get(i).equals(v1)) {
                index = i;
            }
        }
        if (v1.get(0) - v2.get(0) == 1) {
            return new Pair(index, 'e');
        }
        if (v1.get(0) - v2.get(0) == -1) {
            return new Pair(index, 'w');
        }
        if (v1.get(0) - v2.get(0) == 6) {
            return new Pair(index, 's');
        }
        if (v1.get(0) - v2.get(0) == -6) {
            return  new Pair(index, 'n');
        }
    return null;
    }
    public int[] arrlistTointarr(ArrayList<ArrayList<Integer>> l){
        int [] returnGrid = new int[36];
        Arrays.fill(returnGrid, -1);
        for(int m = 0; m < l.size(); m++) {
            for (Integer j : l.get(m)){
                returnGrid [j-1] = m;
            }
        }
        return returnGrid;
    }
    public ArrayList<ArrayList<ArrayList<Integer>>> allPossibleNextStates(ArrayList<ArrayList<Integer>> l){
        ArrayList<ArrayList<ArrayList<Integer>>> allPossibleNextStatesList = new ArrayList<ArrayList<ArrayList<Integer>>>();
        int [] grid = new int[36];
        Arrays.fill(grid, -1);
        for(int i = 0; i < l.size(); i++) {
            for (Integer j : l.get(i)){
                grid [j-1] = j;
            }
        }
        for(int i = 0; i < l.size(); i++){
            if(dirRightLeft(l.get(i)) && l.get(i).get(0)%6 != 1 && grid[l.get(i).get(0)-1-1] == -1){         //if vehicle dir is R or L && first location of the vehicle can move to left
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : l) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) - 1);                          //move all elements to right
                }
                allPossibleNextStatesList.add(inputList);
            }
            if(dirRightLeft(l.get(i)) && l.get(i).get(l.get(i).size()-1)%6 != 0 && grid[l.get(i).get(l.get(i).size()-1)-1+1] == -1){   //if vehicle dir is R or L && first location of the vehicle can move to right
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : l) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) + 1);                          //move all elements to right
                }
                allPossibleNextStatesList.add(inputList);
            }
            if(!(dirRightLeft(l.get(i))) && l.get(i).get(0)%7 != l.get(i).get(0) && grid[l.get(i).get(0)-1-6] == -1){
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : l) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) - 6);                          //move all elements to right
                }
                allPossibleNextStatesList.add(inputList);
            }
            if(!(dirRightLeft(l.get(i))) && l.get(i).get(l.get(i).size()-1)+6 < 36 && grid[l.get(i).get(l.get(i).size()-1)-1+6] == -1){
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : l) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < l.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) + 6);                          //move all elements to right
                }
                allPossibleNextStatesList.add(inputList);
            }
        }
        return allPossibleNextStatesList;
    }
    public ArrayList<State> allPossibleNextStates2(State s){
        ArrayList<State> allPossibleNextStatesList = new ArrayList<>();
        int [] grid = new int[36];
        Arrays.fill(grid, -1);
        for(int i = 0; i < s.stateLocations.size(); i++) {
            for (Integer j : s.stateLocations.get(i)){
                grid [j-1] = j;
            }
        }
        for(int i = 0; i < s.stateLocations.size(); i++){
            if(dirRightLeft(s.stateLocations.get(i)) && s.stateLocations.get(i).get(0)%6 != 1 && grid[s.stateLocations.get(i).get(0)-1-1] == -1){         //if vehicle dir is R or L && first location of the vehicle can move to left
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : s.stateLocations) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) - 1);                          //move all elements to right
                }
                State returnState = new State();
                returnState.setStateLocations(inputList);
                allPossibleNextStatesList.add(returnState);
            }
            if(dirRightLeft(s.stateLocations.get(i)) && s.stateLocations.get(i).get(s.stateLocations.get(i).size()-1)%6 != 0 && grid[s.stateLocations.get(i).get(s.stateLocations.get(i).size()-1)-1+1] == -1){   //if vehicle dir is R or L && first location of the vehicle can move to right
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : s.stateLocations) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) + 1);                          //move all elements to right
                }
                State returnState = new State();
                returnState.setStateLocations(inputList);
                allPossibleNextStatesList.add(returnState);            }
            if(!(dirRightLeft(s.stateLocations.get(i))) && s.stateLocations.get(i).get(0)%7 != s.stateLocations.get(i).get(0) && grid[s.stateLocations.get(i).get(0)-1-6] == -1){
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : s.stateLocations) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < inputList.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) - 6);                          //move all elements to right
                }
                State returnState = new State();
                returnState.setStateLocations(inputList);
                allPossibleNextStatesList.add(returnState);
            }
            if(!(dirRightLeft(s.stateLocations.get(i))) && s.stateLocations.get(i).get(s.stateLocations.get(i).size()-1)+6 < 36 && grid[s.stateLocations.get(i).get(s.stateLocations.get(i).size()-1)-1+6] == -1){
                ArrayList<ArrayList<Integer>> inputList = new ArrayList<>();
                for (ArrayList<Integer> arr : s.stateLocations) {
                    ArrayList<Integer> a = new ArrayList<Integer>(arr);
                    inputList.add(a);
                }
                for(int j = 0; j < s.stateLocations.get(i).size(); j++) {                         //for all elements in one vehicle cell
                    inputList.get(i).set(j, inputList.get(i).get(j) + 6);                          //move all elements to right
                }
                State returnState = new State();
                returnState.setStateLocations(inputList);
                allPossibleNextStatesList.add(returnState);
            }
        }
        return allPossibleNextStatesList;
    }
    public boolean dirRightLeft(ArrayList<Integer> l){
        if(l.get(1) - l.get(0) == 1){
            return true;
        }
        return false;
    }

    public void readInput(String Filename) throws IOException{
        Scanner s1 = null;
        Scanner s2 = null;
        try {
            File f = new File(Filename);
            s1 = new Scanner(f);
            vehicleCount = s1.nextInt();
            s1.nextLine();
            for (int i=0; i < vehicleCount; i++){
                s2 = new Scanner(s1.nextLine());
                ArrayList<Integer> locations = new ArrayList<Integer>();
                while (s2.hasNextInt()){
                    locations.add(s2.nextInt());
                }
                State s = new State();
                initialLocations.add(locations);
                s.setStateLocations(initialLocations);
                initialState = s;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Pair> getPlan(){
        ArrayList<Pair> plan = bfs(this.initialState);
        Collections.reverse(plan);
        return plan;
    }
    public int[] getGrid() {
        return grid;
    }

    public void setGrid(int[] grid) {
        this.grid = grid;
    }

}
class State{
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    boolean visited;
    int layer;
    int numOfPaths;
    State parent;
    ArrayList<ArrayList<Integer>> stateLocations = new ArrayList<>();

    public State(State s){
        this.layer = s.layer;
        this.numOfPaths = s.numOfPaths;
        this.parent = s.parent;
        this.stateLocations = s.stateLocations;
    }
    public State(){

    }
    public void add(ArrayList<Integer> locations) {

    }
    public ArrayList<ArrayList<Integer>> getStateLocations() {
        return stateLocations;
    }

    public void setStateLocations(ArrayList<ArrayList<Integer>> stateLocations) {
        this.stateLocations = stateLocations;
    }
    public boolean goalReached(){
        return this.getStateLocations().get(0).get(this.getStateLocations().get(0).size()-1)==18;


    }
    public int getNumOfPaths() {
        return numOfPaths;
    }

    public void setNumOfPaths(int numOfPaths) {
        this.numOfPaths = numOfPaths;
    }
    public int getLayer() {
        return layer;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }
    public State getParent() {
        return parent;
    }
    public void setParent(State parent) {
        this.parent = parent;
    }
}
class Pair {
    int id;
    char direction; // {’e’, ’w’, ’n’, ’s’}
    public Pair(int i, char d) { id = i; direction = d; }
    char getDirection() { return direction; }
    int getId() { return id; }
    void setDirection(char d) { direction = d; }
    void setId(int i) { id = i; }
}

class HashKey {
    int[] c; // attribute
    public HashKey(int[] inputc) {
        c = new int[inputc.length];
        c = inputc;
    }
    public boolean equals(Object o) {
        boolean flag = true;
        if (this == o) return true; // same object
        if ((o instanceof HashKey)) {
            HashKey h = (HashKey)o;
            int[] locs1 = h.c;
            int[] locs = c;
            if (locs1.length == locs.length) {
                for (int i=0; i<locs1.length; i++) {
                    // mismatch
                    if (locs1[i] != locs[i]) {
                        flag = false;
                        break;
                    }
                }
            }
            else // different size
                flag = false;
        }
        else // not an instance of HashKey
            flag = false;
        return flag;
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return Arrays.hashCode(c); // using default hashing of arrays
    }
}

