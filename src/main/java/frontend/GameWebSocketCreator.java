package frontend;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author polina.artem
 */
@SuppressWarnings("ConstantConditions")
public class GameWebSocketCreator implements WebSocketCreator {
    private final AuthService authService;
    private final GameMechanics gameMechanics;
    private final WebSocketService webSocketService;

    public GameWebSocketCreator(AuthService authService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.authService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = authService.getUserName(sessionId);
        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}
