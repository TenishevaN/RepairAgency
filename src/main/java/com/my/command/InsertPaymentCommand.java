package com.my.command;

import com.my.Path;
import com.my.db.dao.PaymentDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class InsertPaymentCommand implements Command {

    private static final Logger log = Logger.getLogger(InsertPaymentCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();

        int idUser = Integer.parseInt(req.getParameter("idUser"));
        User user = new UserDAO().get(idUser);
        Integer invoiceId = user.getInvoiceId();

        if (invoiceId == -1) {
            session.setAttribute("errorMessage", "You don't have an invoice. Сontact  the manager!");
            return Path.PAGE_ERROR_PAGE;
        }
        String idRepairRequest = req.getParameter("idRepairRequest");
        BigDecimal ammount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("ammount")));
        String operation = req.getParameter("operation");
        if ("payment".equals(operation)) {
            ammount = ammount.negate();
        }
        try {
            Payment payment = new Payment();
            payment.setInvoiceId(invoiceId);
            payment.setRepairRequestId(Integer.parseInt(idRepairRequest));
            payment.setAmmount(ammount);

            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.insert(payment);
            User currentUser = (User) req.getSession().getAttribute("user");
            Role userRole = Role.getRole(currentUser);
            session.setAttribute("user", currentUser);
            session.setAttribute("role", userRole);

            if ("-1".equals(idRepairRequest)) {
                BigDecimal total = new PaymentDAO().getTotal(invoiceId);
                req.setAttribute("total", total);
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
