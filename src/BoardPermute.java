
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
    public int[] board;
    
    public BoardPermute() {
        districts = new HashMap<Integer, Set<Integer>>();
        possibleDistricts = new HashSet<Set<Integer>>();
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
