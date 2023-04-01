import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File outputFile = new File("output.txt");
			FileWriter writer = new FileWriter(outputFile);
			
			maze maze = new maze();
			maze.generateObstacles(0.3); // generate obstacles with 30% probability
			maze.setStartAndGoalStates(outputFile, writer);
			
			AStar aStar = new AStar();
			aStar.findPath(maze.getStart(), maze.getGoal(), maze, outputFile, writer);

			maze.printMaze(outputFile, writer);
			
			writer.close();
		} catch (IOException e) {
		    System.err.println("An error occurred while writing the output file: " + e.getMessage());
		}
	}
		


}
