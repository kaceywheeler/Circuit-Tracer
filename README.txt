****************************
* Circuit Tracer 
* CS 221
* 06/07/21
* Kacey Wheeler
****************************

Overview:
  
  The Circuit Tracer project aims to connect two components on a circuit board with the shortest path or trace. 
  Through either stack or queue-based implementation, this program evaluates and finds the shortest path(s) for 
  the board. 
  
  
Included Files: 
  CircuitBoard.java
  CircuitTracer.java
  Storage.java
  TraceState.java
  CircuitTracerTester.java
  InvalidFileFormatException.java
  OccupiedPositionException.java
  README.txt - this file 
  
  
Analysis: 

  The choice of Storage configuration has a significant affect on how this program operates. Using a stack-based
  storage implementation is a depth-first search, diving deep into one possible path until it is exhausted. Using a 
  queue-based storage implementation is a depth-first search, taking each path one step further simultaneously with 
  the other paths instead of just one path. Using a stack-based versus a queue-based search leads to the same number 
  of search states being created. The total amount of possible paths is unchanging even with different storage 
  implementations. The number of states being created is the same, but the order in which they are evaluated is 
  different. This will allow a solution to be found quicker than the queue-based search, but it is not guaranteed 
  to be the best solution. While the queue-based search takes longer to find a solution, the solution is found to be 
  the best solution or one of the best solutions as it takes the fewest states. Since it is a breadth-first search, 
  it looks through all the paths at once, slowing things down but ensuring the shortest path is found first.
  
  Because the paths are unchanged, the memory is the same for both stack and queue based implementation. The only 
  thing changing is the order in which they are evaluated as stated above. The big O runtime is O(n^2). The number 
  of times it has to loop through will depend on the number of paths needed to explore. If it finds the shortest 
  solution quicker, it won't need to worry about searching through the list anymore. 





Testing: 
  
  To carry out testing of this project, CircuitTracer.java was utilized. This class carried out extensive testing on 
  the functionality of the other classes involved in the Circuit Tracer project. The tester file ran tests broken 
  into several different categories. The first category is CircuitBoard Constructor Tests which runs tests to ensure 
  the character array board is being populated correctly using the given file for input. These tests check population 
  of the board for all different valid files, and they also ensure InvalidFileFormatExceptions are being thrown 
  for different invalid cases such as not having enough elements in a row. The next category is CircuitTracer Valid 
  Input File Tests which ensure the algorithm is being run correctly on the valid files. The CircuitTracer Invalid 
  Input File Tests test to validate the invalid files are outputting the correct information on the Exceptions they 
  throw. The CircuitTracer Invalid Command Line Tests test to make sure the invalid files are being handled correctly
  using the command line arguments. Finally, the CircuitTracer GUI Option Tests are ran and they pass as the GUI 
  functionality is not implemented at this time. 
  
  All 86 tests passed, ensuring all possible scenarios as outlined above were properly accounted for. Through 
  test-driven development, this program was able to correctly give the shortest path or trace between two 
  components on a circuit board. 


Discussion: 

  Through this project, much was learned about how type of storage implementation has a profound impact on a brute 
  force search. I knew nothing about depth-first and breadth-first search prior to completing this project, and it 
  truly allowed me to understand the difference. Since I sketched out a mental model for how the program was going 
  to execute before even working on the program, this allowed me to dive deep into the discrepancies between the two. 
  The portion of the project requiring this mental model was also extremely helpful in general since it reiterated
  the importance of planning before starting to code as well as how something as small as the decision on what storage
  to use has major ramifications for a program. 
  







