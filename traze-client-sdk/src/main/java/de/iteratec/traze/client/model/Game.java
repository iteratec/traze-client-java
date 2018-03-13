package de.iteratec.traze.client.model;

/**
 * 
 * @author Robert Seedorff
 *
 */
public class Game {

	private String name;
	private int activePlayers;
	
	/**
	 * @param name
	 * @param activePlayers
	 */
	public Game(String name, int activePlayers) {
		super();
		this.name = name;
		this.activePlayers = activePlayers;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the activePlayers
	 */
	public int getActivePlayers() {
		return activePlayers;
	}

	/**
	 * @param activePlayers
	 *            the activePlayers to set
	 */
	public void setActivePlayers(int activePlayers) {
		this.activePlayers = activePlayers;
	}

}
