
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrew Marshall
 * 
 * Holds a possible permutation of district selections.
 *
 */
public class BoardPermute {
    
    public class District {
        Set<Integer> members;
        Set<Integer> adjacent;
        
        public District() {
            members = new HashSet<Integer>();
            adjacent = new HashSet<Integer>();
        }
        
        public District(District d) {
            members = new HashSet<Integer>(d.members);
            adjacent = new HashSet<Integer>(d.adjacent);
        }
        
        public boolean equals(Object o) {
            District other = (District) o;
            if (members.equals(other.members)) return true;
            return false;
        }
    }
    
    
    public Graph<Integer> unassigned;
    public Set<Set<Integer>> districts;
    public int districtSize;
    public Set<Set<Integer>> possibleDistricts;
    public LinkedList<District> tempDistricts;
    public int minScore;
    public int boardRows, boardCols;
    public int[] board;
    
    public BoardPermute(int boardRows, int boardCols, int districtSize, int minScore, int[] board) {
        this.boardRows = boardRows;
        this.boardCols = boardCols;
        this.districtSize = districtSize;
        this.minScore = minScore;
        this.board = board;
        districts = new HashSet<Set<Integer>>();
        unassigned = buildInitialGraph();
        System.out.println("Finished building graph!");
        possibleDistricts = new HashSet<Set<Integer>>();
        possibleDistricts();
    }
    
    public BoardPermute(BoardPermute oldBoard, Set<Integer> newDistrict) {
        // you should do this in a different way than just copying forever
        this.boardRows = oldBoard.boardRows;
        this.boardCols = oldBoard.boardCols;
        this.districtSize = oldBoard.districtSize;
        this.minScore = oldBoard.minScore;
        this.board = oldBoard.board;
        districts = new HashSet<Set<Integer>>(oldBoard.districts);
        districts.add(newDistrict);
        
        unassigned = new Graph<Integer>(oldBoard.unassigned);
        for (int member : newDistrict) {
            unassigned.removeNode(member);
        }
        possibleDistricts = new HashSet<Set<Integer>>();
        possibleDistricts();
    }
    
    public Graph<Integer> buildInitialGraph() {
        Graph<Integer> initial = new Graph<>();
        for (int i = 0; i < board.length; i++) {
            initial.addNode(i);
        }
        for (int i = 0; i < board.length; i++) {
            int col = i % boardCols;
            int row = i / boardCols;
            if (col != 0) initial.addEdge(i, i-1);
            if (col != boardCols-1) initial.addEdge(i, i+1);
            if (row != 0) initial.addEdge(i, i - boardCols);
            if (row != boardRows-1) initial.addEdge(i, i + boardCols);
        }
        return initial;
    }
    
    public void possibleDistricts() {
        tempDistricts = new LinkedList<District>();
        District start = new District();
        Integer key = unassigned.popKey();
        if (key == null) return;
        start.members.add(key);
        start.adjacent.addAll(unassigned.getAdjacent(key));
        tempDistricts.add(start);
        traverse();
    }
    
    private void traverse() {
        
        for (int i = 0; i < districtSize; i++) {
            int currPermute = tempDistricts.size();
            System.out.println("Assigned " + i + " districts: " + currPermute + " permutations this round.");
            for (int j = 0; j < currPermute; j++) {
                District curr = tempDistricts.poll();
                
                if (curr.members.size() == districtSize) {
                    // we've hit the right size, now validate it
                    
                    // if the score is too low, do nothing
                    int score = score(curr.members);
                    if (score < minScore && score != 0) {
                        continue;
                    }
                    // if there's an isolated unassigned bit that could never be a district
                    if (unassigned.minimumComponent(curr.members) < districtSize) {
        //                System.out.println("excluded due to connectivity");
                        continue;
                    }
                    // add to possibleDistricts
        //            System.out.println(curr.members);
                    possibleDistricts.add(curr.members);
                } else {
                    // we still need more elements
                    for (int child : curr.adjacent) {
                        District d = new District(curr);
                        d.members.add(child);
                        d.adjacent.addAll(unassigned.getAdjacent(child));
                        d.adjacent.removeAll(d.members);
                        // make sure score is still going to work
                        int score = score(d.members);
                        int otherScore = d.members.size() - score;
                        if (score != 0 && otherScore > minScore) continue;
                        // don't add it if we've already got this tempDistrict
                        if (!tempDistricts.contains(d)) {
                            tempDistricts.add(d);
                        }
                    }
                }
            }
        }
    }
    
    private int score(Set<Integer> district) {
        int score = 0;
        for (int i : district) {
            score += board[i];
        }
        return score;
    }
    
    public String toString() {
        char[] str = new char[board.length];
        char d = 'A';
        for (Set<Integer> district : districts) {
            for (int member : district) {
                str[member] = d;
            }
            d++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            if (i % boardCols == 0) sb.append("\n");
            sb.append(str[i] + " ");
        }
        return sb.toString();
    }
    
}
