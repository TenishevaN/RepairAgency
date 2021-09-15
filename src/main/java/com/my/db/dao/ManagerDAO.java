package com.my.db.dao;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ManagerDAO {

    private static ManagerDAO instance;

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


   private DataSource ds;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    protected void rollBackTransaction(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
         //   logger.log(Level.WARNING, ex.getMessage());
        }
    }

    protected void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                // log
            }
        }
    }
}
