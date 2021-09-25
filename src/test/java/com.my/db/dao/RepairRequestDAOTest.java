package com.my.db.dao;

import com.my.db.model.RepairRequest;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.NamingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class RepairRequestDAOTest {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RepairRequestDAOTest.class);
    private String url = "jdbc:mysql://localhost:3306/db_repair_agency_test";

    @BeforeClass
    public static void beforeTest() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
             Statement statement = con.createStatement()) {

            String fileName = "sql/db_repair_request_test_schema.sql";
            String script = "";
            StringBuilder sb = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), StandardCharsets.UTF_8));) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS db_repair_agency_test DEFAULT CHARACTER SET utf8;");
            script = sb.toString();
            statement.executeUpdate(script);
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`repair_request_full` ( `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (NULL, DEFAULT, DEFAULT, 3, 5, 'new request');");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`repair_request_full` (`cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (100000, '2021-09-15', 2, 4, 6, 'request 1');");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`repair_request_full` (`cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (500000, '2021-09-16', DEFAULT, 4, 5, 'request 2');");

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    public void get() throws NamingException {

        RepairRequestDAO repairRequestDAO = new RepairRequestDAO(url);
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setId(1);
        assertEquals(repairRequest, repairRequestDAO.get(1));
    }

      @AfterClass
    public static void afterTest() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
             Statement statement = con.createStatement()) {
             statement.executeUpdate("DROP DATABASE db_repair_agency_test;");
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }
}