package com.my.web.filter;

import com.my.Path;
import com.my.db.model.Role;
import org.apache.logging.log4j.LogManager;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code CustomFilter} class represents filter realization.
 */

@WebFilter("/*")
public class CustomFilter implements Filter {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CustomFilter.class);
    private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
    private static List<String> outOfControl = new ArrayList<String>();

    static {
        outOfControl.add("login");
        outOfControl.add("insertUser");

        List<String> userRights = new ArrayList<>();
        userRights.add("newRepairRequest");
        userRights.add("repairRequest");
        userRights.add("insertRepairRequest");
        userRights.add("openCardRepairRequest");
        userRights.add("updateRepairRequest");
        userRights.add("insertReview");
        userRights.add("insertUser");
        userRights.add("openCardUser");
        userRights.add("updateCardUser");
        userRights.add("listRequests");
        userRights.add("mainPage");
        userRights.add("insertInvoiceBalance");
        userRights.add("deleteCardRepairRequest");

        accessMap.put(Role.USER, userRights);

        List<String> masterRights = new ArrayList<>();
        masterRights.add("openCardUser");
        masterRights.add("updateCardUser");
        masterRights.add("openCardRepairRequest");
        masterRights.add("listRequests");
        masterRights.add("updateRepairRequest");
        accessMap.put(Role.MASTER, masterRights);

        List<String> managerRights = new ArrayList<>();
        managerRights.add("openCardRepairRequest");
        managerRights.add("listRequests");
        managerRights.add("updateRepairRequest");
        managerRights.add("listUsers");
        managerRights.add("openCardUser");
        managerRights.add("updateCardUser");
        managerRights.add("sortListRequestsByDate");
        managerRights.add("sortListRequestsByCost");
        managerRights.add("sortListRequestsByStatus");
        managerRights.add("reports");
        managerRights.add("filtertListRequestsByStatus");
        managerRights.add("filtertListRequestsByMaster");
        managerRights.add("replenishInvoice");
        managerRights.add("insertInvoiceBalance");
        managerRights.add("formReportPDF");
        accessMap.put(Role.MANAGER, managerRights);

        List<String> adminRights = new ArrayList<>();
        adminRights.add("servicePage");
        adminRights.add("deleteMarkedUsers");
        adminRights.add("openCardRepairRequest");
        adminRights.add("listRequests");
        adminRights.add("updateRepairRequest");
        adminRights.add("insertUser");
        adminRights.add("deleteUser");
        adminRights.add("listUsers");
        adminRights.add("openCardUser");
        adminRights.add("updateCardUser");
        adminRights.add("reports");
        adminRights.add("sortListRequestsByDate");
        adminRights.add("sortListRequestsByCost");
        adminRights.add("sortListRequestsByStatus");
        adminRights.add("filtertListRequestsByStatus");
        adminRights.add("filtertListRequestsByMaster");
        adminRights.add("replenishInvoice");
        accessMap.put(Role.ADMIN, adminRights);
    }

    public CustomFilter() {

    }

    public void init(FilterConfig fConfig) throws ServletException {
       }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (accessAllowed(request)) {
               chain.doFilter(request, response);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessasge);
            request.setAttribute("href", "controller?command=listRequests");
            request.setAttribute("hrefName", "Repair requests");

            try {
                request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                        .forward(request, response);
            } catch (Exception ex) {
                log.debug("Filter exception " + ex.getMessage());
            }
        }
    }

    private boolean accessAllowed(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");

        if (commandName == null || commandName.isEmpty())
            return true;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null) {
            return false;
        }

         return accessMap.get(userRole).contains(commandName);
    }
}
