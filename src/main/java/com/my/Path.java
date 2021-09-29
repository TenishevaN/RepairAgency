package com.my;

/**
 * {@ code Controller} class represents path holder (jsp pages, controller commands).
 * It's the only one servlet that depends on the request command parameter executes the corresponding actions.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public final class Path {

    // pages
    public static final String COMMAND_MAIN_PAGE = "mainPage.jsp";
    public static final String PAGE_ERROR_PAGE = "error.jsp";
    public static final String PAGE_UPDATE_REPAIR_REQUEST = "updateRepairRequest.jsp";
    public static final String PAGE_USER = "cardUser.jsp";
    public static final String PAGE_USER_LIST = "listUsers.jsp";
    public static final String REPORTS = "reports.jsp";
    public static final String SERVICE_PAGE = "servicePage.jsp";
    public static String PAGE_LIST_REPAIR_REQUESTS = "listRepairRequests.jsp";

    // commands
    public static final String COMMAND_LIST_USERS = "controller?command=listUsers";
    public static final String COMMAND_LIST_REQUESTS = "controller?command=listRequests";
    public static final String COMMAND_OPEN_REPAIR_REQUEST_BY_ID = "controller?command=openCardRepairRequest&id=";
    public static final String COMMAND_OPEN_USER_BY_ID = "controller?command=openCardUser&id=";
    public static final String COMMAND_REPORTS = "controller?command=reports";
}