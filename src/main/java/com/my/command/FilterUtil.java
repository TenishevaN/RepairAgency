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
 * {@ code FilterUtil} class represents the service class to implement filtering with different parameters.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class FilterUtil extends PaginationUtil {

    private static final Logger log = LogManager.getLogger(FilterUtil.class);

    private void SetInitialParameters(HttpServletRequest req) {

        SetPaginationInitialParameters(req);
    }

    protected String doExecute(HttpServletRequest req, String sortField, String command) {

        List<RepairRequest> repairRequests;
        SetInitialParameters(req);
        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            int filterValue = Integer.parseInt(req.getParameter(sortField));
            if (filterValue != -1) {
                repairRequests = repairRequestDAO.getAll(start, total, sortField, filterValue);
            } else {
                repairRequests = repairRequestDAO.getAll(start, total);
            }
            req.setAttribute("repairRequests", repairRequests);
            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("idStatus", req.getParameter("status_id"));
            req.setAttribute("idMaster", req.getParameter("master_id"));
            req.setAttribute("userId", currentUser.getId());
            setPaginationSessionParameterPage(req);
            String orderBy = req.getParameter("orderBy");
            if ("null".equals(orderBy)) {
                req.setAttribute("orderBy", "ASC");
            }
            req.setAttribute("command", command);
            req.setAttribute(sortField, req.getParameter(sortField));
        } catch (Exception ex) {
            log.debug("filter exception {}", ex.getMessage());
        }
        return Path.PAGE_LIST_REPAIR_REQUESTS;
    }
}
