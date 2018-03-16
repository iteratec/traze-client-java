/**
 * 
 */
package de.iteratec.traze.client.bot.strategy;

import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Grid;
import de.iteratec.traze.client.mqtt.GameBrokerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author robert
 *
 */
public class CircleStrategy implements TrazeBotStrategy {
	private static final Logger log = LoggerFactory.getLogger(GameBrokerClient.class);

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
		
		log.info("getNextMoveDirection");

		// every move is invalid
		Direction nextmove = Direction.W;
		boolean isFree = false;
		
		for(int i=0; i<4 && !isFree; i++) {
			log.info("Check move: " + nextMoveStrategy[i]);
			isFree = grid.isFree(bike.nextStep(nextMoveStrategy[i]));
			nextmove = nextMoveStrategy[i];
		}

		log.info(String.format("Next move (by strategy): %s , steps %s", nextmove, stepsPerLine));

		return nextmove;
	}
}
