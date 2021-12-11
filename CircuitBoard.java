import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		
		///home/user05/Desktop/Circuit Tracer/boards/
		//G:\\Circuit Tracer\\boards\\
		Scanner fileScan = new Scanner(new File(filename));
		String[] line = fileScan.nextLine().split(" ");
		
		//If there are not 2 values given for num of rows and cols
		if (line.length != 2) {
			throw new InvalidFileFormatException("Valid board size is not given");
		}
		
		//try-catch statement to see if values are integers 
		try { 
			ROWS = Integer.parseInt(line[0]);
			COLS = Integer.parseInt(line[1]);
		}
		catch (NumberFormatException e) {
			throw new InvalidFileFormatException("Valid board size is not given");
		}

		//make array with rows and columns above 
		board = new char[ROWS][COLS];
		int startCount = 0;
		int endCount = 0;
		for (int r=0; r<ROWS; r++) {
			Scanner lineScan = new Scanner(fileScan.nextLine());
        	for (int c=0; c<COLS; c++) {
        		//check if there are not enough elements 
        		if (!lineScan.hasNext()) {
        			lineScan.close();
        			throw new InvalidFileFormatException("Row is not correct length");
        		}
        		char check = lineScan.next().charAt(0);
        		
        		//keep track of number of starts 
        		if (check == START) {
        			startingPoint = new Point(r,c);
        			startCount++;
        		}
        		
        		//keep track of number of ends
        		if (check == END) {
        			endingPoint = new Point (r,c);
        			endCount++;
        		}
        		
        		//check if more than one start or end 
        		if (startCount >= 2 || endCount >= 2) {
        			lineScan.close();
        			throw new InvalidFileFormatException("Too many start or end points");
        		}
        		
        		//add character to board 
        		if (ALLOWED_CHARS.indexOf(check) != -1) {
        			board[r][c] = check;
        		}
        		
        		//if not an allowed character 
        		else {
        			lineScan.close();
        			throw new InvalidFileFormatException(check + " is not allowed in board");
        		}
        	}
        	//if there are more characters than given length of row 
        	if (lineScan.hasNext()) {
        		lineScan.close();
        		throw new InvalidFileFormatException("Row is not correct length");
        	}
        	lineScan.close();
		}
		
		//if no start or end point
		if ((startCount == 0) || (endCount == 0)) {
			throw new InvalidFileFormatException("No start or end point");
		}
		
		//Checks the length of each column to see if it matches the specified number of columns
        if (fileScan.hasNext() ) {
        	if (!fileScan.next().equals("")) {
        		throw new InvalidFileFormatException("Array is not specified size");
        	}
        }
        
        //close fileScan if it is open 
		if (fileScan != null) {
            fileScan.close();
        }
}
          

	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	//Contents of circuit board can loaded from file or are part of a search state.
	//Complete code to read the input file to construct a new board.
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
