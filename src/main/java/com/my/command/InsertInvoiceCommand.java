package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.Invoice;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InsertInvoiceCommand implements Command {

    private static final Logger log =  LogManager.getLogger(InsertInvoiceCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("userId");

        try {
            Invoice invoice = new Invoice();
            invoice.setAccount_id(Integer.parseInt(userId));
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.insert(invoice);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.get(invoice.getAccount_id());
            user.setInvoiceId(invoice.getId());
            userDAO.update(user);

            return Path.COMMAND_OPEN_USER_BY_ID + userId;

        } catch (Exception ex) {
            log.debug("update invoice exception " + ex.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
