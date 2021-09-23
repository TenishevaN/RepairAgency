package web_jstl;

import com.my.db.dao.RepairRequestDAO;
import com.my.db.dao.UserDAO;
import com.my.db.model.User;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PaginationPageTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PaginationPageTag.class);
    int idUser;
    String command;
    String orderBy;
    String status_id;
    String master_id;
    int current_page;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    @Override
    public void doTag() throws JspException {

        String output = "";
        JspWriter out = getJspContext().getOut();

        int page = getPageCount();
        if (page > 1) {
            output += "<ul class = form-inline>";
            for (int i = 1; i <= page; i++) {
                output += "<div>";
                output += "<form action = controller method = get >";
                output += "<input type = hidden name = command  value = " + command + ">";
                output += "<input type = hidden name = status_id  value = " + status_id + ">";
                output += "<input type = hidden name = master_id  value = " + master_id + ">";
                output += "<input type = hidden name = changeOrder class = form-control value = false>";
                output += "<input type = hidden class = form-control name = page value = " + i + " >";
                output += "<input type = hidden class = form-control name = orderBy value = " + orderBy + " >";
                if (current_page == i){
                    output += "<input type = submit style = color:blue;border: 2px solid #eee value = " + i + "><br>";
                } else {
                    output += "<input type = submit  value = " + i + "><br>";
                }
                output += "</form>";
                output += "</div>";
            }
            output += "</ul>";
        }
         try {
            out.println(output);
        } catch (IOException e) {
            log.debug("PaginationPageTag exception " + e.getMessage());
        }
    }

    private int getPageCount() {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(idUser);
        System.out.println(user);
        RepairRequestDAO repairRequestDAO = new RepairRequestDAO();
        if ("user".equals(user.getRoleName(idUser))) {
            return calculateCount(repairRequestDAO.getCountOfAllRequestsDyUserId(idUser));
        } else {
            return calculateTotalPagesNotForUser(repairRequestDAO);
        }
    }

    private int calculateTotalPagesNotForUser(RepairRequestDAO repairRequestDAO) {

        if ("-1".equals(status_id) || "-1".equals(master_id)){
            return calculateCount(repairRequestDAO.getCountOfAllRequests());
        }

        if ("filtertListRequestsByStatus".equals(command)) {
            return calculateCount((int) repairRequestDAO.getCountOfAllRequestsByStatus(status_id));
        }
        if ("filtertListRequestsByMaster".equals(command)) {
            return calculateCount((int) repairRequestDAO.getCountOfAllRequestsByMaster(master_id));
        }
            return calculateCount(repairRequestDAO.getCountOfAllRequests());
    }

    private int calculateCount(int count) {
        int totalCount = count;
        int totalPages = totalCount / 5;

        if (totalCount % 5 > 0) {
            totalPages++;
        }
         return totalPages;
    }
}
