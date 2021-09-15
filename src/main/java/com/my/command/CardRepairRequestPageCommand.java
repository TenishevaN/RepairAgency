package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CardRepairRequestPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            System.out.println("id repair request " + req.getParameter("id"));
            RepairRequest repairRequest = repairRequestDAO.get(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("repairRequest", repairRequest);
            HttpSession session = req.getSession(false);
            Role userRole = (Role) session.getAttribute("role");
            req.setAttribute("role", userRole.getName());
            String currentLocale = (String) session.getAttribute("currentLocale");
            if (currentLocale == null) {
                req.setAttribute("currentLocale", "en");
            } else {
                req.setAttribute("currentLocale", currentLocale);
            }
        } catch (Exception ex) {
            //   System.out.println("exception repair request "+ ex.getMessage());
        }


        return Path.PAGE_UPDATE_REPAIR_REQUEST;
    }
}
