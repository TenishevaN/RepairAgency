package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FilterUtil extends PaginationUtil{

    private void SetInitialParameters(HttpServletRequest req) {

         SetPaginationInitialParameters(req);
    }

    protected String doExecute(HttpServletRequest req, String sortField, String command) {

        List<RepairRequest> repairRequests;
        SetInitialParameters(req);

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            int filterValue = Integer.parseInt(req.getParameter(sortField));

            if(filterValue != -1){
                repairRequests = repairRequestDAO.getAll(start, total, sortField, filterValue);
             } else{
                repairRequests = repairRequestDAO.getAll(start, total);
            }
            req.setAttribute("repairRequests", repairRequests);
            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);
            String currentLocale = (String) req.getSession().getAttribute("currentLocale");
            if (currentLocale == null) {
                req.setAttribute("currentLocale", "en");
            } else {
                req.setAttribute("currentLocale", currentLocale);
            }
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("idStatus", req.getParameter("status_id"));
            req.setAttribute("idMaster", req.getParameter("master_id"));

            //Pagination
            req.setAttribute("userId", currentUser.getId());
            if (pageParameter != null) {
                req.setAttribute("page", pageParameter);
            } else {
                req.setAttribute("page", 1);
            }

            String orderBy = req.getParameter("orderBy");
            if ("null".equals(orderBy)) {
                req.setAttribute("orderBy", "ASC");
            }
            req.setAttribute("command", command);
            req.setAttribute(sortField, req.getParameter(sortField));

        } catch (Exception ex) {
            System.out.println(" filter by status mistake " + ex.getMessage());
        }
        return Path.PAGE_LIST_REPAIR_REQUESTS;


    }
}
