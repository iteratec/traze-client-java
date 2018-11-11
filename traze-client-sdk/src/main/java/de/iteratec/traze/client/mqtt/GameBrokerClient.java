package de.iteratec.traze.client.mqtt;

import java.io.Closeable;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;

/**
 * 
 * @author Kristian Kottke
 *
 */
public class GameBrokerClient implements Closeable {
	private static final Logger log = LogManager.getLogger(GameBrokerClient.class);

	private final MqttClient mqttClient;
	
	boolean cleanSession = true;			// Non durable subscriptions
	String userName = null;
	String password = null;

	public GameBrokerClient(final String brokerUri, final String mqttClientName) throws Exception {
		
		log.info("Starting GameBrokerClient");
		
		this.mqttClient = new MqttClient(brokerUri, mqttClientName);

		MqttConnectOptions options = new MqttConnectOptions();
		
		// Setzen einer Persistent Session
		options.setCleanSession(this.cleanSession);
		//options.setUserName(this.userName);
		//options.setPassword(this.password.toCharArray());

		this.mqttClient.connect(options);
				
		log.info(String.format("connected: %s", this.mqttClient.isConnected()));
        
	}

	public <T> void subscribe(final String topic, final Class<T> contentType, final Callback<T> callback)
			throws Exception {
		this.mqttClient.subscribe(topic, (topic1, message) -> {
			T content = new Gson().fromJson(new String(message.getPayload()), contentType);
			callback.run(content);
		});
	}

	public void subscribe(TraceMessageCallback callback, final String topic) throws MqttException {
		this.mqttClient.setCallback(callback);
		this.mqttClient.subscribe(topic);
	}

	public void unsubscribe(final String topic) throws MqttException {
		this.mqttClient.unsubscribe(topic);
	}

	/**
	 * Retained is false by default.
	 * 
	 * @param topic
	 * @param content
	 * @throws Exception
	 */
	public void publish(final String topic, final Object content) throws Exception {
		this.publish(topic, content, false);
	}

	public void publish(final String topic, final Object content, final boolean retained) throws Exception {
		String payload = new Gson().toJson(content);
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setRetained(retained);
		this.mqttClient.publish(topic, message);
	}

	/**
	 * Close the connection
	 */
	@Override
	public void close() throws IOException {
		try {
			this.mqttClient.disconnect();
			this.mqttClient.close();
		} catch (MqttException e) {
			e.printStackTrace();
			throw new IOException("error while closing client", e);
		}
	}

}
