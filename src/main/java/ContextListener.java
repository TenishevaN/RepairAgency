
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;

import com.my.db.dao.UserDAO;
import com.my.db.model.AccountLocalization;
import com.my.db.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * {@ code ContextListener} class represents context listener.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ContextListener implements ServletContextListener {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ContextListener.class);


    public void contextDestroyed(ServletContextEvent event) {

    }

    public void contextInitialized(ServletContextEvent event) {
        // obtain file name with locales descriptions
        ServletContext context = event.getServletContext();
        String localesFileName = context.getInitParameter("locales");

        // obtain reale path on server
        String localesFileRealPath = context.getRealPath(localesFileName);

        // locale descriptions
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            log.debug(" context listener initialize locale exception  " + e.getMessage());
        }

        // save descriptions to servlet context
        context.setAttribute("locales", locales);
        context.setAttribute("currentLocale", "en");
        Map<User, List<AccountLocalization>> listMasters = getMasterList();
        context.setAttribute("listMasters", listMasters);
        context.setAttribute("command", "start");

    }

    public static Map<User, List<AccountLocalization>> getMasterList() {
        log.debug(" context listener master list get");
        Map<User, List<AccountLocalization>> masters = new HashMap<>();
        try {
            UserDAO userDAO = new UserDAO("jdbc:mysql://localhost:3306/db_repair_agency");
            masters = userDAO.getMasterList("jdbc:mysql://localhost:3306/db_repair_agency");
            log.debug(" context listener master list {}",masters);
        } catch (Exception e) {
            log.debug(" context listener initialize master list exception  {}",e.getMessage());
        }
        return masters;
    }
}
