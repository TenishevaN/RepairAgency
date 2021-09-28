package com.my.command;

import com.my.db.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * {@ code PaginationUtil} class represents the service class to implement painations on the page.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class PaginationUtil {

    protected String pageParameter;
    public static int start;
    public static int total = 5;

    public void SetPaginationInitialParameters(HttpServletRequest req) {

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

    public void setPaginationSessionParameters(HttpServletRequest req, User currentUser, String command) {

        req.setAttribute("userId", currentUser.getId());
        req.setAttribute("page", 1);
        req.setAttribute("command", command);
    }

    public void setPaginationSessionParameterPage(HttpServletRequest req) {

         if (pageParameter != null) {
            req.setAttribute("page", pageParameter);
        } else {
            req.setAttribute("page", 1);
        }
    }
}
