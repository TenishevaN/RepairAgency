package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@ code SortListRequestsByCostCommand} class represents the implementation of the command to sort list requests by cost.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class SortListRequestsByCostCommand extends SortUtil implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return doExecute(req, "cost", "sortListRequestsByCost");
    }
}


