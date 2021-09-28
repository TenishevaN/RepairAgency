package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code SortUtil} class represents the service class to implement sorting with different parameters.
 */
public class SortUtil extends PaginationUtil {

    private static final Logger log =  LogManager.getLogger(SortUtil.class);
    protected String orderBy;


    private void SetInitialParameters(HttpServletRequest req) {

        SetPaginationInitialParameters(req);

        String changeOrder = req.getParameter("changeOrder");
        orderBy = req.getParameter("orderBy");
        if ("true".equals(changeOrder)) {
            if ("ASC".equals(orderBy) || orderBy.isEmpty()) {
                req.setAttribute("orderBy", "DESC");
                orderBy = "DESC";
            } else {
                req.setAttribute("orderBy", "ASC");
                orderBy = "ASC";
            }
        } else {
            req.setAttribute("orderBy", orderBy);
        }
    }

    protected String doExecute(HttpServletRequest req, String sortField, String command) {
        List<RepairRequest> repairRequests;
        SetInitialParameters(req);

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            repairRequests = repairRequestDAO.getAll(start, total, sortField + " " + orderBy);
            setSessionParameters(req, repairRequests, command);
        } catch (Exception ex) {
            log.debug("Get report exception " + ex.getMessage());
        }
        return Path.REPORTS;
    }

    private void setSessionParameters(HttpServletRequest req, List<RepairRequest> repairRequests, String command) {

        req.setAttribute("repairRequests", repairRequests);

        User currentUser = (User) req.getSession().getAttribute("user");
        Role currentRole = Role.getRole(currentUser);
        req.setAttribute("role", currentRole.getName());

        setPaginationSessionParameters(req, currentUser, command);
    }
}
