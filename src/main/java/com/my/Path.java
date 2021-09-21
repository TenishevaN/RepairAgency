package com.my;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author N.Tenisheva
 * 
 */
public final class Path {
	
	// pages
	public static final String COMMAND_MAIN_PAGE = "mainPage.jsp";
	public static final String PAGE_ERROR_PAGE = "error.jsp";
	public static final String PAGE_UPDATE_REPAIR_REQUEST = "updateRepairRequest.jsp";
	public static final String PAGE_USER = "cardUser.jsp";
	public static final String PAGE_USER_LIST =  "listUsers.jsp";
	public static final String REPORTS = "reports.jsp";
	public static String PAGE_LIST_REPAIR_REQUESTS = "listRepairRequests.jsp";


	// commands
	public static final String COMMAND_LIST_USERS = "controller?command=listUsers";
	public static final String COMMAND_LIST_REQUESTS = "controller?command=listRequests";
	public static final String COMMAND_OPEN_REPAIR_REQUEST_BY_ID = "controller?command=openCardRepairRequest&id=";
	public static final String COMMAND_OPEN_USER_BY_ID = "controller?command=openCardUser&id=";



}