package base;

/**
 * @author polina.artem
 */
public interface GameMechanics {

    void addUser(String user);

    void minusEnemyHP(String userName);

    void incrementScore(String userName);

    void run();
}
