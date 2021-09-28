package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code SortListRequestsByStatusCommand} class represents the implementation of the command to sort list requests by status.
 */
public class SortListRequestsByStatusCommand extends SortUtil implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return doExecute(req, "status_name", "sortListRequestsByStatus");
    }
}
