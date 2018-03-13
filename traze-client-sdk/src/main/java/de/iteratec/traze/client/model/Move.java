package de.iteratec.traze.client.model;

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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		Move move = (Move) o;

		if (course != move.course) {
			return false;
		}
		return playerToken.equals(move.playerToken);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + course.hashCode();
		result = 31 * result + playerToken.hashCode();
		return result;
	}
}
