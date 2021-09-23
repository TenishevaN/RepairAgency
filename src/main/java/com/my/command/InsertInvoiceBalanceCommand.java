package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
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

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");

        int idUser = Integer.parseInt(req.getParameter("idUser"));
        User user = new UserDAO().get(idUser);
        Integer invoiceId = user.getInvoiceId();

        log.debug("insert payment {}", invoiceId);
        if (invoiceId == -1) {
            req.setAttribute("errorMessage", ServiceUtil.getKey("don't_have_an_invoice", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        String idRepairRequest = req.getParameter("idRepairRequest");
        if(idRepairRequest == null){
            idRepairRequest = "-1";
        }
        BigDecimal ammount = new BigDecimal(req.getParameter("ammount"));
        String operation = req.getParameter("operation");

        try {
            BigDecimal total = new InvoiceDAO().get(user.getInvoiceId()).getAmmount();
            if(total == null){
               total = BigDecimal.ZERO;
            }
            //Server side data validation
            if(("payment".equals(operation)) &  (total == BigDecimal.ZERO)){
                req.setAttribute("errorMessage",  ServiceUtil.getKey("not_enough_funds_to_pay", currentLocale));
                return Path.PAGE_ERROR_PAGE;
            }
            //Server side data validation
            if(("payment".equals(operation)) & (total.compareTo(ammount) == -1)){
                req.setAttribute("errorMessage",  ServiceUtil.getKey("not_enough_funds_to_pay", currentLocale));
                return Path.PAGE_ERROR_PAGE;
            }
            if ("payment".equals(operation)) {
                ammount = ammount.negate();
            }
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
                //payment from the  repair request card
                return Path.COMMAND_OPEN_REPAIR_REQUEST_BY_ID + idRepairRequest;
            }
        } catch (Exception ex) {
            log.debug("payment was not carried out " + ex.getMessage());
             req.setAttribute("errorMessage",  ServiceUtil.getKey("payment_was_not_carried_out", "en"));
            return Path.PAGE_ERROR_PAGE;
        }
    }
}
