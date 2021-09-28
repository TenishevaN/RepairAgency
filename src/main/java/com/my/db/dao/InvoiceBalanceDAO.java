package com.my.db.dao;

import com.my.db.model.InvoiceBalance;
import org.apache.logging.log4j.LogManager;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code InvoiceBalanceDAO} class implementation InterfaceDAO for the invoice balance model.
 */
public class InvoiceBalanceDAO extends ManagerDAO implements InterfaceDAO<InvoiceBalance> {

    private static final org.apache.logging.log4j.Logger log =  LogManager.getLogger(InvoiceBalanceDAO.class);

    private static final String TABLE_INVOICE_BALANCE = "invoice_balance";
    private static final String ADD_NEW_PAYMENT = "INSERT INTO " + TABLE_INVOICE_BALANCE + "(invoice_id, repair_request_id, ammount) values (?, ?, ?);";
    private static final String FIND_PAYMENT = "SELECT * FROM " + TABLE_INVOICE_BALANCE + " WHERE ID = ?;";
    private static final String UPDATE_PAYMENT = "UPDATE " + TABLE_INVOICE_BALANCE + " SET ammount = ? " + " WHERE " + SQLConstants.FIELD_ID + " = ?;";
    private static final String FIND_ALL_PAYMENTS = "SELECT * FROM " + TABLE_INVOICE_BALANCE;
    private static final String GET_TOTAL_BY_INVOICE_ID = "SELECT SUM(ammount) AS total FROM " + TABLE_INVOICE_BALANCE + " where invoice_id = ?";
    private static final String GET_TOTAL_BY_REQUEST_ID = "SELECT SUM(ammount) AS total FROM " + TABLE_INVOICE_BALANCE + " where repair_request_id = ?";

    @Override
    public boolean insert(InvoiceBalance element) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(ADD_NEW_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, element.getInvoiceId());
            int repairRequestId = element.getRepairRequestId();
            if (repairRequestId != -1) {
                preparedStatement.setInt(2, element.getRepairRequestId());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setBigDecimal(3, element.getAmmount());
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            log.debug(" insert payment exception " + ex.getMessage());
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
    public List<InvoiceBalance> getAll() {

        List<InvoiceBalance> payments = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_PAYMENTS);) {
            while (rs.next()) {
                payments.add(mapPayment(rs));
            }

        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }

        return payments;
    }

    @Override
    public InvoiceBalance get(int id) {

        InvoiceBalance payment = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_PAYMENT);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();
            if (rs.next())
                payment = mapPayment(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return payment;
    }

    public BigDecimal getTotal(int id) {

        BigDecimal total = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(GET_TOTAL_BY_INVOICE_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getBigDecimal(1);
            }
        } catch (
                SQLException ex) {
            log.debug("get total by invoice id exception " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return total;
    }

    public BigDecimal getBalanceOwed(int id) {

        BigDecimal total = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(GET_TOTAL_BY_REQUEST_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getBigDecimal(1);
            }
        } catch (
                SQLException ex) {
            log.debug("get total by request id exception " + ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return total;
    }

    @Override
    public boolean update(InvoiceBalance element) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(UPDATE_PAYMENT);
            preparedStatement.setBigDecimal(1, element.getAmmount());
            preparedStatement.setInt(2, element.getId());
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

    private InvoiceBalance mapPayment(ResultSet rs) {

        InvoiceBalance payment = new InvoiceBalance();
        try {
            payment.setId(rs.getInt(SQLConstants.FIELD_ID));
            payment.setInvoiceId(rs.getInt(SQLConstants.FIELD_INVOICE_ID));
            payment.setInvoiceId(rs.getInt(SQLConstants.FIELD_REPAIR_REQUEST_ID));
            payment.setAmmount(rs.getBigDecimal(SQLConstants.FIELD_AMMOUNT));
        } catch (SQLException ex) {
            log.debug("map payment exception " + ex.getMessage());
        }
        return payment;
    }
}

