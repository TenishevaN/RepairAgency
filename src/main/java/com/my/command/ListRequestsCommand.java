package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * {@ code ListRequestsCommand} class represents the implementation of the command to obtain list of repair reqests from the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ListRequestsCommand implements Command {

    private static final Logger log = LogManager.getLogger(ListRequestsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        List<RepairRequest> repairRequests;
        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);
            PaginationUtil paginationUtil = new PaginationUtil();
            paginationUtil.SetPaginationInitialParameters(req);
            if ("user".equals(currentRole.getName())) {
                repairRequests = repairRequestDAO.getAllByUserId(currentUser.getId(), PaginationUtil.start, PaginationUtil.total);
            } else {
                repairRequests = repairRequestDAO.getAll(PaginationUtil.start, PaginationUtil.total);
            }
            req.setAttribute("repairRequests", repairRequests);
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("userId", currentUser.getId());
            req.setAttribute("command", "listRequests");
            paginationUtil.setPaginationSessionParameterPage(req);
            String orderBy = req.getParameter("orderBy");
            if ("null".equals(orderBy)) {
                req.setAttribute("orderBy", "ASC");
            }
            req.setAttribute("idStatus", "-1");
            req.setAttribute("idMaster", "-1");
            req.setAttribute("currentLocale", currentLocale);

        } catch (Exception ex) {
            log.debug("Get List requests exception " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("contact_manager", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_LIST_REPAIR_REQUESTS;
    }
}
