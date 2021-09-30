package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceBalanceDAO;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.my.ServiceUtil;

/**
 * {@ code CardRepairRequestPageCommand} class represents the implementation of the command to open the repair request card.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class CardRepairRequestPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private BigDecimal balance_owed = BigDecimal.ZERO;
    private BigDecimal paid = BigDecimal.ZERO;
    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private Integer id;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        Map<User, List<AccountLocalization>> listMasters = (Map<User, List<AccountLocalization>>) session.getServletContext().getAttribute("listMasters");
        try {
            Role userRole = (Role) session.getAttribute("role");
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            id = Integer.parseInt(req.getParameter("id"));
            RepairRequest repairRequest = repairRequestDAO.get(id);
            User user = (User) session.getAttribute("user");
            //Server side data validation
            if (("user".equals(userRole.getName()) && (user.getId() != repairRequest.getUserId()))) {
                log.error("user {} has no right to see document Repair request № {}", user.getName(), repairRequest.getId());
                req.setAttribute("errorMessage", ServiceUtil.getKey("no_right_for_document", currentLocale));
                return Path.PAGE_ERROR_PAGE;
            }
            computeBalance(repairRequest, user, req);
            req.setAttribute("role", userRole.getName());
            req.setAttribute("repairRequest", repairRequest);
            req.setAttribute("listMaster", listMasters);
        } catch (Exception ex) {
            log.debug("exception open repair request {}", ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("no_document", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        return Path.PAGE_UPDATE_REPAIR_REQUEST;
    }

    private void computeBalance(RepairRequest repairRequest, User user, HttpServletRequest req) {

        BigDecimal cost = repairRequest.getCost();
        BigDecimal paid = new InvoiceBalanceDAO().getBalanceOwed(id);
        if (cost != null) {
            balance_owed = cost;
            cost = cost.divide(BigDecimal.valueOf(100));
        }
        if (paid != null) {
            balance_owed = balance_owed.add(paid);
        }
        if ((balance_owed != BigDecimal.ZERO)) {
            balance_owed = balance_owed.divide(BigDecimal.valueOf(100));
        }
        BigDecimal total = new InvoiceDAO().get(user.getInvoiceId()).getAmmount();
        if (total != null) {
            total = total.divide(BigDecimal.valueOf(100));
        }
        req.setAttribute("cost", cost);
        req.setAttribute("balance_owed", balance_owed);
        req.setAttribute("total", total);
    }
}
