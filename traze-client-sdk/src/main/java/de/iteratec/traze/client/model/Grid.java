package de.iteratec.traze.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

	private static final int FREE_CELL = 0;

	private int height;
	private int width;

	private int[][] tiles;

	private List<Bike> bikes;

	public Grid() {

	}

	public Grid(int height, int width) {
		this.height = height;
		this.width = width;
		this.tiles = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = FREE_CELL;
			}
		}
		this.bikes = new ArrayList<>();
		this.bikes.add(null);
	}

	public Bike addBike() {
		Bike bike = new Bike(bikes.size(), generateStartPosition());
		bikes.add(bike);

		if (placeBikeOnGrid(bike)) {
			return bike;
		} else {
			// LOG.error("putting bike {} on start position {} failed", bike.getPlayerId(),
			// bike.getCurrentLocation());
			System.out.println(String.format("putting bike {} on start position {} failed", bike.getPlayerId(),
					bike.getCurrentLocation()));
			throw new IllegalStateException("putting bike on start position failed");
		}
	}

	/**
	 * @return the bikes
	 */
	public List<Bike> getBikes() {
		return bikes;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the tiles
	 */
	public int[][] getTiles() {
		return tiles;
	}

	public boolean moveBike(int id, Move move) {
		Bike bike = bikes.get(id);
		if (bike == null) {
			throw new IllegalArgumentException("bike is unknown or destroyed: " + id);
		}

		bike.move(move.getCourse());
		return placeBikeOnGrid(bike);
	}

	public boolean isFree(int[] position) {
		return isValid(position) && tiles[position[0]][position[1]] == FREE_CELL;
	}
	
	public boolean isFreeWithSpace(Direction currentDirection, Direction nextDirection, int[] nextPosition) {
		boolean result = isValid(nextPosition) && tiles[nextPosition[0]][nextPosition[1]] == FREE_CELL;
		System.out.println(String.format("--- Check Next Step: [current position %s | next position %s] isFree: %s", currentDirection, nextDirection, result));
		
		return result;
	}

	public boolean isValid(int[] position) {
		return position != null && position[0] >= 0 && position[1] >= 0 && position[0] < width && position[1] < height;
	}

	public void clearBike(int id) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (tiles[i][j] == id) {
					tiles[i][j] = 0;
				}
			}
		}

		if (bikes.get(id) != null) {
			bikes.get(id).kill();
		}
		bikes.set(id, null);
	}

	private boolean placeBikeOnGrid(Bike bike) {
		if (isFree(bike.getCurrentLocation())) {
			tiles[bike.getCurrentLocation()[0]][bike.getCurrentLocation()[1]] = bike.getPlayerId();
			// bike.getCurrentLocation());
			System.out.println(String.format("place bike {} on {}", bike.getPlayerId(), bike.getCurrentLocation()));
			return true;
		} else {
			// bike.getPlayerId());
			System.out.println(
					String.format("position {} of bike {} is invalid", bike.getCurrentLocation(), bike.getPlayerId()));

			return false;
		}
	}

	private int[] generateStartPosition() {
		Random random = new Random();
		int[] position = new int[2];
		while (!isFree(position)) {
			position = new int[] { random.nextInt(width), random.nextInt(height) };
		}

		return position;
	}
}
