package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceBalanceDAO;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class InsertInvoiceBalanceCommand implements Command {

    private static final Logger log = LogManager.getLogger(InsertInvoiceBalanceCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();

        int idUser = Integer.parseInt(req.getParameter("idUser"));
        User user = new UserDAO().get(idUser);
        Integer invoiceId = user.getInvoiceId();

        log.debug("insert payment {}", invoiceId);
        if (invoiceId == -1) {

            session.setAttribute("errorMessage", "You don't have an invoice. Сontact  the manager!");
            return Path.PAGE_ERROR_PAGE;
        }
        String idRepairRequest = req.getParameter("idRepairRequest");
        BigDecimal ammount = new BigDecimal(req.getParameter("ammount"));
        String operation = req.getParameter("operation");
        if ("payment".equals(operation)) {
            ammount = ammount.negate();
        }
        try {
            InvoiceBalance payment = new InvoiceBalance();
            payment.setInvoiceId(invoiceId);
            payment.setRepairRequestId(Integer.parseInt(idRepairRequest));
            payment.setAmmount(ammount.multiply(BigDecimal.valueOf(100)));

            InvoiceBalanceDAO invoiceBalanceDAO = new InvoiceBalanceDAO();
            invoiceBalanceDAO.insert(payment);

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            Invoice invoice = invoiceDAO.get(idUser);
            invoice.setAmmount(invoiceBalanceDAO.getTotal(invoice.getId()));
            invoiceDAO.update(invoice);

            User currentUser = (User) req.getSession().getAttribute("user");
            Role userRole = Role.getRole(currentUser);
            session.setAttribute("user", currentUser);
            session.setAttribute("role", userRole);

            if ("-1".equals(idRepairRequest)) {
                //replenish the user invoice from the user card
                req.setAttribute("total", invoice.getAmmount());
                return Path.COMMAND_OPEN_USER_BY_ID + idUser;
            } else {
                return Path.COMMAND_OPEN_REPAIR_REQUEST_BY_ID + idRepairRequest;
            }
        } catch (Exception ex) {
            log.debug("update payment exception " + ex.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
