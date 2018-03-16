package de.iteratec.traze.client.demo;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.iteratec.traze.client.bot.strategy.FillTheCompleteBoardStrategy;
import de.iteratec.traze.client.bot.strategy.TrazeBotStrategy;
import de.iteratec.traze.client.model.Bike;
import de.iteratec.traze.client.model.Direction;
import de.iteratec.traze.client.model.Game;
import de.iteratec.traze.client.model.Grid;
import de.iteratec.traze.client.model.Move;
import de.iteratec.traze.client.model.Topics;
import de.iteratec.traze.client.mqtt.GameBrokerClient;
import de.iteratec.traze.client.mqtt.Registration;
import de.iteratec.traze.client.mqtt.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TrazeApplicationExample  {
	private static final Logger log = LoggerFactory.getLogger(TrazeApplicationExample.class);

	private final String playerNickName;
	private final String mqttClientName;
	private final GameBrokerClient client;

	private boolean initialMove = true;
	private boolean killed = false;

	private Registration registration;
	private TrazeBotStrategy myStrategy = new FillTheCompleteBoardStrategy();

	public static void main(String[] args) throws Exception {
		
		String playerNickNameDefault = "myLocalPlayerNick"+ Math.random();
		String mqttClientIdDefault = UUID.randomUUID().toString();

		log.info(String.format("Starting game as '%s' with mqttClientId '%s'", playerNickNameDefault, mqttClientIdDefault));
		
		while(true) {
			TrazeApplicationExample myRunningBot = new TrazeApplicationExample(playerNickNameDefault , mqttClientIdDefault, "tcp://traze.iteratec.de:1883");
		
			while (!myRunningBot.killed) {
	            TimeUnit.SECONDS.sleep(1);
	        }
	        
			myRunningBot.closeClient();
		}
	}
	
	/**
	 * @param playerId
	 * @param client
	 * @throws Exception 
	 */
	public TrazeApplicationExample(String playerNickName, String mqttClientName, String brokerUri) throws Exception {
		super();
		this.playerNickName = playerNickName;
		this.mqttClientName = mqttClientName;
		this.client = new GameBrokerClient(brokerUri, mqttClientName);
		
		this.initGame();
	}
	
	public void closeClient() throws IOException {
        log.info("closing bot...");
        this.client.close();
    }
	
	private void initGame() throws Exception {
        this.client.subscribe(Topics.GAMES_TOPIC, Game[].class, games -> {
            this.client.unsubscribe(Topics.GAMES_TOPIC);

            Game game = games[0];
            
            log.info(String.format("receive running game '%s' with active players '%s'", game.getName(), game.getActivePlayers()));
            
            this.client.subscribe(String.format(Topics.REGISTERED_TOPIC, game.getName(), this.mqttClientName), Registration.class, registration -> {
                this.registration = registration;
                
                log.info(String.format("receive registration %s ", registration));
            });
            register(game);

            this.client.subscribe(String.format(Topics.GRID_TOPIC, game.getName()), Grid.class, (grid) -> playRound(game.getName(), grid));
        });
    }
	
	public void register(Game game) throws Exception {
        log.info(String.format("register to game %s", game.getName()));
        
		client.publish(String.format(Topics.REGISTER_TOPIC, game.getName()), new RegistrationRequest(this.playerNickName, this.mqttClientName));
    }
	
	/**
	 * 
	 * @param game
	 * @param grid
	 * @throws Exception
	 */
	public void playRound(String game, Grid grid) throws Exception {
        // log.info("receive grid update");
        // log.info("receive grid update");
        //log.info(String.format("Current registration: %s", this.registration) );
		log.info("--- round ---");
        
        if (this.registration != null) {
            if (this.initialMove) {
                // make initial move
                log.info("enter running game by initial move...");
                
                publishNextMove(game, Direction.N);
                this.initialMove = false;
            } else {
                // make regular move
                Optional<Bike> bike = grid.getBikes().stream().filter(p -> Optional.ofNullable(p).isPresent() && p.getPlayerId() == registration.getId()).findFirst();
                if (bike.isPresent()) {
                    
                    log.info(String.format("current position: [%s, %s]", bike.get().getCurrentLocation()[0], bike.get().getCurrentLocation()[1]));
                    
                    
                    Direction nextMoveDirection = this.myStrategy.getNextMoveDirection(grid, bike.get());
                    log.info(String.format("Next move: %s", nextMoveDirection));
                    
                    publishNextMove(game, nextMoveDirection);
                } else {
                		log.info("player is killed");
                    
                    this.killed = true;
                }
            }
        }
    }
	
	/**
	 * 
	 * @param game
	 * @param direction
	 * @throws Exception
	 */
	private void publishNextMove(String game, Direction direction) throws Exception {
        String topic = String.format(Topics.STEERING_TOPIC, game, registration.getId());
        Move move = new Move(direction, registration.getSecretUserToken());
        this.client.publish(topic, move);
    }
}
