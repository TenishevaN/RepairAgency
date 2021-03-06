package com.my.db.dao;

import com.my.db.model.AccountLocalization;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@ code UserDAO} class implementation InterfaceDAO for the user model.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class UserDAO extends ManagerDAO implements InterfaceDAO<User> {

    private static final Logger log = LogManager.getLogger(UserDAO.class);

    public static final String TABLE_USER = "account";
    private static final String ADD_NEW_USER = "INSERT INTO " + TABLE_USER + "(login, password, role_id, email, name) values (?, ?, ?, ?, ?);";
    private static final String FIND_ALL_USERS = "SELECT * FROM " + TABLE_USER + " where deleted = false";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM " + TABLE_USER + " WHERE login = ? and deleted = false";
    private static final String FIND_ALL_MASTERS = "SELECT id, role_id, name FROM account ac where role_id = 3 and deleted = false;";
    private static final String UPDATE_USER = "UPDATE " + TABLE_USER + " SET login = ?, name = ?, role_id = ?, invoice_id = ?, email = ? " + " WHERE " + SQLConstants.FIELD_ID + " = ?;";
    public static final String FIND_USER_BY_ID = "SELECT account.id, account.name, account.login, account.password, account.invoice_id, account.email,  account.role_id FROM account where account.id = ?;";
    public static final String DELETE_USER = "UPDATE " + TABLE_USER + " SET deleted = true " + " WHERE " + SQLConstants.FIELD_ID + " = ?;";
    private static final String DELETE_MARKED_USER = "Delete from " + TABLE_USER + " where deleted = true";

    public UserDAO() {
        super();
    }

    public UserDAO(String url) throws NamingException {
        super(url);
    }

    @Override
    public boolean insert(User user) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRoleId());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getName());

            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            log.debug("insert user exception " + ex.getMessage());
            rollBackTransaction(connection);
            return false;
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_USERS);) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException ex) {
            log.debug("get all users exception {}", ex.getMessage());
        }

        return users;
    }

    public Map<User, List<AccountLocalization>> getMasterList() {

        Map<User, List<AccountLocalization>> masters = new HashMap<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_MASTERS);) {
            while (rs.next()) {
                User newMaster = mapMaster(rs);
                AccountLocalizationDAO accountLocalizationDAO = new AccountLocalizationDAO();
                List<AccountLocalization> userLocalization = accountLocalizationDAO.get(newMaster.getId());
                masters.put(newMaster, userLocalization);
            }
            log.debug("Get list master {}", masters);
        } catch (SQLException ex) {
            log.debug("Get list master exception {}", ex.getMessage());
        }
        return masters;

    }

    public Map<User, List<AccountLocalization>> getMasterList(String url) {

        Map<User, List<AccountLocalization>> masters = new HashMap<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_MASTERS);) {
            while (rs.next()) {
                User newMaster = mapMaster(rs);
                AccountLocalizationDAO accountLocalizationDAO = null;
                try {
                    accountLocalizationDAO = new AccountLocalizationDAO(url);
                } catch (NamingException e) {
                    log.debug("Get list master {}", masters);
                }
                List<AccountLocalization> userLocalization = accountLocalizationDAO.get(newMaster.getId());
                masters.put(newMaster, userLocalization);
            }
            log.debug("Get list master {}", masters);
        } catch (SQLException ex) {
            log.debug("Get list master exception {}", ex.getMessage());
        }
        return masters;

    }

    @Override
    public User get(int id) {

        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_USER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (SQLException ex) {
            log.debug("get user exception " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        log.debug("Finded user {}", user);
        return user;
    }

    public User get(String login) {

        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
        } catch (SQLException ex) {
            log.debug("get user exception " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return user;
    }

    @Override
    public boolean update(User element) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, element.getLogin());
            preparedStatement.setString(2, element.getName());
            preparedStatement.setInt(3, element.getRoleId());

            preparedStatement.setString(5, element.getEmail());
            preparedStatement.setInt(6, element.getId());
            Integer invoiceId = element.getInvoiceId();
            if (invoiceId != -1) {
                preparedStatement.setInt(4, invoiceId);
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            connection.commit();
        } catch (SQLException ex) {
            rollBackTransaction(connection);
            log.debug("update user exception {}", ex.getMessage());
            return false;

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    public boolean delete(User user) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.debug(" delete user exception {}", ex.getMessage());
            return false;
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    public boolean deleteMarkedUsers() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(DELETE_MARKED_USER);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            rollBackTransaction(connection);
            log.debug(" delete marked users exception {}", ex.getMessage());
            return false;
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    public User mapMaster(ResultSet rs) {

        User user = new User();
        try {
            user.setId(rs.getInt(SQLConstants.FIELD_ID));
            user.setName(rs.getString(SQLConstants.FIELD_NAME));
        } catch (SQLException ex) {
            log.debug("Map master exception {}", ex.getMessage());
        }
        return user;
    }

    public User mapUser(ResultSet rs) {

        User user = new User();
        try {
            user.setId(rs.getInt(SQLConstants.FIELD_ID));
            user.setName(rs.getString(SQLConstants.FIELD_NAME));
            user.setLogin(rs.getString(SQLConstants.FIELD_LOGIN));
            user.setPassword(rs.getString(SQLConstants.FIELD_PASSWORD));
            user.setRoleId(rs.getInt(SQLConstants.FIELD_ROLE_ID));
            Integer invoiceId = rs.getInt(SQLConstants.FIELD_INVOICE_ID);
            if (invoiceId != null && invoiceId != 0) {
                user.setInvoiceId(invoiceId);
            } else {
                user.setInvoiceId(-1);
            }
            user.setEmail(rs.getString("email"));

        } catch (SQLException ex) {
            log.debug("Map user exception {}", ex.getMessage());
        }
        return user;
    }
}
