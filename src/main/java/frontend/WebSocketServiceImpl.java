package frontend;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class WebSocketServiceImpl implements WebSocketService {
    private final Map<String, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    @Override
    public void notifyMyNewHP(GameUser user) {
        userSockets.get(user.getMyName()).setMyHP(user);
    }

    @Override
    public void notifyEnemyNewHP(GameUser user) {
        userSockets.get(user.getMyName()).setEnemyHP(user);
    }

    @Override
    public void notifyMyNewScore(GameUser user) {
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    @Override
    public void notifyEnemyNewScore(GameUser user) {
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    @Override
    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }
}
