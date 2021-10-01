package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.RepairRequest;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * {@ code UpdateRepairRequestCommand} class represents the implementation of the command to update repair request in the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class UpdateRepairRequestCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdateRepairRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = repairRequestDAO.get(Integer.parseInt(req.getParameter("idRepairRequest")));

            String statusId = req.getParameter("statusId");
            if (statusId != null && !statusId.isEmpty()) {
                repairRequest.setStatusId(Integer.parseInt(statusId));
            }
            String masterId = req.getParameter("masterId");
            if ((masterId != null) && (!masterId.isEmpty())) {
                repairRequest.setMasterId(Integer.parseInt(masterId));
            }
            repairRequest.setMasterName(req.getParameter("master_name"));
            String cost = req.getParameter("cost");
            if (cost != null && !cost.isEmpty()) {
                repairRequest.setCost(getCost(req));
            }
            String description = req.getParameter("description");
            if (description != null) {
                repairRequest.setDescription(description);
            }
            repairRequestDAO.update(repairRequest);

        } catch (Exception ex) {
            log.debug("repair request  was not updated " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("repair_request_was_not_updated", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }

        return Path.COMMAND_OPEN_REPAIR_REQUEST_BY_ID + req.getParameter("idRepairRequest");
    }

    private BigDecimal getCost(HttpServletRequest req) {
        BigDecimal cost = new BigDecimal(req.getParameter("cost"));
        log.debug("cost {}", cost);
        if ((cost != BigDecimal.ZERO) && (cost != null)) {
            return new BigDecimal(String.valueOf(cost.multiply(BigDecimal.valueOf(100))));
        }
        return BigDecimal.ZERO;
    }
}
