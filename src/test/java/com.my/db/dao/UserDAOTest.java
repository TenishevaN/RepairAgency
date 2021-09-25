package com.my.db.dao;


import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.my.db.model.User;
import org.mockito.Mockito;

import javax.naming.NamingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(UserDAOTest.class);
    private String url = "jdbc:mysql://localhost:3306/db_repair_agency_test";

    @BeforeClass
    public static void beforeTest() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
             Statement statement = con.createStatement()) {

            String fileName = "sql/db_account_test_schema.sql";
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
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`account` (`login`, `password`, `deleted`, `name`, `invoice_id`, `role_id`) VALUES ('master', '1', 0, 'master', null, null);");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`account` (`login`, `password`, `deleted`, `name`, `invoice_id`, `role_id`) VALUES ('admin', '1', 0, 'admin', null, null);");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`account` (`login`, `password`, `deleted`, `name`, `invoice_id`, `role_id`) VALUES ('user', '1', 0, 'user', null, null);");

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    public void get() throws NamingException {

        UserDAO userDAO = new UserDAO(url);
        User user = new User();
        user.setId(1);
        UserDAO mockitoDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockitoDAO.get(1)).thenReturn(userDAO.get(1));
        assertEquals(user, mockitoDAO.get(1));
    }

    @Test
    public void insert() throws NamingException {

        UserDAO userDAO = new UserDAO(url);
        User user = new User();
        user.setLogin("test1111");
        user.setPassword("1");
        user.setEmail("email");
        user.setName("test");
        boolean added = userDAO.insert(user);
        assertEquals(true, added);
    }

    @Test
    public void delete() throws NamingException {

        UserDAO userDAO = new UserDAO(url);
        User user = userDAO.get(1);
        boolean deleted = userDAO.delete(user);
        assertEquals(true, deleted);
    }

    @Test
    public void update() throws NamingException {
        UserDAO userDAO = new UserDAO(url);
        User user = userDAO.get(1);
        user.setName("testUpdate");
        userDAO.update(user);
        User userUpdated = userDAO.get(1);
        assertEquals("testUpdate", userUpdated.getName());
    }

    @Test
    public void getAll() throws NamingException {

        UserDAO userDAO = new UserDAO(url);
        List<User> userList = new ArrayList<>();
        userList.add(userDAO.get("admin"));
        userList.add(userDAO.get("user"));
        List<User> userListDB = userDAO.getAll();
        assertEquals(userList, userListDB);
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