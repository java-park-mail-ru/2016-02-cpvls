package cfg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by polina on 26.04.16.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class Configs {
    private final Properties db = new Properties();
    private final Properties server = new Properties();


    int serverPort;
    String serverHost;

    String dbDialect;
    String dbDriver_class;
    String dbUrl;
    String dbName;
    String dbUsername;
    String dbPassword;
    String dbShow_sql;
    String dbHbm2ddlAuto;


    public Configs() {
        try {
            FileInputStream file = new FileInputStream("src/main/java/cfg/db.properties");
            db.load(file);
            file = new FileInputStream("src/main/java/cfg/server.properties");
            server.load(file);

            setAll();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAll() {
        this.serverPort = Integer.valueOf(server.getProperty("port"));
        this.serverHost = server.getProperty("host");

        this.dbDialect = db.getProperty("dialect");
        this.dbDriver_class = db.getProperty("driver_class");
        this.dbUrl = db.getProperty("url");
        this.dbName = db.getProperty("name");
        this.dbUsername = db.getProperty("username");
        this.dbPassword = db.getProperty("password");
        this.dbShow_sql = db.getProperty("show_sql");
        this.dbHbm2ddlAuto = db.getProperty("hbm2ddl_auto");

        assert(this.dbDialect != null);
        assert(this.dbDriver_class != null);
        assert(this.dbUrl != null);
        assert(this.dbName != null);
        assert(this.dbUsername != null);
        assert(this.dbPassword != null);
        assert(this.dbShow_sql != null);
        assert(this.dbHbm2ddlAuto != null);

    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getDbDialect() {
        return dbDialect;
    }

    public void setDbDialect(String dbDialect) {
        this.dbDialect = dbDialect;
    }

    public String getDbDriver_class() {
        return dbDriver_class;
    }

    public void setDbDriver_class(String dbDriver_class) {
        this.dbDriver_class = dbDriver_class;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbShow_sql() {
        return dbShow_sql;
    }

    public void setDbShow_sql(String dbShow_sql) {
        this.dbShow_sql = dbShow_sql;
    }

    public String getDbHbm2ddlAuto() {
        return dbHbm2ddlAuto;
    }

    public void setDbHbm2ddlAuto(String dbHbm2ddlAuto) {
        this.dbHbm2ddlAuto = dbHbm2ddlAuto;
    }


    public Properties getDb() {
        return db;
    }

    public Properties getServer() {
        return server;
    }
}
