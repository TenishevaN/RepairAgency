package com.my.command;

import com.my.Path;
import com.my.db.dao.InvoiceDAO;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListRequestsCommand implements Command {

    private static final Logger log = Logger.getLogger(InvoiceDAO.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

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

          String currentLocale = (String) req.getSession().getAttribute("currentLocale");
            if (currentLocale == null) {
                req.setAttribute("currentLocale", "en");
            } else {
                req.setAttribute("currentLocale", currentLocale);
            }
            req.setAttribute("idStatus", "-1");
            req.setAttribute("idMaster", "-1");
         } catch (Exception ex) {
            log.debug("Get List requests exception " + ex.getMessage());
        }
        return Path.PAGE_LIST_REPAIR_REQUESTS;
    }
}
