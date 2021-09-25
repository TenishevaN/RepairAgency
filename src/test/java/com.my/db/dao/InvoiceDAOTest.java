package com.my.db.dao;

import com.my.db.model.Invoice;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InvoiceDAOTest {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(InvoiceDAOTest.class);
    private String url = "jdbc:mysql://localhost:3306/db_repair_agency_test";

    @BeforeClass
    public static void beforeTest() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
             Statement statement = con.createStatement()) {

            String fileName = "sql/db_invoice_test_schema.sql";
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
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`invoice` (`account_id`) VALUES ('1');");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`invoice` (`account_id`) VALUES ('2');");
            statement.executeUpdate("INSERT INTO `db_repair_agency_test`.`invoice` (`account_id`) VALUES ('3');");

        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
    }

    @Test
    public void get() {

        InvoiceDAO invoiceDAO = new InvoiceDAO(url);
        Invoice invoice = invoiceDAO.get(1);
        System.out.println("invoice " + invoice);
        assertEquals(1, invoice.getAccount_id());
    }

    @Test
    public void getAll() {

        InvoiceDAO invoiceDAO = new InvoiceDAO(url);
        List<Invoice> listExpected = new ArrayList<>();
        listExpected.add(invoiceDAO.get(1));
        listExpected.add(invoiceDAO.get(2));
        listExpected.add(invoiceDAO.get(3));
        List<Invoice> listActual = invoiceDAO.getAll();
        assertEquals(listExpected, listActual);
    }

    @Test
    public void insert() {

        InvoiceDAO invoiceDAO = new InvoiceDAO(url);
        Invoice invoice = new Invoice();
        invoice.setAccount_id(1);
        invoice.setAmmount(new BigDecimal(4000));
        boolean added = invoiceDAO.insert(invoice);
        assertEquals(true, added);
    }


    @Test
    public void update() {

        InvoiceDAO invoiceDAO = new InvoiceDAO(url);
        Invoice invoice = invoiceDAO.get(1);
        invoice.setAmmount(new BigDecimal(1000));
        invoiceDAO.update(invoice);
        Invoice invoiceUpdated = invoiceDAO.get(1);
        assertEquals(new BigDecimal(1000), invoiceUpdated.getAmmount());
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