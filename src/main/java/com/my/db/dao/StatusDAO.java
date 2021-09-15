package com.my.db.dao;

import com.my.db.model.RepairRequest;
import com.my.db.model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO extends ManagerDAO implements InterfaceDAO<Status> {

    private static final String FIND_ALL_STATUS_BY_LOCALE = "SELECT * FROM status_localization left join language on status_localization.language_id = language.id WHERE language.code = ? order by status_id;";
    private static final String FIND_STATUS_BY_ID = "SELECT * FROM status_localization left join language on status_localization.language_id = language.id WHERE language.code = ? and status_localization.status_id = ?;";


    public List<Status> getAll(String locale) {
        
        List<Status> listStatus = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
            try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_STATUS_BY_LOCALE);

            preparedStatement.setString(1, String.valueOf(locale));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listStatus.add(mapStatus(resultSet));
            }
        } catch (SQLException ex) {
                System.out.println("get all status exception " + ex.getMessage());
            //  logger.log(Level.WARNING, ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        System.out.println("Formed status " + listStatus);
        return listStatus;
    }

    public Status get(String locale, int idStatus) {

        Status status = new Status();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;    

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_STATUS_BY_ID);
            preparedStatement.setString(1, String.valueOf(locale));
            preparedStatement.setString(2, String.valueOf(idStatus));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status  = mapStatus(resultSet);
            }
        } catch (SQLException ex) {
            //  logger.log(Level.WARNING, ex.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

        return status;
    }

    private Status mapStatus(ResultSet resultSet) {
        Status status = new Status();
        try {
            status.setId(resultSet.getInt("status_id"));
            status.setName(resultSet.getString("name"));

        } catch (SQLException ex) {
            //   log.log(Level.WARNING, ex.getMessage());
            System.out.println("---map Status exception " + ex.getMessage());
        }

        System.out.println("---status " + status);
        return status;

    }

    @Override
    public boolean insert(Status element) {
        return false;
    }

    @Override
    public List<Status> getAll() {
        return null;
    }

    @Override
    public Status get(int id) {
        return null;
    }

    @Override
    public boolean update(Status element) {
        return false;
    }
}
