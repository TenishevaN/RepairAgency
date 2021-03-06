package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@ code SortListRequestsByDateCommand} class represents the implementation of the command to sort list requests by date.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class SortListRequestsByDateCommand extends SortUtil implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return doExecute(req, "date", "sortListRequestsByDate");

    }
}
