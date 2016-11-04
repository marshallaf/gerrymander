import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph<Node> {
    private Map<Node, Set<Node>> map = new HashMap<>();
    
    public void addNode(Node node) {
        map.put(node, new HashSet<Node>());
    }
    
    public void addEdge(Node node1, Node node2) {
        map.get(node1).add(node2);
        map.get(node2).add(node1);
    }
    
    public void removeNode(Node node) {
        Set<Node> adj = getAdjacent(node);
        for (Node adjNode : adj) {
            map.get(adjNode).remove(node);
        }
    }
    
    public Set<Node> getAdjacent(Node node) {
        return map.get(node);
    }
    
    public Node popKey() {
        Iterator<Node> i = map.keySet().iterator();
        if (i.hasNext())
            return i.next();
        else return null;
    }
}
