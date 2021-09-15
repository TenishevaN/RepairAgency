package com.my.command;

import com.my.Path;
import com.my.db.dao.ReviewDAO;
import com.my.db.model.Review;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        Review review = new Review();
        review.setRepairRequestId(Integer.parseInt(req.getParameter("idRepairRequest")));
        review.setComment(req.getParameter("comment"));
        try {
            ReviewDAO reviewDAO = new ReviewDAO();
            reviewDAO.insert(review);
        } catch (Exception ex) {
        //   log.info("exception "+ex.getMessage());
        }

        return Path.COMMAND_OPEN_REPAIR_REQUEST_BY_ID +req.getParameter("idRepairRequest");
    }
}
