package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.Invoice;
import com.my.db.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class ReplenishInvoiceCommand implements Command {

    private static final Logger log = Logger.getLogger(ReplenishInvoiceCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        InvoiceDAO invoiceDAO = new InvoiceDAO();
         int  invoiceId = Integer.parseInt(req.getParameter("invoiceId"));
        String invoiceAmmount = req.getParameter("invoiceAmmount");
        int id = Integer.parseInt(req.getParameter("id"));
        Invoice invoice = invoiceDAO.get(invoiceId);

        System.out.println("invoice != null " + (invoice != null));

        try {
            if (invoice != null) {
                System.out.println("invoiceAmmount " + invoiceAmmount);
                invoice.setAmmount(BigDecimal.valueOf(Long.parseLong(invoiceAmmount)));
                System.out.println("invoice " + invoice);
                invoiceDAO.update(invoice);
            } else {

                invoice = new Invoice();
                invoice.setAmmount(BigDecimal.valueOf(Long.parseLong(invoiceAmmount)));
                invoiceDAO.insert(invoice);

                UserDAO userDAO = new UserDAO();
                User user = userDAO.get(id);
                user.setInvoiceId(invoice.getId());
                userDAO.update(user);

                System.out.println("invoice " + invoice);
            }

            return Path.COMMAND_OPEN_USER_BY_ID + id;

        } catch (Exception ex) {
            log.debug("update invoice exception " + ex.getMessage());
            System.out.println("update invoice exception " + ex.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
