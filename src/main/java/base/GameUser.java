package base;

/**
 * @author polina.artem
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class GameUser {
    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;
    private int myHP = 100;
    private int enemyHP = 100;

    public int getMyHP() {
        return myHP;
    }

    public int getEnemyHP() {
        return enemyHP;
    }

    public void setMyHP(int myHP) {
        this.myHP = myHP;
    }

    public void setEnemyHP(int enemyHP) {
        this.enemyHP = enemyHP;
    }


    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() {
        return enemyScore;
    }

    public void incrementMyScore() {
        myScore++;
    }

    public void minusMyHP() {
        myHP = myHP - 10;
        if (myHP == 0) {
            myHP = 100;
            incrementEnemyScore();
        }
    }

    public void minusEnemyHP() {
        enemyHP = enemyHP - 10;
        if (enemyHP == 0) {
            enemyHP = 100;
            incrementMyScore();
        }
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}
