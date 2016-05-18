package base;

/**
 * @author polina
 */
public interface GameMechanics {

    public void addUser(String user);

    public void minusEnemyHP(String userName);

    public void incrementScore(String userName);

    public void run();
}
