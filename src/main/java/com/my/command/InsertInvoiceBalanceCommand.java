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

/**
 * {@ code InsertInvoiceBalanceCommand} class represents the implementation of the command to store invoice changes in the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class InsertInvoiceBalanceCommand implements Command {

    private static final Logger log = LogManager.getLogger(InsertInvoiceBalanceCommand.class);
    private BigDecimal ammount = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private String currentLocale;
    private String idRepairRequest = "-1";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        currentLocale = (String) session.getAttribute("currentLocale");

        int idUser = Integer.parseInt(req.getParameter("idUser"));
        User user = new UserDAO().get(idUser);
        Integer invoiceId = user.getInvoiceId();
        idRepairRequest = req.getParameter("idRepairRequest");
        String operation = req.getParameter("operation");
        try {
            ammount = new BigDecimal(req.getParameter("ammount"));
            BigDecimal totalBalance = new InvoiceDAO().get(user.getInvoiceId()).getAmmount();
            if(totalBalance != null){
                total = totalBalance;
            }
            String errorPage = validateOnServer(req, operation);
            if (errorPage != null) {
                return errorPage;
            }
            if ("payment".equals(operation)) {
                ammount = ammount.negate();
            }
            Invoice invoice = updateInvoiceAmmount(invoiceId, idUser);
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
            req.setAttribute("errorMessage", ServiceUtil.getKey("payment_was_not_carried_out", "en"));
            return Path.PAGE_ERROR_PAGE;
        }
    }

    private Invoice updateInvoiceAmmount(Integer invoiceId, int idUser) {

        ammount = ammount.multiply(BigDecimal.valueOf(100));
        InvoiceBalance payment = new InvoiceBalance();
        payment.setInvoiceId(invoiceId);
        payment.setRepairRequestId(Integer.parseInt(idRepairRequest));
        payment.setAmmount(ammount);
        InvoiceBalanceDAO invoiceBalanceDAO = new InvoiceBalanceDAO();
        invoiceBalanceDAO.insert(payment);
        log.debug("invoiceBalanceDAO " +invoiceBalanceDAO);
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        Invoice invoice = invoiceDAO.get(idUser);
        invoice.setAmmount(invoiceBalanceDAO.getTotal(invoice.getId()));
        invoiceDAO.update(invoice);
         return invoice;
    }

    //Server side data validation
    private String validateOnServer(HttpServletRequest req, String operation) {

        String errorPage = null;
        if (("payment".equals(operation)) & (total == BigDecimal.ZERO)) {
            req.setAttribute("errorMessage", ServiceUtil.getKey("not_enough_funds_to_pay", currentLocale));

            errorPage = Path.PAGE_ERROR_PAGE;
        }
        if (("payment".equals(operation)) & (total.compareTo(ammount) == -1)) {
            req.setAttribute("errorMessage", ServiceUtil.getKey("not_enough_funds_to_pay", currentLocale));
            errorPage = Path.PAGE_ERROR_PAGE;
        }
        return errorPage;
    }
}
