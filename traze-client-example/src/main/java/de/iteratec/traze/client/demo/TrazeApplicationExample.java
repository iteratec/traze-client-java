package de.iteratec.traze.client.demo;

import de.iteratec.traze.client.bot.strategy.FillTheCompleteBoardStrategy;
import de.iteratec.traze.client.bot.strategy.TrazeBotStrategy;
import de.iteratec.traze.client.model.*;
import de.iteratec.traze.client.mqtt.GameBrokerClient;
import de.iteratec.traze.client.mqtt.Registration;
import de.iteratec.traze.client.mqtt.RegistrationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Example traze application.
 */
public class TrazeApplicationExample {
    private static final Logger LOGGER = LogManager.getLogger(TrazeApplicationExample.class);

    private final String playerNickName;
    private final String mqttClientName;
    private final GameBrokerClient client;

    private boolean initialMove = true;
    private boolean killed = false;

    private Registration registration;
    private TrazeBotStrategy myStrategy = new FillTheCompleteBoardStrategy();

    /**
     * Main method which can be executed.
     * @param args
     * program arguments.
     * @throws Exception
     * when the
     */
    public static void main(String[] args) throws Exception {

        String playerNickNameDefault = "java-client-" + Math.random();
        String mqttClientIdDefault = UUID.randomUUID().toString();

        LOGGER.info("Starting game as '{}' with mqttClientId '{}'", playerNickNameDefault, mqttClientIdDefault);

        while (true) {
            TrazeApplicationExample myRunningBot= new TrazeApplicationExample(playerNickNameDefault, mqttClientIdDefault, "tcp://traze.iteratec.de:1883");

            while (!myRunningBot.killed) {
                TimeUnit.SECONDS.sleep(1);
            }
            myRunningBot.closeClient();
        }
    }

    /**
     * @param playerNickName this name is shown in the scoreboard.
     * @param mqttClientName must be a unique id.
     * @throws Exception when the registration could not be established.
     */
    private TrazeApplicationExample(String playerNickName, String mqttClientName, String brokerUri) throws Exception {
        super();
        this.playerNickName = playerNickName;
        this.mqttClientName = mqttClientName;
        this.client = new GameBrokerClient(brokerUri, mqttClientName);

        this.initGame();
    }

    private void closeClient() throws IOException {
        LOGGER.info("closing bot...");
        this.client.close();
    }

    private void initGame() throws Exception {
        this.client.subscribe(Topics.GAMES_TOPIC, Game[].class, games -> {
            this.client.unsubscribe(Topics.GAMES_TOPIC);

            Game game = games[0];

            LOGGER.info("receive running game '{}' with active players '{}'", game.getName(), game.getActivePlayers());

            this.client.subscribe(String.format(Topics.REGISTERED_TOPIC, game.getName(), this.mqttClientName), Registration.class, reg -> {
                this.registration = reg;

                LOGGER.info("receive registration {} ", reg);
            });
            register(game);

            this.client.subscribe(String.format(Topics.GRID_TOPIC, game.getName()), Grid.class, grid -> playRound(game.getName(), grid));
        });
    }

    private void register(Game game) throws Exception {
        LOGGER.info("register to game {}", game.getName());

        client.publish(String.format(Topics.REGISTER_TOPIC, game.getName()), new RegistrationRequest(this.playerNickName, this.mqttClientName));
    }

    private void playRound(String game, Grid grid) throws Exception {
        LOGGER.info("--- round ---");

        if (this.registration != null) {
            if (this.initialMove) {
                // make initial move
                LOGGER.info("enter running game by initial move...");

                publishNextMove(game, Direction.N);
                this.initialMove = false;
            } else {
                // make regular move
                Optional<Bike> bike = grid.getBikes().stream().filter(p -> Optional.ofNullable(p).isPresent() && p.getPlayerId() == registration.getId()).findFirst();
                if (bike.isPresent()) {
                    LOGGER.info("current position: [{}, {}]", bike.get().getCurrentLocation()[0], bike.get().getCurrentLocation()[1]);
                    Direction nextMoveDirection = this.myStrategy.getNextMoveDirection(grid, bike.get());
                    publishNextMove(game, nextMoveDirection);
                } else {
                    LOGGER.info("player is killed");
                    this.killed = true;
                }
            }
        }
    }

    private void publishNextMove(String game, Direction direction) throws Exception {
        String topic = String.format(Topics.STEERING_TOPIC, game, registration.getId());
        Move move = new Move(direction, registration.getSecretUserToken());
        this.client.publish(topic, move);
    }
}
