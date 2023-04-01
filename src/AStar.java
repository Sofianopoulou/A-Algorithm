import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AStar {
    private PriorityQueue<Node> open;	// The set of tentative nodes to be evaluated
    private Set<Node> closed;		// The set of nodes already evaluated
    private Map<Node, Node> parents;	// The map of navigated nodes
    private Map<Node, Integer> gCost;	// Cost from start along best known path
    private Map<Node, Integer> fScore;	// Estimated total cost from start to nearest goal through y

    public List<Node> findPath(Node start, Node goal, maze maze, File file, FileWriter writer) {
        open = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return fScore.get(n1) - fScore.get(n2);
            }
        });
        closed = new HashSet<Node>();
        parents = new HashMap<Node, Node>();
        gCost = new HashMap<Node, Integer>();
        fScore = new HashMap<Node, Integer>();

        gCost.put(start, 0);
        fScore.put(start, heuristic(start, goal));
        open.add(start);

        while (!open.isEmpty()) {
            System.out.println("Open size: " + open.size());
            Node current = open.poll();	// Retrieves the head of the queue and also removes it 
            if (current.equals(goal)) {
            	try {
					writer.write("Path found! Here is the maze with obstacles, initial, goal states and optimal path:\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return reconstructPath(current, maze);
            }
            closed.add(current);
            for (Node neighbor : maze.getNeighbors(current)) {
                if (closed.contains(neighbor)) {
                    continue;
                }
                int tentativeGScore = gCost.get(current) + 1; // Assuming a cost of 1 to move to a neighbor
                if (!open.contains(neighbor) || tentativeGScore < gCost.get(neighbor)) {
                    parents.put(neighbor, current);
                    gCost.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristic(neighbor, goal));
                    if (!open.contains(neighbor)) {
                        open.add(neighbor);
                    }
                }
            }
            System.out.println("Closed size: " + closed.size());
        }
	    try {
			writer.write("Error: Could not find a path from start to goal. Here is the maze with obstacles, initial and goal states:\n ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null; // No path found
    }
    
    /* Takes the current node and iteratively follows its parents until it 
     * reaches the start node, creating a list of nodes that represents the 
     * path from the start node to the current node.*/
    private List<Node> reconstructPath(Node current, maze maze) {
        List<Node> path = new ArrayList<Node>();
        path.add(current);
        
        while (parents.containsKey(current)) {
            current = parents.get(current);
            path.add(0, current);
        }
        
        // Setting value 4 to the cells of the paths
        for(Node node : path)
        {
        	if((maze.getCell(node.getX(), node.getY()) == 2) || (maze.getCell(node.getX(), node.getY()) == 3)) {
        		continue;
        	}
        	else {maze.setCell(node.getX(), node.getY(), 4);}
        }
        return path;
    }

    private int heuristic(Node n1, Node n2) {
        // Manhattan distance as a heuristic function
        return Math.abs(n1.getX() - n2.getX()) + Math.abs(n1.getY() - n2.getY());
    }
    
    

}
