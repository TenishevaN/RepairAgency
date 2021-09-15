package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterListRequestsByMasterCommand extends FilterUtil implements Command{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return doExecute(req, "master_id", "filtertListRequestsByMaster");
    }
}
