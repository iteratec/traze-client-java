package de.iteratec.traze.client.mqtt;


public class Registration {

    private int id;
    private String name;
    private String secretUserToken;
    private int[] position;
    private String color;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the secretUserToken
	 */
	public String getSecretUserToken() {
		return secretUserToken;
	}
	/**
	 * @param secretUserToken the secretUserToken to set
	 */
	public void setSecretUserToken(String secretUserToken) {
		this.secretUserToken = secretUserToken;
	}
	/**
	 * @return the position
	 */
	public int[] getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(int[] position) {
		this.position = position;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
    
}
