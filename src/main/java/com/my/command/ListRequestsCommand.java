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

public class ListRequestsCommand implements Command {

    private static final Logger log =  LogManager.getLogger(ListRequestsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        String currentLocale = (String) session.getAttribute("currentLocale");
        List<RepairRequest> repairRequests;
        int start = 0;
        String pageParameter = req.getParameter("page");
        if(pageParameter != null){
            start = Integer.parseInt(req.getParameter("page"));
        }

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();

            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);

            int total = 5;
            if (start != 0) {
                start = start - 1;
                start = start * total;
            }
            if ("user".equals(currentRole.getName())) {
                repairRequests = repairRequestDAO.getAllByUserId(currentUser.getId(), start, total);
            } else {
                repairRequests = repairRequestDAO.getAll(start, total);
             }

            req.setAttribute("repairRequests", repairRequests);
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("userId", currentUser.getId());

            if (pageParameter != null) {
                req.setAttribute("page", pageParameter);
            } else {
                req.setAttribute("page", 1);
            }

            req.setAttribute("command", "listRequests");
            String orderBy = req.getParameter("orderBy");
            if ("null".equals(orderBy)) {
                req.setAttribute("orderBy", "ASC");
            }

            req.setAttribute("idStatus", "-1");
            req.setAttribute("idMaster", "-1");
            req.setAttribute("currentLocale", currentLocale);

         } catch (Exception ex) {
            log.debug("Get List requests exception " + ex.getMessage());
            req.setAttribute("errorMessage",  ServiceUtil.getKey("contact_manager", currentLocale));
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_LIST_REPAIR_REQUESTS;
    }
}
