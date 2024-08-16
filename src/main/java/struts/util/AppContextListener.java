package struts.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
	
	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        try {
	            String envFilePath = sce.getServletContext().getRealPath("/.env");
	            System.out.println("Attempting to load .env file from: " + envFilePath);
	            File envFile = new File(envFilePath);
	            if (!envFile.exists()) {
	                System.err.println("Error: .env file not found at " + envFilePath);
	                return;
	            }

	            EnvUtil.loadEnv(envFilePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
