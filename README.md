# A-Algorithm
In this project, I developed an agent that is able to find its way in a maze full of obstacles.

The maze is created at random but is strictly a rectangular matrix of 60 rows and 80 columns and a certain fraction of its elements (here I used 30%) will contain an obstacle. The obstacles are also chosen at random, same for initial and goal states. Therefore, they are different for each execution of the program.

In the case where the initial or the goal states are occupied matrix elements (objects) the program halts with the message 'Cannot set start state to an obstacle cell. Program halted' or 'Cannot set goal state to an obstacle cell. Program halted'. 

The agent uses the A* algorithm with Manhattan distance as a heuristic function in order to find a sequence of actions that takes it from the initial to the goal state. 

When obstacles do not occupy the initial and the goal state, the output.txt file is a text of 60 row and 80 columns where:
  - * is an obstacle
  - I is the initial state
  - G is the goal state
  - + is a state of the optimal path
  - blank space in all other cases
