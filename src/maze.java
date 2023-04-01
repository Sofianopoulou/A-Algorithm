import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class maze {
	
	private static final int ROWS = 60;
	private static final int COLUMNS = 80;
	private int[][] maze;
	
	public maze() {
		maze = new int [ROWS][COLUMNS];
	}
	
	public int getCell(int row, int col) {
		return maze[row][col];
	}
	
	public void setCell(int row, int col, int value) {
		maze[row][col] = value;
	}
	
	/* The generateObstacles method randomly marks cells in the maze matrix as 
	 * obstacles based on obstacleProbability.*/
	public void generateObstacles(double obstacleProbability) {
		int count=0;
		Random random = new Random();
	    int obstacleCount = (int) (ROWS * COLUMNS * obstacleProbability); // obstacleProbability% of maze elements

	    while (count < obstacleCount) {
	        int row = random.nextInt(ROWS);	// generates random integer between 0 (inclusive) and ROWS (exclusive)
	        int col = random.nextInt(COLUMNS); // generates random integer between 0 (inclusive) and COLUMNS (exclusive)

	        if (maze[row][col] == 0) { // only set as obstacle if cell is not already an obstacle
	            maze[row][col] = 1;
	            count++;
	        }
	       
	    }
	}
	
	public void setStartAndGoalStates(File file, FileWriter writer) {
	    Random random = new Random();

	    // Select random cells until both start and goal cells are unoccupied
	    while (true) {
	        int startRow = random.nextInt(ROWS);
	        int startCol = random.nextInt(COLUMNS);
	        int goalRow = random.nextInt(ROWS);
	        int goalCol = random.nextInt(COLUMNS);

	        if (maze[startRow][startCol] == 0 && maze[goalRow][goalCol] == 0) {
	        	maze[startRow][startCol] = 2;	// initial cell
	        	maze[goalRow][goalCol] = 3;		// goal cell
	            return;
	        } else if (maze[startRow][startCol] == 1) {
	        	try {
					writer.write("Cannot set start state to an obstacle cell. Program halted");
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	            System.exit(0);
	        } else if (maze[goalRow][goalCol] == 1) {
	        	try {
					writer.write("Cannot set goal state to an obstacle cell. Program halted");
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	            System.exit(0);
	        } 
	    }
	}
	
	/* The printMaze method simply writes the maze matrix to a output.txt file, 
	 * using spaces for empty cells, asterisk signs (*) for obstacle cells,
	 * I for initial state and G for goal state.*/
	public void printMaze(File file, FileWriter writer) {
		try {
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLUMNS; col++) {
					if (maze[row][col] == 1) {
						writer.write("*"); 		//obstacle cell
					}
					if(maze[row][col] == 2) {
						writer.write("I"); 		//initial cell
					}
					if(maze[row][col] == 3) {
						writer.write("G"); 		//goal cell
					}
					if(maze[row][col] == 4) {
						writer.write("+"); 		//optimal path
					}
					if(maze[row][col] == 0) {
						writer.write(" "); 		//empty cell
					}
				}
				writer.write(System.lineSeparator()); // move to next row
			}
		} catch (IOException e) {
	        System.err.println("An error occurred while writing the output file.");
		}
	}
	
	// Returns initial cell as 'Node' object
	public Node getStart() {
		Node start = new Node(0, 0);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if(maze[row][col] == 2) {	
					start.setX(row);	
					start.setY(col);
				}
			}
		}
		return start;
	}
	
	// Returns the goal cell as 'Node' object
	public Node getGoal() {
		Node goal = new Node(0, 0);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if(maze[row][col] == 3) {
					goal.setX(row); 
					goal.setY(col);
				}
			}
		}
		return goal;
	}
	
	/* Returns a list of neighboring cells that are not obstacles (or is goal), 
	 * where "neighbor" is defined as any cell that is directly above, 
	 * below, to the left of, or to the right of the given cell. 
	 * It creates a new Node object for each valid neighbor and adds 
	 * it to the list.*/
	public List<Node> getNeighbors(Node node) {
	    List<Node> neighbors = new ArrayList<>();

	    // Check the 4 adjacent cells (up, down, left, right)
	    int x = node.getX();
	    int y = node.getY();
	    if (x > 0 && (maze[x - 1][y] == 0 || x > 0 && maze[x - 1][y] == 3)) {
	        neighbors.add(new Node(x - 1, y));
	    }
	    if (x < maze.length - 1 && (maze[x + 1][y] == 0 ||  x < maze.length - 1 && maze[x + 1][y] == 3)) {
	        neighbors.add(new Node(x + 1, y));
	    }
	    if (y > 0 && (maze[x][y -1] == 0 || y > 0 && maze[x][y -1] == 3)) {
	        neighbors.add(new Node(x, y - 1));
	    }
	    if (y < maze[0].length - 1 && (maze[x][y + 1] == 0|| y < maze[0].length -1 && maze[x][y + 1] == 3)) {
	        neighbors.add(new Node(x, y + 1));
	    }
	    return neighbors;
	}
	
}


