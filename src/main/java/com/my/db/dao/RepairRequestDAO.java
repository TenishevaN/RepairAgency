package com.my.db.dao;

import com.my.db.model.RepairRequest;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairRequestDAO extends ManagerDAO implements InterfaceDAO<RepairRequest> {

    private static final String TABLE_REPAIR_REQUEST = "repair_request";
    private static final String TABLE_REPAIR_REQUEST_FULL = "repair_request_full";
    private static final String ADD_REPAIR_REQUEST = "INSERT INTO repair_request (account_id, description) VALUES(?, ?);";
    private static final String FIND_REPAIR_REQUEST_BY_ID = "SELECT * FROM repair_request_full WHERE id = ?;";
    private static final String FIND_ALL_REPAIR_REQUEST_BY_USER_ID = "SELECT * FROM " + TABLE_REPAIR_REQUEST_FULL + " WHERE account_id = ?";
    private static final String UPDATE_REPAIR_REQUEST = "UPDATE " + TABLE_REPAIR_REQUEST + " SET " + SQLConstants.FIELD_DESCRIPTION + " = ?, cost = ?, master_id = ?, status_id = ? " + " WHERE " + SQLConstants.FIELD_ID + " = ?;";
    private static final String FIND_ALL_REPAIR_REQUESTS_FULL = "SELECT * FROM " + TABLE_REPAIR_REQUEST_FULL;
    private static final String COUNT_ALL_REPAIR_REQUESTS = "SELECT COUNT(id) AS count FROM repair_request;";
    public static final String DELETE_REPAIR_REQUEST = "DELETE FROM " + TABLE_REPAIR_REQUEST + " WHERE " + SQLConstants.FIELD_ID + " = ?;";

    private static final Logger log = Logger.getLogger(RepairRequestDAO.class);

    @Override
    public boolean update(RepairRequest element) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_REPAIR_REQUEST);
            preparedStatement.setString(1, element.getDescription());
            preparedStatement.setBigDecimal(2, element.getCost());
            Integer masterId = element.getMasterId();
            if (masterId != -1) {
                preparedStatement.setInt(3, element.getMasterId());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            preparedStatement.setInt(4, element.getStatusId());
            preparedStatement.setInt(5, element.getId());
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

    @Override
    public List<RepairRequest> getAll() {

        List<RepairRequest> repairRequests = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_REPAIR_REQUESTS_FULL);) {
            while (rs.next()) {
                repairRequests.add(mapRepairRequest(rs));
            }

        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }
        log.info("Formed requests " + repairRequests);
        return repairRequests;
    }


    public List<RepairRequest> getAll(int start, int total) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RepairRequest> repairRequests = new ArrayList<>();

        try {
            connection = getConnection();
            String sqlQuery = FIND_ALL_REPAIR_REQUESTS_FULL + " limit " + total + " OFFSET " + start;
             preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairRequests.add(mapRepairRequest(resultSet));
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return repairRequests;
    }

    public List<RepairRequest> getAll(int start, int total, String orderBy) {

        List<RepairRequest> repairRequests = new ArrayList<>();
        String sqlQuery = FIND_ALL_REPAIR_REQUESTS_FULL + " ORDER BY " + orderBy + " limit " + total + " OFFSET " + start;
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();

             ResultSet rs = stmt.executeQuery(sqlQuery);) {
            while (rs.next()) {
                repairRequests.add(mapRepairRequest(rs));
            }
        } catch (SQLException ex) {
            log.debug(" getAll requests exception " + repairRequests);
        }
        return repairRequests;
    }

    public List<RepairRequest> getAll(int start, int total, String filterField, int id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RepairRequest> repairRequests = new ArrayList<>();

        try {
            connection = getConnection();
            String sqlQuery = FIND_ALL_REPAIR_REQUESTS_FULL + " WHERE " + filterField + " = " + id + " limit " + total + " OFFSET " + start;
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairRequests.add(mapRepairRequest(resultSet));
            }
        } catch (SQLException ex) {
            //  logger.log(Level.WARNING, ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return repairRequests;
    }

    public int getCountOfAllRequests() {
        int count = 0;
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(COUNT_ALL_REPAIR_REQUESTS);) {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            log.debug("count of requests exception " + ex.getMessage());
        }
        return count;
    }

    public double getCountOfAllRequestsByStatus(String status_id) {

        int count = 0;
        String sqlQuery = "SELECT COUNT(id) AS count FROM repair_request WHERE status_id = " + status_id;
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery);) {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }

        return count;
    }

    public double getCountOfAllRequestsByMaster(String master_id) {

        int count = 0;
        String sqlQuery = "SELECT COUNT(id) AS count FROM repair_request  WHERE master_id = " + master_id;
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();

             ResultSet rs = stmt.executeQuery(sqlQuery);) {
            while (rs.next()) {
                count = rs.getInt(1);
                   }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }
        return count;
    }

    public int getCountOfAllRequestsDyUserId(int userId) {

        int count = 0;
        String sqlQuery = "SELECT COUNT(id) AS count FROM repair_request WHERE account_id = " + userId;
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();

             ResultSet rs = stmt.executeQuery(sqlQuery);) {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        }
        return count;
    }

    @Override
    public RepairRequest get(int id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RepairRequest repairRequest = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_REPAIR_REQUEST_BY_ID);
            preparedStatement.setString(1, String.valueOf(id));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairRequest = mapRepairRequest(resultSet);
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return repairRequest;
    }

    @Override
    public boolean insert(RepairRequest element) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_REPAIR_REQUEST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, element.getUserId());
            preparedStatement.setString(2, element.getDescription());
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
               rollBackTransaction(connection);
            //  logger.log(Level.INFO, ex.getMessage());
            return false;

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return true;
    }

    public List<RepairRequest> getAllByUserId(int id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RepairRequest> repairRequests = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_REPAIR_REQUEST_BY_USER_ID);
            preparedStatement.setString(1, String.valueOf(id));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairRequests.add(mapRepairRequest(resultSet));
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return repairRequests;
    }

    public boolean delete(RepairRequest repairRequest) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            System.out.println("repairRequest.getId() " + repairRequest.getId());
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_REPAIR_REQUEST);
            preparedStatement.setInt(1, repairRequest.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            log.info("executed " + preparedStatement);
        } catch (SQLException ex) {
            rollBackTransaction(connection);
            log.debug(ex.getMessage());
            return false;
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return true;
    }



    public List<RepairRequest> getAllByUserId(int id, int start, int total) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RepairRequest> repairRequests = new ArrayList<>();

        try {
            connection = getConnection();
            String sqlQuery = FIND_ALL_REPAIR_REQUEST_BY_USER_ID + " limit " + total + " OFFSET " + start;
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(id));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairRequests.add(mapRepairRequest(resultSet));
            }
        } catch (SQLException ex) {
            log.debug(ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return repairRequests;
    }

    private RepairRequest mapRepairRequest(ResultSet rs) {

        RepairRequest repairRequest = new RepairRequest();
        try {
            repairRequest.setId(rs.getInt(SQLConstants.FIELD_ID));
            repairRequest.setUserId(rs.getInt(SQLConstants.FIELD_ACCOUNT_ID));
            repairRequest.setCost(rs.getBigDecimal(SQLConstants.FIELD_COST));
            repairRequest.setDate((rs.getDate((SQLConstants.FIELD_DATE))));
            repairRequest.setDescription((rs.getString((SQLConstants.FIELD_DESCRIPTION))));
            repairRequest.setMasterName(rs.getString(SQLConstants.FIELD_MASTER_NAME));
            if (repairRequest.getMasterName() != null) {
                repairRequest.setMasterId(rs.getInt(SQLConstants.FIELD_MASTER_ID));
            } else {
                repairRequest.setMasterId(-1);
            }
            repairRequest.setStatusId(rs.getInt(SQLConstants.FIELD_STATUS_ID));
            repairRequest.setStatusName(rs.getString(SQLConstants.FIELD_STATUS_NAME));
            repairRequest.setUserName(rs.getString(SQLConstants.FIELD_USER_NAME));
        } catch (SQLException ex) {
            log.debug("map Request exception " + ex.getMessage());
        }
        return repairRequest;
    }
}
