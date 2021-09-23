
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
           }

    public void contextInitialized(ServletContextEvent event) {

        // obtain file name with locales descriptions
        ServletContext context = event.getServletContext();
        String localesFileName = context.getInitParameter("locales");

        System.out.println("localesFileName " + localesFileName);

        // obtain reale path on server
        String localesFileRealPath = context.getRealPath(localesFileName);

        // locale descriptions
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
            System.out.println("locales " + locales);
        } catch (IOException e) {
           e.getMessage();
        }

        // save descriptions to servlet context
        context.setAttribute("locales", locales);
        locales.list(System.out);
        context.setAttribute("currentLocale", "en");
    }