
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
    }
    
    
    public Graph<Integer> unassigned;
    public Map<Integer, Set<Integer>> districts;
    public int districtSize;
    public Set<Set<Integer>> possibleDistricts;
    public int minScore;
    public int boardRows, boardCols;
    public int[] board;
    
    public BoardPermute(int boardRows, int boardCols, int districtSize, int minScore, int[] board) {
        this.boardRows = boardRows;
        this.boardCols = boardCols;
        this.districtSize = districtSize;
        this.minScore = minScore;
        this.board = board;
        districts = new HashMap<Integer, Set<Integer>>();
        unassigned = buildInitialGraph();
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
        districts = new HashMap<Integer, Set<Integer>>();
        for (Map.Entry<Integer, Set<Integer>> entry : oldBoard.districts.entrySet()) {
            districts.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
        }
        
        unassigned = new Graph<Integer>(oldBoard.unassigned);
        for (int member : newDistrict) {
            unassigned.removeNode(member);
        }
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
        District start = new District();
        int key = unassigned.popKey();
        start.members.add(key);
        start.adjacent.addAll(unassigned.getAdjacent(key));
        traverse(start);
    }
    
    private void traverse(District curr) {
        if (curr.members.size() == districtSize) {
            // we've hit the right size, now validate it
            
            // if the score is too low, do nothing
            int score = score(curr.members);
            if (score < minScore && score != 0) {
                return;
            }
            // if there's an isolated unassigned bit that could never be a district
            if (unassigned.minimumComponent(curr.members) < districtSize) {
                return;
            }
            // add to possibleDistricts
            possibleDistricts.add(curr.members);
        } else {
            // we still need more elements
            for (int child : curr.adjacent) {
                District d = new District(curr);
                d.members.add(child);
                d.adjacent.addAll(unassigned.getAdjacent(child));
                d.adjacent.removeAll(d.members);
                traverse(d);
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
    
}
