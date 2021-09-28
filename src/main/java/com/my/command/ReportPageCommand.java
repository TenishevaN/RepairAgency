package com.my.command;

import com.my.Path;
import com.my.ServiceUtil;
import com.my.command.PaginationUtil;
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
 * {@ code ReportPageCommand} class represents the implementation of the command to obtain reports data from the database.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ReportPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(ReportPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        List<RepairRequest> repairRequests;
        try {
            PaginationUtil paginationUtil = new PaginationUtil();
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);
            if ("user".equals(currentRole.getName())) {
                repairRequests = repairRequestDAO.getAllByUserId(currentUser.getId());
            } else {
                paginationUtil.SetPaginationInitialParameters(req);
                repairRequests = repairRequestDAO.getAll(PaginationUtil.start, PaginationUtil.total);
            }
            req.setAttribute("repairRequests", repairRequests);
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("userId", currentUser.getId());
            req.setAttribute("currentLocale", currentLocale);
            req.setAttribute("command", "reports");
            paginationUtil.setPaginationSessionParameterPage(req);

        } catch (Exception ex) {
            log.debug("reports exception " + ex.getMessage());
            req.setAttribute("errorMessage", ServiceUtil.getKey("contact_manager", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.REPORTS;
    }
}
