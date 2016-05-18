package base;

import frontend.GameWebSocket;

/**
 * @author polina
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyMyNewHP(GameUser user);

    void notifyEnemyNewHP(GameUser user);

    void notifyMyNewScore(GameUser user);

    void notifyEnemyNewScore(GameUser user);

    void notifyStartGame(GameUser user);

    void notifyGameOver(GameUser user, boolean win);
}
