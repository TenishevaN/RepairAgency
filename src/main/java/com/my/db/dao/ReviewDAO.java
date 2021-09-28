package com.my.db.dao;

import com.my.db.model.Review;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@ code ReviewDAO} class implementation InterfaceDAO for the review model.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ReviewDAO extends ManagerDAO {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ReviewDAO.class);

    public static final String TABLE_REVIEW = "review";
    private static final String FIND_ALL_REVIEWS = "SELECT * FROM " + TABLE_REVIEW;
    private static final String FIND_ALL_REVIEWS_BY_REQUEST_ID = "SELECT r.id, r.repair_request_id, r.comment, r.date FROM " + TABLE_REVIEW + " r LEFT JOIN repair_request rr ON r.repair_request_id = rr.id  LEFT JOIN account a ON rr.account_id = a.id WHERE r.repair_request_id = ?";
    private static final String ADD_NEW_REVIEW = "INSERT INTO " + TABLE_REVIEW + " (repair_request_id, comment) VALUES(?, ?);";

    public boolean insert(Review element) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(ADD_NEW_REVIEW, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, element.getRepairRequestId());
            preparedStatement.setString(2, (element.getComment()));

            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getInt(1));
            }
            connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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

    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_REVIEWS);) {
            while (rs.next()) {
                reviews.add(mapReview(rs));
            }

        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }

        return reviews;
    }

    public List<Review> getAllByRequestId(int id) {

        List<Review> reviews = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_ALL_REVIEWS_BY_REQUEST_ID);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(mapReview(rs));
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return reviews;
    }

    private Review mapReview(ResultSet rs) {

        Review review = new Review();
        try {
            review.setId(rs.getInt(SQLConstants.FIELD_ID));
            review.setRepairRequestId(rs.getInt(SQLConstants.FIELD_REPAIR_REQUEST_ID));
            review.setComment(rs.getString(SQLConstants.FIELD_COMMENT));
            review.setDate(rs.getDate(SQLConstants.FIELD_DATE));

        } catch (SQLException ex) {
            log.debug("exception Review: " + ex.getMessage());
        }
        return review;
    }
}
