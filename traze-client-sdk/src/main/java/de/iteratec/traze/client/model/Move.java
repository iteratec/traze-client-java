package de.iteratec.traze.client.model;

import java.util.Objects;

public class Move {

	private Direction course;
	private String playerToken;

	/**
	 * @param course
	 * @param playerToken
	 */
	public Move(Direction course, String playerToken) {
		super();
		this.course = course;
		this.playerToken = playerToken;
	}

	/**
	 * @return the course
	 */
	public Direction getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            the course to set
	 */
	public void setCourse(Direction course) {
		this.course = course;
	}

	/**
	 * @return the playerToken
	 */
	public String getPlayerToken() {
		return playerToken;
	}

	/**
	 * @param playerToken
	 *            the playerToken to set
	 */
	public void setPlayerToken(String playerToken) {
		this.playerToken = playerToken;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Move move = (Move) o;
		return course == move.course &&
				Objects.equals(playerToken, move.playerToken);
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, playerToken);
	}
}
