package cecs274;

import edu.princeton.cs.introcs.*;
import java.util.Random;

/**
 * Generates, displays, and solves a maze.
 * @author Christian Lam christian.lam@student.csulb.edu
 * @author Gideon Essel gideon.essel@student.csulb.edu
 * @author Peter Drake drake@lclark.edu
 * @see <a href="http://ljing.org">Learn Java in N Games</a>
*/
public class Maze {

	/** Index for east direction. */
	public static final int EAST = 1;

	/** Index for north direction. */
	public static final int NORTH = 0;

	/**
	 * x and y offsets for per direction. For example, the point to the east of
	 * x, y is x + OFFSETS[EAST][0], y + OFFSETS[EAST][1].
	 */
	public static final int[][] OFFSETS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	/** Index for south direction. */
	public static final int SOUTH = 2;

	/** Index for west direction. */
	public static final int WEST = 3;

	/**
	 * Modifies passage to contain a one-way passage from location a to location
	 * b. Assumes these two locations (arrays of two numbers) are adjacent.
	 *
	 * @param passages
	 *            passages[x][y][direction] is true if there is a passage from
	 *            location x, y to its neighbor in direction. Directions are
	 *            specified by the constants NORTH, EAST, SOUTH, and WEST.
	 */
	public static void addPassage(boolean[][][] passages, int[] a, int[] b)
	{

		int xOfA = a[0];
		int yOfA = a[1];
		int xOfB = b[0];
		int yOfB = b[1];

		if(yOfA + 1 == yOfB) //Checks value adjacent to a[1] in the Northward direction
		{
			passages[xOfA][yOfA][0] = true;
		}

		else if(xOfA + 1 == xOfB) //Checks value adjacent to a[0] in the Eastward direction
		{
			passages[xOfA][yOfA][1] = true;
		}

		else if(yOfA - 1 == yOfB) //Checks value adjacent to a[1] in the Southward direction
		{
			passages[xOfA][yOfA][2] = true;
		}

		else
		{
			passages[xOfA][yOfA][3] = true;
		}


	}

	/**
	 * Returns a new array of pairs containing start followed by all of the
	 * elements in list.
	 * @param start the pair to be at the front of the new list
	 * @param list the list containing the rest of the elements to be followed after the start pair
	 * @return the new 2D array with the start pair and the other elements of list
	 */
	public static int[][] addToFront(int[] start, int[][] list)
	{
		// Creates a new empty 2D Array
		int[][] addedToFront = new int[list.length + (start.length-1)][start.length];
		// Stores first element in 2D Array
		addedToFront[0]= start;
		// Loops through rows starting at the second element of 2D Array
		for ( int i = 1; i < addedToFront.length; i++ )
		{
			// Loops through cols
			for( int j =  0; j < addedToFront[i].length; j++ )
			{

				// Stores the rest of the list into new 2D Array

				addedToFront[i][j] = list[i-1][j];
			}
		}
		// Returns New Array
		return addedToFront;
	}

	/**
	 * Returns a random one of the first n elements of list.
	 * @param list a list containing pairs
	 * @param n first n elements of the list
	 * @return a random pair from the first n elements of list
	 */
	public static int[] chooseRandomlyFrom(int[][] list, int n)
	{
		// Creates a random object
		Random rand = new Random();

		int next = rand.nextInt(n);

		// Returns random pair
		return list[next];
	}

	/**
	 * Returns true if pair, assumed to be an array of two numbers, has the same
	 * numbers as one of the first n elements of list.
	 * @param pair a pair that is in the list or not in list
	 * @param list a list of points
	 * @param n the length of elements compared
	 * @return true or false whether the pair is in the list or not
	 */
	public static boolean contains(int[] pair, int[][] list, int n)
	{
		// Checks if a pair is found
		boolean hasPair = false;

		for(int i = 0; i < n; i++)
		{
			// If pair is in list
			if (list[i][0] == pair[0] && list[i][1] == pair[1]) {
				// returns true
				hasPair = true;
			}
		}
		return hasPair;
	}

