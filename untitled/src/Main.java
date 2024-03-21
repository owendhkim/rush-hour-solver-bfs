import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        GameBoard gb = new GameBoard();
        gb.readInput("1.txt");
        ArrayList<Pair> path = gb.getPlan();
        for (int i=0; i<path.size(); i++){
            System.out.println(path.get(i).getId() + " " + path.get(i).getDirection());
        }
        System.out.println(gb.getNumOfPaths());
//        System.out.println(gb.getNumOfPaths());
//        ArrayList<ArrayList<Integer>> a = gb.getInitialLocations();
//        System.out.println("initial location from readinput : "+a);
//        ArrayList<ArrayList<ArrayList<Integer>>> apm = gb.allPossibleNextStates(a);
//        System.out.println("all possible moves from initial state "+ apm.size() + " : "+apm);
//        ArrayList<ArrayList<Integer>> l1 = new ArrayList<>();
//        ArrayList<ArrayList<Integer>> l2 = new ArrayList<>();
//        l1.addAll(apm.get(0));
//        l2.addAll(apm.get(1));
//        l2.removeAll(a);
//        System.out.println(l2);

//        State s = new State();
//        s.setStateLocations(gb.getInitialLocations());
//        gb.bfs(s);
//        gb.calcPair(gb.getShortestWonStates().get(0));

//        State s1 = new State();
//        State s2 = new State();
//        State s3 = new State();
//        GameBoard gb1 = new GameBoard();
//        GameBoard gb2 = new GameBoard();
//        GameBoard gb3 = new GameBoard();
//        gb1.readInput("1.txt");
//        gb2.readInput("1.txt");
//        ArrayList<ArrayList<Integer>> a1 = gb1.getInitialLocations();
//        ArrayList<ArrayList<Integer>> a2 = gb2.getInitialLocations();
//        s1.setStateLocations(a1);
//        s2.setStateLocations(a1);
//        int hc1 = Arrays.hashCode(a1);
//        int hc2 = Arrays.hashCode(a2);
//        System.out.println(hc1 + " " + hc2);

    }

}
