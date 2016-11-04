
import java.util.HashMap;
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
    
    public BoardPermute() {
        districts = new HashMap<Integer, Set<Integer>>();
    }

}
