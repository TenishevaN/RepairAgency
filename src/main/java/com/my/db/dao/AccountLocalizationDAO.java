package com.my.db.dao;

import com.my.db.model.AccountLocalization;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountLocalizationDAO extends ManagerDAO{

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(AccountLocalizationDAO.class);

    private static final String FIND_USER_LOCALIZATION_BY_ID = "SELECT * FROM account_localization where account_id = ?;";
    private static final String FIND_USER_LOCALIZATION_BY_ID_LANGUAGE = "SELECT * FROM account_localization where account_id = ? and language_id = ?;";
    private static final String UPDATE_USER_LOCALIZATION = "UPDATE account_localization SET name = ? where account_id = ? and language_id = ?;";
    private static final String INSERT_USER_LOCALIZATION = "INSERT INTO account_localization (language_id, account_id) values (?, ?)";

    public List<AccountLocalization> get(int id) {
        List<AccountLocalization> accountLocalization = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_USER_LOCALIZATION_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountLocalization.add(mapAccountLocalization(rs));
            }
        } catch (SQLException ex) {
            log.debug("get account localization exception {}", ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return accountLocalization;
    }

    public AccountLocalization get(int userId, int languageId) {
        AccountLocalization accountLocalization = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_USER_LOCALIZATION_BY_ID_LANGUAGE);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, languageId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountLocalization = mapAccountLocalization(rs);
            }
        } catch (SQLException ex) {
            log.debug("get account localization exception {}", ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }

        return accountLocalization;
    }

    public boolean insert(int userId, int languageId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER_LOCALIZATION, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, languageId);
            preparedStatement.setInt(2, userId);
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            connection.commit();
        } catch (SQLException ex) {
            log.debug("insert user localization  exception " + ex.getMessage());
            rollBackTransaction(connection);
            return false;
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    public boolean update(AccountLocalization accountLocalization) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_LOCALIZATION);
            preparedStatement.setString(1, accountLocalization.getName());
            preparedStatement.setInt(2, accountLocalization.getAccount_id());
            preparedStatement.setInt(3, accountLocalization.getLanguage_id());
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            connection.commit();
        } catch (SQLException ex) {
            rollBackTransaction(connection);
            log.debug(ex.getMessage());
            return false;

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    private AccountLocalization mapAccountLocalization(ResultSet rs) {

        AccountLocalization accountLocalization = new AccountLocalization();
        try {
            accountLocalization.setId(rs.getInt(SQLConstants.FIELD_ID));
            accountLocalization.setAccount_id(rs.getInt(SQLConstants.FIELD_ACCOUNT_ID));
            accountLocalization.setLanguage_id(rs.getInt(SQLConstants.FIELD_LANGUAGE_ID));
            accountLocalization.setName(rs.getString(SQLConstants.FIELD_NAME));

        } catch (SQLException ex) {
            log.debug("Map account localization exception {}",  ex.getMessage());
        }
        return accountLocalization;
    }
}
