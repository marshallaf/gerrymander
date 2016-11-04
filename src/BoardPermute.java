
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
    public Graph<Integer> unassigned;
    public Map<Integer, Set<Integer>> districts;
    public int districtSize;
    public Set<Set<Integer>> possibleDistricts;
    
    public BoardPermute() {
        districts = new HashMap<Integer, Set<Integer>>();
        possibleDistricts = new HashSet<Set<Integer>>();
    }
    
    public void possibleDistricts(int base) {
        Node root = new Node();
        root.value = unassigned.popKey();
        root.depth = 1;
        root.children = unassigned.getAdjacent(root.value);
        Set<Integer> buildSet = new HashSet<Integer>();
        buildSet.add(root.value);
        traverse(root, buildSet);
    }
    
    private void traverse(Node curr, Set<Integer> buildSet) {
        if (curr.depth == districtSize) {
            // we've hit the right size, now validate it
            
        } else {
            // we still need more elements
            for (int child : curr.children) {
                Set<Integer> currBuild = new HashSet<>(buildSet);
                currBuild.add(child);
                Node childNode = new Node();
                childNode.value = child;
                childNode.depth = curr.depth+1;
                childNode.children = unassigned.getAdjacent(child);
                traverse(childNode, currBuild);
            }
        }
    }
    
    public class Node {
        int value;
        int depth;
        Set<Integer> children;
    }
}
