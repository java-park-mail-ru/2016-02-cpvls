package cfg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by polina on 26.04.16.
 */
public class configs {
    final public Properties db = new Properties();
    final public Properties server = new Properties();


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



    public configs() {
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
        setServerPort(Integer.valueOf(server.getProperty("port")));
        setServerHost(server.getProperty("host"));

        setDbDialect(db.getProperty("dialect"));
        setDbDriver_class(db.getProperty("driver_class"));
        setDbUrl(db.getProperty("url"));
        setDbName(db.getProperty("name"));
        setDbUsername(db.getProperty("username"));
        setDbPassword(db.getProperty("password"));
        setDbShow_sql(db.getProperty("show_sql"));
        setDbHbm2ddlAuto(db.getProperty("hbm2ddl_auto"));

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
