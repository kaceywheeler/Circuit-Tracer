import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args)  {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: java CircuitTracer [-s | -q] [-c | -g] [filename]");
		System.out.println("-s: stack, -q: queue, -c: console, -g: gui");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()obj
	 * @throws FileNotFoundException 
	 */
	public CircuitTracer(String[] args) {
		//if there are not three arguments 
		if (args.length != 3) {
			printUsage();
			return;
		}
		if (!args[0].equals("-s") && !args[0].equals("-q")) {
			printUsage();
			return; //exit the constructor immediately
		}
		
		if (args[1].equals("-g")) {
			System.out.println("GUI is not supported at this time");
			return;//exit constructor 
		}
		
		else if (!args[1].equals("-c")) {
			printUsage();
			return; //exit the constructor immediately 
		}
		
		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();
		
		CircuitBoard board;
		try {
			board = new CircuitBoard(args[2]);
			Storage<TraceState> stateStore;
				
			//if using stack 
			if (args[0].equals("-s")) {
				stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
			}
			
			//if using queue 
			else if (args[0].equals("-q")){
				stateStore =  new Storage<TraceState>(Storage.DataStructure.queue);
			}
			
			else {
				printUsage();
				return; //exit the constructor immediately 
			}
			
			//for each open position adjacent to starting component, add trace TraceState object 
			Point start = board.getStartingPoint();
			
			//if position above is empty  
			if (board.isOpen((int)start.getX(), (int)start.getY() + 1)) {
				stateStore.store(new TraceState(board, (int)start.getX(), (int)start.getY() + 1));
			}
			
			//if position to the right is empty 
			if (board.isOpen((int)start.getX() + 1, (int)start.getY())) {
				stateStore.store(new TraceState(board, (int)start.getX() + 1, (int)start.getY()));
			}
			
			//if position below is empty 
			if (board.isOpen((int)start.getX(), (int)start.getY() - 1)) {
				stateStore.store(new TraceState(board, (int)start.getX(), (int)start.getY() - 1));
			}
			
			//if position to the left is empty
			if (board.isOpen((int)start.getX() - 1, (int)start.getY())) {
				stateStore.store(new TraceState(board, (int)start.getX() - 1, (int)start.getY()));
			}
			
			while (!stateStore.isEmpty()) {
				//retrieve the next trace depending on if stack of queue storage 
				TraceState trace = stateStore.retrieve();
				if (trace.isComplete()) {
					if (bestPaths.isEmpty() || trace.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(trace);
					}
					else if (trace.pathLength() < bestPaths.get(0).pathLength()) {
						bestPaths.clear();
						bestPaths.add(trace);
					}
				}
				
				else {
					
					//generate all valid next TraceStates from current TraceState 
					//if position above is empty 
					if (trace.isOpen(trace.getRow(), trace.getCol() + 1)) {
						stateStore.store(new TraceState(trace, trace.getRow(), trace.getCol() + 1));
					}
					
					//if position to the right is empty 
					if (trace.isOpen(trace.getRow() + 1, trace.getCol())) {
						stateStore.store(new TraceState(trace, trace.getRow() + 1, trace.getCol()));
					}
					
					//if position below is empty 
					if (trace.isOpen(trace.getRow(), trace.getCol() - 1)) {
						stateStore.store(new TraceState(trace, trace.getRow(), trace.getCol() - 1));
					}
					
					//if position to the left is emptyFileNotFoundException 
					if (trace.isOpen(trace.getRow() - 1, trace.getCol())) {
						stateStore.store(new TraceState(trace, trace.getRow() - 1, trace.getCol()));
					}
				}
			}
			
			for (TraceState t : bestPaths) {
				System.out.println(t.getBoard());
			}
		}
		
		catch (InvalidFileFormatException e) {
			System.out.println("InvalidFileFormatException");
		}
		
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		
		
	}
	
} // class CircuitTracer
