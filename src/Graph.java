import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph<Node> {
    public Map<Node, Set<Node>> map;
    private Set<Node> visited;
    private Map<Integer, Integer> component;
    
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
        map.remove(node);
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
        
        // initialize all vertices as not visited
        visited = new HashSet<>();
        visited.addAll(exclude);
        
        component = new HashMap<>();
        int componentLabel = 0;
        Set<Node> keys = map.keySet();
        for (Node key : keys) {
            // if not visited
            if (!visited.contains(key)) {
                componentLabel++;
                DFS(key, componentLabel, exclude);
            }
        }
        
        // return lowest mapping
        if (component.values().isEmpty()) return Integer.MAX_VALUE;
        else return Collections.min(component.values());
    }

    private void DFS(Node node, int componentLabel, Set<Node> exclude) {
        visited.add(node);
        if (component.containsKey(componentLabel)) {
            component.put(componentLabel, component.get(componentLabel)+1);
        } else component.put(componentLabel, 1);
        for (Node adj : getAdjacent(node)) {
            if (!exclude.contains(adj) && !visited.contains(adj)) {
                DFS(adj, componentLabel, exclude);
            }
        }
    }
}
