package com.my.command;

import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReportPageCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
         System.out.println("---------- Reports command ---------");
        List<RepairRequest> repairRequests;
        int start = 0;
        String pageParameter = req.getParameter("page");
        if(pageParameter != null){
            start = Integer.parseInt(req.getParameter("page"));;
        }

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();

            User currentUser = (User) req.getSession().getAttribute("user");
            Role currentRole = Role.getRole(currentUser);
            if ("user".equals(currentRole.getName())) {
                repairRequests = repairRequestDAO.getAllByUserId(currentUser.getId());
            } else {
                int total = 5;
                if (start != 0) {
                    start = start - 1;
                    start = start * total + 1;
                }
                System.out.println("start " + start);
                repairRequests = repairRequestDAO.getAll(start, total);
                System.out.println("repairRequests " + repairRequests);
            }

            req.setAttribute("repairRequests", repairRequests);
            req.setAttribute("role", currentRole.getName());
            req.setAttribute("userId", currentUser.getId());


            //Pagination
            if (pageParameter != null) {
                req.setAttribute("page", pageParameter);
            } else {
                req.setAttribute("page", 1);
            }
            req.setAttribute("command", "reports");


          //  String orderBy = req.getParameter("orderBy");
          //  if (orderBy == null) {
          //      req.setAttribute("orderBy", "ASC");
          //  }
        } catch (Exception ex) {
           System.out.println(" obtain list reports pagination exception " +ex.getMessage());
        }
        return "reports.jsp";
    }
}
