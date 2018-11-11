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

import java.util.Arrays;
import java.util.List;

/**
 * @author robert
 */
public class CircleStrategy implements TrazeBotStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameBrokerClient.class);

    private final List<Direction> nextMoveStrategy = Arrays.asList(Direction.N, Direction.E, Direction.S, Direction.W);

    private int stepsPerLine = 0;

    @Override
    public Direction getNextMoveDirection(Grid grid, Bike bike) {
        LOGGER.debug("getNextMoveDirection");

        // every move is invalid
        Direction nextmove = Direction.W;
        boolean isFree = false;

        for (int i = 0; i < 4 && !isFree; i++) {
            LOGGER.debug("Check move: {}", nextMoveStrategy.get(i));
            isFree = grid.isFree(bike.nextStep(nextMoveStrategy.get(i)));
            nextmove = nextMoveStrategy.get(i);
        }

        LOGGER.debug("Next move (by strategy): {} , steps {}", nextmove, stepsPerLine);

        return nextmove;
    }
}