	/**
	 * Graphically draws the maze.
	 *
	 * @param passages
	 *            passages[x][y][direction] is true if there is a passage from
	 *            location x, y to its neighbor in direction. Directions are
	 *            specified by the constants NORTH, EAST, SOUTH, and WEST.
	 */
	public static void drawMaze(boolean[][][] passages) {
		StdDraw.clear(StdDraw.PINK);
		StdDraw.setPenColor(StdDraw.WHITE);
		int width = passages.length;
		StdDraw.setPenRadius(0.75 / width);
		// Draw passages
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				if (passages[x][y][NORTH] || (y + 1 < width && passages[x][y + 1][SOUTH])) {
					StdDraw.line(x, y, x, y + 1);
				}
				if (passages[x][y][EAST] || (x + 1 < width && passages[x + 1][y][WEST])) {
					StdDraw.line(x, y, x + 1, y);
				}
			}
		}
		// Draw entrance and exit
		StdDraw.line(0, 0, -1, 0);
		StdDraw.line(width - 1, width - 1, width, width - 1);
		StdDraw.show();
	}

	/**
	 * Graphically draws the solution.
	 * @param path a list containing the points to create paths
	 * @param width the size of the paths
	 */
	public static void drawSolution(int[][] path, int width) {
		StdDraw.setPenColor(); // Black by default
		StdDraw.setPenRadius();
		StdDraw.line(0, 0, -1, 0);
		StdDraw.line(width - 1, width - 1, width, width - 1);
		for (int i = 0; i < path.length - 1; i++) {
			StdDraw.line(path[i][0], path[i][1], path[i + 1][0], path[i + 1][1]);
		}
		StdDraw.show();
	}

	/**
	 * Checks if here's neighbor in direction (called there) is in unexplored.
	 * If so, adds a passage from here to there and returns there. If not,
	 * returns null.
	 *
	 * @param passages
	 *            passages[x][y][direction] is true if there is a passage from
	 *            location x, y to its neighbor in direction. Directions are
	 *            specified by the constants NORTH, EAST, SOUTH, and WEST.
	 * @param unexplored
	 *            a list of locations that have not yet been reached.
	 * @param n
	 *            the number of valid elements in unexplored. Unexplored is
	 *            "resized" by changing this number.
	 * @param here
	 *            the location from which a neighbor is sought.
	 * @param direction
	 *            the direction to expand location, one of NORTH, SOUTH, EAST,
	 *            or WEST.
	 * @return the newly-explored location there if this succeeded, null
	 *         otherwise.
	 */
	public static int[] expandLocation(boolean[][][] passages, int[][] unexplored, int n, int[] here, int direction) {
		int[] there = new int[2];
		// Find the neighboring point
		there[0] = here[0] + OFFSETS[direction][0];
		there[1] = here[1] + OFFSETS[direction][1];
		// Checks if there contains the same values as the first n elements in unexplored(explored/unexplored)
		if (contains(there, unexplored, n))
		{
			// Adds a passage from here to there
			addPassage(passages, here, there);
			return there;
		}
		return null;
	}

	/**
	 * Chooses "here" to be either lastExploredLocation (if it is not null) or a
	 * random location in frontier. If possible, adds a passage from "here" to a
	 * location "there" in unexplored, then moves "there" from unexplored to
	 * frontier. If not, moves "here" from frontier to done. Returns "there", or
	 * null if no new location was explored.
	 *
	 * @param passages
	 *            passages[x][y][direction] is true if there is a passage from
	 *            location x, y to its neighbor in direction. Directions are
	 *            specified by the constants NORTH, EAST, SOUTH, and WEST.
	 * @param done
	 *            a list of locations from which no new edges can be drawn to
	 *            locations in unexplored.
	 * @param frontier
	 *            a list of locations that have been reached by some edge but
	 *            are not yet in done.
	 * @param unexplored
	 *            a list of locations that have not yet been reached.
	 * @param counts
	 *            the number of valid elements in each of the three previous
	 *            arrays. The arrays are "resized" by changing these elements.
	 * @param lastExploredLocation
	 *            the last location that was explored or null.
	 * @return the newly explored location (or null if no new location was
	 *         explored).
	 */
	public static int[] expandMaze(boolean[][][] passages, int[][] done, int[][] frontier, int[][] unexplored, int[] counts, int[] lastExploredLocation)
	{
		int[] here;
		if (lastExploredLocation == null)
		{
			//Random pair is extracted from frontier
			here = chooseRandomlyFrom(frontier, counts[1]);
		}
		else {
			here = lastExploredLocation;
		}
		// Choose a random direction
		int direction = StdRandom.uniform(4);
		for (int i = 0; i < 4; i++)
		{
			int[] there = expandLocation(passages, unexplored, counts[2], here, direction);
			if (there != null)
			{
				// Move there from unexplored to frontier
				frontier[counts[1]] = there;
				counts[1]++;
				// Elements of there are replaced/removed from unexplored
				remove(there, unexplored, counts[2]);
				counts[2]--;
				return there;
			}
			direction = (direction + 1) % 4;
		}
		// No valid neighbor was found. Move here from frontier to done.
		done[counts[0]] = here;
		counts[0]++;
		//Elements of here are replaced/removed from frontier
		remove(here, frontier, counts[1]);
		counts[1]--;
		return null;
	}

	/** Draws and then solves a maze. */
	public static void main(String[] args)
	{
		StdDraw.enableDoubleBuffering();
		int width = 20;
		StdDraw.setXscale(-0.5, width - 0.5);
		StdDraw.setYscale(-0.5, width - 0.5);
		StdDraw.show();
		boolean[][][] passages = new boolean[width][width][4];
		// Initially, no locations are done
		int[][] done = new int[width * width][];
		// The frontier only contains {0, 0}
		int[][] frontier = new int[width * width][];
		frontier[0] = new int[] { 0, 0 };
		// All other locations are in unexplored
		int[][] unexplored = new int[width * width][];
		// Number of nodes done, on the frontier, and unexplored
		int[] counts = { 0, 1, width * width - 1 };
		int i = 0;
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < width; y++)
			{
				if (x != 0 || y != 0)
				{
					unexplored[i] = new int[] { x, y };
					i++;
				}
			}
		}
		// As long as there are unexplored locations, expand the maze
		int[] lastExploredLocation = null;
		while (counts[2] > 0)
		{
			lastExploredLocation = expandMaze(passages, done, frontier, unexplored, counts, lastExploredLocation);
			drawMaze(passages);
			StdDraw.show();
			StdDraw.pause(25);
		}
		// Solve the maze
		int[][] solution = solve(passages, new int[] { 0, 0 }, new int[] { width - 1, width - 1 });
		drawSolution(solution, width);
	}

	/**
	 * Modifies list so that pair is replaced with the (n - 1)th element of
	 * list. Assumes pair is an array of two numbers that appears somewhere in
	 * list. Thus, when this method is done, the first n - 1 element of list are
	 * the same as the first n elements of the old version, but with pair
	 * removed and with the order of elements potentially different.
	 * @param pair pair to be removed
	 * @param list the list that may contain the pair
	 * @param n the certain number of elements to remove pair from
	 */
	public static void remove(int[] pair, int[][] list, int n)
	{
		// Loops through rows
		for (int i = 0; i < n; i++)
		{
				if (list[i][0] == pair[0] && list[i][1] == pair[1])
				{
					// We replace the pair with the n-1 element
					list[i] = list[n-1];
				}

		}
	}


	/**
	 * Returns a path (sequence of locations) leading from start to goal in
	 * passages or null if there is no such path.
	 *
	 * @param passages
	 *            passages[x][y][direction] is true if there is a passage from
	 *            location x, y to its neighbor in direction. Directions are
	 *            specified by the constants NORTH, EAST, SOUTH, and WEST.
	 * @param start the starting point of the maze'
	 * @param goal the ending point to finish maze
	 * @returns a path solved from the start to the goal or null if a path cannot be found
	 */
	public static int[][] solve(boolean[][][] passages, int[] start, int[] goal)
	{
		// Base case: we're already at the goal
		if ((start[0] == goal[0]) && (start[1] == goal[1]))
		{
			return new int[][] { goal };
		}
		// Can we reach the goal from any of our neighbors?
		for (int d = 0; d < 4; d++)
		{
			if (passages[start[0]][start[1]][d])
			{
				int[] next = { start[0] + OFFSETS[d][0], start[1] + OFFSETS[d][1] };

				int[][] restOfPath = solve(passages,next,goal);

				if (restOfPath != null)
				{
					return addToFront(start, restOfPath);

				}
			}
		}
		// Nope -- we can't get there from here
		return null;
	}

}
