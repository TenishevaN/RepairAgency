package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code FilterListRequestsByStatusCommand} class represents the implementation of the command to filter list requests by status.
 */
public class FilterListRequestsByStatusCommand extends FilterUtil implements Command {

        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) {

            return doExecute(req, "status_id", "filtertListRequestsByStatus");
        }
    }