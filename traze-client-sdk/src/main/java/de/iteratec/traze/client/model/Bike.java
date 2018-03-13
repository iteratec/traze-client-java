package de.iteratec.traze.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Robert Seedorff
 *
 */
public class Bike {

	private static final int[] DEFAULT_POSITION = new int[] { -1, -1 };

	private int playerId;

	private int[] currentLocation;
	private Direction direction;
	private List<int[]> trail = new ArrayList<>();

	/**
	 * @param playerId
	 * @param currentLocation
	 */
	public Bike(int playerId, int[] currentLocation) {
		this.playerId = playerId;
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @return the currentLocation
	 */
	public int[] getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Returns a list of all positions. Each position (a list element) represents a
	 * coordinate (x,y).
	 * 
	 * @return the trail a list of all positions. Each position (a list element)
	 *         represents a corridinate (x,y).
	 */
	public List<int[]> getTrail() {
		return trail;
	}

	/**
	 * Returns true if the Bike is alive (moving on the grid), otherwise false
	 * (player is killed).
	 * 
	 * @return true if the Bike is alive (moving on the grid), otherwise false
	 *         (player is killed).
	 */
	boolean isAlive() {
		return currentLocation != null && !DEFAULT_POSITION.equals(currentLocation);
	}

	void kill() {
		currentLocation = DEFAULT_POSITION;
		trail.clear();
	}

	/**
	 * 
	 * @param direction
	 * @return
	 */
	public int[] nextStep(Direction direction) {
		return new int[] { currentLocation[0] + direction.getDeltaX(), currentLocation[1] + direction.getDeltaY() };
	}

	/**
	 * 
	 * @param direction
	 */
	public void move(Direction direction) {
		trail.add(currentLocation);
		currentLocation = nextStep(direction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		Bike bike = (Bike) o;

		return playerId == bike.playerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + playerId;
		return result;
	}
}
