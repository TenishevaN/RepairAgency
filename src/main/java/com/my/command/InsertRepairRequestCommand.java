package com.my.command;

import com.my.Path;
import com.my.db.dao.RepairRequestDAO;
import com.my.db.model.RepairRequest;
import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertRepairRequestCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
            RepairRequest repairRequest = new RepairRequest();

            User user = (User) req.getSession().getAttribute("user");
            repairRequest.setUserId(user.getId());
            repairRequest.setDescription(req.getParameter("description"));
            repairRequestDAO.insert(repairRequest);

        } catch (Exception ex) {
            //     log
        }
        return Path.COMMAND_LIST_REQUESTS;
    }

}
