package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceBalanceDAO;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import com.my.ServiceUtil;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code CardRepairRequestPageCommand} class represents the implementation of the command to open the repair request card.
 */
public class CardRepairRequestPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            Role userRole = (Role) session.getAttribute("role");
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            Integer id = Integer.parseInt(req.getParameter("id"));
            RepairRequest repairRequest = repairRequestDAO.get(id);
            User user = (User) session.getAttribute("user");
            //Server side data validation
            if (("user".equals(userRole.getName()) && (user.getId() != repairRequest.getUserId()))) {
                log.error("user {} has no right to see document Repair request № {}", user.getName(), repairRequest.getId());
                req.setAttribute("errorMessage", ServiceUtil.getKey("no_right_for_document", currentLocale));
                return Path.PAGE_ERROR_PAGE;
            }
            req.setAttribute("repairRequest", repairRequest);
            req.setAttribute("role", userRole.getName());
            BigDecimal balance_owed = BigDecimal.ZERO;
            BigDecimal paid = new InvoiceBalanceDAO().getBalanceOwed(id);
            if (paid != null) {
                balance_owed = balance_owed.add(paid);
            }
            BigDecimal cost = repairRequest.getCost();
            if (cost == null) {
                cost = BigDecimal.ZERO;
            } else {
                balance_owed = balance_owed.add(cost);
                cost = cost.divide(BigDecimal.valueOf(100));
            }

            if ((balance_owed != BigDecimal.ZERO)) {
                balance_owed = balance_owed.divide(BigDecimal.valueOf(100));
            }


            req.setAttribute("cost", cost);
            req.setAttribute("balance_owed", balance_owed);
            BigDecimal total = new InvoiceDAO().get(user.getInvoiceId()).getAmmount();
            if (total != null) {
                req.setAttribute("total", total.divide(BigDecimal.valueOf(100)));
            } else {
                req.setAttribute("total", 0);
            }
        } catch (Exception ex) {
            log.debug("exception open repair request {}", ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("no_document", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        return Path.PAGE_UPDATE_REPAIR_REQUEST;
    }
}
