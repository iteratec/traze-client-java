package de.iteratec.traze.client.bot.strategy;

import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Grid;

public interface TrazeBotStrategy {
	
	/**
	 * 
	 * @param grid
	 * @param bike
	 * @return
	 */
	public Direction getNextMoveDirection(Grid grid, Bike bike);
	
}