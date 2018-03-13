/**
 * 
 */
package de.iteratec.traze.client.bot.strategy;

import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Grid;

/**
 * @author Robert Seedorff
 *
 */
public class FillTheCompleteBoardStrategy implements TrazeBotStrategy {

	private Direction[] nextMoveStrategy;

	/**
	 * 
	 * @param grid
	 * @param bike
	 * @return
	 */
	private Direction[] getMoveStrategyTrail(Grid grid, Bike bike) {
		Direction[] strategy = new Direction[4];
		if (bike.getCurrentLocation()[0] < grid.getWidth() / 2) {
			// east first
			strategy[0] = Direction.E;
			strategy[1] = Direction.W;
		} else {
			// west first
			strategy[0] = Direction.W;
			strategy[1] = Direction.E;
		}
		if (bike.getCurrentLocation()[1] < grid.getHeight() / 2) {
			// north first
			strategy[2] = Direction.N;
			strategy[3] = Direction.S;
		} else {
			// south first
			strategy[2] = Direction.S;
			strategy[3] = Direction.N;
		}

		printStrategy(strategy);

		return strategy;
	}

	private void printStrategy(Direction[] strategy) {
		String resultStrategy = "";
		for (Direction direction : strategy) {
			resultStrategy += resultStrategy + String.format(" %s,", direction);
		}

		//System.out.println("Current Strategy: " + resultStrategy);
	}

	/**
	 * 
	 */
	@Override
	public Direction getNextMoveDirection(Grid grid, Bike bike) {

		// every move is invalid
		Direction nextmove = Direction.W;

		// ensure there is a current strategy filled with a list of moves
		if (this.nextMoveStrategy == null) {
			this.nextMoveStrategy = this.getMoveStrategyTrail(grid, bike);
		}
		
		// printStrategy(this.nextMoveStrategy);

		for (Direction planedDirection : this.nextMoveStrategy) {
			if (grid.isFree(bike.nextStep(planedDirection))) {
				nextmove = planedDirection;
			}
		}

		//System.out.println("Next move (by strategy): " + nextmove);

		return nextmove;
	}
}
