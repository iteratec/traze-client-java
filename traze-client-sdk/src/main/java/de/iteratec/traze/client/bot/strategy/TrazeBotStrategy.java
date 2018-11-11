package de.iteratec.traze.client.bot.strategy;

import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Grid;

public interface TrazeBotStrategy {

    /**
     * Evaluates the next {@link Direction} for the bot.
     *
     * @param grid Must not be null.
     * @param bike Must not be null.
     * @return Is not null.
     */
    public Direction getNextMoveDirection(Grid grid, Bike bike);

}