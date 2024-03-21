# Rush-hour solver

Design and Analysis of Algorithms project.

A program that prints out a sample solution paths of the given rush-hour puzzle, as well as the number of all the possible optimal(minimum number of moves) solutions.

Implemented with bfs algorithm to search for all the possible moves that can be performed after each moves. 
Arrays.hascode to get the unique key, and pass current state of the game as a value into hashmap. 
Repeat until goal is reached, trace back the state node to sum all the layer to calculate how many moves was required for this solution. 
After bfs exploration is complete, search through hashmap to count the number of optimal solutions.

All code in one [GameBoard.java](https://github.com/owendhkim/rush-hour-solver-bfs/blob/main/untitled/src/GameBoard.java) file.

## Main takeaways

From this project, I had experience applying bfs algorithm to solve a problem, and in the meanwhile I was able to get better understandings of the algorithm.

I had experience with applying bfs algorithm to an object, as well as learning what which part of the algorithm I need to modify in order to acheive different goals compared to vanila bfs algorithm, what fields I need to store in an object to make that happen.


## Authors

[Owen Kim](https://www.linkedin.com/in/owen-kim-657249169/)

