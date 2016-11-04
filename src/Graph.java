import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph<Node> {
    public Map<Node, Set<Node>> map;
    
    public Graph() {
        map = new HashMap<>();
    }
    
    public Graph(Graph<Node> oldGraph) {
        map = new HashMap<>();
        for (Map.Entry<Node, Set<Node>> entry : oldGraph.map.entrySet()) {
            map.put(entry.getKey(), new HashSet<Node>(entry.getValue()));
        }
    }
    
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
    
    public int minimumComponent(Set<Node> exclude) {
        // TODO: implement this
        
        // placeholder return
        return Integer.MAX_VALUE;
    }
}
