package com.my.db.dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

/**
 * {@ code ManagerDAO} class repesents database manager.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ManagerDAO {

    private static ManagerDAO instance;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ManagerDAO.class);

    public static synchronized ManagerDAO getInstance() {

        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    protected ManagerDAO() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/RepairAgency");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot init DBManager", ex);
        }
    }

    protected ManagerDAO(String url) {

        MysqlConnectionPoolDataSource mds = new MysqlConnectionPoolDataSource();
        mds.setURL(url);
        mds.setUser("root");
        mds.setPassword("root");
        ds = mds;
    }


    private DataSource ds;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    protected void rollBackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }
    }

    protected void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                log.debug(ex.getMessage());
            }
        }
    }
}
