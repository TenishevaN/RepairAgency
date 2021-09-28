package com.my.command;

import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code PaginationUtil} class represents the service class to implement painations on the page.
 */
public class PaginationUtil {

    protected int start;
    protected int total = 5;
    protected String pageParameter;

    protected void SetPaginationInitialParameters(HttpServletRequest req) {

        start = 0;
       pageParameter = req.getParameter("page");
        if (pageParameter != null) {
            start = Integer.parseInt(req.getParameter("page"));
        }
        if (start != 0) {
            start = start - 1;
            if (start != 0) {
                start = start * total;
            }
        }
    }

    protected void setPaginationSessionParameters(HttpServletRequest req, User currentUser, String command) {

        req.setAttribute("userId", currentUser.getId());
        req.setAttribute("page", 1);
        req.setAttribute("command", command);
    }
}
