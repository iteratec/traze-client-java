package de.iteratec.traze.client.mqtt;

public class RegistrationRequest {

	private String name;
	
	private String mqttClientName;

	/**
	 * @param name
	 * @param mqttClientName
	 */
	public RegistrationRequest(String name, String mqttClientName) {
		super();
		this.name = name;
		this.mqttClientName = mqttClientName;
	}

	/**
	 * @return the mqttClientName
	 */
	public String getMqttClientName() {
		return mqttClientName;
	}

	/**
	 * @param mqttClientName the mqttClientName to set
	 */
	public void setMqttClientName(String mqttClientName) {
		this.mqttClientName = mqttClientName;
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

}
