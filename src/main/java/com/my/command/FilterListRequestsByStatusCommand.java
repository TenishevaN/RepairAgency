package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterListRequestsByStatusCommand extends FilterUtil implements Command {

        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) {

            return doExecute(req, "status_id", "filtertListRequestsByStatus");
        }
    }