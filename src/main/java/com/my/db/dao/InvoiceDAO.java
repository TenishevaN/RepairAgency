package com.my.db.dao;

import com.my.db.model.Invoice;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code InvoiceDAO} class implementation InterfaceDAO for the invoice model.
 */
public class InvoiceDAO extends ManagerDAO implements InterfaceDAO<Invoice> {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(InvoiceDAO.class);
    private static final String TABLE_INVOICE = "invoice";
    private static final String FIND_INVOICE = "SELECT * FROM "+ TABLE_INVOICE + " WHERE ID = ?;";
    private static final String ADD_NEW_INVOICE = "INSERT INTO " + TABLE_INVOICE + "(account_id) values (?);";
    private static final String UPDATE_INVOICE = "UPDATE " + TABLE_INVOICE + " SET ammount = ? " + " WHERE " + SQLConstants.FIELD_ID + " = ?;";
    private static final String FIND_ALL_INVOICES = "SELECT * FROM " + TABLE_INVOICE + ";";

    public InvoiceDAO() {
        super();
    }

    public InvoiceDAO(String url) {
        super(url);
    }

    public Invoice get(int id) {
        Invoice invocie = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_INVOICE);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();
            if (rs.next())
                invocie = mapInvoice(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return invocie;

    }

    @Override
    public boolean insert(Invoice invoice) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(ADD_NEW_INVOICE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, invoice.getAccount_id());
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                invoice.setId(resultSet.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            log.debug(" insert invoice exception " + ex.getMessage());
            System.out.println(" insert invoice exception " + ex.getMessage());
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
    public List<Invoice> getAll() {

        List<Invoice> invoices = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_INVOICES);) {
            while (rs.next()) {
                invoices.add(mapInvoice(rs));
            }

        } catch (SQLException ex) {
            log.debug("get all users exception {}", ex.getMessage());
        }

        return invoices;
    }


    @Override
    public boolean update(Invoice invoice) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(UPDATE_INVOICE);
            preparedStatement.setBigDecimal(1, invoice.getAmmount());
            preparedStatement.setInt(2, invoice.getId());
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

    private Invoice mapInvoice(ResultSet rs) {

        Invoice invoice = new Invoice();
        try {
            invoice.setId(rs.getInt(SQLConstants.FIELD_ID));
            invoice.setAccount_id(rs.getInt(SQLConstants.FIELD_ACCOUNT_ID));
            invoice.setAmmount(rs.getBigDecimal(SQLConstants.FIELD_AMMOUNT));
        } catch (SQLException ex) {
            log.debug("map invoice exception " + ex.getMessage());
        }
        return invoice;
    }
}
