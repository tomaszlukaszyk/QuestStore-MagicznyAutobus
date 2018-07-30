package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.PostgreDBC;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class ConnectionPool {

//    private static GenericObjectPool gPool = null;
    private static ConnectionPool pool = null;
    private DataSource db;
    private  static  GenericObjectPool gPool = null;

    private ConnectionPool() {
        this.db = setUpPool();
    }

    public static ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
            return pool;
        } else {
            return pool;
        }
    }

    public void print() {
        System.out.println(pool);
        System.out.println(db);
    }
    @SuppressWarnings("unused")
    private DataSource setUpPool() {
        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
        gPool = new GenericObjectPool();
        gPool.setMaxActive(10);
        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
        ConnectionFactory cf = new DriverManagerConnectionFactory(PostgreDBC.dbUrl, PostgreDBC.username, PostgreDBC.password);
        // Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    private GenericObjectPool getConnectionPool() {
        return gPool;
    }


    public Connection getConnection() throws SQLException {
        return db.getConnection();
    }
    // This Method Is Used To Print The Connection Pool Status

    public void printDbStatus() {

        System.out.println("Max.: " + getConnectionPool().getMaxActive() + "; Active: "
                                    + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }

}