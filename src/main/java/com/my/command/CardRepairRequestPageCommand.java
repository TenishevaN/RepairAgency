package com.my.command;

import com.my.Path;
import com.my.db.dao.PaymentDAO;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import com.my.ServiceLocale;

public class CardRepairRequestPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession session = req.getSession(false);
            Role userRole = (Role) session.getAttribute("role");
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            Integer id = Integer.parseInt(req.getParameter("id"));
            RepairRequest repairRequest = repairRequestDAO.get(id);
            User user = (User) session.getAttribute("user");
            if(("user".equals(userRole.getName()) && (user.getId() != repairRequest.getUserId()))){
                log.error("user {} has no right to see document Repair request № {}", user.getName(), repairRequest.getId());
                req.setAttribute("errorMessage",  ServiceLocale.getKey("no_right_for_document", "en"));
                return Path.PAGE_ERROR_PAGE;
            }
            req.setAttribute("repairRequest", repairRequest);
            req.setAttribute("role", userRole.getName());

            String currentLocale = (String) session.getAttribute("currentLocale");
            if (currentLocale == null) {
                req.setAttribute("currentLocale", "en");
            } else {
                req.setAttribute("currentLocale", currentLocale);
            }
            BigDecimal paid = new PaymentDAO().getBalanceOwed(id);
            if (paid == null) {
                paid = BigDecimal.ZERO;
            }
            BigDecimal cost = repairRequest.getCost();
            BigDecimal balance_owed = cost.add(paid);
            req.setAttribute("balance_owed", (balance_owed));

        } catch (Exception ex) {
            log.debug("exception open repair request {}", ex.getMessage());
            req.setAttribute("errorMessage",  ServiceLocale.getKey("no_document", "en"));
            return Path.PAGE_ERROR_PAGE;
        }

        return Path.PAGE_UPDATE_REPAIR_REQUEST;
    }
}
