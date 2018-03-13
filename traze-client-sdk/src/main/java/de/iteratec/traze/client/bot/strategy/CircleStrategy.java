/**
 * 
 */
package de.iteratec.traze.client.bot.strategy;

import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Grid;

/**
 * @author robert
 *
 */
public class CircleStrategy implements TrazeBotStrategy {

	private Direction[] nextMoveStrategy;
	
	private int stepsPerLine = 0;
	
	/**
	 * 
	 */
	public CircleStrategy() {
		super();
		Direction[] nextMoveStrategy = new Direction[4];
		
		nextMoveStrategy[0] = Direction.N;
		nextMoveStrategy[1] = Direction.E;
		nextMoveStrategy[2] = Direction.S;
		nextMoveStrategy[3] = Direction.W;
	}

	/**
	 * 
	 */
	@Override
	public Direction getNextMoveDirection(Grid grid, Bike bike) {
		
		System.out.println("getNextMoveDirection");

		// every move is invalid
		Direction nextmove = Direction.W;
		boolean isFree = false;
		
		for(int i=0; i<4 && !isFree; i++) {
			System.out.println("Check move: " + nextMoveStrategy[i]);
			isFree = grid.isFree(bike.nextStep(nextMoveStrategy[i]));
			nextmove = nextMoveStrategy[i];
		}

		System.out.println(String.format("Next move (by strategy): %s , steps %s", nextmove, stepsPerLine));

		return nextmove;
	}
}
